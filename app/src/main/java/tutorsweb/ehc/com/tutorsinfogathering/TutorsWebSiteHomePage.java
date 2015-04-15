package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class TutorsWebSiteHomePage extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutors_web_site_home_page);

        webView = (WebView) findViewById(R.id.web_view);
        webView.loadUrl("file:///android_asset/tutor_home_page.html");
    }
}
