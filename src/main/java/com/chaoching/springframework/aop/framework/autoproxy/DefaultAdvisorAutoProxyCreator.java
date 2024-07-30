package com.chaoching.springframework.aop.framework.autoproxy;

import com.chaoching.springframework.aop.Advisor;
import com.chaoching.springframework.aop.ClassFilter;
import com.chaoching.springframework.aop.Pointcut;
import com.chaoching.springframework.aop.TargetSource;
import com.chaoching.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.chaoching.springframework.aop.framework.ProxyFactory;
import com.chaoching.springframework.beans.BeansException;
import com.chaoching.springframework.beans.PropertyValues;
import com.chaoching.springframework.beans.factory.BeanFactory;
import com.chaoching.springframework.beans.factory.BeanFactoryAware;
import com.chaoching.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.chaoching.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;

import java.util.Collection;

public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private DefaultListableBeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 避免死循环？
        if (this.isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        try {
            ProxyFactory proxyFactory = new ProxyFactory();
            Collection<AspectJExpressionPointcutAdvisor> advisors = this.beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
            for (AspectJExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(bean.getClass())) {
                    if (proxyFactory.getTargetSource() == null) {
                        proxyFactory.setTargetSource(new TargetSource(bean));
                    }
                    proxyFactory.addAdvisor(advisor);
                }
            }
            if (!proxyFactory.getAdvisors().isEmpty()) {
                return proxyFactory.getProxy();
            }
        } catch (Exception e) {
            throw new BeansException("Error create proxy bean for: " + beanName, e);
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }
}
