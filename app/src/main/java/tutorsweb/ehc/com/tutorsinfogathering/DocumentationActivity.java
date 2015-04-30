package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class DocumentationActivity extends Activity implements View.OnClickListener {
    private Button tutorDemonstration;
    private Button instituteDemonstration;
    private Button studentDemonstration;
    private Button leadDemonstration;
    private Button pressDemonstration;

    private ActionBar actionBar;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        getWidgets();
        applyActions();

        setActionBarProperties();
    }

    private void getWidgets() {
        tutorDemonstration = (Button) findViewById(R.id.tutor_demonstration);
        instituteDemonstration = (Button) findViewById(R.id.institute_demonstration);
        studentDemonstration = (Button) findViewById(R.id.student_demonstration);
        leadDemonstration = (Button) findViewById(R.id.lead_demonstration);
        pressDemonstration = (Button) findViewById(R.id.press_demonstration);
    }

    private void applyActions() {
        tutorDemonstration.setOnClickListener(this);
        instituteDemonstration.setOnClickListener(this);
        studentDemonstration.setOnClickListener(this);
        leadDemonstration.setOnClickListener(this);
        pressDemonstration.setOnClickListener(this);
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle(getString(R.string.documentation_title));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                /*Intent intent1 = new Intent(this, HomePage.class);
                startActivity(intent1);*/
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(this, DemonstrationActivity.class);
        switch (v.getId()) {
            case R.id.tutor_demonstration:
//                intent.putExtra("imageSlidesType", tutorDemonstration.getText().toString());
                intent.putExtra("size", getResources().obtainTypedArray(R.array.tutor).length());
                break;
            case R.id.institute_demonstration:
                intent.putExtra("size", getResources().obtainTypedArray(R.array.tutor).length());
                break;
            case R.id.student_demonstration:
                intent.putExtra("size", getResources().obtainTypedArray(R.array.tutor).length());
                break;
            case R.id.lead_demonstration:
                intent.putExtra("size", getResources().obtainTypedArray(R.array.tutor).length());
                break;
            case R.id.press_demonstration:
                intent.putExtra("size", getResources().obtainTypedArray(R.array.tutor).length());
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
