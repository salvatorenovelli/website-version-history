package com.github.salvatorenovelli.seo.websiteversioning.domain;

import com.github.salvatorenovelli.seo.websiteversioning.crawler.Worker;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.crawler4j.Crawler4JCrawlerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DefaultWorkerFactory implements WorkerFactory {

    private final Crawler4JCrawlerFactory crawlerFactory;
    private final AtomicInteger id = new AtomicInteger(0);

    public DefaultWorkerFactory(Crawler4JCrawlerFactory crawlerFactory) {
        this.crawlerFactory = crawlerFactory;
    }

    @Override
    public Worker createWorker() {
        return new Worker(String.valueOf(id.getAndIncrement()), crawlerFactory.createCrawler());
    }
}
