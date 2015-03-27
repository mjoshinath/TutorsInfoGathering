package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class HomePage extends Activity implements View.OnClickListener {

    private Button signUpTutorButton;
    private ActionBar actionBar;
    private Button addMettingLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        signUpTutorButton = (Button) findViewById(R.id.signup_tutor);
        addMettingLog = (Button) findViewById(R.id.add_meeting_log);

        signUpTutorButton.setOnClickListener(this);

        setActionBarProperties();
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("Home");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_tutor:
                Intent intent = new Intent(this, PersonnelInfoActivity.class);
                startActivity(intent);
            case R.id.add_meeting_log:
        }
    }
}
