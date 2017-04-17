package com.github.salvatorenovelli.seo.websiteversioning.domain;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;

import java.io.IOException;
import java.nio.file.Path;

public class PageSnapshotSerializer {

    private final ObjectWriter objectWriter;

    public PageSnapshotSerializer(ObjectWriter writer) {
        this.objectWriter = writer;
    }

    public void serialize(PageSnapshot pageSnapshot, Path filePath) throws IOException {
        objectWriter.writeValue(filePath.toFile(), pageSnapshot);
    }
}
