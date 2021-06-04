package leo.yahoonewsrssjavasample;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prof.rssparser.Article;

import java.util.Collections;
import java.util.List;

public class SharedPreferencesUtils {
    public static final String ARTICLES_PREF_KEY = "ARTICLES_PREF_KEY";
    private static final String LIST_PREFS_DEF_VALUE = "[]";

    public synchronized void setArticles(Context context, List<Article> articles) {
        final SharedPreferences prefs = getSharedPreferences(context);
        final Gson gson = new Gson();
        prefs.edit().putString(ARTICLES_PREF_KEY, gson.toJson(articles)).apply();
    }

    public synchronized List<Article> getArticles(Context context) {
        final SharedPreferences prefs = getSharedPreferences(context);
        final Gson gson = new Gson();
        final String json = prefs.getString(ARTICLES_PREF_KEY, LIST_PREFS_DEF_VALUE);
        if (LIST_PREFS_DEF_VALUE.equals(json)) {
            return Collections.emptyList();
        } else {
            return gson.fromJson(json, new TypeToken<List<Article>>() {
            }.getType());
        }
    }

    private synchronized SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("pref", Context.MODE_PRIVATE);
    }

    public synchronized void clearArticles(Context context) {
        final SharedPreferences prefs = getSharedPreferences(context);
        prefs.edit().remove(ARTICLES_PREF_KEY).apply();
    }
}
