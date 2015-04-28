package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
    private Button submit;
    private EditText clientName;
    private EditText addressLead;
    private EditText contactNumber;
    private EditText emailLead;
    private EditText notes;
    private TextView emailTextView;
    private TextView needFollowupTextView;
    private TextView contactNumberTextView;
    private RadioGroup needFollowup;
    private RadioButton selectedOptionId;
    private RadioButton yes;
    private RadioButton no;
    private ActionBar actionBar;
    private Spinner typeOfClient;
    private Spinner typeOfInteraction;

    private ArrayAdapter<CharSequence> clientAdapter;
    private ArrayAdapter<CharSequence> interactionAdapter;

    private String typeOfClientSelected;
    private String typeOfInteractionSelected;
    private int needFollowupSelection;
    private String selectedOptionIdText;
    private String selectedOptionIdValue;
    private String clientNameText;
    private String addressLeadText;
    private String contactNumberText;
    private String emailLeadText;
    private String notesText;
    private StringEntity entity;
    private String json;
    private int id;

    private DataBaseHelper dataBaseHelper;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEdit;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_capture_layout);

        sharedPreferences = getSharedPreferences(getString(R.string.signInCredentials), MODE_MULTI_PROCESS);
        sharedPreferencesEdit = sharedPreferences.edit();

        id = sharedPreferences.getInt(getString(R.string.userId), 0);

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
        interactionAdapter = ArrayAdapter.createFromResource(this, R.array.type_of_interaction, R.layout.simple_spinner_dropdown);
        interactionAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        typeOfInteraction.setAdapter(interactionAdapter);
    }

    private void setClientAdapter() {
        clientAdapter = ArrayAdapter.createFromResource(this, R.array.type_of_client, R.layout.simple_spinner_dropdown);
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

        emailTextView = (TextView) findViewById(R.id.email_text_view);
        contactNumberTextView = (TextView) findViewById(R.id.contact_number_text_view);
        needFollowupTextView = (TextView) findViewById(R.id.need_followup_text_view);

        Spannable emailSpannable = new SpannableString(emailTextView.getText().toString());
        int emailStarLoc = emailTextView.getText().toString().indexOf("*");
        emailSpannable.setSpan(new ForegroundColorSpan(Color.RED), emailStarLoc, emailStarLoc + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        emailTextView.setText(emailSpannable);

        Spannable contactNumberSpannable = new SpannableString(contactNumberTextView.getText().toString());
        int contactStarIndex = contactNumberTextView.getText().toString().indexOf("*");
        contactNumberSpannable.setSpan(new ForegroundColorSpan(Color.RED), contactStarIndex, contactStarIndex + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        contactNumberTextView.setText(contactNumberSpannable);

        Spannable needFollowUpSpannable = new SpannableString(needFollowupTextView.getText().toString());
        int needFollowUpStarIndex = needFollowupTextView.getText().toString().indexOf("*");
        needFollowUpSpannable.setSpan(new ForegroundColorSpan(Color.RED), needFollowUpStarIndex, needFollowUpStarIndex + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        needFollowupTextView.setText(needFollowUpSpannable);
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle(getString(R.string.lead_capture_title));
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
    public void onBackPressed() {
        Intent intent = new Intent(this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.type_of_client:
                typeOfClientSelected = parent.getItemAtPosition(position).toString();
                break;
            case R.id.type_of_interaction:
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
            contactNumber.setError(getString(R.string.mobile_number_required_msg));
            contactNumber.requestFocus();
            return false;
        } else {
            contactNumber.setError(null);
        }

        if (!Patterns.PHONE.matcher(mobileNumber).matches() && !TextUtils.isEmpty(mobileNumber)) {
            contactNumber.setError(getString(R.string.invalid_mobile_number_msg));
            contactNumber.requestFocus();
            return false;
        } else {
            contactNumber.setError(null);
        }

        if (emailId.equalsIgnoreCase("")) {
            emailLead.setError(getString(R.string.email_required_msg));
            emailLead.requestFocus();
            return false;
        } else {
            emailLead.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches() && !TextUtils.isEmpty(emailId)) {
            emailLead.setError(getString(R.string.invalid_email_msg));
            emailLead.requestFocus();
            return false;
        } else {
            emailLead.setError(null);
        }

        if (!(yes.isChecked() || no.isChecked())) {
            Toast.makeText(getApplicationContext(), getString(R.string.need_followup_must_be_checked_msg), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void postingLeadCaptureData() {
        sharedPreferencesEdit.putBoolean(getString(R.string.process), true);
        sharedPreferencesEdit.commit();
        json = createJsonObject();
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
        if (selectedOptionId != null)
            selectedOptionIdText = selectedOptionId.getText().toString();
        if (selectedOptionIdText.equalsIgnoreCase(getString(R.string.yes)))
            selectedOptionIdValue = getString(R.string.boolean_value_true);
        else
            selectedOptionIdValue = getString(R.string.boolean_value_false);
    }

    @Override
    public void populateData(String jsonResponse) {
        dataBaseHelper.deleteLeadCapture(Long.parseLong(jsonResponse));
    }

    @Override
    public void hideProgressBarOnFailure(String response) {

    }
}