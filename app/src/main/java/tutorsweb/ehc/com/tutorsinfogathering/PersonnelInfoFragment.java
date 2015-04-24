package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import static tutorsweb.ehc.com.tutorsinfogathering.R.*;

public class PersonnelInfoFragment extends Fragment implements View.OnClickListener {

    private ActionBar actionBar;
    private Button next;
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
    private Button captureImage;
    private ImageView userImage;
    private Button previous;
    private View personnelPhase;
    private View view;
    private TextView emailIdTextView;
    private TextView mobileNoTextView;
    private Spinner gender;
    private ArrayAdapter<CharSequence> adapter;

    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;

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
    private String genderSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(layout.activity_personnel_info, null);

        userSharedPreference = getActivity().getSharedPreferences("session", Context.MODE_MULTI_PROCESS);
        sharedPrefsEditable = userSharedPreference.edit();

        sharedPrefsEditable.putBoolean("personnel", true);
        sharedPrefsEditable.commit();

        getWidgetsFromActivity();
        getWidgets(view);
        setHasOptionsMenu(true);
        applyActions();
        setActionBarProperties();

        updateUi();
        setAdapterForGenderSpinner();

        personnelPhase = getActivity().findViewById(id.phase_personnel);
        personnelPhase.setClickable(false);
        personnelPhase.setBackgroundColor(Color.parseColor("#FFCB04"));
        previous.setVisibility(View.INVISIBLE);
        return view;
    }

    private void setAdapterForGenderSpinner() {
        adapter = ArrayAdapter.createFromResource(getActivity(), array.gender, layout.simple_spinner_dropdown);
        adapter.setDropDownViewResource(layout.simple_spinner_dropdown);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderSelected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getWidgetsFromActivity() {
        next = (Button) getActivity().findViewById(id.next);
        previous = (Button) getActivity().findViewById(id.previous);
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

    private void saveFieldsDataInSharedPrefs() {

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
        sharedPrefsEditable.putString("genderSelectedText", genderSelected);

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
        gender = (Spinner) view.findViewById(id.gender);

        emailIdTextView = (TextView) view.findViewById(id.email_text_view);
        mobileNoTextView = (TextView) view.findViewById(id.mobile_no_text_view);

        Spannable emailSpannable = new SpannableString(emailIdTextView.getText().toString());
        int emailIdLoc = emailIdTextView.getText().toString().indexOf("*");
        emailSpannable.setSpan(new ForegroundColorSpan(Color.RED), emailIdLoc, emailIdLoc + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        emailIdTextView.setText(emailSpannable);

        Spannable mobileSpannable = new SpannableString(mobileNoTextView.getText().toString());
        int mobileStarLoc = mobileNoTextView.getText().toString().indexOf("*");
        mobileSpannable.setSpan(new ForegroundColorSpan(Color.RED), mobileStarLoc, mobileStarLoc + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mobileNoTextView.setText(mobileSpannable);
    }

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Personal Information");
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
                if (doValidation()) {
                    saveFieldsDataInSharedPrefs();
                    fragmentReplaceMethod();
                }
                break;
        }
    }

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
        String emailId = this.emailId.getText().toString().trim();
        String mobileNumber = this.mobileNumber.getText().toString().trim();

        if (emailId.equalsIgnoreCase("")) {
            this.emailId.setError("Email Required!");
            this.emailId.requestFocus();
            return false;
        } else {
            this.emailId.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches() && !TextUtils.isEmpty(emailId)) {
            this.emailId.setError("Invalid Email");
            this.emailId.requestFocus();
            return false;
        } else {
            this.emailId.setError(null);
        }

        if (mobileNumber.equalsIgnoreCase("")) {
            this.mobileNumber.setError("Mobile Number Required!");
            this.mobileNumber.requestFocus();
            return false;
        } else {
            this.mobileNumber.setError(null);
        }

        if (!Patterns.PHONE.matcher(mobileNumber).matches() && !TextUtils.isEmpty(mobileNumber)) {
            this.mobileNumber.setError("Invalid Mobile Number");
            this.mobileNumber.requestFocus();
            return false;
        }

        /*
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
        }*/
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
        personnelPhase.setClickable(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent1 = new Intent(getActivity(), HomePage.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
    }

}
