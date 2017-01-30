package com.github.salvatorenovelli.seo;

import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;
import org.eclipse.jetty.server.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.salvatorenovelli.seo.TestWebsiteBuilder.givenAWebsite;
import static com.github.salvatorenovelli.seo.TestWebsiteBuilder.testUri;
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


}


