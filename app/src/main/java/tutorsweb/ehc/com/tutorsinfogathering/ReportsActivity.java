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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import helper.WebServiceCallBack;
import helper.WebserviceHelper;
import model.categories.Category;
import model.categories.reports.Reports;

public class ReportsActivity extends Activity implements WebServiceCallBack {

    private ProgressBar circularProgressBar;
    private TextView progress;
    private ActionBar actionBar;
    private SharedPreferences signInCredentialsPrefs;
    private Reports reports;
    private String achieved;
    private String target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        circularProgressBar = (ProgressBar) findViewById(R.id.tutor_progress_bar);
        progress = (TextView) findViewById(R.id.progress_text_view);

        circularProgressBar.setProgress(Integer.parseInt(achieved));
        progress.setText("" + achieved);

        String id = "217";
        new WebserviceHelper(getApplicationContext()).getData(this, "staff_targets/staff/" + id);

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

    @Override
    public void populateData(String jsonResponse) {
        if (jsonResponse != null && !jsonResponse.isEmpty()) {
            reports = new Gson().fromJson(jsonResponse, new TypeToken<ArrayList<Category>>() {
            }.getType());
            if (reports.getStatus().equalsIgnoreCase("success")) {
                if (reports.getData().get(8).toString().equalsIgnoreCase("tutor")) {
                    achieved = reports.getData().get(9).toString();
                    target = reports.getData().get(7).toString();
                }
            }
        } else {

        }
    }

    @Override
    public void hideProgressBarOnFailure(String response) {

    }
}
