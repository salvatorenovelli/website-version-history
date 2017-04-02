package com.github.salvatorenovelli.seo.websiteversioning;

import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PageStore {
    private final Path basePath;

    public PageStore(Path basePath) {
        this.basePath = basePath;
    }

    public void storePage(URI requestUri, PageSnapshot pageSnapshot) throws IOException {
        String path = extractPath(requestUri);
        String lastPathSegment = extractLastPathSegment(requestUri);

        Path baseResourcePath = basePath.resolve("." + path);


        FileUtils.forceMkdir(baseResourcePath.toFile());
        FileUtils.touch(baseResourcePath.resolve(lastPathSegment.hashCode() + ".json").toFile());
    }

    private String extractPath(URI requestUri) {
        String path = requestUri.getPath();
        return path.substring(0, path.lastIndexOf("/"));
    }

    private String extractLastPathSegment(URI uri) {
        String[] segments = uri.getPath().split("/");
        return segments[segments.length - 1];
    }


}
