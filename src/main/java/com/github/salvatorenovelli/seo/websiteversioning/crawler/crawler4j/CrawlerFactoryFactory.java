package com.github.salvatorenovelli.seo.websiteversioning.crawler.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.springframework.stereotype.Component;


/**
 * As the edu.uci.ics.crawler4j.crawler.CrawlController api makes little sense to me, this is a factory of factory!
 */
@Component
class CrawlerFactoryFactory {
    public CrawlController.WebCrawlerFactory<DefaultWebCrawler> factoryForUrl(String baseUrl) {
        return new DefaultWebCrawlerFactory(baseUrl);
    }
}


