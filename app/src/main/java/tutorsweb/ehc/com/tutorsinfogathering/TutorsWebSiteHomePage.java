package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class TutorsWebSiteHomePage extends Activity {

    private WebView webView;
    private String ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors_web_site_home_page);

        webView = (WebView) findViewById(R.id.web_view);
        ui = getResources().getResourceEntryName(R.raw.tutor_home_page);
        webView.loadData(ui, "text/html", "UTF-8");
    }

}
