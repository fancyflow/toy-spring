package com.chaoching.springframework.ioc;

import com.chaoching.springframework.core.convert.converter.Converter;
import com.chaoching.springframework.core.convert.support.StringToNumberConverterFactory;
import org.junit.Test;

import java.math.BigDecimal;

public class ConverterFactoryTest {
    @Test
    public void testConverterFactory() {
        StringToNumberConverterFactory stringToNumberConverterFactory = new StringToNumberConverterFactory();
        Converter<String, BigDecimal> converter = stringToNumberConverterFactory.getConverter(BigDecimal.class);
        BigDecimal convert = converter.convert("12000000000000000000000000000000000000000000000000000000000000");
        System.out.println(convert);
    }
}
