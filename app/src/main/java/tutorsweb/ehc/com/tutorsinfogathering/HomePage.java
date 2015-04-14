package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import helper.WebServiceCallBack;
import helper.WebserviceHelper;
import model.categories.InstituteDetails;
import model.categories.TutorDetails;
import support.DataBaseHelper;


public class HomePage extends Activity implements View.OnClickListener, WebServiceCallBack {

    private Button signUpTutorButton;
    private ActionBar actionBar;
    private Button addMettingLog;

    private DataBaseHelper dataBaseHelper;
    private ArrayList<TutorDetails> multipleTutorDetails;
    private RequestParams requestParams;
    private Button syncDataButton;
    private SharedPreferences signInCredentialsPrefs;
    private SharedPreferences.Editor signInCredentialsPrefsEdit;
    private Button reportsButton;
    private Button documentationButton;
    private Button signUpInstituteButton;
    private ArrayList<InstituteDetails> multipleInstituteDetails;
    private int noOfUnsyncRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        signInCredentialsPrefs = getSharedPreferences("signInCredentials", MODE_MULTI_PROCESS);
        signInCredentialsPrefsEdit = signInCredentialsPrefs.edit();

        getWidgets();

        applyActions();

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        multipleTutorDetails = dataBaseHelper.getTutorDetails();
        multipleInstituteDetails = dataBaseHelper.getInstituteDetails();

        if (multipleInstituteDetails.size() > 0 || multipleTutorDetails.size() > 0) {
            noOfUnsyncRecords = multipleTutorDetails.size() + multipleInstituteDetails.size();
            syncDataButton.setText("Sync Data (" + noOfUnsyncRecords + " Unsync Record(s) )");
        }

        setActionBarProperties();
    }

    private void getWidgets() {
        signUpTutorButton = (Button) findViewById(R.id.signup_tutor);
        addMettingLog = (Button) findViewById(R.id.add_meeting_log);
        syncDataButton = (Button) findViewById(R.id.sync_data);
        reportsButton = (Button) findViewById(R.id.reports);
        documentationButton = (Button) findViewById(R.id.documentation);
        signUpInstituteButton = (Button) findViewById(R.id.signup_institute);
    }

    private void applyActions() {
        signUpTutorButton.setOnClickListener(this);
        addMettingLog.setOnClickListener(this);
        syncDataButton.setOnClickListener(this);
        reportsButton.setOnClickListener(this);
        documentationButton.setOnClickListener(this);
        signUpInstituteButton.setOnClickListener(this);
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("IReg");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            signInCredentialsPrefsEdit.clear();
            signInCredentialsPrefsEdit.commit();
            Intent intent = new Intent(this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_tutor:
                Intent intent = new Intent(this, RegStepsHostActivity.class);
                startActivity(intent);
                break;
            case R.id.signup_institute:
                Intent intent3 = new Intent(this, InstituteSignUpHostActivity.class);
                startActivity(intent3);
                break;
            case R.id.add_meeting_log:
                Intent webViewIntent = new Intent(this, ShowWebView.class);
                startActivity(webViewIntent);
                break;
            case R.id.sync_data:
                /*dataBaseHelper = new DataBaseHelper(getApplicationContext());
                multipleTutorDetails = dataBaseHelper.getTutorDetails();*/
                Log.d("test08", "multipleTutorDetails-" + multipleTutorDetails);
                if (multipleTutorDetails != null)
                    syncDataForTutor();
                if (multipleInstituteDetails != null)
                    syncDataForInstitute();
                break;
            case R.id.reports:
                Intent intent1 = new Intent(this, ReportsActivity.class);
                startActivity(intent1);
                break;
            case R.id.documentation:
                Intent intent2 = new Intent(this, DocumentationActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void syncDataForInstitute() {
        Iterator<InstituteDetails> instituteIterator = multipleInstituteDetails.iterator();
        while (instituteIterator.hasNext()) {
            InstituteDetails eachInstituteDetails = instituteIterator.next();
            Log.d("test111", "eachInstituteDetails>----->" + eachInstituteDetails);
            try {
                if (eachInstituteDetails.getDetails() == null) {
                    long tempId = eachInstituteDetails.getId();
                    dataBaseHelper.deleteInstitute(tempId);
                } else {
                    JSONObject eachInstituteDetailsInJsonFormat = new JSONObject(eachInstituteDetails.getDetails());
                    StringEntity entity = null;
                    entity = new StringEntity(eachInstituteDetailsInJsonFormat.toString());
                    Log.d("test08", "entity-" + entity);
                    new WebserviceHelper(getApplicationContext()).postData(this, entity, eachInstituteDetails.getId(), "institutes/staff/217");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void syncDataForTutor() {
        Iterator<TutorDetails> iterator = multipleTutorDetails.iterator();
        while (iterator.hasNext()) {
            TutorDetails eachTutorDetails = iterator.next();
            try {
                JSONObject eachTutorDetailsInJsonFormat = new JSONObject(eachTutorDetails.getDetails());
                StringEntity entity = null;
                entity = new StringEntity(eachTutorDetailsInJsonFormat.toString());
                Log.d("test08", "entity-" + entity);
                new WebserviceHelper(getApplicationContext()).postData(this, entity, eachTutorDetails.getId(), "tutors/staff/217");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void populateData(String jsonResponse) {
        dataBaseHelper.deleteTutor(Long.parseLong(jsonResponse));
        dataBaseHelper.deleteInstitute(Long.parseLong(jsonResponse));
    }

    @Override
    public void hideProgressBarOnFailure(String response) {
//        dataBaseHelper.deleteTutor(Long.parseLong(response));
//        dataBaseHelper.deleteInstitute(Long.parseLong(response));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
