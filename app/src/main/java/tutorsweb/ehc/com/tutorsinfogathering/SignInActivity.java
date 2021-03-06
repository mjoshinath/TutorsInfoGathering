package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SignInActivity extends Activity implements View.OnClickListener {

    private EditText emailId;
    private EditText password;
    private Button signIn;
    private String email;
    private String passwordString;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailId = (EditText) findViewById(R.id.email_id);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.sign_in);

        email = emailId.getText().toString();
        passwordString = password.getText().toString();

        signIn.setOnClickListener(this);

        setActionBarProperties();
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
        }
    }
}
