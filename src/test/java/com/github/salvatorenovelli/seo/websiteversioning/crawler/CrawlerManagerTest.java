package com.github.salvatorenovelli.seo.websiteversioning.crawler;

import com.github.salvatorenovelli.seo.websiteversioning.Crawler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


@DataJpaTest
@RunWith(SpringRunner.class)
public class CrawlerManagerTest {


    private static final String CRAWLER_ID = "ABCD";
    @Autowired
    private CrawlerRepository repository;
    private CrawlerManager crawlerManager;

    @Before
    public void setUp() throws Exception {
        repository.save(new CrawlerDTO(CRAWLER_ID));
        crawlerManager = new CrawlerManager(repository);
    }

    @Test
    public void shouldReturnTheCrawlerIfItExist() throws Exception {
        Optional<Crawler> crawler = crawlerManager.getCrawler("example.com", CRAWLER_ID);
        assertTrue(crawler.isPresent());
        assertThat(crawler.get().getId(), is(CRAWLER_ID));
    }

    @Test
    public void shouldReturnNoneIfTheCrawlerDoesNotExist() throws Exception {
        Optional<Crawler> crawler = crawlerManager.getCrawler("example.com", "ZZZZZZZ");
        assertFalse(crawler.isPresent());
    }
}