package com.github.salvatorenovelli.seo.websiteversioning.controller;


import com.github.salvatorenovelli.seo.websiteversioning.model.CrawlStartResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    @PutMapping("{crawlerId}/start")
    public CrawlStartResponse startCrawling(@PathVariable String crawlerId, @RequestParam String url) {
        return new CrawlStartResponse(true, "Starting " + crawlerId + " " + url);
    }
}
