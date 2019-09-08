package net.mftd313.updatelibrary;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

import static android.content.Context.NOTIFICATION_SERVICE;

public final class UpdateLibrary {

    public static UpdateManager with(Context context){
        return new UpdateManager(context);
    }

    public static final class UpdateManager {

        Context context;
        int notificationSmallIconResource = -1;

        UpdateManager(Context context){
            this.context = context;
        }

        public UpdateManager setNotificationSmallIconResource(int resourceId){
            notificationSmallIconResource = resourceId;
            return this;
        }

        public UpdateManager setDownloadedNotificationSmallIconResource(int resourceId){
            UpdateRepository.getInstance(context).setDownloadedNotificationSmallIconResource(resourceId);
            return this;
        }

        public UpdateManager setDownloadedNotificationLargeIconResource(int resourceId){
            UpdateRepository.getInstance(context).setDownloadedNotificationLargeIconResource(resourceId);
            return this;
        }

        public UpdateManager setDownloadedNotificationTitle(String title){
            UpdateRepository.getInstance(context).setDownloadedNotificationTitle(title);
            return this;
        }

        public UpdateManager setDownloadedNotificationText(String text){
            UpdateRepository.getInstance(context).setDownloadedNotificationText(text);
            return this;
        }

        public UpdateManager setDownloadingNotificationTitle(String title){
            UpdateRepository.getInstance(context).setDownloadingNotificationTitle(title);
            return this;
        }

        public UpdateManager setDownloadingNotificationText(String text){
            UpdateRepository.getInstance(context).setDownloadingNotificationText(text);
            return this;
        }

        public void update(Uri uri){

            String url = uri.toString();
            UpdateRepository.getInstance(context).setLastDownloadUrl(url);
            if (url.isEmpty()) {
                return;
            }

            if (UpdateCore.isDownloading(context, uri) ||
                    UpdateCore.isPendingDownload(context, uri)) {
                return;
            }

            initNotificationChannel();
            UpdateCore.beginDownload(context, uri);
        }

        private void initNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                final String channelId = context.getString(R.string.app_name);
                final String channelName = context.getString(R.string.app_name);
                final NotificationChannel defaultChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_MIN);
                final NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                if (manager != null) {
                    manager.createNotificationChannel(defaultChannel);
                }
            }
        }
    }
}
