package com.github.salvatorenovelli.seo.websiteversioning.crawler.crawler4j;

import com.github.salvatorenovelli.seo.websiteversioning.crawler.Crawler;
import edu.uci.ics.crawler4j.crawler.CrawlController;

public class Crawler4JCrawler implements Crawler {

    public static final int NUMBER_OF_THREAD = 1;
    private final CrawlController controller;
    private final CrawlerFactoryFactory crawlerFactory;

    public Crawler4JCrawler(CrawlController controller, CrawlerFactoryFactory crawlerFactory) {
        this.controller = controller;
        this.crawlerFactory = crawlerFactory;
    }

    @Override
    public void startCrawling(String url) {
        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed(url);

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */

        controller.startNonBlocking(crawlerFactory.factoryForUrl(url), NUMBER_OF_THREAD);
    }
}

