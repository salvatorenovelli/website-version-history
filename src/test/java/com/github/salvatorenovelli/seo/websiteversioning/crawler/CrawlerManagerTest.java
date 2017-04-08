package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import com.github.salvatorenovelli.seo.websiteversioning.Crawler;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


public class CrawlerManagerTest {


    @Test
    public void shouldCreateACrawler() throws Exception {

        CrawlerManager crawlerManager = new CrawlerManager(repository);
        Crawler crawler = crawlerManager.getCrawler("example.com", "ABCD");

        assertNotNull(crawler);
        assertThat(crawler.getId(), is("ABCD"));

    }
}