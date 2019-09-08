package net.mftd313.updatelibrary;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public final class UpdateInstallService extends Service {

    private final BroadcastReceiver notificationClickedListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            UpdateCore.beginInstall(context, Uri.parse(url));
            stopSelf();
        }
    };

    private String url;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        url = UpdateRepository.getInstance(this).getLastDownloadUrl();
        if (url.isEmpty()) {
            stopSelf();
            return START_NOT_STICKY;
        }

        registerReceiver(notificationClickedListener, new IntentFilter(UpdateConstants.NOTIFICATION_CLICKED));
        showDownloadSuccessNotification();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(notificationClickedListener);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void showDownloadSuccessNotification() {

        Toast.makeText(this, UpdateRepository.getInstance(this).getDownloadedNotificationText(),
                Toast.LENGTH_LONG).show();

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, getString(R.string.app_name));
        builder.setWhen(System.currentTimeMillis());
        if(UpdateRepository.getInstance(this).getDownloadedNotificationSmallIconResource() != -1){
            builder.setSmallIcon(UpdateRepository.getInstance(this).getDownloadedNotificationSmallIconResource());
        }
        if(UpdateRepository.getInstance(this).getDownloadedNotificationLargeIconResource() != -1){
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), UpdateRepository.getInstance(this).getDownloadedNotificationLargeIconResource()));
        }
        builder.setContentTitle(UpdateRepository.getInstance(this).getDownloadedNotificationTitle());
        builder.setContentText(UpdateRepository.getInstance(this).getDownloadedNotificationText());
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
        builder.setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE);

        Intent clickIntent = new Intent(UpdateConstants.NOTIFICATION_CLICKED);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this, 0, clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(clickPendingIntent);

        startForeground(UpdateConstants.NOTIFICATION_ID, builder.build());
    }
}
