package net.mftd313.updatelibrary.listeners;

import android.content.Context;
import android.net.Uri;

public interface UpdateReadyToDownloadListener {
    void onReadyToDownload(Context context, Uri uri);
}
