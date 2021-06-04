package leo.yahoonewsrssjavasample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.prof.rssparser.Article;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class YahooNewsWidgetProvider extends AppWidgetProvider {
    private static final String TAG = YahooNewsWidgetProvider.class.getSimpleName();
    public static final String EXTRA_ITEM_LINK = BuildConfig.APPLICATION_ID + "EXTRA_ITEM_LINK";
    public static final String ACTION_ITEM_CLICKED = BuildConfig.APPLICATION_ID + "ACTION_ITEM_CLICKED";
    public static final String ACTION_REFRESH_MANUAL = BuildConfig.APPLICATION_ID + "ACTION_REFRESH_MANUAL";
    private final RssFeedHandler rssFeedHandler = new RssFeedHandler();
    private final SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();

    private RemoteViews getRemoteViews(Context context, int appWidgetId) {
        Log.d(TAG, "getWidgetRemoteViews appWidgetId: " + appWidgetId);
        Intent intent = new Intent(context, MyRemoteViewsService.class);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.yahoo_news_widget_provider);
        remoteViews.setRemoteAdapter(R.id.view_flipper, intent);
        remoteViews.setEmptyView(R.id.view_flipper, R.id.empty_view); // コレクションにアイテムがない場合、空のビューを表示させる
        // ボタンのクリックイベントをハンドリングする.
        Intent refreshManualIntent = new Intent(context, YahooNewsWidgetProvider.class);
        refreshManualIntent.setAction(ACTION_REFRESH_MANUAL);
        refreshManualIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        remoteViews.setOnClickPendingIntent(R.id.button_refresh_manual, PendingIntent.getBroadcast(context, appWidgetId, refreshManualIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        // セルのクリックイベントをハンドリングする場合は、setPendingIntentTemplateとsetOnClickFillInIntentの組み合わせで
        // PendingIntentを作成する.
        Intent cellItemIntent = new Intent(context, YahooNewsWidgetProvider.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, cellItemIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.view_flipper, pendingIntent);
        return remoteViews;
    }

    /**
     * ユーザがウィジェットを追加した時も呼び出される。
     * ただし、設定アクティビティを宣言している場合は、
     * このメソッドはユーザーがウィジェットを追加したときには呼び出されず、以後の更新時に呼び出される。
     * 設定の完了時に最初の更新を実行するのは設定アクティビティの責任。
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate appWidgetIds: " + Arrays.toString(appWidgetIds) + " hashcode:" + hashCode());
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    /**
     * ウィジェットのインスタンスが初めて作成されたときに呼び出される。
     * たとえば、ユーザーがウィジェットのインスタンスを2つ追加した場合は、初回のみ呼び出される。
     * 1 回だけ必要なセットアップを行う必要がある場合は、このタイミングが適している。
     */
    @Override
    public void onEnabled(Context context) {
        Log.d(TAG, "onEnabled" + " hashcode:" + hashCode());
        // Enter relevant functionality for when the first widget is created
        Log.d(TAG, "onEnabled END");
    }

    /**
     * ウィジェットの最後のインスタンスがウィジェット ホストから削除されたときに呼び出される。
     * {@link YahooNewsWidgetProvider#onEnabled(android.content.Context)}で行った作業がある場合はこのタイミングで消去する
     */
    @Override
    public void onDisabled(Context context) {
        Log.d(TAG, "onDisabled" + " hashcode:" + hashCode());
        // Enter relevant functionality for when the last widget is disabled
        sharedPreferencesUtils.clearArticles(context);
    }

    /**
     * ウィジェットがウィジェット ホスト(例えば、HOME画面)から削除されるたびに呼び出される。
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(TAG, "onDeleted appWidgetIds: " + Arrays.toString(appWidgetIds) + " hashcode:" + hashCode());
    }

    /**
     * ウィジェットが最初に配置されたとき、
     * および以後ウィジェットのサイズが変更されたときに呼び出される
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d(TAG, "onAppWidgetOptionsChanged appWidgetId: " + appWidgetId + " newOptions: " + newOptions + " hashcode:" + hashCode());
    }

    /**
     * 通常はこのメソッドを実装する必要はない。
     * <p>
     * android.appwidget.action.APPWIDGET_ENABLED インテントが飛んできて、
     * onEnabled()を内部で呼び出している
     * <p>
     * android.appwidget.action.APPWIDGET_UPDATE インテントが飛んできて、
     * onUpdate()を内部で呼び出している
     * <p>
     * android.appwidget.action.APPWIDGET_DELETED インテントが飛んできて、
     * onDeleted()を内部で呼び出している
     * <p>
     * android.appwidget.action.APPWIDGET_DISABLED インテントが飛んできて、
     * onDisabled()を内部で呼び出している
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive intent: " + intent + " hashcode:" + hashCode());
        super.onReceive(context, intent);

        final String action = intent.getAction();
        if (ACTION_ITEM_CLICKED.equals(action)) {
            final String link = intent.getStringExtra(EXTRA_ITEM_LINK);
            final Intent newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(newIntent);
            } catch (ActivityNotFoundException ex) {
                ex.printStackTrace();
            }
        } else if (ACTION_REFRESH_MANUAL.equals(action)) {
            final int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            updateAppWidget(context, appWidgetId);
        }
    }

    public void updateAppWidget(Context context, int appWidgetId) {
        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, getRemoteViews(context, appWidgetId));
        fetchFeed(context, appWidgetId);
    }

    private void fetchFeed(Context context, int appWidgetId) {
        rssFeedHandler.fetchFeed(new OnFetchFeedCompletedListener() {
            @Override
            public void onTaskCompleted(List<Article> articles) {
                sharedPreferencesUtils.clearArticles(context);
                sharedPreferencesUtils.setArticles(context, articles);
                AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.view_flipper);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}