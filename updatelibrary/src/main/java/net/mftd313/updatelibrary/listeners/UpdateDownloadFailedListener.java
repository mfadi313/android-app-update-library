package net.mftd313.updatelibrary.listeners;

import android.content.Context;
import android.net.Uri;

public interface UpdateDownloadFailedListener {
    void onDownloadFailed(Context context, Uri uri);
}
