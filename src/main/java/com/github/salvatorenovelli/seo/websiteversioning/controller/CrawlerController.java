package com.github.salvatorenovelli.seo.websiteversioning.controller;


import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerDTO;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import com.github.salvatorenovelli.seo.websiteversioning.model.CrawlStartResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/worker")
public class CrawlerController {


    private final WorkerManager workerManager;
    private final String secretKey;

    public CrawlerController(WorkerManager workerManager, @Value("${secret.key}") String secretKey) {
        this.workerManager = workerManager;
        this.secretKey = secretKey;
    }

    @PutMapping("{providedKey}/create")
    public ResponseEntity<WorkerDTO> createWorker(@PathVariable String providedKey) {
        if (providedKey.equals(secretKey)) {
            return ResponseEntity.ok(new WorkerDTO(workerManager.createWorker().getId()));
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("{crawlerId}/start")
    public CrawlStartResponse startCrawling(@PathVariable String crawlerId, @RequestParam String url) {
        return new CrawlStartResponse(true, "Starting " + crawlerId + " " + url);
    }
}
