package com.github.salvatorenovelli.seo.websiteversioning.crawler.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Crawler4JCrawlerTest {


    CrawlerFactoryFactory crawlerFactory = new CrawlerFactoryFactory();
    Crawler4JCrawler crawler;
    @Mock private CrawlController controller;

    @Before
    public void setUp() throws Exception {
        crawler = new Crawler4JCrawler(controller, crawlerFactory);
    }

    @Test
    public void startCrawling() throws Exception {
        crawler.startCrawling("testUrl");
        Mockito.verify(controller).startNonBlocking(crawlerFactory.factoryForUrl("testUrl"), 1);
    }

}