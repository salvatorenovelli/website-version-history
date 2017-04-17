package com.github.salvatorenovelli.seo.websiteversioning;

import com.github.salvatorenovelli.seo.websiteversioning.crawler.Worker;

public interface WorkerFactory {
    Worker createWorker();
}
