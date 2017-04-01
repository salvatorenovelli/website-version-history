package com.github.salvatorenovelli.seo.websiteversioning.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PageSnapshot {
    private final String title;
    private Elements h1;

    public PageSnapshot(Document page) {
        title = page.title();
        h1 = page.select("H1");
    }

    public String getTitle() {
        return title;
    }

    public List<String> getH1() {
        return StreamSupport.stream(h1.spliterator(), false)
                .map(Element::html).collect(Collectors.toList());
    }
}
