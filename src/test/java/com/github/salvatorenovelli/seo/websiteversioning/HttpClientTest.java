package com.github.salvatorenovelli.seo.websiteversioning;

import com.github.salvatorenovelli.seo.websiteversioning.domain.HttpClient;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpClientTest {


    public static final String TEST_TITLE = "Hello Title";
    private HttpClient sut = new HttpClient();


    @After
    public void tearDown() throws Exception {
        TestWebsiteBuilder.tearDownCurrentServer();
    }

    @Test
    public void get() throws Exception {
        TestWebsiteBuilder.givenAWebsite("/hello")
                .withTitle(TEST_TITLE)
                .run();

        Document htmlPage = sut.get(TestWebsiteBuilder.testUri("/hello"));
        assertThat(htmlPage.title(), is(TEST_TITLE));
    }



}