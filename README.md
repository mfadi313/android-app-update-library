# Android App Update Library

This library simplifies the process of downloading and installing new version of the app from the internet, that is when you want to add an update functionality for yor app regardless of Google Play update mechanism.

# How to use

```java

String url = "https://example.com/your_app_new_version.apk";

final ProgressDialog progressDialog = new ProgressDialog(this);

UpdateLibrary.with(AppUpdateActivity.this)

    // while downloading notification
    .setDownloadingNotificationTitle(getString(R.string.app_name))
    .setDownloadingNotificationText(getString(R.string.downloading_new_version))
    
    // before installing notification
    .setDownloadedNotificationSmallIconResource(R.mipmap.ic_launcher)
    .setDownloadedNotificationLargeIconResource(R.mipmap.ic_launcher)
    .setDownloadedNotificationTitle(getString(R.string.app_name))
    .setDownloadedNotificationText(getString(R.string.download_completed))
    
    .setUpdateReadyToDownloadListener(new UpdateReadyToDownloadListener() {
        @Override
        public void onReadyToDownload(final Context context, Uri uri) {
            UpdateLibrary.getUpdateManager().download(context);
            progressDialog.hide();
        }
    })
    .setUpdateDownloadStartedListener(new UpdateDownloadStartedListener() {
        @Override
        public void onDownloadStarted(Context context, Uri uri) {
            progressDialog.setMessage(getString(R.string.downloading_new_version));
            progressDialog.show();
        }
    })
    .setUpdateReadyToInstallListener(new UpdateReadyToInstallListener() {
        @Override
        public void onReadyToInstall(final Context context, Uri uri) {
            UpdateLibrary.getUpdateManager().install(context);
            progressDialog.hide();
        }
    })
    .setUpdateInstallStartedListener(new UpdateInstallStartedListener() {
        @Override
        public void onInstallStarted(Context context, Uri uri) {
            progressDialog.setMessage(getString(R.string.installing_new_version));
            progressDialog.show();
        }
    })

    .init(Uri.parse(url));

```

# Add to your project

Add the JitPack repository to your build file in your root build.gradle at the end of repositories:

```gradle

allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

```

Add the dependency:

```gradle

dependencies {
    ...
    implementation 'com.github.mfadi313:android-app-update-library:1.1.1'
}

```

# License

```
MIT License

Copyright (c) 2019 Mohammad Fadi Taqi Al-Din

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
