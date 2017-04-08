package com.github.salvatorenovelli.seo.websiteversioning.controller;


import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import com.github.salvatorenovelli.seo.websiteversioning.model.CrawlStartResponse;
import com.github.salvatorenovelli.seo.websiteversioning.model.CreateCrawlerRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/crawler")
public class CrawlerController {


    private final WorkerManager workerManager;

    public CrawlerController(WorkerManager workerManager) {
        this.workerManager = workerManager;
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
