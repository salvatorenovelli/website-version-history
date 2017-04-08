package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CrawlerDTO {
    @Id
    private String id;
}
