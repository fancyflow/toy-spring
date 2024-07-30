package com.chaoching.springframework.context;

import com.chaoching.springframework.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * 刷新容器
     * @throws BeansException
     */
    void refresh() throws BeansException;

    /**
     * 关闭应用程序上下文
     */
    void close();

    /**
     * 向JVM注册一个钩子方法，在JVM关闭之前执行关闭容器操作
     */
    void registerShutdownHook();
}
