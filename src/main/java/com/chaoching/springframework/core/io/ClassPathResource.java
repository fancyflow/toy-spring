package com.chaoching.springframework.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * classpath路径下的资源
 */
public class ClassPathResource implements Resource {
    private final String path;

    public ClassPathResource(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(this.path);
        if (stream == null) {
            throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
        }
        return stream;
    }
}
