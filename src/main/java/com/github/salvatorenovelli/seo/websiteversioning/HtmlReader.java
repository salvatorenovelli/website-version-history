package com.github.salvatorenovelli.seo.websiteversioning;


import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;

import java.io.IOException;
import java.net.URI;

public class HtmlReader {


    private final HttpClient httpClient;

    public HtmlReader(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public PageSnapshot snapshotPage(URI s) throws IOException {
        return new PageSnapshot(s, httpClient.get(s));
    }
}
