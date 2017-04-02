package com.github.salvatorenovelli.seo.websiteversioning.model;

import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
public class PageSnapshot implements Serializable {
    private final String title;
    private final List<String> h1s;
    private final List<String> h2s;

    public PageSnapshot(Document page) {
        title = page.title();
        h1s = getTagContents(page, "H1");
        h2s = getTagContents(page, "H2");
    }

    private List<String> getTagContents(Document page, String tag) {
        return StreamSupport.stream(page.select(tag).spliterator(), false)
                .map(Element::html).collect(Collectors.toList());
    }

    public List<String> getH2s() {
        return h2s;
    }
}
