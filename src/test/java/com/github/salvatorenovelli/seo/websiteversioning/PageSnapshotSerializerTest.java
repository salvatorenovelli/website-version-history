package com.github.salvatorenovelli.seo.websiteversioning;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.salvatorenovelli.seo.websiteversioning.model.PageSnapshot;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.net.URI;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


public class PageSnapshotSerializerTest {


    private static final String HTML = "" +
            "<HTML>" +
            " <head>" +
            "  <title>Title</title>" +
            "  <meta name=\"description\" content=\"Meta description\">" +
            "  <link href=\"http://www.example.com/canonical-version-of-page/\" rel=\"canonical\" />" +
            " </head>" +
            " <body>" +
            "  <H1>First H1</H1>" +
            "  <H1>Second H1</H1>" +
            "  <H2>First H2</H2>" +
            "  <H2>Second H2</H2>" +
            " </body>" +
            "</HTML>";
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private DocumentContext jsonDocument;


    @Before
    public void setUp() throws Exception {

        System.out.println(temporaryFolder.getRoot());

        Document page = Jsoup.parse(HTML);

        PageSnapshot snapshot = new PageSnapshot(URI.create("http://www.example.com/path/to/resource?parama=1234&otherparam=456#fragment"), page);
        PageSnapshotSerializer serializer = new PageSnapshotSerializer(new ObjectMapper().writerWithDefaultPrettyPrinter());
        Path jsonFileOutputFile = temporaryFolder.getRoot().toPath().resolve("path.json");
        serializer.serialize(snapshot, jsonFileOutputFile);
        jsonDocument = JsonPath.parse(jsonFileOutputFile.toFile());

    }

    @Test
    public void serializeShouldSaveTitle() throws Exception {
        assertThat(jsonDocument.read("$.title"), is("Title"));
    }

    @Test
    public void serializeShouldSaveH1() throws Exception {
        assertThat(jsonDocument.read("$.h1s"), hasSize(2));
        assertThat(jsonDocument.read("$.h1s[0]"), is("First H1"));
        assertThat(jsonDocument.read("$.h1s[1]"), is("Second H1"));
    }

    @Test
    public void serializeShouldSaveH2s() throws Exception {
        assertThat(jsonDocument.read("$.h2s"), hasSize(2));
        assertThat(jsonDocument.read("$.h2s[0]"), is("First H2"));
        assertThat(jsonDocument.read("$.h2s[1]"), is("Second H2"));
    }

    @Test
    public void shouldSaveURIWithoutFragment() throws Exception {
        assertThat(jsonDocument.read("$.uri"), is("http://www.example.com/path/to/resource?parama=1234&otherparam=456"));
    }

    @Test
    public void shouldSaveMetaDescrition() throws Exception {
        assertThat(jsonDocument.read("$.metaDescritions"), hasSize(1));
        assertThat(jsonDocument.read("$.metaDescritions[0]"), is("Meta description"));
    }

    @Test
    public void shouldSaveCanonicalTag() throws Exception {
        assertThat(jsonDocument.read("$.canonicals"), hasSize(1));
        assertThat(jsonDocument.read("$.canonicals[0]"), is("http://www.example.com/canonical-version-of-page/"));
    }
}