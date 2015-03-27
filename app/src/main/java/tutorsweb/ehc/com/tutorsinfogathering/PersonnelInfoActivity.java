package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import support.DatePickerFragment;

import static tutorsweb.ehc.com.tutorsinfogathering.R.*;


public class PersonnelInfoActivity extends Activity implements View.OnClickListener {

    private static final int PICTURE_REQUEST_CODE = 1;
    private Button nextButton;
    private ActionBar actionBar;
    private EditText firstName;
    private EditText lastName;
    private EditText dateOfBirth;
    private EditText address;
    private EditText city;
    private EditText state;
    private EditText zipCode;
    private EditText country;
    private EditText userName;
    private boolean valid;
    private Button captureImage;
    private String fileName;
    private ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_personnel_info);

        getWidgets();
        applyActions();
        setActionBarProperties();
    }

    private void applyActions() {
        nextButton.setOnClickListener(this);
        dateOfBirth.setOnClickListener(this);
        captureImage.setOnClickListener(this);
    }

    private void getWidgets() {
        nextButton = (Button) findViewById(id.next);
        firstName = (EditText) findViewById(id.first_name);
        lastName = (EditText) findViewById(id.last_name);
        dateOfBirth = (EditText) findViewById(id.date_of_birth);
        address = (EditText) findViewById(id.address);
        city = (EditText) findViewById(id.city);
        state = (EditText) findViewById(id.state);
        zipCode = (EditText) findViewById(id.zip_code);
        country = (EditText) findViewById(id.country);
        userName = (EditText) findViewById(id.username);
        captureImage = (Button) findViewById(id.capture_image);
        userImage= (ImageView) findViewById(id.user_image);
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("Personnel Information");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.date_of_birth:
                showDatePickerDialog(v);
                break;
            case id.next:
                if (doValidation()) {
                    Intent intent = new Intent(this, CategoriesActivity.class);
                    startActivity(intent);
                }
                break;
            case id.capture_image:
                File tempFile = null;
                try {
                    tempFile = File.createTempFile("my_app", ".jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fileName = tempFile.getAbsolutePath();
                Uri uri = Uri.fromFile(tempFile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, PICTURE_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICTURE_REQUEST_CODE) {
                captureImage.setVisibility(View.GONE);
                userImage.setImageBitmap(BitmapFactory.decodeFile(fileName));
                //userImage.setImageDrawable(uri);
                userImage.setVisibility(View.VISIBLE);
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    private boolean doValidation() {
        String firstName = this.firstName.getText().toString().trim();
        String lastName = this.lastName.getText().toString().trim();
        String dateOfBirth = this.dateOfBirth.getText().toString().trim();
        String address = this.address.getText().toString().trim();
        String city = this.city.getText().toString().trim();
        String state = this.state.getText().toString().trim();
        String zipCode = this.zipCode.getText().toString().trim();
        String country = this.country.getText().toString().trim();
        String userName = this.userName.getText().toString().trim();

        if (firstName.equalsIgnoreCase("")) {
            this.firstName.setError("Required First Name!");
            this.firstName.requestFocus();
            return false;
        } else {
            this.firstName.setError(null);
        }

        if (firstName.length() > 20) {
            this.firstName.setError("First Name should not exceed 20 characters!");
            this.firstName.requestFocus();
            return false;
        } else {
            this.firstName.setError(null);
        }

        if (lastName.equalsIgnoreCase("")) {
            this.lastName.setError("Required Last Name!");
            this.lastName.requestFocus();
            return false;
        } else {
            this.lastName.setError(null);
        }

        if (lastName.length() > 20) {
            this.lastName.setError("Last Name should not exceed 20 characters!");
            this.lastName.requestFocus();
            return false;
        } else {
            this.lastName.setError(null);
        }

        if (dateOfBirth.equalsIgnoreCase("")) {
            this.dateOfBirth.setError("Required Date of Birth!");
            this.dateOfBirth.requestFocus();
            return false;
        } else {
            this.dateOfBirth.setError(null);
        }

        if (address.equalsIgnoreCase("")) {
            this.address.setError("Required Address!");
            this.address.requestFocus();
            return false;
        } else {
            this.address.setError(null);
        }

        if (city.equalsIgnoreCase("")) {
            this.city.setError("Required City!");
            this.city.requestFocus();
            return false;
        } else {
            this.city.setError(null);
        }

        if (state.equalsIgnoreCase("")) {
            this.state.setError("Required State!");
            this.state.requestFocus();
            return false;
        } else {
            this.state.setError(null);
        }

        if (zipCode.equalsIgnoreCase("")) {
            this.zipCode.setError("Required Zip Code!");
            this.zipCode.requestFocus();
            return false;
        } else {
            this.zipCode.setError(null);
        }

        if (zipCode.length() != 6) {
            this.zipCode.setError("Invalid Zip Code!");
            this.zipCode.requestFocus();
            return false;
        } else {
            this.zipCode.setError(null);
        }

        if (country.equalsIgnoreCase("")) {
            this.country.setError("Required Country!");
            this.country.requestFocus();
            return false;
        } else {
            this.country.setError(null);
        }

        if (userName.equalsIgnoreCase("")) {
            this.userName.setError("Required Username!");
            this.userName.requestFocus();
            return false;
        } else {
            this.userName.setError(null);
        }
        return true;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        private int year;
        private int month;
        private int day;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
            dateOfBirth.setText(day + " / " + (month + 1) + " / " + year);
        }
    }

}
