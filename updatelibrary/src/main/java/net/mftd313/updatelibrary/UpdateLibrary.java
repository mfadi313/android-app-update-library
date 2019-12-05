package net.mftd313.updatelibrary;

import android.content.Context;
import android.net.Uri;

import net.mftd313.updatelibrary.listeners.UpdateDownloadFailedListener;
import net.mftd313.updatelibrary.listeners.UpdateDownloadStartedListener;
import net.mftd313.updatelibrary.listeners.UpdateInstallStartedListener;
import net.mftd313.updatelibrary.listeners.UpdateReadyToDownloadListener;
import net.mftd313.updatelibrary.listeners.UpdateReadyToInstallListener;

import java.io.File;

public final class UpdateLibrary {

    private static UpdateReadyToDownloadListener readyToDownloadListener;
    private static UpdateDownloadStartedListener downloadStartedListener;
    private static UpdateDownloadFailedListener downloadFailedListener;
    private static UpdateReadyToInstallListener readyToInstallListener;
    private static UpdateInstallStartedListener installStartedListener;
    private static UpdateManager updateManager;

    public static UpdateInitializer with(Context context) {
        return new UpdateInitializer(context);
    }

    public static UpdateManager getUpdateManager() {
        return updateManager;
    }

    static UpdateDownloadStartedListener getDownloadStartedListener() {
        return downloadStartedListener;
    }

    static UpdateDownloadFailedListener getDownloadFailedListener() {
        return downloadFailedListener;
    }

    static UpdateReadyToInstallListener getReadyToInstallListener() {
        return readyToInstallListener;
    }

    static UpdateInstallStartedListener getInstallStartedListener() {
        return installStartedListener;
    }

    public static final class UpdateInitializer {

        Context context;
        int notificationSmallIconResource = -1;

        private UpdateInitializer(Context context) {
            this.context = context;
        }

        public UpdateInitializer setUpdateReadyToDownloadListener(UpdateReadyToDownloadListener listener) {
            readyToDownloadListener = listener;
            return this;
        }

        public UpdateInitializer setUpdateDownloadStartedListener(UpdateDownloadStartedListener listener) {
            downloadStartedListener = listener;
            return this;
        }

        public UpdateInitializer setUpdateDownloadFailedListener(UpdateDownloadFailedListener listener) {
            downloadFailedListener = listener;
            return this;
        }

        public UpdateInitializer setUpdateReadyToInstallListener(UpdateReadyToInstallListener listener) {
            readyToInstallListener = listener;
            return this;
        }

        public UpdateInitializer setUpdateInstallStartedListener(UpdateInstallStartedListener listener) {
            installStartedListener = listener;
            return this;
        }

        public UpdateInitializer setNotificationSmallIconResource(int resourceId) {
            notificationSmallIconResource = resourceId;
            return this;
        }

        public UpdateInitializer setDownloadedNotificationSmallIconResource(int resourceId) {
            UpdateRepository.getInstance(context).setDownloadedNotificationSmallIconResource(resourceId);
            return this;
        }

        public UpdateInitializer setDownloadedNotificationLargeIconResource(int resourceId) {
            UpdateRepository.getInstance(context).setDownloadedNotificationLargeIconResource(resourceId);
            return this;
        }

        public UpdateInitializer setDownloadedNotificationTitle(String title) {
            UpdateRepository.getInstance(context).setDownloadedNotificationTitle(title);
            return this;
        }

        public UpdateInitializer setDownloadedNotificationText(String text) {
            UpdateRepository.getInstance(context).setDownloadedNotificationText(text);
            return this;
        }

        public UpdateInitializer setDownloadingNotificationTitle(String title) {
            UpdateRepository.getInstance(context).setDownloadingNotificationTitle(title);
            return this;
        }

        public UpdateInitializer setDownloadingNotificationText(String text) {
            UpdateRepository.getInstance(context).setDownloadingNotificationText(text);
            return this;
        }

        public void init(Uri uri) {

            UpdateRepository.getInstance(context).setLastDownloadUrl(uri.toString());
            updateManager = new UpdateManager(uri);

            if (UpdateCore.isDownloadRunning(context, uri) ||
                    UpdateCore.isDownloadPending(context, uri)) {
                if (downloadStartedListener != null) {
                    downloadStartedListener.onDownloadStarted(context, uri);
                }
                return;
            }

            File file = new File(context.getExternalFilesDir(null), uri.getLastPathSegment());
            if (file.exists() && UpdateCore.isDownloadSuccess(context, uri)) {
                if (readyToInstallListener != null) {
                    readyToInstallListener.onReadyToInstall(context, uri);
                }
                return;
            }

            if (readyToDownloadListener != null) {
                readyToDownloadListener.onReadyToDownload(context, uri);
            }
        }
    }

    public static final class UpdateManager {

        Uri uri;

        private UpdateManager(Uri uri) {
            this.uri = uri;
        }

        public void download(Context context) {
            UpdateCore.beginDownload(context, uri);
        }

        public void install(Context context) {
            UpdateCore.beginInstall(context, uri);
        }
    }
}
