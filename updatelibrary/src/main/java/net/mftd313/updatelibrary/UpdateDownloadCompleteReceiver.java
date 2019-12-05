package net.mftd313.updatelibrary;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import static android.content.Context.DOWNLOAD_SERVICE;

public final class UpdateDownloadCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long lastDownloadId = UpdateRepository.getInstance(context).getLastDownloadId();
        if(lastDownloadId == -1){
            return;
        }

        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        if (id != lastDownloadId) {
            return;
        }

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(lastDownloadId));
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                if (UpdateLibrary.getReadyToInstallListener() != null) {
                    UpdateLibrary.getReadyToInstallListener().onReadyToInstall(context,
                            Uri.parse(UpdateRepository.getInstance(context).getLastDownloadUrl()));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(new Intent(context, UpdateInstallService.class));
                } else {
                    context.startService(new Intent(context, UpdateInstallService.class));
                }
            } else if (status == DownloadManager.STATUS_FAILED) {
                if (UpdateLibrary.getDownloadFailedListener() != null) {
                    UpdateLibrary.getDownloadFailedListener().onDownloadFailed(context,
                            Uri.parse(UpdateRepository.getInstance(context).getLastDownloadUrl()));
                }
            }
        }
    }
}
