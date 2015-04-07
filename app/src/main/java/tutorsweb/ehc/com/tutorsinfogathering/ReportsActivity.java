package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class ReportsActivity extends Activity {

    private ProgressBar circularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        circularProgressBar = (ProgressBar) findViewById(R.id.circularProgressBar);
        circularProgressBar.setProgress(50);
    }

}
