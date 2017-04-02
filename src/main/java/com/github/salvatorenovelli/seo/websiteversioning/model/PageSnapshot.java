package com.github.salvatorenovelli.seo.websiteversioning.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PageSnapshot {
    private final String title;
    private final Document page;

    public PageSnapshot(Document page) {
        title = page.title();
        this.page = page;
    }

    public List<String> getTagContents(String tag) {
        return StreamSupport.stream(page.select(tag).spliterator(), false)
                .map(Element::html).collect(Collectors.toList());
    }

    public String getTitle() {
        return title;
    }


}
