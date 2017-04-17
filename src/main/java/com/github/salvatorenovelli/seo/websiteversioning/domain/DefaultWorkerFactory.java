package com.github.salvatorenovelli.seo.websiteversioning.domain;

import com.github.salvatorenovelli.seo.websiteversioning.crawler.Worker;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DefaultWorkerFactory implements WorkerFactory {

    private final CrawlerFactory crawlerFactory;
    private final AtomicInteger id = new AtomicInteger(0);

    public DefaultWorkerFactory(CrawlerFactory crawlerFactory) {
        this.crawlerFactory = crawlerFactory;
    }

    @Override
    public Worker createWorker() {
        return new Worker(String.valueOf(id.getAndIncrement()), crawlerFactory.createCrawler());
    }
}
