package com.github.salvatorenovelli.seo.websiteversioning;

import com.github.salvatorenovelli.seo.websiteversioning.domain.HtmlReader;
import com.github.salvatorenovelli.seo.websiteversioning.domain.HttpClient;
import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.github.salvatorenovelli.seo.websiteversioning.TestWebsiteBuilder.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


@RunWith(MockitoJUnitRunner.class)
public class HtmlReaderTest {


    public static final String TEST_TITLE = "Test title";
    public static final String ROOT_PAGE_PATH = "/";
    private HtmlReader sut;

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
    public void shouldReadTags() throws Exception {
        givenAWebsite(ROOT_PAGE_PATH)
                .withTag("H1", "Test H1")
                .run();
        PageSnapshot snapshot = sut.snapshotPage(testUri(ROOT_PAGE_PATH));

        List<String> h1s = snapshot.getH1s();

        assertThat(h1s, hasSize(1));
        assertThat(h1s.get(0), is("Test H1"));
    }

    @Test
    public void shouldReadMultipleTags() throws Exception {
        givenAWebsite(ROOT_PAGE_PATH)
                .withTag("H1", "Test H1")
                .withTag("H1", "Test second H1")
                .run();
        PageSnapshot snapshot = sut.snapshotPage(testUri(ROOT_PAGE_PATH));

        List<String> h1s = snapshot.getH1s();

        assertThat(h1s, hasSize(2));
        assertThat(h1s.get(0), is("Test H1"));
        assertThat(h1s.get(1), is("Test second H1"));
    }

    @Test
    public void shouldReadMultipleDiverseTags() throws Exception {
        givenAWebsite(ROOT_PAGE_PATH)
                .withTag("H1", "Test H1")
                .withTag("H1", "Test second H1")
                .withTag("H2", "Test H2")
                .run();

        PageSnapshot snapshot = sut.snapshotPage(testUri(ROOT_PAGE_PATH));

        List<String> h1s = snapshot.getH1s();
        List<String> h2s = snapshot.getH2s();

        assertThat(h1s, hasSize(2));
        assertThat(h2s, hasSize(1));

        assertThat(h2s.get(0), is("Test H2"));

    }
}


