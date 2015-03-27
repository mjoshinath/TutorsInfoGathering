package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    private boolean doValidation() {
        String password = this.password.getText().toString().trim();
        String email = this.emailId.getText().toString().trim();
        if (email.equalsIgnoreCase("")) {
            emailId.setError("Email field cannot be empty!");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email)) {
            emailId.setError("Invalid Email");
            emailId.requestFocus();
            return false;
        }
        if (password.equalsIgnoreCase("")) {
            this.password.setError("Password field cannot be empty!");
            this.password.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            this.password.setError("Password length must be greater than 6");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                if (doValidation()) {
                    Intent intent = new Intent(this, HomePage.class);
                    startActivity(intent);
                }
        }
    }
}
