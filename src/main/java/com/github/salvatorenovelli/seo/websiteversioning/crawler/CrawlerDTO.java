package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CrawlerDTO {
    @Id
    @GeneratedValue
    private String id;


}
