package com.chaoching.springframework.aop.aspectj;

import com.chaoching.springframework.aop.Pointcut;
import com.chaoching.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    private Advice advice;
    private String expression;
    private AspectJExpressionPointcut pointcut;

    public AspectJExpressionPointcutAdvisor() {

    }

    public AspectJExpressionPointcutAdvisor(String expression) {
        this.expression = expression;
        this.pointcut = new AspectJExpressionPointcut(expression);
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (this.pointcut == null) {
            this.pointcut = new AspectJExpressionPointcut(this.expression);
        }
        return this.pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
