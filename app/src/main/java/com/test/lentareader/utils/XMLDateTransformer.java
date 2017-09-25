package com.test.lentareader.utils;

import org.simpleframework.xml.transform.Transform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class XMLDateTransformer implements Transform<Date> {
    private static final SimpleDateFormat format =
            new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

    @Override
    public Date read(String value) throws Exception {
        return format.parse(value);
    }

    @Override
    public String write(Date value) throws Exception {
        return format.format(value);
    }
}
