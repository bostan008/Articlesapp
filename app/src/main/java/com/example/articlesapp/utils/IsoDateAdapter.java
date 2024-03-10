package com.example.articlesapp.utils;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.ToJson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IsoDateAdapter {
        private static final SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

        @ToJson
        String toJson(Date date) {
            return ISO_8601_FORMAT.format(date);
        }

        @FromJson
        Date fromJson(JsonReader reader) throws IOException {
            String isoDateString = reader.nextString();
            try {
                return ISO_8601_FORMAT.parse(isoDateString);
            } catch (ParseException e) {
                throw new IOException("Error parsing ISO date string", e);
            }
        }
    }