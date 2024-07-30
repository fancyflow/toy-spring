package com.chaoching.springframework.beans.factory.config;

/**
 * 处理配置文件中的占位符
 */
public abstract class PlaceholderConfigurerSupport implements BeanFactoryPostProcessor {
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    protected String placeholderPrefix;
    protected String placeholderSuffix;

    public PlaceholderConfigurerSupport() {
        this.placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX;
        this.placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX;
    }

    public void setPlaceholderPrefix(String placeholderPrefix) {
        this.placeholderPrefix = placeholderPrefix;
    }

    public void setPlaceholderSuffix(String placeholderSuffix) {
        this.placeholderSuffix = placeholderSuffix;
    }
}
