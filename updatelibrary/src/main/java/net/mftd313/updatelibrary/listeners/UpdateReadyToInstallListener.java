package net.mftd313.updatelibrary.listeners;

import android.content.Context;
import android.net.Uri;

public interface UpdateReadyToInstallListener {
    void onReadyToInstall(Context context, Uri uri);
}
