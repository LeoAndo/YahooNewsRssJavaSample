package leo.yahoonewsrssjavasample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class WidgetPinnedReceiver extends BroadcastReceiver {
    private static final int BROADCAST_ID = 12345;
    private static final String TAG = WidgetPinnedReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        final int appwidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        Log.d(TAG, "onReceive appwidgetId: " + appwidgetId);
        Toast.makeText(context, context.getString(R.string.created_widget_message, appwidgetId), Toast.LENGTH_SHORT).show();
    }

    public static PendingIntent getPendingIntent(Context context) {
        final Intent callbackIntent = new Intent(context, WidgetPinnedReceiver.class);
        final Bundle bundle = new Bundle();
        callbackIntent.putExtras(bundle);
        return PendingIntent.getBroadcast(
                context, BROADCAST_ID, callbackIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}