package net.mftd313.updatelibrary.sample;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.mftd313.updatelibrary.UpdateLibrary;
import net.mftd313.updatelibrary.listeners.UpdateDownloadStartedListener;
import net.mftd313.updatelibrary.listeners.UpdateInstallStartedListener;
import net.mftd313.updatelibrary.listeners.UpdateReadyToDownloadListener;
import net.mftd313.updatelibrary.listeners.UpdateReadyToInstallListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.hide();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        final String url = "http://ytsmartplayer.mftd313.net/releases/YTSmartPlayer-release-v1.0.8.apk";
        //final String url = "https://d-15.winudf.com/b/apk/Y29tLmZhY2Vib29rLmthdGFuYV8xNzY1MTUyMDVfOThhMjQzYTI?_fn=RmFjZWJvb2tfdjI0Mi4wLjAuNDMuMTE5X2Fwa3B1cmUuY29tLmFwaw&_p=Y29tLmZhY2Vib29rLmthdGFuYQ&k=30f00e65680b1f8e8a9475d6248771c05d9f426a&uu=http%3A%2F%2F172.16.53.1%2Fb%2Fapk%2FY29tLmZhY2Vib29rLmthdGFuYV8xNzY1MTUyMDVfOThhMjQzYTI%3Fk%3D6bc71e7abd95402778f14da21bc9f4675d9f426a";

        UpdateLibrary.with(MainActivity.this)

                .setDownloadingNotificationTitle(getString(R.string.app_name))
                .setDownloadingNotificationText(getString(R.string.downloading_new_version))

                .setDownloadedNotificationTitle(getString(R.string.app_name))
                .setDownloadedNotificationText(getString(R.string.download_completed))
                .setDownloadedNotificationSmallIconResource(R.drawable.ic_launcher_foreground)
                .setDownloadedNotificationLargeIconResource(R.drawable.ic_launcher_foreground)

                .setUpdateReadyToDownloadListener(new UpdateReadyToDownloadListener() {
                    @Override
                    public void onReadyToDownload(final Context context, Uri uri) {
                        progressDialog.hide();
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                UpdateLibrary.getUpdateManager().download(context);
                            }
                        });
                        fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_cloud_download_black_18dp));
                        fab.show();
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
                        progressDialog.hide();
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                UpdateLibrary.getUpdateManager().install(context);
                            }
                        });
                        fab.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.baseline_get_app_black_18dp));
                        fab.show();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
