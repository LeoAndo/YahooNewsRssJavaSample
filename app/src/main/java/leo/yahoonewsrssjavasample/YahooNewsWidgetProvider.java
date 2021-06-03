package leo.yahoonewsrssjavasample;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Arrays;

/**
 * Implementation of App Widget functionality.
 */
public class YahooNewsWidgetProvider extends AppWidgetProvider {
    private static final String TAG = YahooNewsWidgetProvider.class.getSimpleName();
    public static final String EXTRA_ITEM = BuildConfig.APPLICATION_ID + "EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "updateAppWidget appWidgetId: " + appWidgetId);
        Intent intent = new Intent(context, MyRemoteViewsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.yahoo_news_widget_provider);
        remoteViews.setRemoteAdapter(R.id.view_flipper, intent);

        remoteViews.setEmptyView(R.id.view_flipper, R.id.empty_view); // コレクションにアイテムがない場合、空のビューを表示させる

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
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
        for (int i = 0; i < appWidgetIds.length; ++i) {
            Log.d(TAG, "i: " + i);
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
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
    }

    /**
     * ウィジェットの最後のインスタンスがウィジェット ホストから削除されたときに呼び出される。
     * {@link YahooNewsWidgetProvider#onEnabled(android.content.Context)}で行った作業がある場合はこのタイミングで消去する
     */
    @Override
    public void onDisabled(Context context) {
        Log.d(TAG, "onDisabled"+ " hashcode:" + hashCode());
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * ウィジェットがウィジェット ホスト(例えば、HOME画面)から削除されるたびに呼び出される。
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(TAG, "onDeleted appWidgetIds: " + Arrays.toString(appWidgetIds)+ " hashcode:" + hashCode());
    }

    /**
     * ウィジェットが最初に配置されたとき、
     * および以後ウィジェットのサイズが変更されたときに呼び出される
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.d(TAG, "onAppWidgetOptionsChanged appWidgetId: " + appWidgetId + " newOptions: " + newOptions+ " hashcode:" + hashCode());
    }

    /**
     * 通常はこのメソッドを実装する必要はない。
     * <p>
     * onEnabled()が呼ばれた後に、
     * android.appwidget.action.APPWIDGET_ENABLED インテントが飛んでくる
     * <p>
     * onUpdate()が呼ばれた後に、
     * android.appwidget.action.APPWIDGET_UPDATE インテントが飛んでくる
     * <p>
     * onDeleted()が呼ばれた後に、
     * android.appwidget.action.APPWIDGET_DELETED インテントが飛んでくる
     * <p>
     * onDisabled()が呼ばれた後に、
     * android.appwidget.action.APPWIDGET_DISABLED インテントが飛んでくる
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive intent: " + intent+ " hashcode:" + hashCode());
    }
}