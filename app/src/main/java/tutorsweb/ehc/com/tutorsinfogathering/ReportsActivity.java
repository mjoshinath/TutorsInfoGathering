package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import helper.Network;
import helper.WebServiceCallBack;
import helper.WebserviceHelper;

import model.categories.reports.Reports;

public class ReportsActivity extends Activity implements WebServiceCallBack {

    private ActionBar actionBar;
    private ProgressBar tutorCircularProgressBar;
    private ProgressBar instituteCircularProgressBar;
    private ProgressBar leadCircularProgressBar;
    private TextView tutorProgress;
    private TextView tutorTargetTextView;
    private TextView marketingExecutiveId;
    private TextView reportsFor;
    private TextView instituteProgress;
    private TextView instituteTargetTextView;
    private TextView leadProgress;
    private TextView leadTargetTextView;

    private Reports reports;

    private int tutorAchieved;
    private int tutorTarget;
    private int instituteTarget;
    private int instituteAchieved;
    private int leadAchieved;
    private int leadTarget;
    private int i;
    private int id;

    private SharedPreferences.Editor sharedPreferencesEdit;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        sharedPreferences = getSharedPreferences(getString(R.string.signInCredentials), MODE_MULTI_PROCESS);
        sharedPreferencesEdit = sharedPreferences.edit();

        id = sharedPreferences.getInt(getString(R.string.userId), 0);

        getWidgets();

        if (Network.isConnected(getApplicationContext())) {
            new WebserviceHelper(getApplicationContext()).getData(this, "staff_targets/staff/" + id);
        }

        setActionBarProperties();
    }

    private void getWidgets() {
        marketingExecutiveId = (TextView) findViewById(R.id.marketing_executive_id);
        reportsFor = (TextView) findViewById(R.id.reports_for);

        tutorCircularProgressBar = (ProgressBar) findViewById(R.id.tutor_progress_bar);
        tutorProgress = (TextView) findViewById(R.id.tutor_progress_text_view);
        tutorTargetTextView = (TextView) findViewById(R.id.tutor_target);

        instituteCircularProgressBar = (ProgressBar) findViewById(R.id.institute_progress_bar);
        instituteProgress = (TextView) findViewById(R.id.institute_progress_text_view);
        instituteTargetTextView = (TextView) findViewById(R.id.institute_target);

        leadCircularProgressBar = (ProgressBar) findViewById(R.id.lead_progress_bar);
        leadProgress = (TextView) findViewById(R.id.lead_progress_text_view);
        leadTargetTextView = (TextView) findViewById(R.id.lead_target);
    }

    private void updateUi() {
        marketingExecutiveId.setText("ID : " + id);

        if (reports.getData().size() != 0)
            reportsFor.setText("Reports for : " + reports.getData().get(0).getMonth() + " " + reports.getData().get(0).getYear());

        tutorCircularProgressBar.setProgress(tutorAchieved);
        tutorCircularProgressBar.setMax(tutorTarget);
        tutorTargetTextView.setText("Target : " + tutorTarget);
        tutorProgress.setText("" + tutorAchieved);

        instituteCircularProgressBar.setProgress(instituteAchieved);
        instituteCircularProgressBar.setMax(instituteTarget);
        instituteTargetTextView.setText("Target : " + instituteTarget);
        instituteProgress.setText("" + instituteAchieved);

        leadCircularProgressBar.setProgress(leadAchieved);
        leadCircularProgressBar.setMax(leadTarget);
        leadTargetTextView.setText("Target : " + leadTarget);
        leadProgress.setText("" + leadAchieved);
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle(getString(R.string.reports_title));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void populateData(String jsonResponse) {
        Gson gson = new Gson();
        reports = gson.fromJson(jsonResponse, Reports.class);

        i = 0;
        while (i < reports.getData().size()) {
            if (reports.getData().get(i).getTargetType().toString().equalsIgnoreCase(getString(R.string.tutor))) {
                tutorAchieved = reports.getData().get(i).getAchieved();
                tutorTarget = reports.getData().get(i).getTarget();
            } else if (reports.getData().get(i).getTargetType().toString().equalsIgnoreCase(getString(R.string.lead))) {
                leadAchieved = reports.getData().get(i).getAchieved();
                leadTarget = reports.getData().get(i).getTarget();
            } else if (reports.getData().get(i).getTargetType().toString().equalsIgnoreCase(getString(R.string.company))) {
                instituteAchieved = reports.getData().get(i).getAchieved();
                instituteTarget = reports.getData().get(i).getTarget();
            }
            i++;
        }

        updateUi();
    }

    @Override
    public void hideProgressBarOnFailure(String response) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
