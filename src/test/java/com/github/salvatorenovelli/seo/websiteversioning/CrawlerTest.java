package com.github.salvatorenovelli.seo.websiteversioning;

import com.github.salvatorenovelli.seo.websiteversioning.config.Constants;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

public class CrawlerTest {


    @Test
    @Ignore
    public void shouldNotVisitOutsideMainDomain() throws Exception {

        int numberOfCrawlers = 7;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(Constants.DATA_STORE_DIR.getAbsolutePath());

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("http://www.example.it");


        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(new MyCrawlerFactory("http://www.example.it"), numberOfCrawlers);
    }


    class MyCrawlerFactory implements CrawlController.WebCrawlerFactory<Crawler> {


        private final String baseUrl;

        public MyCrawlerFactory(String baseUrl) {
            this.baseUrl = baseUrl;

        }

        @Override
        public Crawler newInstance() {
            return new Crawler(baseUrl, UUID.randomUUID().toString());
        }
    }

}