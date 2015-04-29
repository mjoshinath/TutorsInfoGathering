package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import helper.Network;
import helper.WebServiceCallBack;
import helper.WebserviceHelper;

import model.categories.InstituteDetails;
import model.categories.LeadCaptureDetailsDBModel;
import model.categories.TutorDetails;

import support.DataBaseHelper;

public class HomePage extends Activity implements View.OnClickListener, WebServiceCallBack {

    private Button signUpTutorButton;
    private Button syncDataButton;
    private Button reportsButton;
    private Button documentationButton;
    private Button signUpInstituteButton;
    private Button tutorsWebSiteButton;
    private Button leadCaptureButton;

    private ActionBar actionBar;

    private DataBaseHelper dataBaseHelper;

    private ArrayList<TutorDetails> multipleTutorDetails;
    private ArrayList<InstituteDetails> multipleInstituteDetails;
    private ArrayList<LeadCaptureDetailsDBModel> multipleLeadCaptureDetails;

    private SharedPreferences signInCredentialsPrefs;
    private SharedPreferences.Editor signInCredentialsPrefsEdit;
    private SharedPreferences categorySharedPref;
    private SharedPreferences.Editor categoryEditor;

    private int noOfUnSyncRecords = 0;
    private Intent intent;
    private int id;
    private TextView toastTextView;
    private View toastView;
    private long count;
    private int syncDataCount = 0;
    private ProgressBar homeProgressBar;
    private RelativeLayout homeLayout;
    private AlertDialog dataSyncAlert;
    private AlertDialog.Builder builder;

    private ResponseReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_common);

        getSharedPreferences();

        toastView = setToastLayout();

        id = signInCredentialsPrefs.getInt(getString(R.string.userId), 0);

        getWidgets();

        applyActions();

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        getLocalStorageData();

        setUnSyncDataNotification();

