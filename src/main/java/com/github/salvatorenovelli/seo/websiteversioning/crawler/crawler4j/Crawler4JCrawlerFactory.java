package com.github.salvatorenovelli.seo.websiteversioning.crawler.crawler4j;

import com.github.salvatorenovelli.seo.websiteversioning.config.Constants;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.Crawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.stereotype.Component;

@Component
public class Crawler4JCrawlerFactory {

    private final CrawlerFactoryFactory crawlerFactory;

    public Crawler4JCrawlerFactory(CrawlerFactoryFactory crawlerFactory) {
        this.crawlerFactory = crawlerFactory;
    }

    public Crawler createCrawler() {

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(Constants.DATA_STORE_DIR.getAbsolutePath());

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = null;
        try {
            controller = new CrawlController(config, pageFetcher, robotstxtServer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return new Crawler4JCrawler(controller, crawlerFactory);
    }

}
