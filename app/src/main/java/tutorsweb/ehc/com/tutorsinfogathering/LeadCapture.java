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

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_capture_layout);

        submit = (Button) findViewById(R.id.submit_lead);

        typeOfClient = (Spinner) findViewById(R.id.type_of_client);
        typeOfInteraction = (Spinner) findViewById(R.id.type_of_interaction);

        clientAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.type_of_client, android.R.layout.simple_spinner_item);
        clientAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        typeOfClient.setAdapter(clientAdapter);

        interactionAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.type_of_interaction, android.R.layout.simple_spinner_item);
        interactionAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        typeOfInteraction.setAdapter(interactionAdapter);

        typeOfClient.setOnItemSelectedListener(this);
        typeOfInteraction.setOnItemSelectedListener(this);
        submit.setOnClickListener(this);

        setActionBarProperties();
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
                break;
        }
    }
}