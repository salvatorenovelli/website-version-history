package com.github.salvatorenovelli.seo.websiteversioning.model;

import org.jsoup.nodes.Document;

public class PageSnapshot {
    private final String title;

    public PageSnapshot(Document page) {
        title = "";
    }

    public String getTitle() {
        return title;
    }
}
