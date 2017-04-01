package com.github.salvatorenovelli.seo;

import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.salvatorenovelli.seo.TestWebsiteBuilder.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
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
        sut = new HtmlReader(new HttpClient());
    }

    @After
    public void tearDown() throws Exception {
        tearDownCurrentServer();
    }

    @Test
    public void shouldRetrieveTitle() throws Exception {
        givenAWebsite(ROOT_PAGE_PATH)
                .withTitle(TEST_TITLE)
                .run();
        PageSnapshot snapshot = sut.snapshotPage(testUri(ROOT_PAGE_PATH));
        assertThat(snapshot.getTitle(), is(TEST_TITLE));
    }

    @Test
    public void shouldReadH1() throws Exception {
        givenAWebsite(ROOT_PAGE_PATH)
                .withH1("Test H1")
                .run();
        PageSnapshot snapshot = sut.snapshotPage(testUri(ROOT_PAGE_PATH));
        assertThat(snapshot.getH1(), hasSize(1));
        assertThat(snapshot.getH1().get(0), is("Test H1"));
    }

    @Test
    public void shouldReadMultipleH1() throws Exception {
        givenAWebsite(ROOT_PAGE_PATH)
                .withH1("Test H1")
                .withH1("Test second H1")
                .run();
        PageSnapshot snapshot = sut.snapshotPage(testUri(ROOT_PAGE_PATH));
        assertThat(snapshot.getH1(), hasSize(2));
        assertThat(snapshot.getH1().get(0), is("Test H1"));
        assertThat(snapshot.getH1().get(1), is("Test second H1"));
    }
}


