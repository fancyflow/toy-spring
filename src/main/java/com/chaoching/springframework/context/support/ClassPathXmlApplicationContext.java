package com.chaoching.springframework.context.support;

import com.chaoching.springframework.beans.BeansException;

/**
 * xml文件的应用程序上下文
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
    private String[] configLocations;

    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        this(new String[] {configLocation});
    }

    /**
     * 从xml文件中解析并注册bean对象的定义类BeanDefinition信息
     * @param configLocations
     * @throws BeansException
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.setConfigLocations(configLocations);
        this.refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return this.configLocations;
    }

    public void setConfigLocations(String[] configLocations) {
        this.configLocations = configLocations;
    }
}
