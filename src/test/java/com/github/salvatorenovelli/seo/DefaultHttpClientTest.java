package com.github.salvatorenovelli.seo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultHttpClientTest {


    public static final String TEST_TITLE = "Hellp Title";
    private Server testServer;
    private HttpClient sut = new HttpClient();

    @Test
    public void get() throws Exception {

        givenAWebsite()
                .havingPage("/hello")
                .withTitle(TEST_TITLE)
                .run();

        Document htmlPage = sut.get(testUri("/hello"));

        assertThat(htmlPage.title(), is(TEST_TITLE));


    }

    private TestWebsiteBuilder givenAWebsite() {
        testServer = new Server(0);
        return new TestWebsiteBuilder(testServer);
    }

    private URI testUri(String url) throws URISyntaxException {
        if (!testServer.isStarted()) {
            throw new IllegalStateException("Sorry, you'll need to run the scenario before asking for URI. (At the moment the server port is not known)");
        }
        int localPort = ((ServerConnector) testServer.getConnectors()[0]).getLocalPort();
        return new URI("http://localhost:" + localPort + url);
    }

}