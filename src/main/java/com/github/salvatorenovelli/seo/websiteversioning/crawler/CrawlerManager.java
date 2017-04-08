package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import com.github.salvatorenovelli.seo.websiteversioning.Crawler;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
public class CrawlerManager {


    private final CrawlerRepository repository;

    public CrawlerManager(CrawlerRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public Crawler getCrawler(String baseDomain, String crawlerId) {

        CrawlerDTO one = repository.getOne(crawlerId);


        return new Crawler(baseDomain, crawlerId);
    }
}
