package com.github.salvatorenovelli.seo.websiteversioning.crawler;


import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class Worker {


    private final String id;
    private final Crawler crawler;

    public Worker(String id, Crawler crawler) {
        this.id = id;
        this.crawler = crawler;
    }

    public String getId() {
        return id;
    }

    public void startCrawling(String url) {
        crawler.startCrawling(url);
    }
}