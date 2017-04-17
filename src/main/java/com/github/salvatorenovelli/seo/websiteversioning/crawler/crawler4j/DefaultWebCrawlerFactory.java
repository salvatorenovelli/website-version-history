package com.github.salvatorenovelli.seo.websiteversioning.crawler.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@EqualsAndHashCode
@ToString
class DefaultWebCrawlerFactory implements CrawlController.WebCrawlerFactory<DefaultWebCrawler> {

    private final String baseUrl;

    DefaultWebCrawlerFactory(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public DefaultWebCrawler newInstance() throws Exception {
        return new DefaultWebCrawler(baseUrl, UUID.randomUUID().toString());
    }

}
