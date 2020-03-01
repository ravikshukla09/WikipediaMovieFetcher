package com.thinkanalytics.rks.wiki.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.thinkanalytics.rks.wiki.Utils;
import com.thinkanalytics.rks.wiki.dto.Movie;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieDeserializer extends StdDeserializer<Movie> {

    Logger logger = LoggerFactory.getLogger(MovieDeserializer.class);

    public MovieDeserializer() {
        this(null);
    }

    protected MovieDeserializer(Class<?> vc) {
        super(vc);
    }

//    @SneakyThrows
    @Override
    public Movie deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode rootNode = objectCodec.readTree(jsonParser);

        JsonNode parseNode =  rootNode.get("parse");
        String title = parseNode.get("title").textValue();
        List<String> categories = new ArrayList<>();
        parseNode.get("categories").forEach(categoriesNode -> {
            JsonNode hidden = categoriesNode.get("hidden");
            if (hidden == null) {
                categories.add(categoriesNode.get("*").textValue());
            }
        });

        logger.debug("Processing: {}", title);

        Document document = Jsoup.parse(parseNode.get("text").get("*").textValue());
        Element movieIntro = document.selectFirst("p:not([class])");
        Element table = document.selectFirst("table.infobox");
        Elements rows = table.select("tr");

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setCategories(categories);
        movie.setIntro(movieIntro != null ? Utils.cleanText(movieIntro.text()) : "");

        for (Element row : rows) {
            Element rowTitle = row.selectFirst("th");
            Element rowValue = row.selectFirst("td");

            if (rowTitle == null || rowValue == null)
                continue;

            final String key = rowTitle.text();
            final String value = rowValue.text();

            if (key.equalsIgnoreCase("Starring")) {
                movie.setActors(extractList(rowValue));
            } else if (key.equalsIgnoreCase("Directed by")) {
                movie.setDirectors(extractList(rowValue));
            } else if (key.equalsIgnoreCase("Produced by")) {
                movie.setProducers(extractList(rowValue));
            } else if (key.equalsIgnoreCase("Music by")) {
                movie.setMusicComposers(extractList(rowValue));
            } else if (key.equalsIgnoreCase("Production Companies")
                    || key.equalsIgnoreCase("Production Company")) {
                movie.setProductionCompanies(extractList(rowValue));
            } else if (key.equalsIgnoreCase("Release date")) {
                Element releaseDate = rowValue.selectFirst("li").selectFirst("span").selectFirst("span");
                movie.setReleaseDate(Utils.cleanStringDate(releaseDate.text()));
            } else if (key.equalsIgnoreCase("Running time")) {
                movie.setDuration(Utils.cleanText(value));
            } else if (key.equalsIgnoreCase("Language")) {
                movie.setLanguage(Utils.cleanText(value));
            } else if (key.equalsIgnoreCase("Budget")) {
                movie.setBudget(Utils.cleanText(value));
            } else if (key.equalsIgnoreCase("Box Office")) {
                movie.setBoxOfficeCollection(Utils.cleanText(value));
            }
        }
        return movie;
    }

    private List<String> extractList(Element element) {
        List<String> list = new ArrayList<>();
        element.select("li").forEach(node -> {
            String text = Utils.cleanText(node.text());
            if (text.length() >= 1) list.add(text);
        });
        if (list.isEmpty()) {
            element.select("a").forEach(node -> {
                String text = Utils.cleanText(node.text());
                if (text.length() >= 1) list.add(text);
            });
        }
        if (list.isEmpty()) {
            list.add(Utils.cleanText(element.text()));
        }
        return list;
    }
}
