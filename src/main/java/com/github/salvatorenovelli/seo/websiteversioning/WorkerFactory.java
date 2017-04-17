package com.github.salvatorenovelli.seo.websiteversioning;

import com.github.salvatorenovelli.seo.websiteversioning.crawler.Worker;

import java.util.concurrent.atomic.AtomicInteger;

public class WorkerFactory {

    private AtomicInteger id = new AtomicInteger(0);

    public Worker createWorker() {
        return new Worker(String.valueOf(id.getAndIncrement()));
    }
}
