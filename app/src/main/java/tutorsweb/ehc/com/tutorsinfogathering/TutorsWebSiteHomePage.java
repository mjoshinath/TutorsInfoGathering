package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TutorsWebSiteHomePage extends Activity {

    private WebView webView;
    private String ui;
    private int resFile;
    private int size;
    private byte[] buffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors_web_site_home_page);

        webView = (WebView) findViewById(R.id.web_view);
//        webView.loadUrl("file:///android_asset/tutor_home_page.html");


       /* String temp = "<html>\n" +
                "<head>\n" +
                "    <link type=\"text/html\" rel=\"stylesheet\" href=\"tutor_home_page.html\" />\n" +
                "</head>\n" +
                "<body bgcolor=\"white\">\n" +
                "<table width=\"100%\" height=\"100%\">\n" +
                "    <tr>\n" +
                "        <td align=\"center\" valign=\"center\">\n" +
                "            <font color=\"gray\">Some text you display</font>\n" +
                "            <br/>\n" +
                "            <br/>\n" +
                "            <br/>\n" +
                "            <br/>\n" +
                "            <br/>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>";*/
//        ui = getResources().getResourceEntryName(R.raw.tutor_home_page);
//        resFile = getResources().getIdentifier("raw/tutor_home_page", "raw", getPackageName());
//        Uri path = Uri.parse("android.resource://tutorsweb.ehc.com.tutorsinfogathering/raw/tutor_home_page");
//        webView.loadUrl(":///RegEzee/assets/tutor_home_page.html");
//        /home/ehc/Workspace/RegEzee/assets
        webView.loadUrl("http://www.tutorsweb.com/");

    }

}