//        new UpdateUi().execute("");

        setActionBarProperties();

        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);
    }

    private void getSharedPreferences() {
        categorySharedPref = getSharedPreferences(getString(R.string.categories), Context.MODE_MULTI_PROCESS);
        categoryEditor = categorySharedPref.edit();

        signInCredentialsPrefs = getSharedPreferences(getString(R.string.signInCredentials), MODE_MULTI_PROCESS);
        signInCredentialsPrefsEdit = signInCredentialsPrefs.edit();
    }

    private void getLocalStorageData() {
        multipleTutorDetails = dataBaseHelper.getTutorDetails();
        multipleInstituteDetails = dataBaseHelper.getInstituteDetails();
        multipleLeadCaptureDetails = dataBaseHelper.getLeadCaptureDetails();
    }

    private void setUnSyncDataNotification() {
        if (multipleInstituteDetails.size() > 0)
            noOfUnSyncRecords = noOfUnSyncRecords + multipleInstituteDetails.size();
        if (multipleTutorDetails.size() > 0)
            noOfUnSyncRecords = noOfUnSyncRecords + multipleTutorDetails.size();
        if (multipleLeadCaptureDetails.size() > 0)
            noOfUnSyncRecords = noOfUnSyncRecords + multipleLeadCaptureDetails.size();
        if (noOfUnSyncRecords > 0)
            syncDataButton.setText("Sync Data ( " + noOfUnSyncRecords + " Unsync Record(s) )");
        syncDataCount = noOfUnSyncRecords;
    }

    private void getWidgets() {
        signUpTutorButton = (Button) findViewById(R.id.signup_tutor);
        leadCaptureButton = (Button) findViewById(R.id.lead_capture);
        syncDataButton = (Button) findViewById(R.id.sync_data);
        reportsButton = (Button) findViewById(R.id.reports);
        documentationButton = (Button) findViewById(R.id.documentation);
        signUpInstituteButton = (Button) findViewById(R.id.signup_institute);
        tutorsWebSiteButton = (Button) findViewById(R.id.tutors_web_site);

        homeProgressBar = (ProgressBar) findViewById(R.id.home_progress_bar);
        homeLayout = (RelativeLayout) findViewById(R.id.home_layout);
    }

    private void applyActions() {
        signUpTutorButton.setOnClickListener(this);
        leadCaptureButton.setOnClickListener(this);
        syncDataButton.setOnClickListener(this);
        reportsButton.setOnClickListener(this);
        documentationButton.setOnClickListener(this);
        signUpInstituteButton.setOnClickListener(this);
        tutorsWebSiteButton.setOnClickListener(this);
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle(getString(R.string.ireg_title));
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
            categoryEditor.putBoolean(getString(R.string.logDetect), false);
            categoryEditor.commit();
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
                intent = new Intent(this, RegStepsHostActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.signup_institute:
                intent = new Intent(this, InstituteSignUpHostActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.lead_capture:
                intent = new Intent(this, LeadCapture.class);
                finish();
                startActivity(intent);
                break;
            case R.id.sync_data:
                startService(new Intent(this, SyncDataService.class));
//                syncLocalStorageDataToServer();
                break;
            case R.id.reports:
                callForReportsActivity();
                break;
            case R.id.documentation:
                intent = new Intent(this, DocumentationActivity.class);
                startActivity(intent);
                break;
            case R.id.tutors_web_site:
                intent = new Intent(this, TutorsWebSiteHomePage.class);
                startActivity(intent);
                break;
        }
    }

    private void callForReportsActivity() {
        if (Network.isConnected(getApplicationContext())) {
            intent = new Intent(this, ReportsActivity.class);
            startActivity(intent);
        } else {
            toastTextView.setText(getString(R.string.network_not_connected_msg));
            toastMessageProperties(toastView);
        }
    }

    /*private void syncLocalStorageDataToServer() {
        if (noOfUnSyncRecords == 0) {
            toastTextView.setText(getString(R.string.no_data_available_to_sync_msg));
            toastMessageProperties(toastView);
        } else if (Network.isConnected(getApplicationContext())) {
            new BackgroundOperation().execute("");
            signInCredentialsPrefsEdit.putBoolean(getString(R.string.process), false);
            signInCredentialsPrefsEdit.commit();

            if (multipleTutorDetails != null)
                syncDataForTutor();
            if (multipleInstituteDetails != null)
                syncDataForInstitute();
            if (multipleLeadCaptureDetails != null)
                syncDataForLeadCapture();
        } else {
            toastTextView.setText(getString(R.string.network_not_connected_msg));
            toastMessageProperties(toastView);
        }
    }*/

    /*private void syncDataForInstitute() {
        Iterator<InstituteDetails> instituteIterator = multipleInstituteDetails.iterator();
        while (instituteIterator.hasNext()) {
            InstituteDetails eachInstituteDetails = instituteIterator.next();
            try {
                JSONObject eachInstituteDetailsInJsonFormat = new JSONObject(eachInstituteDetails.getDetails());
                StringEntity entity = null;
                entity = new StringEntity(eachInstituteDetailsInJsonFormat.toString());
                new WebserviceHelper(getApplicationContext()).postData(this, entity, eachInstituteDetails.getId(), "institutes/staff/" + id);
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
                new WebserviceHelper(getApplicationContext()).postData(this, entity, eachTutorDetails.getId(), "tutors/staff/" + id);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void syncDataForLeadCapture() {
        Iterator<LeadCaptureDetailsDBModel> iterator = multipleLeadCaptureDetails.iterator();
        while (iterator.hasNext()) {
            LeadCaptureDetailsDBModel eachLeadCaptureDetails = iterator.next();
            try {
                JSONObject eachLeadCaptureDetailsInJsonFormat = new JSONObject(eachLeadCaptureDetails.getDetails());
                StringEntity entity = null;
                entity = new StringEntity(eachLeadCaptureDetailsInJsonFormat.toString());
                new WebserviceHelper(getApplicationContext()).postData(this, entity, eachLeadCaptureDetails.getId(), "lead_capture/staff/" + id);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }*/

    @Override
    public void populateData(String jsonResponse) {
        dataBaseHelper.deleteTutor(Long.parseLong(jsonResponse));
        dataBaseHelper.deleteInstitute(Long.parseLong(jsonResponse));
        dataBaseHelper.deleteLeadCapture(Long.parseLong(jsonResponse));
        syncDataCount = syncDataCount - 1;
    }

    @Override
    public void hideProgressBarOnFailure(String response) {
        syncDataCount = syncDataCount - 1;
        dataSyncAlert.dismiss();
    }

    private View setToastLayout() {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.toast_view, null);
        toastTextView = (TextView) layout.findViewById(R.id.toast_message_text_view);
        return layout;
    }

    private void toastMessageProperties(View layout) {
        Toast toast = new Toast(getApplicationContext());
        toast.setView(layout);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 36);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.exit_confirmation_title));
        builder.setMessage(getString(R.string.exit_confirmation_msg));
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                    }
                });
        builder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class BackgroundOperation extends AsyncTask<String, Long, String> {

        @Override
        protected String doInBackground(String... params) {
            while (syncDataCount != 0) {
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            count = dataBaseHelper.getRecordsCountFromDB();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dataSyncAlert.dismiss();
            if (count > 0) {
                syncDataButton.setText("Sync Data ( " + count + " Unsync Record(s) )");
                toastTextView.setText(getString(R.string.processed_incompletely_msg));
                toastMessageProperties(toastView);
            } else {
                syncDataButton.setText(getString(R.string.sync_data));
                toastTextView.setText(getString(R.string.process_completed_successfully_msg));
                toastMessageProperties(toastView);
            }
            noOfUnSyncRecords = (int) count;
        }

        @Override
        protected void onPreExecute() {
            builder = new AlertDialog.Builder(HomePage.this);
//            builder.setMessage("Processing...");
            builder.setTitle(getString(R.string.processing_msg));
            builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.setCancelable(false);
            dataSyncAlert = builder.create();
            dataSyncAlert.show();
        }
    }

    /*private class UpdateUi extends AsyncTask<String, Long, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count = dataBaseHelper.getRecordsCountFromDB();
            return "" + count;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (count > 0)
                syncDataButton.setText("Sync Data ( " + s + " Unsync Record(s) )");
            else
                syncDataButton.setText(getString(R.string.sync_data));
        }
    }*/

    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP =
                "intent.action.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("test111", "ResponseReceiver");
            int count = intent.getIntExtra("count", 0);
            Log.d("test111", "count"+count);

            if (count > 0)
                syncDataButton.setText("Sync Data ( " + count + " Unsync Record(s) )");
            else
                syncDataButton.setText(getString(R.string.sync_data));
        }
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiver);
        super.onStop();
    }
}
