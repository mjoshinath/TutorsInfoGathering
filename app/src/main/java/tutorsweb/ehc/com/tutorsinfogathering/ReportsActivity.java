package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Iterator;

import helper.WebServiceCallBack;
import helper.WebserviceHelper;
import model.categories.reports.Reports;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        getWidgets();

        String id = "217";
        new WebserviceHelper(getApplicationContext()).getData(this, "staff_targets/staff/" + id);


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
        marketingExecutiveId.setText("ID : " + reports.getData().get(0).getTargetPersonId().toString());
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
            } else if (reports.getData().get(i).getTargetType().toString().equalsIgnoreCase("lead")) {
                leadAchieved = reports.getData().get(i).getAchieved();
                leadTarget = reports.getData().get(i).getTarget();
            } else if (reports.getData().get(i).getTargetType().toString().equalsIgnoreCase("company")) {
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

    /*private Bitmap imageResize(int weight, Bitmap bitmapImage) {
        switch (weight) {
            case 2:
                return Bitmap.createScaledBitmap(bitmapImage, 180, 180, true);
            case 3:
                return Bitmap.createScaledBitmap(bitmapImage, 232, 180, true);
            case 4:
                return Bitmap.createScaledBitmap(bitmapImage, 480, 300, true);
            case 6:
                return Bitmap.createScaledBitmap(bitmapImage, 480, 300, true);
        }
        return bitmapImage;
    }*/
}
