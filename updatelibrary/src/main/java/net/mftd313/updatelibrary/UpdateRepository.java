package net.mftd313.updatelibrary;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

final class UpdateRepository {

    private static UpdateRepository repository;
    static UpdateRepository getInstance(Context context){
        if(repository == null){
            repository = new UpdateRepository(context);
        }
        return repository;
    }

    private SharedPreferences sharedPreferences;

    private UpdateRepository(Context context){
        sharedPreferences = context.getSharedPreferences(UpdateConstants.REPOSITORY_NAME, Activity.MODE_PRIVATE);
    }

    void setLastDownloadId(long downloadId) {
        sharedPreferences.edit().putLong("download_id", downloadId).apply();
    }

    long getLastDownloadId() {
        return sharedPreferences.getLong("download_id", -1);
    }

    void setLastDownloadUrl(String downloadUrl) {
        sharedPreferences.edit().putString("download_url", downloadUrl).apply();
    }

    String getLastDownloadUrl() {
        return sharedPreferences.getString("download_url", "");
    }

    /******************************************************************************/

    void setDownloadedNotificationSmallIconResource(int resourceId){
        sharedPreferences.edit().putInt("downloaded_notification_small_icon_resource", resourceId).apply();
    }

    int getDownloadedNotificationSmallIconResource() {
        return sharedPreferences.getInt("downloaded_notification_small_icon_resource", -1);
    }

    void setDownloadedNotificationLargeIconResource(int resourceId){
        sharedPreferences.edit().putInt("downloaded_notification_large_icon_resource", resourceId).apply();
    }

    int getDownloadedNotificationLargeIconResource() {
        return sharedPreferences.getInt("downloaded_notification_large_icon_resource", -1);
    }

    void setDownloadedNotificationTitle(String downloadedNotificationTitle) {
        sharedPreferences.edit().putString("downloaded_notification_title", downloadedNotificationTitle).apply();
    }

    String getDownloadedNotificationTitle() {
        return sharedPreferences.getString("downloaded_notification_title", "");
    }

    void setDownloadedNotificationText(String downloadedNotificationText) {
        sharedPreferences.edit().putString("downloaded_notification_text", downloadedNotificationText).apply();
    }

    String getDownloadedNotificationText() {
        return sharedPreferences.getString("downloaded_notification_text", "");
    }

    void setDownloadingNotificationTitle(String downloadingNotificationTitle) {
        sharedPreferences.edit().putString("downloading_notification_title", downloadingNotificationTitle).apply();
    }

    String getDownloadingNotificationTitle() {
        return sharedPreferences.getString("downloading_notification_title", "");
    }

    void setDownloadingNotificationText(String downloadingNotificationText) {
        sharedPreferences.edit().putString("downloading_notification_text", downloadingNotificationText).apply();
    }

    String getDownloadingNotificationText() {
        return sharedPreferences.getString("downloading_notification_text", "");
    }
}
