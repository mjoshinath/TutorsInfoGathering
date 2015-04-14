package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import java.io.File;
import java.io.IOException;

public class TutorsWebSiteHomePage extends Activity {

    private WebView webView;
    private String ui;
    private int resFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors_web_site_home_page);

        webView = (WebView) findViewById(R.id.web_view);
//        ui = getResources().getResourceEntryName(R.raw.tutor_home_page);
//        resFile = getResources().getIdentifier("raw/tutor_home_page", "raw", getPackageName());
//        Uri path = Uri.parse("android.resource://tutorsweb.ehc.com.tutorsinfogathering/raw/tutor_home_page");
//        webView.loadData("", "text/html", "UTF-8");
//        webView.loadUrl("file:///android_asset/aboutcertified.html");
        webView.loadUrl(getPackageResourcePath()+ "/raw/tutor_home_page.html");
    }

}
