package com.github.salvatorenovelli.seo.websiteversioning.domain;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;

public class HttpClient {
    public Document get(URI path) throws IOException {
            Connection connect = Jsoup.connect(path.toASCIIString());
            return connect.get();
    }
}
