package com.github.salvatorenovelli.seo;

import org.eclipse.jetty.server.Server;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Test;

import static com.github.salvatorenovelli.seo.TestWebsiteBuilder.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpClientTest {


    public static final String TEST_TITLE = "Hello Title";
    private Server testServer = new Server(0);
    private HttpClient sut = new HttpClient();


    @After
    public void tearDown() throws Exception {
        tearDownCurrentServer();
    }

    @Test
    public void get() throws Exception {

        givenAWebsite("/hello")
                .withTitle(TEST_TITLE)
                .run();

        Document htmlPage = sut.get(testUri("/hello"));
        assertThat(htmlPage.title(), is(TEST_TITLE));
    }



}