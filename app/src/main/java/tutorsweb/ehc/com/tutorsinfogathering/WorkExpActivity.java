package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import support.DatePickerFragment;


public class WorkExpActivity extends Activity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private EditText startDateButton;
    private EditText endDateButton;
    private Button next;
    private EditText startDateWorkExpButton;
    private EditText endDateWorkExpButton;
    private Button previous;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_exp);

        startDateButton = (EditText) findViewById(R.id.start_date);
        startDateButton.setOnClickListener(this);

        endDateButton = (EditText) findViewById(R.id.end_date);
        endDateButton.setOnClickListener(this);

        startDateWorkExpButton = (EditText) findViewById(R.id.start_date_work_exp);
        startDateWorkExpButton.setOnClickListener(this);

        endDateWorkExpButton = (EditText) findViewById(R.id.end_date_work_exp);
        endDateWorkExpButton.setOnClickListener(this);

        previous = (Button) findViewById(R.id.previous);
        previous.setOnClickListener(this);

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);

        setActionBarProperties();
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("Work Experience");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.work_exp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_date:
            case R.id.start_date_work_exp:
            case R.id.end_date:
            case R.id.end_date_work_exp:
                showDatePickerDialog(v);
                break;
            case R.id.next:
                Intent intent = new Intent(this, SubmitActivity.class);
                startActivity(intent);
                break;
            case R.id.previous:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
