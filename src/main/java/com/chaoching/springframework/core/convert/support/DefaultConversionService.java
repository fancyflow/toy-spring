package com.chaoching.springframework.core.convert.support;

import com.chaoching.springframework.core.convert.converter.ConverterRegistry;

public class DefaultConversionService extends GenericConversionService {
    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
        // TODO 添加其它ConverterFactory
    }

    public DefaultConversionService() {
        addDefaultConverters(this);
    }
}
