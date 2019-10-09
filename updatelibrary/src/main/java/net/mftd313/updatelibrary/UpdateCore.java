package net.mftd313.updatelibrary;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

final class UpdateCore {

    static void beginInstall(Context context, Uri uri) {
        File file = new File(context.getExternalFilesDir(null), uri.getLastPathSegment());
        if (!file.exists() || !UpdateCore.isDownloadSuccess(context, uri)) {
            return;
        }
        if (UpdateLibrary.getInstallStartedListener() != null) {
            UpdateLibrary.getInstallStartedListener().onInstallStarted(context, uri);
        }
        File apkFile = new File(context.getExternalFilesDir(null), uri.getLastPathSegment());
        Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".net.mftd313.updatelibrary.fileprovider", apkFile);
        Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        install.setData(apkUri);
        install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
        context.stopService(new Intent(context, UpdateInstallService.class));
    }

    static void beginDownload(Context context, Uri uri) {
        if (UpdateLibrary.getDownloadStartedListener() != null) {
            UpdateLibrary.getDownloadStartedListener().onDownloadStarted(context, uri);
        }
        File file = new File(context.getExternalFilesDir(null), uri.getLastPathSegment());
        if (file.exists()) {
            if (isDownloadSuccess(context, uri)) {
                if (UpdateLibrary.getReadyToInstallListener() != null) {
                    UpdateLibrary.getReadyToInstallListener().onReadyToInstall(context, uri);
                }
                return;
            }
            else {
                file.delete();
            }
        }
        DownloadManager.Request request = new DownloadManager.Request(uri)
                .setTitle(UpdateRepository.getInstance(context).getDownloadingNotificationTitle())
                .setDescription(UpdateRepository.getInstance(context).getDownloadingNotificationText())
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationUri(Uri.fromFile(file))
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        long downloadID = downloadManager.enqueue(request);
        UpdateRepository.getInstance(context).setLastDownloadId(downloadID);
    }

    static boolean isDownloadRunning(Context context, Uri uri) {
        boolean isDownloadRunning = false;

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);

        Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_RUNNING));
        String downingURI;
        while (cursor.moveToNext()) {
            downingURI = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
            if (downingURI.equalsIgnoreCase(uri.toString())) {
                isDownloadRunning = true;
                break;
            }
        }
        cursor.close();
        return isDownloadRunning;
    }

    static boolean isDownloadPending(Context context, Uri uri) {
        boolean isPending = false;

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);

        Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_PENDING | DownloadManager.STATUS_PAUSED));
        String downingURI;
        while (cursor.moveToNext()) {
            downingURI = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
            if (downingURI.equalsIgnoreCase(uri.toString())) {
                isPending = true;
                break;
            }
        }
        cursor.close();
        return isPending;
    }

    static boolean isDownloadSuccess(Context context, Uri uri) {
        boolean isDownloaded = false;

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);

        Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL));
        String downingURI;
        while (cursor.moveToNext()) {
            downingURI = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
            if (downingURI.equalsIgnoreCase(uri.toString())) {
                isDownloaded = true;
                break;
            }
        }
        cursor.close();
        return isDownloaded;
    }
}
