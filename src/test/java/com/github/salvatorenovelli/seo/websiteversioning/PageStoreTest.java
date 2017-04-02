package com.github.salvatorenovelli.seo.websiteversioning;

import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;
import org.hamcrest.Matchers;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;


@RunWith(MockitoJUnitRunner.class)
public class PageStoreTest {


    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    @Mock private Document document;
    private PageStore sut;
    @Mock private PageSnapshotSerializer snapShotSerializer;

    @Before
    public void setUp() throws Exception {
        sut = new PageStore(temporaryFolder.getRoot().toPath(), snapShotSerializer);
        System.out.println("Base is:" + temporaryFolder.getRoot());
    }

    @Test
    public void shouldStorePageInTheProperPath() throws Exception {
        URI uri = URI.create("http://www.example.com/path/of/request/resource");
        sut.storePage(uri, new PageSnapshot(document));
        File path = temporaryFolder.getRoot().toPath().resolve("path/of/request/").toFile();
        assertTrue(path.exists());
        assertTrue(path.isDirectory());
    }

    @Test
    public void shouldStoreUnicodePageInTheProperPath() throws Exception {
        URI uri = URI.create("http://www.example.com/sayfa/teşekkür/kayit-onay-tesekkurler");
        sut.storePage(uri, new PageSnapshot(document));
        File path = temporaryFolder.getRoot().toPath().resolve("sayfa/teşekkür/").toFile();
        assertTrue(path.exists());
        assertTrue(path.isDirectory());
    }


    @Test
    public void shouldNameTheStoredFileAsTheLastSegmentHashCodeWithoutFragment() throws Exception {
        URI uri = URI.create("http://www.example.com/path/of/request/page.jsp&someParam=1#fragment");
        PageSnapshot snapshot = new PageSnapshot(document);
        sut.storePage(uri, snapshot);

        String lastSegmentOfPathExcludingFragment = "page.jsp&someParam=1";

        Path filePath = temporaryFolder
                .getRoot()
                .toPath()
                .resolve("path/of/request/" + lastSegmentOfPathExcludingFragment.hashCode() + ".json");

        Mockito.verify(snapShotSerializer).serialize(snapshot, filePath);
    }
}