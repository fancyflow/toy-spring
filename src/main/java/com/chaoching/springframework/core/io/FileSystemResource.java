package com.chaoching.springframework.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemResource implements Resource {
    private final String filePath;

    public FileSystemResource(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        Path path = new File(this.filePath).toPath();
        return Files.newInputStream(path);
    }
}
