package com.github.salvatorenovelli.seo.websiteversioning.controller;


import com.github.salvatorenovelli.seo.websiteversioning.crawler.CrawlerManager;
import com.github.salvatorenovelli.seo.websiteversioning.model.CrawlStartResponse;
import com.github.salvatorenovelli.seo.websiteversioning.model.CreateCrawlerRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/crawler")
public class CrawlerController {


    private final CrawlerManager crawlerManager;

    public CrawlerController(CrawlerManager crawlerManager) {
        this.crawlerManager = crawlerManager;
    }

    @PostMapping("/create")
    public String createCrawler(@RequestBody CreateCrawlerRequest request) {
        return null;
    }

    @PutMapping("{crawlerId}/start")
    public CrawlStartResponse startCrawling(@PathVariable String crawlerId, @RequestParam String url) {
        return new CrawlStartResponse(true, "Starting " + crawlerId + " " + url);
    }
}
