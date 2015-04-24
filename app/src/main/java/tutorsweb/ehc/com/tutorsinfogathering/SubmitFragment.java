package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import helper.Network;
import helper.WebServiceCallBack;
import helper.WebserviceHelper;
import model.categories.AcademicDegreesAttribute;
import model.categories.Address;
import model.categories.Tutor;
import model.categories.TutorModel;
import model.categories.WorkExperiences;
import model.categories.WorkExperiencesAttribute;
import support.DataBaseHelper;

public class SubmitFragment extends Fragment implements View.OnClickListener, WebServiceCallBack {

    private ActionBar actionBar;
    private Button previous;
    private Button submit;
    private View view;
    private View submitPhase;
    private TextView firstName;
    private TextView lastName;
    private TextView emailId;
    private TextView mobileNumber;
    private TextView dateOfBirth;
    private TextView address;
    private TextView city;
    private TextView state;
    private TextView zipCode;
    private TextView country;
    private TextView userName;
    private TextView yearsOfTeachingExp;
    private TextView tutoringExp;
    private TextView languages;
    private TextView interests;
    private TextView degreeName;
    private TextView universityName;
    private TextView startDate;
    private TextView endDate;
    private TextView startDateWorkExp;
    private TextView endDateWorkExp;
    private TextView location;
    private TextView locationWorkExp;
    private TextView fieldOfStudy;
    private TextView companyName;
    private TextView jobTitle;
    private TextView jobDescription;
    private TextView categories;
    private TextView gender;
    private ImageView userImage;

    private Bitmap userImageInBitFormat;

    private String firstNameText;
    private String lastNameText;
    private String dateOfBirthText;
    private String addressText;
    private String stateText;
    private String cityText;
    private String zipCodeText;
    private String countryText;
    private String userNameText;
    private String userImageString;
    private String yrsOfTeachingExpText;
    private String tutoringExpText;
    private String languagesText;
    private String interestsText;
    private String degreeNameText;
    private String universityNameText;
    private String startDateText;
    private String endDateText;
    private String startDateWorkExpText;
    private String endDateWorkExpText;
    private String locationText;
    private String locationWorkExpText;
    private String jobTitleText;
    private String jobDescriptionText;
    private String fieldOfStudyText;
    private String companyNameText;
    private String emailIdText;
    private String mobileNumberText;
    private String genderSelectedText;
    private int id;

    private DataBaseHelper dataBaseHelper;

    private Set<String> categoriesSet;
    private List<WorkExperiencesAttribute> workExperiencesAttributeList = new ArrayList<WorkExperiencesAttribute>();
    private List<AcademicDegreesAttribute> academicDegreesAttributeList = new ArrayList<AcademicDegreesAttribute>();
    private StringBuilder categoriesString = new StringBuilder();
    private Iterator<String> iterator;

