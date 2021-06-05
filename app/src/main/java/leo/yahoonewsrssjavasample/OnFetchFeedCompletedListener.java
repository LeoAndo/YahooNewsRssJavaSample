package leo.yahoonewsrssjavasample;

import com.prof.rssparser.Article;
import java.util.List;

interface OnFetchFeedCompletedListener {
    void onTaskCompleted(List<Article> articles);

    void onError(Exception e);
}
