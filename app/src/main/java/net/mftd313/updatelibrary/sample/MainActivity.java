package net.mftd313.updatelibrary.sample;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import net.mftd313.updatelibrary.UpdateLibrary;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String url = "http://ytsmartplayer.mftd313.net/releases/YTSmartPlayer-release-v1.0.8.apk";

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdateLibrary.with(MainActivity.this)

                        .setDownloadingNotificationTitle(getString(R.string.app_name))
                        .setDownloadingNotificationText(getString(R.string.downloading_new_version))

                        .setDownloadedNotificationTitle(getString(R.string.app_name))
                        .setDownloadedNotificationText(getString(R.string.download_completed))
                        .setDownloadedNotificationSmallIconResource(R.drawable.ic_launcher_foreground)
                        .setDownloadedNotificationLargeIconResource(R.drawable.ic_launcher_foreground)

                        .update(Uri.parse(url));

                Snackbar.make(view, getString(R.string.updating), Snackbar.LENGTH_LONG).show();
            }
        });
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
