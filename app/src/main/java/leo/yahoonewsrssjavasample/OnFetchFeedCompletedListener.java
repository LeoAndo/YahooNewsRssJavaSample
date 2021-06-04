package leo.yahoonewsrssjavasample;

import com.prof.rssparser.Article;
import java.util.List;

public interface OnFetchFeedCompletedListener {
    void onTaskCompleted(List<Article> articles);

    void onError(Exception e);
}
