# Android App Update Library

This library simplifies the process of downloading and installing new version of the app from the internet, that is when you want to add an update functionality for yor app regardless of Google Play update mechanism.

# How to use

```java

String url = "https://example.com/your_app_new_version.apk";

UpdateLibrary.with(AppUpdateActivity.this)

    // while downloading notification
    .setDownloadingNotificationTitle(getString(R.string.app_name))
    .setDownloadingNotificationText(getString(R.string.downloading_new_version))
    
    // before installing notification
    .setDownloadedNotificationSmallIconResource(R.mipmap.ic_launcher)
    .setDownloadedNotificationLargeIconResource(R.mipmap.ic_launcher)
    .setDownloadedNotificationTitle(getString(R.string.app_name))
    .setDownloadedNotificationText(getString(R.string.download_completed))
    
    // start update
    .update(Uri.parse(url));

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
    implementation 'com.github.mfadi313:android-app-update-library:1.0.0'
}

```

# License

```
Copyright 2019 Mohammad Fadi Taqi Al-Din

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
