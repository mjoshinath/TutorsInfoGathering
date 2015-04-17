package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

import helper.Network;
import helper.WebServiceCallBack;
import helper.WebserviceHelper;
import model.categories.lead_capture.LeadCaptureDetails;
import model.categories.lead_capture.LeadCaptureModel;
import support.DataBaseHelper;

public class LeadCapture extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener, WebServiceCallBack {

    private WebView webView;
    private ActionBar actionBar;
    private Spinner typeOfClient;
    private Spinner typeOfInteraction;
    private ArrayAdapter<CharSequence> clientAdapter;
    private ArrayAdapter<CharSequence> interactionAdapter;
    private String typeOfClientSelected;
    private String typeOfInteractionSelected;

    private Button submit;
    private EditText clientName;
    private EditText addressLead;
    private EditText contactNumber;
    private EditText emailLead;
    private EditText notes;
    private RadioGroup needFollowup;
    private int needFollowupSelection;
    private RadioButton selectedOptionId;
    private String selectedOptionIdText;
    private String selectedOptionIdValue;
    private String clientNameText;
    private String addressLeadText;
    private String contactNumberText;
    private String emailLeadText;
    private String notesText;
    private StringEntity entity;
    private String json;
    private DataBaseHelper dataBaseHelper;
    private RadioButton yes;
    private RadioButton no;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEdit;
    private int id;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_capture_layout);

        sharedPreferences = getSharedPreferences("signInCredentials", MODE_MULTI_PROCESS);
        sharedPreferencesEdit = sharedPreferences.edit();

        id = sharedPreferences.getInt("userId", 0);

        getWidgets();

        setClientAdapter();
        setInteractionAdapter();
        applyActions();

        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        setActionBarProperties();
    }

    private void applyActions() {
        typeOfClient.setOnItemSelectedListener(this);
        typeOfInteraction.setOnItemSelectedListener(this);
        submit.setOnClickListener(this);
    }

    private void setInteractionAdapter() {
        interactionAdapter = ArrayAdapter.createFromResource(this, R.array.type_of_interaction, android.R.layout.simple_spinner_item);
        interactionAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        typeOfInteraction.setAdapter(interactionAdapter);
    }

    private void setClientAdapter() {
        clientAdapter = ArrayAdapter.createFromResource(this, R.array.type_of_client, android.R.layout.simple_spinner_item);
        clientAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        typeOfClient.setAdapter(clientAdapter);
    }

    private void getWidgets() {
        clientName = (EditText) findViewById(R.id.client_name);
        addressLead = (EditText) findViewById(R.id.address_lead);
        typeOfClient = (Spinner) findViewById(R.id.type_of_client);
        typeOfInteraction = (Spinner) findViewById(R.id.type_of_interaction);
        contactNumber = (EditText) findViewById(R.id.contact_number);
        emailLead = (EditText) findViewById(R.id.email_lead);
        notes = (EditText) findViewById(R.id.notes);
        needFollowup = (RadioGroup) findViewById(R.id.need_followup);
        submit = (Button) findViewById(R.id.submit_lead);
        yes = (RadioButton) findViewById(R.id.yes);
        no = (RadioButton) findViewById(R.id.no);
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("Lead Capture");
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("test888", "onItemSelected");
        switch (parent.getId()) {
            case R.id.type_of_client:
                Log.d("test888", "type_of_client");
                typeOfClientSelected = parent.getItemAtPosition(position).toString();
                break;
            case R.id.type_of_interaction:
                Log.d("test888", "type_of_interaction:");
                typeOfInteractionSelected = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_lead:
                if (doValidation()) {
                    getFieldsData();
                    postingLeadCaptureData();
                }
                break;
        }
    }

    private boolean doValidation() {
        String emailId = emailLead.getText().toString().trim();
        String mobileNumber = contactNumber.getText().toString().trim();

        if (mobileNumber.equalsIgnoreCase("")) {
            contactNumber.setError("Mobile Number Required!");
            contactNumber.requestFocus();
            return false;
        } else {
            contactNumber.setError(null);
        }

        if (!Patterns.PHONE.matcher(mobileNumber).matches() && !TextUtils.isEmpty(mobileNumber)) {
            contactNumber.setError("Invalid Mobile Number");
            contactNumber.requestFocus();
            return false;
        } else {
            contactNumber.setError(null);
        }

        if (emailId.equalsIgnoreCase("")) {
            emailLead.setError("Email Required!");
            emailLead.requestFocus();
            return false;
        } else {
            emailLead.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches() && !TextUtils.isEmpty(emailId)) {
            emailLead.setError("Invalid Email");
            emailLead.requestFocus();
            return false;
        } else {
            emailLead.setError(null);
        }

        if (!(yes.isChecked() || no.isChecked())) {
            Toast.makeText(getApplicationContext(), "Need Followup must be Checked!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void postingLeadCaptureData() {
        json = createJsonObject();
        Log.d("test999", "json--->" + json);
        if (Network.isConnected(getApplicationContext())) {
            try {
                entity = new StringEntity(json);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            new WebserviceHelper(getApplicationContext()).postData(this, entity, 0L, "lead_capture/staff/" + id);
        } else {
            dataBaseHelper.insertLeadCaptureDetails(json);
        }
        Intent intent = new Intent(this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private String createJsonObject() {
        LeadCaptureDetails leadCaptureDetails = new LeadCaptureDetails();

        leadCaptureDetails.setClientName(clientNameText);
        leadCaptureDetails.setAddress(addressLeadText);
        leadCaptureDetails.setContactNo(contactNumberText);
        leadCaptureDetails.setEmail(emailLeadText);
        leadCaptureDetails.setNotes(notesText);
        leadCaptureDetails.setClientType(typeOfClientSelected);
        leadCaptureDetails.setInteractionType(typeOfInteractionSelected);
        Log.d("test999", typeOfClientSelected + " and " + typeOfInteractionSelected);
        leadCaptureDetails.setFollowUp(selectedOptionIdValue);

        LeadCaptureModel leadCaptureModel = new LeadCaptureModel();
        leadCaptureModel.setLeadCapture(leadCaptureDetails);

        return new Gson().toJson(leadCaptureModel);
    }

    private void getFieldsData() {
        clientNameText = clientName.getText().toString();
        addressLeadText = addressLead.getText().toString();
        contactNumberText = contactNumber.getText().toString();
        emailLeadText = emailLead.getText().toString();
        notesText = notes.getText().toString();

        needFollowupSelection = needFollowup.getCheckedRadioButtonId();
        selectedOptionId = (RadioButton) findViewById(needFollowupSelection);
        Log.d("test777", "selectedOptionId-->" + selectedOptionId + ",selectedOptionIdText-->" + selectedOptionIdText);
        if (selectedOptionId != null)
            selectedOptionIdText = selectedOptionId.getText().toString();
        if (selectedOptionIdText.equalsIgnoreCase("yes"))
            selectedOptionIdValue = "true";
        else
            selectedOptionIdValue = "false";
    }

    @Override
    public void populateData(String jsonResponse) {
        dataBaseHelper.deleteLeadCapture(Long.parseLong(jsonResponse));
    }

    @Override
    public void hideProgressBarOnFailure(String response) {

    }
}