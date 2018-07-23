package com.example.dell.newsarticlesummarizer.utils;

import android.content.Context;

import com.example.dell.newsarticlesummarizer.models.Article;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<Article> readRawTextFile(Context ctx, int resId) {
        List<Article> articles = new ArrayList<>();
        InputStream inputStream = ctx.getResources().openRawResource(resId);
        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;

        try {
            while (( line = buffreader.readLine()) != null) {
                String[] arr = line.split(" ", 2);
                if(arr.length > 1) {
                    articles.add(new Article(arr[1], arr[0]));
                }
            }
        } catch (IOException e) {
            return null;
        }
        return articles;
    }
}
