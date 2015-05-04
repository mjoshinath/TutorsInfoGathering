package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

public class SyncDataService extends IntentService implements WebServiceCallBack {

    private DataBaseHelper dataBaseHelper;
    private ArrayList<TutorDetails> multipleTutorDetails;
    private ArrayList<InstituteDetails> multipleInstituteDetails;
    private ArrayList<LeadCaptureDetailsDBModel> multipleLeadCaptureDetails;
    private int noOfUnSyncRecords = 0;
    private int syncDataCount = 0;
    private SharedPreferences signInCredentialsPrefs;
    private SharedPreferences.Editor signInCredentialsPrefsEdit;
    private TextView toastTextView;
    private View toastView;
    private int id;

    public SyncDataService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        signInCredentialsPrefs = getSharedPreferences(getString(R.string.signInCredentials), MODE_MULTI_PROCESS);
        signInCredentialsPrefsEdit = signInCredentialsPrefs.edit();

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        id = signInCredentialsPrefs.getInt(getString(R.string.userId), 0);
        Log.d("test111", "id" + id);

        toastView = setToastLayout();

        getLocalStorageData();
        setUnSyncDataNotification();
        syncLocalStorageDataToServer();
    }

    private void getLocalStorageData() {
        multipleTutorDetails = dataBaseHelper.getTutorDetails();
        multipleInstituteDetails = dataBaseHelper.getInstituteDetails();
        multipleLeadCaptureDetails = dataBaseHelper.getLeadCaptureDetails();
        Log.d("test111", "data" + multipleTutorDetails + "/" + multipleInstituteDetails + "/" + multipleLeadCaptureDetails);
    }

    private void setUnSyncDataNotification() {
        if (multipleInstituteDetails.size() > 0)
            noOfUnSyncRecords = noOfUnSyncRecords + multipleInstituteDetails.size();
        if (multipleTutorDetails.size() > 0)
            noOfUnSyncRecords = noOfUnSyncRecords + multipleTutorDetails.size();
        if (multipleLeadCaptureDetails.size() > 0)
            noOfUnSyncRecords = noOfUnSyncRecords + multipleLeadCaptureDetails.size();
        if (noOfUnSyncRecords > 0)
            syncDataCount = noOfUnSyncRecords;
    }

    private void syncLocalStorageDataToServer() {
        Log.d("test111", "syncLocalStorageDataToServer");
        /*if (noOfUnSyncRecords == 0) {
            toastTextView.setText(getString(R.string.no_data_available_to_sync_msg));
            toastMessageProperties(toastView);
        } else if (Network.isConnected(getApplicationContext())) {*/
        Log.d("test111", "syncLocalStorageDataToServer1");

        signInCredentialsPrefsEdit.putBoolean(getString(R.string.process), false);
        signInCredentialsPrefsEdit.commit();

        if (multipleTutorDetails != null)
            syncDataForTutor();
        if (multipleInstituteDetails != null)
            syncDataForInstitute();
        if (multipleLeadCaptureDetails != null)
            syncDataForLeadCapture();
        Log.d("test111", "syncLocalStorageDataToServer2");

        /*} else {
            toastTextView.setText(getString(R.string.network_not_connected_msg));
            toastMessageProperties(toastView);
        }*/
    }

    private void syncDataForInstitute() {
        Log.d("test111", "syncDataForInstitute1");

        Iterator<InstituteDetails> instituteIterator = multipleInstituteDetails.iterator();
        while (instituteIterator.hasNext()) {
            InstituteDetails eachInstituteDetails = instituteIterator.next();
            try {
                JSONObject eachInstituteDetailsInJsonFormat = new JSONObject(eachInstituteDetails.getDetails());
                StringEntity entity = null;
                entity = new StringEntity(eachInstituteDetailsInJsonFormat.toString());
//                new WebserviceHelper(getApplicationContext()).postData(this, entity, eachInstituteDetails.getId(), "institutes/staff/" + id);
                new WebserviceHelper(getApplicationContext()).postSyncData(this, eachInstituteDetails.getId(), "institutes/staff/", entity, id);
                Log.d("test111", "syncDataForInstitute2");

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void syncDataForTutor() {
        Log.d("test111", "syncDataForTutor1");
        Iterator<TutorDetails> iterator = multipleTutorDetails.iterator();
        while (iterator.hasNext()) {
            TutorDetails eachTutorDetails = iterator.next();
            try {
                JSONObject eachTutorDetailsInJsonFormat = new JSONObject(eachTutorDetails.getDetails());
                StringEntity entity = null;
                entity = new StringEntity(eachTutorDetailsInJsonFormat.toString());
//                new WebserviceHelper(getApplicationContext()).postData(this, entity, eachTutorDetails.getId(), "tutors/staff/" + id);
                new WebserviceHelper(getApplicationContext()).postSyncData(this, eachTutorDetails.getId(), "tutors/staff/", entity, id);
                Log.d("test111", "syncDataForTutor2");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void syncDataForLeadCapture() {
        Log.d("test111", "syncDataForLeadCapture1");
        Iterator<LeadCaptureDetailsDBModel> iterator = multipleLeadCaptureDetails.iterator();
        while (iterator.hasNext()) {
            LeadCaptureDetailsDBModel eachLeadCaptureDetails = iterator.next();
            try {
                JSONObject eachLeadCaptureDetailsInJsonFormat = new JSONObject(eachLeadCaptureDetails.getDetails());
                StringEntity entity = null;
                entity = new StringEntity(eachLeadCaptureDetailsInJsonFormat.toString());
//                new WebserviceHelper(getApplicationContext()).postData(this, entity, eachLeadCaptureDetails.getId(), "lead_capture/staff/" + id);
                new WebserviceHelper(getApplicationContext()).postSyncData(this, eachLeadCaptureDetails.getId(), "lead_capture/staff/", entity, id);
                Log.d("test111", "syncDataForLeadCapture2");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
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
    public void populateData(String jsonResponse) {
        Log.d("test111", "populateData1");
        dataBaseHelper.deleteTutor(Long.parseLong(jsonResponse));
        dataBaseHelper.deleteInstitute(Long.parseLong(jsonResponse));
        dataBaseHelper.deleteLeadCapture(Long.parseLong(jsonResponse));
        syncDataCount = syncDataCount - 1;
        Log.d("test111", "populateData2");

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(HomePage.ResponseReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra("count", (int) dataBaseHelper.getRecordsCountFromDB());
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void hideProgressBarOnFailure(String response) {
        syncDataCount = syncDataCount - 1;
        Log.d("test111", "hideProgressBarOnFailure");
    }
}
