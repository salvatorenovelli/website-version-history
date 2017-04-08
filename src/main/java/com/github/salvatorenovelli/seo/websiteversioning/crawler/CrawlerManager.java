package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import com.github.salvatorenovelli.seo.websiteversioning.Crawler;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;


@Component
public class CrawlerManager {


    private final CrawlerRepository repository;

    public CrawlerManager(CrawlerRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public Optional<Crawler> getCrawler(String baseDomain, String crawlerId) {
        try {
            CrawlerDTO one = repository.getOne(crawlerId);
            return Optional.of(new Crawler(baseDomain, one.getId()));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
}
