package com.github.salvatorenovelli.seo.websiteversioning;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PatternGeneratorTest {

    private final List<String> baseUrls = Arrays.asList("www.example.com", "example.com", "http://www.example.com", "https://www.example.com");


    @Test
    public void shouldRemoveWWW() throws Exception {
        for (String baseUrl : baseUrls) {
            Pattern pattern = PatternGenerator.generatePatternFor(baseUrl);
            assertTrue(pattern.matcher("example.com").matches());
        }
    }

    @Test
    public void shouldStillAllowWWW() throws Exception {
        for (String baseUrl : baseUrls) {
            Pattern pattern = PatternGenerator.generatePatternFor(baseUrl);
            assertTrue(baseUrl + " should match!", pattern.matcher("www.example.com").matches());
        }
    }

    @Test
    public void shouldAllowHttpAndHttps() throws Exception {
        for (String baseUrl : baseUrls) {
            Pattern pattern = PatternGenerator.generatePatternFor(baseUrl);
            assertTrue(baseUrl + " should match!", pattern.matcher("http://example.com").matches());
            assertTrue(baseUrl + " should match!", pattern.matcher("https://example.com").matches());
        }
    }

    @Test
    public void shouldNotAllow_ss_js_gif_jpg_png_mp3_mp3_zip_gz() throws Exception {
        for (String baseUrl : baseUrls) {
            Pattern pattern = PatternGenerator.generatePatternFor(baseUrl);
            assertFalse(baseUrl + " should not match!", pattern.matcher("https://example.com/tes.css").matches());
            assertFalse(baseUrl + " should not match!", pattern.matcher("https://example.com/tes.js").matches());
            assertFalse(baseUrl + " should not match!", pattern.matcher("https://example.com/tes.gif").matches());
            assertFalse(baseUrl + " should not match!", pattern.matcher("https://example.com/tes.jpg").matches());
            assertFalse(baseUrl + " should not match!", pattern.matcher("https://example.com/tes.png").matches());
            assertFalse(baseUrl + " should not match!", pattern.matcher("https://example.com/tes.mp3").matches());
            assertFalse(baseUrl + " should not match!", pattern.matcher("https://example.com/tes.zip").matches());
            assertFalse(baseUrl + " should not match!", pattern.matcher("https://example.com/tes.gz").matches());
        }
    }
}