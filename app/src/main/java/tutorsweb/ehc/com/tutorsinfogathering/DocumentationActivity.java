package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class DocumentationActivity extends Activity implements View.OnClickListener {

    private TextView tutorDemonstration;
    private TextView instituteDemonstration;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        tutorDemonstration = (TextView) findViewById(R.id.tutor_demonstration);
        instituteDemonstration = (TextView) findViewById(R.id.institute_demonstration);

        tutorDemonstration.setOnClickListener(this);
        instituteDemonstration.setOnClickListener(this);

        setActionBarProperties();
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("Documentation");
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tutor_demonstration:
                Intent intent = new Intent(this, DemonstrationActivity.class);
//                intent.putExtra("imageSlidesType", tutorDemonstration.getText().toString());
                intent.putExtra("size", getResources().obtainTypedArray(R.array.tutor).length());
                startActivity(intent);
                break;
            case R.id.institute_demonstration:
                break;

        }
    }
}