    private String json;

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEdit;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_submit, null);

        getWidgets(view);

        sharedPrefs = getActivity().getSharedPreferences("session", Context.MODE_MULTI_PROCESS);
        sharedPrefsEdit = sharedPrefs.edit();

        sharedPreferences = getActivity().getSharedPreferences("signInCredentials", getActivity().MODE_MULTI_PROCESS);
        sharedPreferencesEdit = sharedPreferences.edit();

        id = sharedPreferences.getInt("userId", 0);

        sharedPrefsEdit.putBoolean("submit", true);
        sharedPrefsEdit.commit();

        getSharedPrefsData();

        setSharedPrefsDataToReview();
        setActionBarProperties();
        setHasOptionsMenu(true);

        getActivityWidgets();

        submit.setText("Submit");
        submit.setOnClickListener(this);
        previous.setOnClickListener(this);

        submitPhase.setBackgroundColor(Color.parseColor("#FFCB04"));
        submitPhase.setClickable(false);
        return view;
    }

    private void getActivityWidgets() {
        submit = (Button) getActivity().findViewById(R.id.next);
        previous = (Button) getActivity().findViewById(R.id.previous);
        submitPhase = getActivity().findViewById(R.id.phase_submit);
    }

    private void getSharedPrefsData() {
        firstNameText = sharedPrefs.getString("firstNameText", "");
        lastNameText = sharedPrefs.getString("lastNameText", "");
        dateOfBirthText = sharedPrefs.getString("dateOfBirthText", "");
        addressText = sharedPrefs.getString("addressText", "");
        stateText = sharedPrefs.getString("stateText", "");
        cityText = sharedPrefs.getString("cityText", "");
        zipCodeText = sharedPrefs.getString("zipCodeText", "");
        countryText = sharedPrefs.getString("countryText", "");
        userNameText = sharedPrefs.getString("userNameText", "");
        emailIdText = sharedPrefs.getString("emailIdText", "");
        mobileNumberText = sharedPrefs.getString("mobileNumberText", "");
        genderSelectedText = sharedPrefs.getString("genderSelectedText", "");

        userImageString = sharedPrefs.getString("userImageString", "");

        yrsOfTeachingExpText = sharedPrefs.getString("yrsOfTeachingExpText", "");
        tutoringExpText = sharedPrefs.getString("tutoringExpText", "");
        languagesText = sharedPrefs.getString("languagesText", "");
        interestsText = sharedPrefs.getString("interestsText", "");

        degreeNameText = sharedPrefs.getString("degreeNameText", "");
        universityNameText = sharedPrefs.getString("universityNameText", "");
        startDateText = sharedPrefs.getString("startDateText", "");
        endDateText = sharedPrefs.getString("endDateText", "");
        startDateWorkExpText = sharedPrefs.getString("startDateWorkExpText", "");
        endDateWorkExpText = sharedPrefs.getString("endDateWorkExpText", "");
        locationText = sharedPrefs.getString("locationText", "");
        locationWorkExpText = sharedPrefs.getString("locationWorkExpText", "");
        jobTitleText = sharedPrefs.getString("jobTitleText", "");
        jobDescriptionText = sharedPrefs.getString("jobDescriptionText", "");
        fieldOfStudyText = sharedPrefs.getString("fieldOfStudyText", "");
        companyNameText = sharedPrefs.getString("companyNameText", "");
    }

    private void getWidgets(View view) {
        firstName = (TextView) view.findViewById(R.id.first_name);
        lastName = (TextView) view.findViewById(R.id.last_name);
        dateOfBirth = (TextView) view.findViewById(R.id.date_of_birth);
        address = (TextView) view.findViewById(R.id.address);
        city = (TextView) view.findViewById(R.id.city);
        state = (TextView) view.findViewById(R.id.state);
        zipCode = (TextView) view.findViewById(R.id.zip_code);
        country = (TextView) view.findViewById(R.id.country);
        userName = (TextView) view.findViewById(R.id.username);
        emailId = (TextView) view.findViewById(R.id.email_id);
        mobileNumber = (TextView) view.findViewById(R.id.mobile_number);
        userImage = (ImageView) view.findViewById(R.id.user_image);
        gender = (TextView) view.findViewById(R.id.gender);

        categories = (TextView) view.findViewById(R.id.categories);

        yearsOfTeachingExp = (TextView) view.findViewById(R.id.years_of_teaching_exp);
        tutoringExp = (TextView) view.findViewById(R.id.tutoring_exp);
        languages = (TextView) view.findViewById(R.id.languages);
        interests = (TextView) view.findViewById(R.id.interests);

        degreeName = (TextView) view.findViewById(R.id.degree_name);
        universityName = (TextView) view.findViewById(R.id.university_name);
        startDate = (TextView) view.findViewById(R.id.start_date);
        endDate = (TextView) view.findViewById(R.id.end_date);
        startDateWorkExp = (TextView) view.findViewById(R.id.start_date_work_exp);
        endDateWorkExp = (TextView) view.findViewById(R.id.end_date_work_exp);
        location = (TextView) view.findViewById(R.id.location);
        locationWorkExp = (TextView) view.findViewById(R.id.location_work_exp);
        fieldOfStudy = (TextView) view.findViewById(R.id.field_of_study);
        companyName = (TextView) view.findViewById(R.id.company_name);
        jobTitle = (TextView) view.findViewById(R.id.job_title);
        jobDescription = (TextView) view.findViewById(R.id.job_description);
    }

    private void setSharedPrefsDataToReview() {
        firstName.setText(firstNameText);
        lastName.setText(lastNameText);
        dateOfBirth.setText(dateOfBirthText);
        address.setText(addressText);
        city.setText(cityText);
        state.setText(stateText);
        zipCode.setText(zipCodeText);
        country.setText(countryText);
        userName.setText(userNameText);
        emailId.setText(emailIdText);
        mobileNumber.setText(mobileNumberText);
        gender.setText(genderSelectedText);

        Log.d("test18", "image in string->" + userImageString);
        userImageInBitFormat = stringToBitMap(userImageString);
        userImage.setImageBitmap(userImageInBitFormat);

        categoriesSet = sharedPrefs.getStringSet("checkedCategories", null);
        if (categoriesSet != null) {
            iterator = categoriesSet.iterator();
            while (iterator.hasNext()) {
                categoriesString.append(iterator.next().toString() + ", ");
            }
            categories.setText(categoriesString);
        }

        yearsOfTeachingExp.setText(yrsOfTeachingExpText);
        tutoringExp.setText(tutoringExpText);
        languages.setText(languagesText);
        interests.setText(interestsText);

        degreeName.setText(degreeNameText);
        universityName.setText(universityNameText);
        startDate.setText(startDateText);
        endDate.setText(endDateText);
        startDateWorkExp.setText(startDateWorkExpText);
        endDateWorkExp.setText(endDateWorkExpText);
        location.setText(locationText);
        locationWorkExp.setText(locationWorkExpText);
        jobTitle.setText(jobTitleText);
        jobDescription.setText(jobDescriptionText);
        fieldOfStudy.setText(fieldOfStudyText);
        companyName.setText(companyNameText);
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

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Review / Submit");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(getActivity(), HomePage.class);
                startActivity(intent1);
                sharedPrefsEdit.clear();
                sharedPrefsEdit.commit();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous:
                Log.d("test18", "previous called");
                getActivity().onBackPressed();
                break;
            case R.id.next:
                sharedPreferencesEdit.putBoolean("process", true);
                sharedPreferencesEdit.commit();
                Log.d("test18", "called");
                json = createJSONObject();
                if (Network.isConnected(getActivity())) {
                    StringEntity entity = null;
                    try {
                        entity = new StringEntity(json);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    new WebserviceHelper(getActivity()).postData(this, entity, 0L, "tutors/staff/" + id);
                } else {
                    dataBaseHelper = new DataBaseHelper(getActivity());
                    dataBaseHelper.insertTutorDetails(json);
                    sharedPrefsEdit.clear();
                    sharedPrefsEdit.commit();
                }

                Intent intent = new Intent(getActivity(), HomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    public String createJSONObject() {
        Tutor tutor = new Tutor();
        tutor.setFirstName(firstNameText);
        tutor.setLastName(lastNameText);
        tutor.setDob(dateOfBirthText);
        tutor.setEmail(emailIdText);
        tutor.setPrimaryContactNumber(mobileNumberText);
        tutor.setDisplayName(userNameText);
        tutor.setGender(genderSelectedText);
        tutor.setYearsOfTeachingExperience(yrsOfTeachingExpText);
        tutor.setTutoringExperience(tutoringExpText);
        tutor.setLanguages(languagesText);
        tutor.setAvatar(userImageString);

        Address address = new Address();
        address.setCity(cityText);
        address.setCountry(countryText);
        address.setZipCode(zipCodeText);
        address.setStreet1(addressText);

        tutor.setAddress(address);

        WorkExperiencesAttribute workExperiencesAttribute = new WorkExperiencesAttribute();
        workExperiencesAttribute.setCompanyName(companyNameText);
        workExperiencesAttribute.setStartDate(startDateWorkExpText);
        workExperiencesAttribute.setEndDate(endDateWorkExpText);
        workExperiencesAttribute.setLocation(locationWorkExpText);
        workExperiencesAttribute.setJobTitle(jobTitleText);
        workExperiencesAttribute.setDescription(jobDescriptionText);

        workExperiencesAttributeList.add(workExperiencesAttribute);
        tutor.setWorkExperiencesAttributes(workExperiencesAttributeList);

        AcademicDegreesAttribute academicDegreesAttribute = new AcademicDegreesAttribute();
        academicDegreesAttribute.setName(degreeNameText);
        academicDegreesAttribute.setSchoolName(universityNameText);
        academicDegreesAttribute.setStartDate(startDateText);
        academicDegreesAttribute.setEndDate(endDateText);
        academicDegreesAttribute.setLocation(locationText);
        academicDegreesAttribute.setFieldOfStudy(fieldOfStudyText);

        academicDegreesAttributeList.add(academicDegreesAttribute);
        tutor.setAcademicDegreesAttributes(academicDegreesAttributeList);

        TutorModel tutorModel = new TutorModel();
        tutorModel.setTutor(tutor);

        return new Gson().toJson(tutorModel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        submit.setText("Next");
        submitPhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
        submitPhase.setClickable(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void populateData(String jsonResponse) {
        sharedPrefsEdit.clear();
        sharedPrefsEdit.commit();
    }

    @Override
    public void hideProgressBarOnFailure(String response) {
        dataBaseHelper = new DataBaseHelper(getActivity());
        dataBaseHelper.insertTutorDetails(json);
    }
}
