package com.chaoching.springframework.context.support;

import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.chaoching.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.chaoching.springframework.beans.factory.config.BeanPostProcessor;
import com.chaoching.springframework.context.ApplicationEvent;
import com.chaoching.springframework.context.ApplicationListener;
import com.chaoching.springframework.context.ConfigurableApplicationContext;
import com.chaoching.springframework.context.event.ApplicationContextClosedEvent;
import com.chaoching.springframework.context.event.ApplicationContextRefreshedEvent;
import com.chaoching.springframework.context.event.ApplicationEventMulticaster;
import com.chaoching.springframework.context.event.SimpleApplicationEventMulticaster;
import com.chaoching.springframework.core.convert.ConversionService;
import com.chaoching.springframework.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象应用程序上下文
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";
    public static final String CONVERSION_SERVICE_BEAN_NAME = "conversionService";
    private ApplicationEventMulticaster applicationEventMulticaster;
    @Override
    public void refresh() throws BeansException {
        // 刷新容器前的准备工作
        this.prepareRefresh();

        // 创建BeanFactory，解析并注册bean的定义对象BeanDefinition信息
        ConfigurableListableBeanFactory beanFactory = this.obtainFreshBeanFactory();

        // 设置bean工厂的一些对象信息
        // 将ApplicationContext对象以BeanPostProcessor的形式注入到容器中，方便后续将其作为参数传入实现了
        // ApplicationContextAware接口类的对应方法中
        this.prepareBeanFactory(beanFactory);

        // 在bean实例化之前，执行BeanFactoryPostProcessor
        this.invokeBeanFactoryPostProcessors(beanFactory);

        // 提前注册BeanPostProcessor，方便后续对bean对象进行扩展
        this.registerBeanPostProcessors(beanFactory);

        // 初始化事件发布者
        this.initApplicationEventMulticaster();

        // 注册事件监听器
        this.registerListeners();

        // 提前实例化bean对象
        this.finishBeanFactoryInitialization(beanFactory);

        // 发布容器刷新完毕事件
        this.finishRefresh();
    }

    protected void prepareRefresh(){

    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        this.refreshBeanFactory();
        return this.getBeanFactory();
    }

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
    }

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    protected void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = this.getBeanFactory();
        this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.addSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
    }

    protected void registerListeners() {
        Collection<ApplicationListener> applicationListeners = this.getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener<?> applicationListener : applicationListeners) {
            this.applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        // 设置类型转换器
        if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME)) {
            Object conversionService = beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME);
            if (conversionService instanceof ConversionService) {
                beanFactory.setConversionService((ConversionService) conversionService);
            }
        }
        // 提前实例化bean对象
        beanFactory.preInstantiateSingletons();
    }

    protected void finishRefresh() {
        this.publishEvent(new ApplicationContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.applicationEventMulticaster.multicastEvent(event);
    }

    public void close() {
        this.doClose();
    }

    protected void doClose() {
        // 发布容器关闭事件
        this.publishEvent(new ApplicationContextClosedEvent(this));
        // 执行单例bean的销毁方法
        this.destroyBeans();
    }

    protected void destroyBeans() {
        this.getBeanFactory().destroySingletons();
    }

    public void registerShutdownHook() {
        Thread shutdownHook = new Thread(this::doClose);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return this.getBeanFactory().getBean(beanName);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return this.getBeanFactory().getBean(requiredType);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return this.getBeanFactory().getBean(beanName, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return this.getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> requiredType) {
        return this.getBeanFactory().getBeanNamesForType(requiredType);
    }

    @Override
    public boolean containsBean(String beanName) {
        return this.getBeanFactory().containsBean(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.getBeanFactory().getBeanDefinitionNames();
    }
}
