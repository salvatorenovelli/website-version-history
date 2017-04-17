package com.github.salvatorenovelli.seo.websiteversioning;

import com.github.salvatorenovelli.seo.websiteversioning.config.Constants;
import com.github.salvatorenovelli.seo.websiteversioning.domain.PatternGenerator;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;
import java.util.regex.Pattern;

public class CrawlerTest {


    @Test
@Ignore
    public void shouldNotVisitOutsideMainDomain() throws Exception {

        int numberOfCrawlers = 1;

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


    class Crawler extends WebCrawler {

        private final Pattern patter;
        private final String id;

        public Crawler(String baseDomain, String id) {
            patter = PatternGenerator.generatePatternFor(baseDomain);
            this.id = id;
        }

        @Override
        public boolean shouldVisit(Page referringPage, WebURL url) {
            return patter.matcher(url.getURL()).matches();
        }

        @Override
        public void visit(Page page) {
            String url = page.getWebURL().getURL();
            System.out.println("URL: " + url);
//
//        if (page.getParseData() instanceof HtmlParseData) {
//            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
//            String text = htmlParseData.getText();
//            String html = htmlParseData.getHtml();
//            Set<WebURL> links = htmlParseData.getOutgoingUrls();
//
//            System.out.println("Text length: " + text.length());
//            System.out.println("Html length: " + html.length());
//            System.out.println("Number of outgoing links: " + links.size());
//        }
        }

        public String getId() {
            return id;
        }

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