package com.chaoching.springframework.core.convert.support;

import cn.hutool.core.util.StrUtil;
import com.chaoching.springframework.core.convert.converter.Converter;
import com.chaoching.springframework.core.convert.converter.ConverterFactory;

import java.math.BigDecimal;

public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {
    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<>(targetType);
    }

    private static final class StringToNumber<T extends Number> implements Converter<String, T> {
        private final Class<T> targetType;

        private StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            if (StrUtil.isEmpty(source)) {
                return null;
            } else if (this.targetType.equals(Integer.class)) {
                return (T) Integer.valueOf(source);
            } else if (this.targetType.equals(Long.class)) {
                return (T) Long.valueOf(source);
            } else if (this.targetType.equals(BigDecimal.class)) {
                return (T) new BigDecimal(source);
            }
            // TODO 其它数字类型
            else {
                throw new IllegalArgumentException("Cannot convert String [" + source + "] to target class [" + this.targetType.getName() + "]");
            }
        }
    }
}
