package net.mftd313.updatelibrary.listeners;

import android.content.Context;
import android.net.Uri;

public interface UpdateInstallStartedListener {
    void onInstallStarted(Context context, Uri uri);
}
