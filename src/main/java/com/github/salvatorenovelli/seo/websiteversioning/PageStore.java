package com.github.salvatorenovelli.seo.websiteversioning;

import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

public class PageStore {
    private final Path basePath;

    public PageStore(Path basePath) {
        this.basePath = basePath;
    }

    public void storePage(URI requestUri, PageSnapshot pageSnapshot) throws IOException {
        FileUtils.forceMkdir(basePath.resolve("." + requestUri.getPath()).toFile());
    }


}
