package com.github.salvatorenovelli.seo.websiteversioning.controller;


import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import com.github.salvatorenovelli.seo.websiteversioning.model.CrawlStartResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/crawler")
public class CrawlerController {


    private final WorkerManager workerManager;
    private final String secretKey;

    public CrawlerController(WorkerManager workerManager, @Value("${secret.key}") String secretKey) {
        this.workerManager = workerManager;
        this.secretKey = secretKey;
    }

    @GetMapping("/create")
    public String createCrawler() {
        System.out.printf("" + secretKey);
        return null;
    }

    @PutMapping("{crawlerId}/start")
    public CrawlStartResponse startCrawling(@PathVariable String crawlerId, @RequestParam String url) {
        return new CrawlStartResponse(true, "Starting " + crawlerId + " " + url);
    }
}
