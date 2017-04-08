package com.github.salvatorenovelli.seo.websiteversioning.model;


import lombok.Data;

@Data
public class CrawlStartResponse {
    private final boolean accepted;
    private final String statusMessage;
}
