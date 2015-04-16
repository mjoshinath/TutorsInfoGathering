package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class LeadCapture extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

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
    private boolean selectedOptionIdValue;
    private String clientNameText;
    private String addressLeadText;
    private String contactNumberText;
    private String emailLeadText;
    private String notesText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_capture_layout);

        getWidgets();

        setClientAdapter();
        setInteractionAdapter();

        applyActions();
        setActionBarProperties();
    }

    private void applyActions() {
        typeOfClient.setOnItemSelectedListener(this);
        typeOfInteraction.setOnItemSelectedListener(this);
        submit.setOnClickListener(this);
    }

    private void setInteractionAdapter() {
        interactionAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.type_of_interaction, android.R.layout.simple_spinner_item);
        interactionAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        typeOfInteraction.setAdapter(interactionAdapter);
    }

    private void setClientAdapter() {
        clientAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.type_of_client, android.R.layout.simple_spinner_item);
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
        switch (view.getId()) {
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
                getFieldsData();
                break;
        }
    }

    private void getFieldsData() {
        clientNameText = clientName.getText().toString();
        addressLeadText = addressLead.getText().toString();
        contactNumberText = contactNumber.getText().toString();
        emailLeadText = emailLead.getText().toString();
        notesText = notes.getText().toString();

        needFollowupSelection = needFollowup.getCheckedRadioButtonId();
        selectedOptionId = (RadioButton) findViewById(needFollowupSelection);
        selectedOptionIdText = selectedOptionId.getText().toString();
        if (selectedOptionIdText.equalsIgnoreCase("yes"))
            selectedOptionIdValue = true;
        else
            selectedOptionIdValue = false;
    }
}