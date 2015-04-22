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
import support.DataBaseHelper;

public class ReportsActivity extends Activity implements WebServiceCallBack {

    private ProgressBar tutorCircularProgressBar;
    private TextView progress;
    private ActionBar actionBar;
    private SharedPreferences signInCredentialsPrefs;
    private TextView tutorProgress;
    private TextView tutorTargetTextView;
    private Reports reports;
    private int tutorAchieved;
    private int tutorTarget;
    private TextView marketingExecutiveId;
    private TextView reportsFor;
    private ProgressBar instituteCircularProgressBar;
    private TextView instituteProgress;
    private TextView instituteTargetTextView;
    private ProgressBar leadCircularProgressBar;
    private TextView leadProgress;
    private TextView leadTargetTextView;
    private int instituteTarget;
    private int instituteAchieved;
    private int leadAchieved;
    private int leadTarget;
    private int i;
    private SharedPreferences.Editor sharedPreferencesEdit;
    private String targetPersonId;
    private String month;
    private String year;
    private SharedPreferences sharedPreferences;
    private DataBaseHelper dataBaseHelper;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        sharedPreferences = getSharedPreferences("signInCredentials", MODE_MULTI_PROCESS);
        sharedPreferencesEdit = sharedPreferences.edit();

        id = sharedPreferences.getInt("userId", 0);

        getWidgets();

        if (Network.isConnected(getApplicationContext())) {
            new WebserviceHelper(getApplicationContext()).getData(this, "staff_targets/staff/" + id);
        }
/*         else {
            Log.d("test111", "network fail...");
            tutorTarget = Integer.parseInt(sharedPreferences.getString("tutorTarget", ""));
            tutorAchieved = Integer.parseInt(sharedPreferences.getString("tutorAchieved", ""));
            instituteTarget = Integer.parseInt(sharedPreferences.getString("instituteTarget", ""));
            instituteAchieved = Integer.parseInt(sharedPreferences.getString("instituteAchieved", ""));
            leadTarget = Integer.parseInt(sharedPreferences.getString("leadTarget", ""));
            leadAchieved = Integer.parseInt(sharedPreferences.getString("leadAchieved", ""));
            targetPersonId = sharedPreferences.getString("targetPersonId", "");
            month = sharedPreferences.getString("month", "");
            year = sharedPreferences.getString("year", "");
            Log.d("test111", "data" + tutorTarget + "/" + tutorAchieved + "-" + instituteTarget + "/" + instituteAchieved + "-" + leadTarget + "/" + leadAchieved + "-" + targetPersonId + "," + month + "," + year);
            updateUi();
        }*/

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
        /*if (!Network.isConnected(getApplicationContext())) {
            marketingExecutiveId.setText("ID : " + id);
            reportsFor.setText("Reports for : " + month + " " + year);
        } else {*/
        marketingExecutiveId.setText("ID : " + id);
        if (reports.getData().size() != 0)
            reportsFor.setText("Reports for : " + reports.getData().get(0).getMonth() + " " + reports.getData().get(0).getYear());
//        }
        tutorCircularProgressBar.setProgress(tutorAchieved);
        tutorCircularProgressBar.setMax(tutorTarget);
        tutorTargetTextView.setText("Target : " + tutorTarget);
        tutorProgress.setText("" + tutorAchieved);
        Log.d("test111", "tutor" + tutorAchieved + "" + tutorTarget);

        instituteCircularProgressBar.setProgress(instituteAchieved);
        instituteCircularProgressBar.setMax(instituteTarget);
        instituteTargetTextView.setText("Target : " + instituteTarget);
        instituteProgress.setText("" + instituteAchieved);
        Log.d("test111", "institute" + instituteAchieved + "" + instituteTarget);

        leadCircularProgressBar.setProgress(leadAchieved);
        leadCircularProgressBar.setMax(leadTarget);
        leadTargetTextView.setText("Target : " + leadTarget);
        leadProgress.setText("" + leadAchieved);
        Log.d("test111", "lead" + leadAchieved + "" + leadTarget);
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
        Gson gson = new Gson();
        reports = gson.fromJson(jsonResponse, Reports.class);

        i = 0;
        while (i < reports.getData().size()) {
            if (reports.getData().get(i).getTargetType().toString().equalsIgnoreCase("tutor")) {
                tutorAchieved = reports.getData().get(i).getAchieved();
                tutorTarget = reports.getData().get(i).getTarget();
                Log.d("test111", "lead" + leadAchieved + "" + leadTarget);
            } else if (reports.getData().get(i).getTargetType().toString().equalsIgnoreCase("lead")) {
                leadAchieved = reports.getData().get(i).getAchieved();
                leadTarget = reports.getData().get(i).getTarget();
            } else if (reports.getData().get(i).getTargetType().toString().equalsIgnoreCase("company")) {
                instituteAchieved = reports.getData().get(i).getAchieved();
                instituteTarget = reports.getData().get(i).getTarget();
            }
            i++;
        }

//        reportsSharedPrefs();
        updateUi();
    }

   /* private void reportsSharedPrefs() {
        sharedPreferencesEdit.putString("tutorAchieved", "" + tutorAchieved);
        sharedPreferencesEdit.putString("tutorTarget", "" + tutorTarget);
        sharedPreferencesEdit.putString("instituteAchieved", "" + instituteAchieved);
        sharedPreferencesEdit.putString("instituteTarget", "" + instituteTarget);
        sharedPreferencesEdit.putString("leadAchieved", "" + leadAchieved);
        sharedPreferencesEdit.putString("leadTarget", "" + leadTarget);
        sharedPreferencesEdit.putString("targetPersonId", "" + reports.getData().get(0).getTargetPersonId());
        sharedPreferencesEdit.putString("month", "" + reports.getData().get(0).getMonth());
        sharedPreferencesEdit.putString("year", "" + reports.getData().get(0).getYear());
        sharedPreferencesEdit.commit();
    }*/

    @Override
    public void hideProgressBarOnFailure(String response) {
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
