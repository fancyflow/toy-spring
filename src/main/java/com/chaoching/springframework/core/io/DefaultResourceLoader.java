package com.chaoching.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader {
    public static final String CLASSPATH_URL_PREFIX = "classpath:";
    @Override
    public Resource getResource(String location) {
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            // classpath下的资源
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        }
        try {
            // 尝试当成url处理
            URL url = new URL(location);
            return new UrlResource(url);
        } catch (MalformedURLException e) {
            // 当成文件系统下的资源进行处理
            return new FileSystemResource(location);
        }
    }
}
