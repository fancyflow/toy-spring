package com.chaoching.springframework.core.convert.converter;

/**
 * 类型转换抽象接口
 * @param <S>
 * @param <T>
 */
public interface Converter<S, T> {
    T convert(S source);
}
