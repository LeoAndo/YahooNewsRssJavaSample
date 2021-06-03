package leo.yahoonewsrssjavasample;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import static leo.yahoonewsrssjavasample.YahooNewsWidgetProvider.EXTRA_ITEM;

public class MyRemoteViewsService extends RemoteViewsService {
    private static final String TAG = MyRemoteViewsService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory intent: " + intent + " hashcode:" + hashCode());
        return new MyRemoteViewsFactory(getApplicationContext(), intent);
    }

    private static class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private final String TAG = MyRemoteViewsFactory.class.getSimpleName();
        private final List<WidgetItem> widgetItems = new ArrayList<>();
        private final Context context;
        private final RemoteViews rvLoading;

        public MyRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            final int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            rvLoading = new RemoteViews(context.getPackageName(), R.layout.widget_loading);
            Log.d(TAG, "MyRemoteViewsFactory appWidgetId: " + appWidgetId + " hashcode:" + hashCode());
        }

        /**
         * ここで、メインスレッドの処理で20秒以上かかると、ANRになり強制終了するので注意.
         */
        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate " + " hashcode:" + hashCode());
            for (int i = 0; i < 10; i++) {
                widgetItems.add(new WidgetItem(i + "!"));
            }

            // ここで 3 秒間スリープして、その間に空のビューがどのように表示されるかのテスト.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * AppWidgetManager#notifyAppWidgetViewDataChangedを呼び出すときにトリガーされます.
         * onCreateの後にも呼ばれる.
         */
        @Override
        public void onDataSetChanged() {
            Log.d(TAG, "onDataSetChanged " + " hashcode:" + hashCode());
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy " + " hashcode:" + hashCode());
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
            Log.d(TAG, "getViewAt " + " hashcode:" + hashCode());
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            rv.setTextViewText(R.id.widget_item, widgetItems.get(position).text);

            Intent fillInIntent = new Intent();
            fillInIntent.putExtra(EXTRA_ITEM, position);
            rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

            try {
                Log.d(TAG, "Loading view" + position);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
