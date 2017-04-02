package com.github.salvatorenovelli.seo.websiteversioning.model;

import lombok.Getter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Getter
public class PageSnapshot implements Serializable {
    private final URI uri;
    private final String title;
    private final List<String> h1s;
    private final List<String> h2s;
    private final List<String> metaDescriptions;
    private final List<String> canonicals;

    public PageSnapshot(URI uri, Document page) {
        this.uri = removeFragment(uri);
        this.title = page.title();
        this.h1s = getTagContents(page, "H1");
        this.h2s = getTagContents(page, "H2");
        this.metaDescriptions = extractFromTag(page.head(), "meta[name=\"description\"]", element -> element.attr("content"));
        this.canonicals = extractFromTag(page.head(), "link[rel=\"canonical\"]", element -> element.attr("href"));
    }

    private URI removeFragment(URI uri) {
        return URI.create(uri.toString().split("#")[0]);
    }

    private List<String> extractFromTag(Element element, String filter, Function<Element, String> mapper) {
        return StreamSupport.stream(element.select(filter).spliterator(), false)
                .map(mapper).collect(Collectors.toList());
    }

    private List<String> getTagContents(Element page, String tag) {
        return extractFromTag(page, tag, Element::html);
    }
}
