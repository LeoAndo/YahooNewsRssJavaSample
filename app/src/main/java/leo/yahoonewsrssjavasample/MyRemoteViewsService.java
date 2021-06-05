package leo.yahoonewsrssjavasample;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.prof.rssparser.Article;

import java.util.ArrayList;
import java.util.List;

public class MyRemoteViewsService extends RemoteViewsService {
    private static final String TAG = MyRemoteViewsService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory intent: " + intent);
        return new MyRemoteViewsFactory(getApplicationContext());
    }

    private static class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private final String TAG = MyRemoteViewsFactory.class.getSimpleName();
        private final List<WidgetItem> widgetItems;
        private final Context context;
        private final RemoteViews rvLoading;
        private final SharedPreferencesUtils sharedPreferencesUtils;

        public MyRemoteViewsFactory(Context context) {
            Log.d(TAG, "MyRemoteViewsFactory");
            this.context = context;
            rvLoading = new RemoteViews(context.getPackageName(), R.layout.widget_loading);
            sharedPreferencesUtils = new SharedPreferencesUtils();
            widgetItems = new ArrayList<>();
        }

        /**
         * ここで、メインスレッドの処理で20秒以上かかると、ANRになり強制終了するので注意.
         */
        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate");
        }

        /**
         * AppWidgetManager#notifyAppWidgetViewDataChangedを呼び出すときにトリガーされます.
         * onCreateの後にも呼ばれる.
         */
        @Override
        public void onDataSetChanged() {
            Log.d(TAG, "onDataSetChanged");
            List<Article> articles = sharedPreferencesUtils.getArticles(context);
            Log.d(TAG, "articles: " + articles);
            widgetItems.clear();
            for (Article article : articles) {
                widgetItems.add(new WidgetItem(article.getTitle(), article.getDescription(), article.getLink()));
            }
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy");
            widgetItems.clear();
        }

        @Override
        public int getCount() {
            final int count = widgetItems.size();
            Log.d(TAG, "getCount: " + count);
            return count;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.d(TAG, "getViewAt");
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            rv.setTextViewText(R.id.text_title, widgetItems.get(position).title);
            rv.setTextViewText(R.id.text_description, widgetItems.get(position).description);

            Intent fillInIntent = new Intent(YahooNewsWidgetProvider.ACTION_ITEM_CLICKED);
            fillInIntent.putExtra(YahooNewsWidgetProvider.EXTRA_ITEM_LINK, widgetItems.get(position).link);
            rv.setOnClickFillInIntent(R.id.text_description, fillInIntent); // rootのview IDを指定できない！ (@+id/item_container)
            return rv;
        }

        /**
         * カスタムの読み込みビューを作成できる(たとえば、getViewAt() が遅い場合)
         */
        @Override
        public RemoteViews getLoadingView() {
            // return null; // ここで null を返すと、デフォルトの読み込みビューが表示される。
            return rvLoading;
        }

        /**
         * すべてのリストアイテムに対して常に同じタイプのビューを返すのでその場合、 1 を返すでOK!
         */
        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
