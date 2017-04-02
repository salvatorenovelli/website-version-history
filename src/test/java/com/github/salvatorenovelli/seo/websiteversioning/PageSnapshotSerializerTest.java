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

import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


public class PageSnapshotSerializerTest {


    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private PageSnapshot snapshot;
    private Path jsonFileOutputFile;


    @Before
    public void setUp() throws Exception {
        System.out.println(temporaryFolder.getRoot());


        String html = "" +
                "<html>" +
                " <head>" +
                "  <title>Title</title>" +
                " </head>" +
                " <body>" +
                "  <H1>First H1</H1>" +
                "  <H1>Second H1</H1>" +
                "  <H2>First H2</H2>" +
                "  <H2>Second H2</H2>" +
                " </body>" +
                "</html>";

        Document page = Jsoup.parse(html);

        snapshot = new PageSnapshot(page);

        PageSnapshotSerializer serializer = new PageSnapshotSerializer(new ObjectMapper().writerWithDefaultPrettyPrinter());
        jsonFileOutputFile = temporaryFolder.getRoot().toPath().resolve("path.json");
        serializer.serialize(snapshot, jsonFileOutputFile);

    }

    @Test
    public void serializeShouldSaveTitle() throws Exception {
        DocumentContext jsonDocument = JsonPath.parse(jsonFileOutputFile.toFile());

        assertThat(jsonDocument.read("$.title"), is("Title"));
    }

    @Test
    public void serializeShouldSaveH1() throws Exception {
        DocumentContext jsonDocument = JsonPath.parse(jsonFileOutputFile.toFile());

        assertThat(jsonDocument.read("$.h1s"), hasSize(2));
        assertThat(jsonDocument.read("$.h1s[0]"), is("First H1"));
        assertThat(jsonDocument.read("$.h1s[1]"), is("Second H1"));
    }

    @Test
    public void serializeShouldSaveH2s() throws Exception {
        DocumentContext jsonDocument = JsonPath.parse(jsonFileOutputFile.toFile());

        assertThat(jsonDocument.read("$.h2s"), hasSize(2));
        assertThat(jsonDocument.read("$.h2s[0]"), is("First H2"));
        assertThat(jsonDocument.read("$.h2s[1]"), is("Second H2"));
    }

}