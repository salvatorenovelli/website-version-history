package com.github.salvatorenovelli.seo.websiteversioning.crawler;


import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Worker {
    private final String id;

    public Worker(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void startCrawling(String url) {

    }
}