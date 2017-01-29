package com.github.salvatorenovelli.seo;

import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@RunWith(MockitoJUnitRunner.class)
public class HtmlReaderTest {


    public static final String TEST_TITLE = "Test title";
    public static final String ROOT_PAGE_PATH = "/";
    private HtmlReader sut;
    @Mock private HttpClient httpClient;
    private Server testServer;

    @Before
    public void setUp() throws Exception {
        sut = new HtmlReader(httpClient);
    }

    @Test
    public void shouldRetrieveTitle() throws Exception {

        givenAWebsite()
                .havingPage(ROOT_PAGE_PATH)
                .withTitle(TEST_TITLE)
                .run();
        PageSnapshot snapshot = sut.snapshotPage(testUri(ROOT_PAGE_PATH));
        assertThat(snapshot.getTitle(), is(TEST_TITLE));

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


