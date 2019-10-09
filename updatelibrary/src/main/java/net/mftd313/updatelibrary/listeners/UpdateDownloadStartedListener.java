package net.mftd313.updatelibrary.listeners;

import android.content.Context;
import android.net.Uri;

public interface UpdateDownloadStartedListener {
    void onDownloadStarted(Context context, Uri uri);
}
