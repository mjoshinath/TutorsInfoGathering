package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SubmitActivity extends Activity implements View.OnClickListener {

    private Button previous;
    private Button submit;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        previous = (Button) findViewById(R.id.previous);
        previous.setOnClickListener(this);

        submit = (Button) findViewById(R.id.next);
        submit.setOnClickListener(this);

        setActionBarProperties();
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("Review / Submit");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous:
                onBackPressed();
                break;
            /*case R.id.submit:
                //ToDo posting collected info. to database
                break;*/
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
