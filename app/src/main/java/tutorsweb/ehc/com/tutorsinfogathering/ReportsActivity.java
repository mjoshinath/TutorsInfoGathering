package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ReportsActivity extends Activity {

    private ProgressBar circularProgressBar;
    private TextView progress;
    private ActionBar actionBar;
    private SharedPreferences signInCredentialsPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        circularProgressBar = (ProgressBar) findViewById(R.id.tutor_progress_bar);
        progress = (TextView) findViewById(R.id.progress_text_view);

        int progressValue = 30;

        circularProgressBar.setProgress(progressValue);
        progress.setText("" + progressValue);

        setActionBarProperties();
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("Reports");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(this, HomePage.class);
                startActivity(intent1);
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

}
