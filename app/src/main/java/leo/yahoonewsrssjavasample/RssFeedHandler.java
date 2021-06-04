package leo.yahoonewsrssjavasample;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.prof.rssparser.Channel;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

public class RssFeedHandler {

    public RssFeedHandler() {
    }


    @WorkerThread
    public void fetchFeed(OnFetchFeedCompletedListener listener) {
        final String URL_STRING = "https://news.yahoo.co.jp/rss/topics/it.xml";
        Parser parser = new Parser.Builder()
                // If you want to provide a custom charset (the default is utf-8):
                // .charset(Charset.forName("ISO-8859-7"))
                // .cacheExpirationMillis() and .context() not called because on Java side, caching is NOT supported
                .build();

        parser.onFinish(new OnTaskCompleted() {

            //what to do when the parsing is done
            @Override
            public void onTaskCompleted(@NonNull Channel channel) {
                listener.onTaskCompleted(channel.getArticles());
            }

            //what to do in case of error
            @Override
            public void onError(@NonNull Exception e) {
                listener.onError(e);
            }
        });
        parser.execute(URL_STRING); // 内部でExecutorServiceを使って処理している.
    }
}
