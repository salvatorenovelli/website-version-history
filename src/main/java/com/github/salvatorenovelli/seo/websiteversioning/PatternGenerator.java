package com.github.salvatorenovelli.seo.websiteversioning;

import java.util.regex.Pattern;

public class PatternGenerator {


    private final static String regexBase = "^(http://|https://)?((www\\.)?)";
    private final static String regexEnd = ".*(?<!css|js|gif|jpg|png|mp3|mp3|zip|gz)$";

    public static Pattern generatePatternFor(String domain) {
        String regex = regexBase + domain.replaceAll("(www\\.)|(http://)|(https://)", "") + regexEnd;
        return Pattern.compile(regex);
    }
}
