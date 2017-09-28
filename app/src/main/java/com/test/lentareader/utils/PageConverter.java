package com.test.lentareader.utils;

import com.test.lentareader.network.models.Page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class PageConverter implements Converter<ResponseBody, Page> {

    private static final String BBOX = "b-box";
    private static final String BBOX_INLINE = "b-inline-topics-box";
    private static final String TOPIC_CONTENT = "b-topic__content";
    private static final String TOPIC_TITLE = "b-topic__title";
    private static final String TOPIC_TITLE_IMAGE = "b-topic__title-image";
    private static final String TOPIC_TEXT = "js-topic__text";
    private static final String PICTURE = "g-picture";
    private static final String SRC_ATTR = "src";

    public static final Converter.Factory FACTORY = new Converter.Factory() {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(
                Type type, Annotation[] annotations, Retrofit retrofit) {
            if (type == Page.class) return new PageConverter();
            return null;
        }
    };

    @Override
    public Page convert(ResponseBody responseBody) throws IOException {
        Document document = Jsoup.parse(responseBody.string());
        Page retPage = new Page();
        List<Page.Item> items = new ArrayList<>();
        Element topic = document.getElementsByClass(TOPIC_CONTENT).first();

        String title = topic
                .getElementsByClass(TOPIC_TITLE).first().text();
        String image = null;
        Element imageContainer = topic
                .getElementsByClass(TOPIC_TITLE_IMAGE).first();
        if (imageContainer != null) {
            Element imageElement = imageContainer
                    .getElementsByClass(PICTURE).first();
            if (imageElement != null) {
                image = imageElement.attr(SRC_ATTR);
            }

        }

        Element body = topic.getElementsByClass(TOPIC_TEXT).first();

        for (Element i : body.children()) {
            if (i.hasClass(BBOX_INLINE)) continue;
            if (i.hasClass(BBOX)) continue;
            Page.Item item = new Page.Item();
            item.setText(i.html());
            items.add(item);
        }

        retPage.setTitle(title);
        retPage.setTitleImage(image);
        retPage.setItems(Collections.unmodifiableList(items));

        return retPage;
    }
}
