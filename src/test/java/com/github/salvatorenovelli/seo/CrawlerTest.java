package com.github.salvatorenovelli.seo;

import org.junit.Test;

import java.net.URI;
import java.util.function.Consumer;

public class CrawlerTest {

    @Test
    public void name() throws Exception {


        Crawler crawler = new Crawler();


        crawler.crawl(URI.create("http://www.example.com"), uri -> System.out.println("Consuming: " + uri.toString()));
    }
}