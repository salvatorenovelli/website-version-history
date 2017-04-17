package com.github.salvatorenovelli.seo.websiteversioning.domain;

import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

public class PageStore {
    private final Path basePath;
    private final PageSnapshotSerializer snapShotSerializer;

    public PageStore(Path basePath, PageSnapshotSerializer snapShotSerializer) {
        this.basePath = basePath;
        this.snapShotSerializer = snapShotSerializer;
    }

    public void storePage(PageSnapshot pageSnapshot) throws IOException {

        URI requestUri = pageSnapshot.getUri();
        String path = extractPath(requestUri);
        String lastPathSegment = extractLastPathSegment(requestUri);
        Path baseResourcePath = basePath.resolve(path);
        FileUtils.forceMkdir(baseResourcePath.toFile());

        Path filePath = baseResourcePath.resolve(lastPathSegment.hashCode() + ".json");
        snapShotSerializer.serialize(pageSnapshot, filePath);
    }

    private String extractPath(URI requestUri) {
        String path = requestUri.getPath();
        int endIndex = path.lastIndexOf("/");
        if (endIndex > 0) {
            return path.substring(1, endIndex);
        } else {
            return "";
        }
    }

    private String extractLastPathSegment(URI uri) {
        String[] segments = uri.getPath().split("/");
        return segments[segments.length - 1];
    }


}
