package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;

import static tutorsweb.ehc.com.tutorsinfogathering.R.*;


public class PersonnelInfoFragment extends Fragment implements View.OnClickListener {

    private static final int PICTURE_REQUEST_CODE = 1;
    private Button next;
    private ActionBar actionBar;
    private EditText firstName;
    private EditText lastName;
    private EditText emailId;
    private EditText mobileNumber;
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
    private LinearLayout mainView;
    private Intent intent;
    private View currentPhase;
    private View view;
    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private Button previous;
    private Button home;
    private View personnelPhase;
    private SharedPreferences userSharedPreference;
    private SharedPreferences.Editor sharedPrefsEditable;
    private String firstNameText;
    private String lastNameText;
    private String emailIdText;
    private String mobileNumberText;
    private String dateOfBirthText;
    private String addressText;
    private String cityText;
    private String stateText;
    private String zipCodeText;
    private String countryText;
    private String userNameText;

    private static final int CAMERA_REQUEST = 1;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.pager_host_layout);

        mainView = (LinearLayout) findViewById(id.main_view);
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(layout.activity_personnel_info, null);
        next = (Button) findViewById(id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValidation()) {
                    intent = new Intent(getApplicationContext(), CategoriesFragment.class);
                    startActivity(intent);
                }
            }
        });

        mainView.addView(addView);
        currentPhase = findViewById(id.phase_personnel);
        currentPhase.setVisibility(View.VISIBLE);

        getWidgets(mainView);
        applyActions();
        setActionBarProperties();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(layout.activity_personnel_info, null);

        userSharedPreference = getActivity().getSharedPreferences("session", Context.MODE_MULTI_PROCESS);
        sharedPrefsEditable = userSharedPreference.edit();

        sharedPrefsEditable.putBoolean("personnel", true);
        sharedPrefsEditable.commit();

        next = (Button) getActivity().findViewById(id.next);
        previous = (Button) getActivity().findViewById(id.previous);
        getWidgets(view);
        setHasOptionsMenu(true);
        applyActions();
        setActionBarProperties();

        updateUi();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilledDataInSharedPrefs();
                fragmentReplaceMethod();
                /*if (doValidation()) {
                    saveFilledDataInSharedPrefs();
                    fragmentReplaceMethod();
                }*/
            }
        });
        personnelPhase = getActivity().findViewById(id.phase_personnel);
        personnelPhase.setBackgroundColor(Color.parseColor("#32B1D2"));
        previous.setVisibility(View.INVISIBLE);
        return view;
    }

    private void updateUi() {
        firstName.setText(userSharedPreference.getString("firstNameText", ""));
        lastName.setText(userSharedPreference.getString("lastNameText", ""));
        address.setText(userSharedPreference.getString("addressText", ""));
        city.setText(userSharedPreference.getString("cityText", ""));
        state.setText(userSharedPreference.getString("stateText", ""));
        dateOfBirth.setText(userSharedPreference.getString("dateOfBirthText", ""));
        zipCode.setText(userSharedPreference.getString("zipCodeText", ""));
        country.setText(userSharedPreference.getString("countryText", ""));
        userName.setText(userSharedPreference.getString("userNameText", ""));
        emailId.setText(userSharedPreference.getString("emailIdText", ""));
        mobileNumber.setText(userSharedPreference.getString("mobileNumberText", ""));
    }

    private void saveFilledDataInSharedPrefs() {

        firstNameText = firstName.getText().toString().trim();
        lastNameText = lastName.getText().toString().trim();
        dateOfBirthText = dateOfBirth.getText().toString().trim();
        addressText = address.getText().toString().trim();
        cityText = city.getText().toString().trim();
        stateText = state.getText().toString().trim();
        zipCodeText = zipCode.getText().toString().trim();
        countryText = country.getText().toString().trim();
        userNameText = userName.getText().toString().trim();
        emailIdText = emailId.getText().toString().trim();
        mobileNumberText = mobileNumber.getText().toString().trim();

        sharedPrefsEditable.putString("firstNameText", firstNameText);
        sharedPrefsEditable.putString("lastNameText", lastNameText);
        sharedPrefsEditable.putString("dateOfBirthText", dateOfBirthText);
        sharedPrefsEditable.putString("addressText", addressText);
        sharedPrefsEditable.putString("cityText", cityText);
        sharedPrefsEditable.putString("stateText", stateText);
        sharedPrefsEditable.putString("zipCodeText", zipCodeText);
        sharedPrefsEditable.putString("countryText", countryText);
        sharedPrefsEditable.putString("userNameText", userNameText);
        sharedPrefsEditable.putString("emailIdText", emailIdText);
        sharedPrefsEditable.putString("mobileNumberText", mobileNumberText);

        sharedPrefsEditable.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(getActivity(), HomePage.class);
                startActivity(intent1);
                sharedPrefsEditable.clear();
                sharedPrefsEditable.commit();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void applyActions() {
        next.setOnClickListener(this);
        dateOfBirth.setOnClickListener(this);
    }

    private void getWidgets(View view) {
        firstName = (EditText) view.findViewById(id.first_name);
        lastName = (EditText) view.findViewById(id.last_name);
        emailId = (EditText) view.findViewById(id.email_id);
        mobileNumber = (EditText) view.findViewById(id.mobile_number);
        dateOfBirth = (EditText) view.findViewById(id.date_of_birth);
        address = (EditText) view.findViewById(id.address);
        city = (EditText) view.findViewById(id.city);
        state = (EditText) view.findViewById(id.state);
        zipCode = (EditText) view.findViewById(id.zip_code);
        country = (EditText) view.findViewById(id.country);
        userName = (EditText) view.findViewById(id.username);
        captureImage = (Button) view.findViewById(id.capture_image);
        userImage = (ImageView) view.findViewById(id.user_image);
    }

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Personnel Information");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.date_of_birth:
                showDatePickerDialog(v);
                break;
            case android.R.id.home:
                getActivity().onBackPressed();
                Log.d("test18", "home");
                break;
            case id.next:
                fragmentReplaceMethod();
                /*if (doValidation()) {
                    fragmentReplaceMethod();
                }*/
                break;
            case id.capture_image:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

        }
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            userImage.setImageBitmap(photo);
            Log.d("photo", "" + photo.toString());

            userImageString = BitMapToString(photo);
        }
    }*/

    /*public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }*/

    private void fragmentReplaceMethod() {
        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("Personnel");
        fragmentTransaction.replace(id.main_view, new CaptureUserImageFragment());
        fragmentTransaction.commit();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        personnelPhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
