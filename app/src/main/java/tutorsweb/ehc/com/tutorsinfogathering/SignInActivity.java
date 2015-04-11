package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import helper.WebServiceCallBack;
import helper.WebserviceHelper;
import model.categories.Category;
import support.DataBaseHelper;


public class SignInActivity extends Activity implements View.OnClickListener, WebServiceCallBack {
    private EditText emailId;
    private EditText password;
    private Button signIn;
    private String email;
    private String passwordString;
    private ActionBar actionBar;
    private String[] emailIdResource;
    private String[] passwordResource;
    private DataBaseHelper databaseHelper;
    private SharedPreferences signInCredentialsPrefs;
    private SharedPreferences.Editor signInCredentialsPrefsEdit;
    private LinearLayout errorMsgPopup;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEdit;
    private SharedPreferences categorySharedPref;
    private SharedPreferences.Editor categoryEditor;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        categorySharedPref = getSharedPreferences("categories", Context.MODE_MULTI_PROCESS);
        categoryEditor = categorySharedPref.edit();

        signInCredentialsPrefs = getSharedPreferences("signInCredentials", MODE_MULTI_PROCESS);
        signInCredentialsPrefsEdit = signInCredentialsPrefs.edit();

        if (signInCredentialsPrefs.contains("email") && !signInCredentialsPrefs.getString("email", "").equals("")) {
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }

        sharedPrefs = getSharedPreferences("session", MODE_MULTI_PROCESS);
        sharedPrefsEdit = sharedPrefs.edit();

        getWidgets();
        updateUi();
        databaseHelper = new DataBaseHelper(getApplicationContext());

        signIn.setOnClickListener(this);

        emailId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMsgPopup.setVisibility(View.GONE);
            }
        });

        new WebserviceHelper(getApplicationContext()).getData(this, "categories");

        setActionBarProperties();
    }

    private void getWidgets() {
        emailId = (EditText) findViewById(R.id.email_id);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.sign_in);
        errorMsgPopup = (LinearLayout) findViewById(R.id.error_msg_popup);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    private void updateUi() {
        emailId.setText(signInCredentialsPrefs.getString("email", ""));
        password.setText(signInCredentialsPrefs.getString("password", ""));
    }

    @Override
    public void populateData(String jsonResponse) {
        if (jsonResponse.contains("target_person_id"))
            sharedPrefsEdit.putString("reports", jsonResponse).commit();
        categoryEditor.putString("categories", jsonResponse).commit();

    }

    @Override
    public void hideProgressBarOnFailure(String response) {

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
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email)) {
            emailId.setError("Invalid Email");
            emailId.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
        if (password.equalsIgnoreCase("")) {
            this.password.setError("Password field cannot be empty!");
            this.password.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
        if (password.length() < 6) {
            this.password.setError("Password length must be greater than 6");
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                progressBar.setVisibility(View.VISIBLE);
                emailId.clearFocus();
                if (doValidation()) {
                    if (databaseHelper.getAuthentication(emailId.getText().toString(), password.getText().toString()) == 1) {
                        progressBar.setVisibility(View.INVISIBLE);
                        signInCredentialsPrefsEdit.putString("email", emailId.getText().toString());
                        signInCredentialsPrefsEdit.putString("password", password.getText().toString());
                        signInCredentialsPrefsEdit.commit();
                        Intent intent = new Intent(this, HomePage.class);
                        startActivity(intent);
                    } else {
                        errorMsgPopup.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    databaseHelper.close();
                }
        }
    }


}
