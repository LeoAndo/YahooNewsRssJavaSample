package leo.yahoonewsrssjavasample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import leo.yahoonewsrssjavasample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonRequestPinWidget.setOnClickListener(v -> requestWidget());
    }

    private void requestWidget() {
        Log.d(TAG, "requestWidget-START");
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Toast.makeText(this, R.string.toast_message_andorid_o_above, Toast.LENGTH_LONG).show();
            return;
        }
        if (!appWidgetManager.isRequestPinAppWidgetSupported()) {
            Toast.makeText(this,
                    getString(R.string.error_widget_pinning_not_supported_by_launcher), Toast.LENGTH_LONG).show();
            return;
        }
        final PendingIntent successCallback = WidgetPinnedReceiver.getPendingIntent(this);
        final RemoteViews remoteViews = getPreviewRemoteViews(this);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AppWidgetManager.EXTRA_APPWIDGET_PREVIEW, remoteViews);
        final ComponentName provider = new ComponentName(this, YahooNewsWidgetProvider.class);
        try {
            Log.d(TAG, "call appWidgetManager.requestPinAppWidget");
            appWidgetManager.requestPinAppWidget(provider, bundle, successCallback);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            Toast.makeText(this, "The caller doesn't have a foreground activity or a foreground.", Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "requestWidget-END");
    }

    private RemoteViews getPreviewRemoteViews(Context context) {
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.yahoo_news_widget_provider);
        remoteViews.setTextViewText(R.id.text_title, getString(R.string.dummy_text_title));
        remoteViews.setTextViewText(R.id.text_description, getString(R.string.dummy_text_description));
        return remoteViews;
    }
}