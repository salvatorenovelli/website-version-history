package com.github.salvatorenovelli.seo.websiteversioning.controller;


import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerDTO;
import com.github.salvatorenovelli.seo.websiteversioning.crawler.WorkerManager;
import com.github.salvatorenovelli.seo.websiteversioning.model.CrawlStartResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/api")
public class CrawlerController {


    private final WorkerManager workerManager;
    private final String secretKey;

    public CrawlerController(WorkerManager workerManager, @Value("${secret.key}") String secretKey) {
        this.workerManager = workerManager;
        this.secretKey = secretKey;
    }

    @GetMapping("/test")
    public String test() {
        return "yea";
    }

    @PutMapping("/create")
    public ResponseEntity<WorkerDTO> createWorker() {
        return ResponseEntity.ok(new WorkerDTO(workerManager.createWorker().getId()));
    }

    @PutMapping("{workerId}/start")
    public CrawlStartResponse startCrawling(@PathVariable String workerId, @RequestParam String url) {
        return new CrawlStartResponse(true, "Starting worker " + workerId + " " + url);
    }
}
