package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;


public class ProfessionalInfoActivity extends Activity implements View.OnClickListener {

    private Spinner profExpSpinner;
    private ArrayAdapter<CharSequence> adapter;
    private Button next;
    private Button previous;
    private ActionBar actionBar;
    private EditText tutoringExp;
    private EditText languages;
    private Spinner yrsOfTeachingExp;
    private EditText interests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_info);

        profExpSpinner = (Spinner) findViewById(R.id.teaching_exp);
        adapter = ArrayAdapter.createFromResource(this, R.array.years_of_exp, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profExpSpinner.setAdapter(adapter);

        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        yrsOfTeachingExp = (Spinner) findViewById(R.id.teaching_exp);
        tutoringExp = (EditText) findViewById(R.id.tutoring_exp);
        languages = (EditText) findViewById(R.id.languages);
        interests = (EditText) findViewById(R.id.interests);

        previous.setOnClickListener(this);
        next.setOnClickListener(this);


        setActionBarProperties();
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("Professional Information");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (doValidation()) {
                    Intent intent = new Intent(this, WorkExpActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.previous:
                onBackPressed();
                break;
        }
    }

    private boolean doValidation() {
        String tutoringExp = this.tutoringExp.getText().toString().trim();
        String languages = this.languages.getText().toString().trim();
        if (tutoringExp.equalsIgnoreCase("")) {
            this.tutoringExp.setError("Required Tutoring experience!");
            this.tutoringExp.requestFocus();
            return false;
        } else {
            this.tutoringExp.setError(null);
        }

        if (languages.equalsIgnoreCase("")) {
            this.languages.setError("Languages field can't be empty!");
            this.languages.requestFocus();
            return false;
        } else {
            this.languages.setError(null);
        }
        return true;
    }
}
