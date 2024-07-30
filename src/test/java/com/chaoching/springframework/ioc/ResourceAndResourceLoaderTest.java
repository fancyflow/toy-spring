package com.chaoching.springframework.ioc;

import cn.hutool.core.io.IoUtil;
import com.chaoching.springframework.core.io.DefaultResourceLoader;
import com.chaoching.springframework.core.io.FileSystemResource;
import com.chaoching.springframework.core.io.Resource;
import com.chaoching.springframework.core.io.UrlResource;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ResourceAndResourceLoaderTest {
    @Test
    public void testResourceAndResourceLoader() throws IOException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

        //加载classpath下的资源
        Resource resource = resourceLoader.getResource("classpath:a.txt");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
        Assertions.assertThat(content).isEqualTo("test");

        //加载文件系统资源
        resource = resourceLoader.getResource("src/main/resources/a.txt");
        Assertions.assertThat(resource instanceof FileSystemResource).isTrue();
        inputStream = resource.getInputStream();
        content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
        Assertions.assertThat(content).isEqualTo("test");

        //加载url资源
        resource = resourceLoader.getResource("https://www.baidu.com");
        Assertions.assertThat(resource instanceof UrlResource).isTrue();
        inputStream = resource.getInputStream();
        content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }
}
