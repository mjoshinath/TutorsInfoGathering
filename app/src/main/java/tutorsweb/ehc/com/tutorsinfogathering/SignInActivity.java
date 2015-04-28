package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import helper.WebServiceCallBack;
import helper.WebserviceHelper;

import support.DataBaseHelper;

public class SignInActivity extends Activity implements View.OnClickListener, WebServiceCallBack {
    private ActionBar actionBar;
    private EditText emailId;
    private EditText password;
    private Button signIn;
    private ProgressBar progressBar;
    private LinearLayout errorMsgPopup;

    private DataBaseHelper databaseHelper;
    private Cursor cursorObject;

    private SharedPreferences signInCredentialsPrefs;
    private SharedPreferences.Editor signInCredentialsPrefsEdit;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEdit;
    private SharedPreferences categorySharedPref;
    private SharedPreferences.Editor categoryEditor;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSharedPreferences();

        if (signInCredentialsPrefs.contains(getString(R.string.email)) && !signInCredentialsPrefs.getString(getString(R.string.email), "").equals("")) {
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }

        getWidgets();
        updateUi();
        databaseHelper = new DataBaseHelper(getApplicationContext());

        applyActions();

        new WebserviceHelper(getApplicationContext()).getData(this, getString(R.string.categories));

        setActionBarProperties();
    }

    private void getSharedPreferences() {
        categorySharedPref = getSharedPreferences(getString(R.string.categories), Context.MODE_MULTI_PROCESS);
        categoryEditor = categorySharedPref.edit();

        signInCredentialsPrefs = getSharedPreferences(getString(R.string.signInCredentials), MODE_MULTI_PROCESS);
        signInCredentialsPrefsEdit = signInCredentialsPrefs.edit();

        sharedPrefs = getSharedPreferences(getString(R.string.session), MODE_MULTI_PROCESS);
        sharedPrefsEdit = sharedPrefs.edit();
    }

    private void applyActions() {
        signIn.setOnClickListener(this);
        emailId.setOnClickListener(this);
        password.setOnClickListener(this);
    }

    private void getWidgets() {
        emailId = (EditText) findViewById(R.id.email_id);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.sign_in);
        errorMsgPopup = (LinearLayout) findViewById(R.id.error_msg_popup);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    private void updateUi() {
        emailId.setText(signInCredentialsPrefs.getString(getString(R.string.email), ""));
        password.setText(signInCredentialsPrefs.getString(getString(R.string.password), ""));
    }

    @Override
    public void populateData(String jsonResponse) {
        if (jsonResponse.contains(getString(R.string.target_person_id)))
            sharedPrefsEdit.putString(getString(R.string.reports), jsonResponse).commit();
        categoryEditor.putString(getString(R.string.categories), jsonResponse).commit();
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
            emailId.setError(getString(R.string.email_required_msg));
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(email)) {
            emailId.setError(getString(R.string.invalid_email_msg));
            emailId.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
        if (password.equalsIgnoreCase("")) {
            this.password.setError(getString(R.string.password_required_msg));
            this.password.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return false;
        }
        if (password.length() < 6) {
            this.password.setError(getString(R.string.password_length_suggest_msg));
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
                getValidationAndAuthentication();
                break;
            case R.id.email_id:
                errorMsgPopup.setVisibility(View.INVISIBLE);
                break;
            case R.id.password:
                errorMsgPopup.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void getValidationAndAuthentication() {
        if (doValidation()) {
            cursorObject = databaseHelper.getAuthentication(emailId.getText().toString(), password.getText().toString());
            if (cursorObject.moveToFirst()) {
                do {
                    id = Integer.parseInt(cursorObject.getString(cursorObject.getColumnIndex(getString(R.string.userId))));
                } while (cursorObject.moveToNext());
            }
            if (cursorObject.getCount() == 1) {
                cursorObject.close();

                signInCredentialsPrefsEdit.putInt(getString(R.string.userId), id);
                signInCredentialsPrefsEdit.commit();

                progressBar.setVisibility(View.INVISIBLE);

                signInCredentialsPrefsEdit.putString(getString(R.string.email), emailId.getText().toString());
                signInCredentialsPrefsEdit.putString(getString(R.string.password), password.getText().toString());
                signInCredentialsPrefsEdit.commit();

                categoryEditor.putBoolean(getString(R.string.logDetect), true);
                categoryEditor.commit();

                Intent intent = new Intent(this, HomePage.class);
                startActivity(intent);
            } else {
                errorMsgPopup.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
            databaseHelper.close();
        } else {
            errorMsgPopup.setVisibility(View.VISIBLE);
            emailId.clearFocus();
            password.clearFocus();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.exit_confirmation_title));
        builder.setMessage(getString(R.string.exit_confirmation_msg));
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        applicationExit();
                    }
                });
        builder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void applicationExit() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
