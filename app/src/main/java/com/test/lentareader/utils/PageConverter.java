package com.test.lentareader.utils;

import android.text.Html;
import android.text.SpannedString;

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

public class PageConverter  implements Converter<ResponseBody, Page> {
    public static final Converter.Factory FACTORY = new Converter.Factory() {
        @Override public Converter<ResponseBody, ?> responseBodyConverter(
                Type type, Annotation[] annotations, Retrofit retrofit) {
            if (type == Page.class) return new PageConverter();
            return null;
        }
    };

    @Override public Page convert(ResponseBody responseBody) throws IOException {
        Document document = Jsoup.parse(responseBody.string());
        Page retPage = new Page();
        List<Page.Item> items = new ArrayList<>();
        Element topic = document.getElementsByClass("b-topic__content").first();

        String title = topic
                .getElementsByClass("b-topic__title").first().text();

        String image = topic
                .getElementsByClass("b-topic__title-image").first()
                .getElementsByClass("g-picture").first().attr("src");

        Element body = topic.getElementsByClass("js-topic__text").first();


        for (Element i : body.children()) {
            Page.Item item = new Page.Item();
            if(i.hasClass("b-inline-topics-box")) continue;
            item.setText(i.html());
            items.add(item);
        }

        retPage.setTitle(title);
        retPage.setTitleImage(image);
        retPage.setItems(Collections.unmodifiableList(items));

        return retPage;
    }
}
