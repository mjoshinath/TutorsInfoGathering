package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class InstituteInfoFragment extends Fragment implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1;

    private View view;
    private View institutePhase;
    private Button previous;
    private Button next;
    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private ActionBar actionBar;

    private EditText instituteName;
    private EditText address1;
    private EditText address2;
    private EditText dateOfEstablishment;
    private EditText city;
    private EditText state;
    private EditText zipCode;
    private EditText country;
    private EditText username;
    private EditText instituteDescription;
    private EditText website;
    private EditText mobileNumber;
    private Button captureImage;
    private Bitmap photo;
    private ImageView instituteImage;
    private String instituteImageString;
    private String instituteNameText;
    private String address1Text;
    private String address2Text;
    private String cityText;
    private String stateText;
    private String zipCodeText;
    private String countryText;
    private String dateOfEstablishmentText;
    private String instituteDescriptionText;
    private String usernameText;
    private String websiteText;
    private String mobileNumberText;
    private SharedPreferences instituteSharedPrefs;
    private SharedPreferences.Editor instituteSharedPrefsEdit;

    private EditText employeeFirstName;
    private EditText employeeLastName;
    private EditText employeeUsername;
    private EditText employeeEmail;
    private EditText employeeContactNumber;

    private String employeeFirstNameText;
    private String employeeLastNameText;
    private String employeeUsernameText;
    private String employeeEmailText;
    private String employeeContactNumberText;

    private TextView emailIdTextView;
    private TextView instituteNameTextView;
    private TextView websiteTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_institute_info, container, false);

        instituteSharedPrefs = getActivity().getSharedPreferences("instituteSession", Context.MODE_MULTI_PROCESS);
        instituteSharedPrefsEdit = instituteSharedPrefs.edit();

        getWidgets();

        getWidgets(view);
        updateUi();

        applyActions();

        institutePhase.setBackgroundColor(Color.parseColor("#FFCB04"));
        previous.setVisibility(View.INVISIBLE);

        setHasOptionsMenu(true);
        setActionBarProperties();

        return view;
    }

    private void getWidgets() {
        institutePhase = getActivity().findViewById(R.id.phase_institute);
        previous = (Button) getActivity().findViewById(R.id.previous);
        next = (Button) getActivity().findViewById(R.id.next);
    }

    private void applyActions() {
        next.setOnClickListener(this);
        dateOfEstablishment.setOnClickListener(this);
        captureImage.setOnClickListener(this);
    }

    private void updateUi() {
        instituteName.setText(instituteSharedPrefs.getString(instituteNameText, ""));
        address1.setText(instituteSharedPrefs.getString(address1Text, ""));
        address2.setText(instituteSharedPrefs.getString(address2Text, ""));
        city.setText(instituteSharedPrefs.getString(cityText, ""));
        state.setText(instituteSharedPrefs.getString(stateText, ""));
        zipCode.setText(instituteSharedPrefs.getString(zipCodeText, ""));
        country.setText(instituteSharedPrefs.getString(countryText, ""));
        dateOfEstablishment.setText(instituteSharedPrefs.getString(dateOfEstablishmentText, ""));
        instituteDescription.setText(instituteSharedPrefs.getString(instituteDescriptionText, ""));
        username.setText(instituteSharedPrefs.getString(usernameText, ""));
        website.setText(instituteSharedPrefs.getString(websiteText, ""));
        Log.d("testp", "instituteSharedPrefs" + instituteSharedPrefs.getString(websiteText, ""));

        mobileNumber.setText(instituteSharedPrefs.getString(mobileNumberText, ""));
        instituteImage.setImageBitmap(stringToBitMap(instituteSharedPrefs.getString(instituteImageString, "")));
    }

    private void maintainSharedPrefs() {
        instituteSharedPrefsEdit.putString("instituteNameText", instituteNameText);
        instituteSharedPrefsEdit.putString("address1Text", address1Text);
        instituteSharedPrefsEdit.putString("address2Text", address2Text);
        instituteSharedPrefsEdit.putString("cityText", cityText);
        instituteSharedPrefsEdit.putString("stateText", stateText);
        instituteSharedPrefsEdit.putString("zipCodeText", zipCodeText);
        instituteSharedPrefsEdit.putString("countryText", countryText);
        instituteSharedPrefsEdit.putString("dateOfEstablishmentText", dateOfEstablishmentText);
        instituteSharedPrefsEdit.putString("instituteDescriptionText", instituteDescriptionText);
        instituteSharedPrefsEdit.putString("usernameText", usernameText);
        instituteSharedPrefsEdit.putString("websiteText", websiteText);
        instituteSharedPrefsEdit.putString("mobileNumberText", mobileNumberText);
        instituteSharedPrefsEdit.putString("instituteImageString", instituteImageString);

        instituteSharedPrefsEdit.putString("employeeFirstNameText", employeeFirstNameText);
        instituteSharedPrefsEdit.putString("employeeLastNameText", employeeLastNameText);
        instituteSharedPrefsEdit.putString("employeeUsernameText", employeeUsernameText);
        instituteSharedPrefsEdit.putString("employeeEmailText", employeeEmailText);
        instituteSharedPrefsEdit.putString("employeeContactNumberText", employeeContactNumberText);

        instituteSharedPrefsEdit.commit();
    }

    private void getFilledData() {
        instituteNameText = instituteName.getText().toString();
        address1Text = address1.getText().toString();
        address2Text = address2.getText().toString();
        cityText = city.getText().toString();
        stateText = state.getText().toString();
        zipCodeText = zipCode.getText().toString();
        countryText = country.getText().toString();
        dateOfEstablishmentText = dateOfEstablishment.getText().toString();
        instituteDescriptionText = instituteDescription.getText().toString();
        usernameText = username.getText().toString();
        websiteText = website.getText().toString();
        Log.d("testp", "websiteText" + websiteText);
        mobileNumberText = mobileNumber.getText().toString();

        employeeFirstNameText = employeeFirstName.getText().toString();
        employeeLastNameText = employeeLastName.getText().toString();
        employeeUsernameText = employeeUsername.getText().toString();
        employeeEmailText = employeeEmail.getText().toString();
        employeeContactNumberText = employeeContactNumber.getText().toString();
    }

    private void getWidgets(View view) {
        instituteName = (EditText) view.findViewById(R.id.institute_name);
        address1 = (EditText) view.findViewById(R.id.address1);
        address2 = (EditText) view.findViewById(R.id.address2);
        city = (EditText) view.findViewById(R.id.city);
        state = (EditText) view.findViewById(R.id.state);
        zipCode = (EditText) view.findViewById(R.id.zip_code);
        country = (EditText) view.findViewById(R.id.country);
        dateOfEstablishment = (EditText) view.findViewById(R.id.date_of_establishment);
        instituteDescription = (EditText) view.findViewById(R.id.institute_description);
        username = (EditText) view.findViewById(R.id.username);
        website = (EditText) view.findViewById(R.id.website);
        mobileNumber = (EditText) view.findViewById(R.id.mobile_number);
        captureImage = (Button) view.findViewById(R.id.capture_image);
        instituteImage = (ImageView) view.findViewById(R.id.institute_image);

        employeeFirstName = (EditText) view.findViewById(R.id.employee_first_name);
        employeeLastName = (EditText) view.findViewById(R.id.employee_last_name);
        employeeUsername = (EditText) view.findViewById(R.id.employee_user_name);
        employeeEmail = (EditText) view.findViewById(R.id.employee_email);
        employeeContactNumber = (EditText) view.findViewById(R.id.employee_contact_number);

        emailIdTextView = (TextView) view.findViewById(R.id.email_id_text_view);
        instituteNameTextView = (TextView) view.findViewById(R.id.institute_name_text_view);
        websiteTextView = (TextView) view.findViewById(R.id.website_text_view);

        setMandatoryFieldsStarColors();
    }

    private void setMandatoryFieldsStarColors() {
        Spannable emailSpannable = new SpannableString(emailIdTextView.getText().toString());
        int emailStarLoc = emailIdTextView.getText().toString().indexOf("*");
        emailSpannable.setSpan(new ForegroundColorSpan(Color.RED), emailStarLoc, emailStarLoc + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        emailIdTextView.setText(emailSpannable);

        Spannable instituteSpannable = new SpannableString(instituteNameTextView.getText().toString());
        int instituteNameStarLoc = instituteNameTextView.getText().toString().indexOf("*");
        instituteSpannable.setSpan(new ForegroundColorSpan(Color.RED), instituteNameStarLoc, instituteNameStarLoc + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        instituteNameTextView.setText(instituteSpannable);

        Spannable websiteSpannable = new SpannableString(websiteTextView.getText().toString());
        int websiteStarLoc = websiteTextView.getText().toString().indexOf("*");
        websiteSpannable.setSpan(new ForegroundColorSpan(Color.RED), websiteStarLoc, websiteStarLoc + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        websiteTextView.setText(websiteSpannable);
    }

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Institute Information");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (doValidation()) {
                    getFilledData();
                    maintainSharedPrefs();
                    fragmentReplaceMethod();
                }
                break;
            case R.id.date_of_establishment:
                showDatePickerDialog(v);
                break;
            case R.id.capture_image:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
        }
    }

    private boolean doValidation() {
        String emailId = employeeEmail.getText().toString().trim();
        String instituteName = this.instituteName.getText().toString().trim();
        String websiteName = website.getText().toString().trim();

        if (instituteName.equalsIgnoreCase("")) {
            this.instituteName.setError("Institute Name Required!");
            this.instituteName.requestFocus();
            return false;
        } else {
            this.instituteName.setError(null);
        }

        if (websiteName.equalsIgnoreCase("")) {
            website.setError("Website URL Required!");
            website.requestFocus();
            return false;
        } else {
            website.setError(null);
        }

        if (!Patterns.WEB_URL.matcher(websiteName).matches() && !TextUtils.isEmpty(websiteName)) {
            website.setError("Invalid Website URL!");
            website.requestFocus();
            return false;
        } else {
            website.setError(null);
        }

        if (emailId.equalsIgnoreCase("")) {
            employeeEmail.setError("Email Required!");
            employeeEmail.requestFocus();
            return false;
        } else {
            employeeEmail.setError(null);
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches() && !TextUtils.isEmpty(emailId)) {
            employeeEmail.setError("Invalid Email");
            employeeEmail.requestFocus();
            return false;
        } else {
            employeeEmail.setError(null);
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            instituteImage.setImageBitmap(photo);
            instituteImageString = BitMapToString(photo);
            Log.d("test18", "on capture" + instituteImageString);
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(getActivity(), HomePage.class);
                startActivity(intent1);
                instituteSharedPrefsEdit.clear();
                instituteSharedPrefsEdit.commit();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void fragmentReplaceMethod() {
        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("Institute");
        fragmentTransaction.replace(R.id.main_view, new AddMemberFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent1 = new Intent(getActivity(), HomePage.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        institutePhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
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
            dateOfEstablishment.setText(day + " / " + (month + 1) + " / " + year);
        }
    }
}
