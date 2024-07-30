package com.chaoching.springframework.context.support;

import com.chaoching.springframework.beans.factory.FactoryBean;
import com.chaoching.springframework.beans.factory.InitializingBean;
import com.chaoching.springframework.core.convert.ConversionService;
import com.chaoching.springframework.core.convert.converter.Converter;
import com.chaoching.springframework.core.convert.converter.ConverterFactory;
import com.chaoching.springframework.core.convert.converter.ConverterRegistry;
import com.chaoching.springframework.core.convert.converter.GenericConverter;
import com.chaoching.springframework.core.convert.support.DefaultConversionService;
import com.chaoching.springframework.core.convert.support.GenericConversionService;

import java.util.Set;

public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {
    private Set<?> converters;
    private GenericConversionService conversionService;

    @Override
    public ConversionService getObject() throws Exception {
        return this.conversionService;
    }

    @Override
    public Class<ConversionService> getObjectType() {
        return ConversionService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.conversionService = new DefaultConversionService();
        this.registerConverters(this.converters, this.conversionService);
    }

    private void registerConverters(Set<?> converters, ConverterRegistry registry) {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    registry.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    registry.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else {
                    throw new IllegalArgumentException("Each converter object must implement one of the " +
                            "Converter, ConverterFactory, or GenericConverter interfaces");
                }
            }
        }
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }
}
