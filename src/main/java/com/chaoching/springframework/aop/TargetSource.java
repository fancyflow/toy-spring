package com.chaoching.springframework.aop;

public class TargetSource {
    // 需要生成代理对象的目标对象
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Object getTarget() {
        return this.target;
    }

    public Class<?>[] getTargetClass() {
        return this.target.getClass().getInterfaces();
    }
}
