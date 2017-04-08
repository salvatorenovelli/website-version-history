package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrawlerRepository extends JpaRepository<CrawlerDTO, String> {
}
