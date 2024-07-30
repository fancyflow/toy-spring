package com.chaoching.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.chaoching.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.chaoching.springframework.beans.factory.config.BeanDefinition;
import com.chaoching.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.chaoching.springframework.stereotype.Component;

import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
	public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "com.chaoching.springframework.beans.factory.annotation.internalAutowiredAnnotationProcessor";
	private BeanDefinitionRegistry beanDefinitionRegistry;

	public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
		this.beanDefinitionRegistry = registry;
	}

	public void doScan(String... basePackages) {
		for (String basePackage : basePackages) {
			Set<BeanDefinition> candidates = this.findCandidateComponents(basePackage);
			for (BeanDefinition candidate : candidates) {
				// 解析bean的作用域
				String beanScope = this.resolveBeanScope(candidate);
				if (StrUtil.isNotEmpty(beanScope)) {
					candidate.setScope(beanScope);
				}
				//生成bean的名称
				String beanName = this.determineBeanName(candidate);
				//注册BeanDefinition
				this.beanDefinitionRegistry.registerBeanDefinition(beanName, candidate);
			}
		}
		// 添加处理@Value和@Autowired注解的BeanPostProcessor
		this.beanDefinitionRegistry.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
	}

	/**
	 * 获取bean的作用域
	 *
	 * @param beanDefinition
	 * @return
	 */
	private String resolveBeanScope(BeanDefinition beanDefinition) {
		Class<?> beanClass = beanDefinition.getBeanClass();
		Scope scope = beanClass.getAnnotation(Scope.class);
		if (scope != null) {
			return scope.value();
		}
		return StrUtil.EMPTY;
	}


	/**
	 * 生成bean的名称
	 *
	 * @param beanDefinition
	 * @return
	 */
	private String determineBeanName(BeanDefinition beanDefinition) {
		Class<?> beanClass = beanDefinition.getBeanClass();
		Component component = beanClass.getAnnotation(Component.class);
		String value = component.value();
		if (StrUtil.isEmpty(value)) {
			value = StrUtil.lowerFirst(beanClass.getSimpleName());
		}
		return value;
	}
}