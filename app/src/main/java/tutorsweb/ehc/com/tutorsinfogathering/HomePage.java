package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        signInCredentialsPrefs = getSharedPreferences("signInCredentials", MODE_MULTI_PROCESS);
        signInCredentialsPrefsEdit = signInCredentialsPrefs.edit();

        signUpTutorButton = (Button) findViewById(R.id.signup_tutor);
        addMettingLog = (Button) findViewById(R.id.add_meeting_log);
        syncDataButton = (Button) findViewById(R.id.sync_data);
        reportsButton = (Button) findViewById(R.id.reports);

        signUpTutorButton.setOnClickListener(this);
        addMettingLog.setOnClickListener(this);
        syncDataButton.setOnClickListener(this);
        reportsButton.setOnClickListener(this);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        multipleTutorDetails = dataBaseHelper.getTutorDetails();
        Log.d("test111", "" + multipleTutorDetails.size());
        if (multipleTutorDetails.size() > 0)
            syncDataButton.setText("Sync Data ( " + multipleTutorDetails.size() + " record(s) )");

        setActionBarProperties();
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
            case R.id.add_meeting_log:
                Intent webViewIntent = new Intent(this, ShowWebView.class);
                startActivity(webViewIntent);
                break;
            case R.id.sync_data:
                /*dataBaseHelper = new DataBaseHelper(getApplicationContext());
                multipleTutorDetails = dataBaseHelper.getTutorDetails();*/
                Log.d("test08", "multipleTutorDetails-" + multipleTutorDetails);
                Iterator<TutorDetails> iterator = multipleTutorDetails.iterator();
                while (iterator.hasNext()) {
                    TutorDetails eachTutorDetails = iterator.next();
                    try {
                        JSONObject eachTutorDetailsInJsonFormat = new JSONObject(eachTutorDetails.getDetails());
                        StringEntity entity = null;
                        entity = new StringEntity(eachTutorDetailsInJsonFormat.toString());
                        Log.d("test08", "entity-" + entity);
                        new WebserviceHelper(getApplicationContext()).postData(this, entity, eachTutorDetails.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.reports:
                Intent intent1 = new Intent(this, ReportsActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void populateData(String jsonResponse) {
        dataBaseHelper.delete(Long.parseLong(jsonResponse));
    }

    @Override
    public void hideProgressBarOnFailure(String response) {
        dataBaseHelper.delete(Long.parseLong(response));
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
