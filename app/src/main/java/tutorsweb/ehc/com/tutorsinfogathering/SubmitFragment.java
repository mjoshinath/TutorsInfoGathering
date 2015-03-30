package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class SubmitFragment extends Fragment implements View.OnClickListener {

    private Button previous;
    private Button submit;
    private ActionBar actionBar;
    private View view;
    private View submitPhase;
    private SharedPreferences sharedPrefs;
    private TextView firstName;
    private TextView lastName;
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
    private SharedPreferences.Editor sharedPrefsEdit;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        previous = (Button) findViewById(R.id.previous);
        previous.setOnClickListener(this);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_submit, null);

        getWidgets(view);

        sharedPrefs = getActivity().getSharedPreferences("session", Context.MODE_MULTI_PROCESS);
        sharedPrefsEdit = sharedPrefs.edit();

        sharedPrefsEdit.putBoolean("categories", true);
        sharedPrefsEdit.commit();

        setSharedPrefsDataToReview();
        setActionBarProperties();

        submit = (Button) getActivity().findViewById(R.id.next);
        submit.setText("Submit");
        submit.setOnClickListener(this);
        previous = (Button) getActivity().findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        submitPhase = getActivity().findViewById(R.id.phase_submit);
        submitPhase.setBackgroundColor(Color.parseColor("#32B1D2"));
        return view;
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
        firstName.setText(sharedPrefs.getString("firstNameText", ""));
        lastName.setText(sharedPrefs.getString("lastNameText", ""));
        dateOfBirth.setText(sharedPrefs.getString("dateOfBirthText", ""));
        address.setText(sharedPrefs.getString("addressText", ""));
        city.setText(sharedPrefs.getString("cityText", ""));
        state.setText(sharedPrefs.getString("stateText", ""));
        zipCode.setText(sharedPrefs.getString("zipCodeText", ""));
        country.setText(sharedPrefs.getString("countryText", ""));
        userName.setText(sharedPrefs.getString("userNameText", ""));

        yearsOfTeachingExp.setText(sharedPrefs.getString("yrsOfTeachingExpText", ""));
        tutoringExp.setText(sharedPrefs.getString("tutoringExpText", ""));
        languages.setText(sharedPrefs.getString("languagesText", ""));
        interests.setText(sharedPrefs.getString("interestsText", ""));

        degreeName.setText(sharedPrefs.getString("degreeNameText", ""));
        universityName.setText(sharedPrefs.getString("universityNameText", ""));
        startDate.setText(sharedPrefs.getString("startDateText", ""));
        endDate.setText(sharedPrefs.getString("endDateText", ""));
        startDateWorkExp.setText(sharedPrefs.getString("startDateWorkExpText", ""));
        endDateWorkExp.setText(sharedPrefs.getString("endDateWorkExpText", ""));
        location.setText(sharedPrefs.getString("locationText", ""));
        locationWorkExp.setText(sharedPrefs.getString("locationWorkExpText", ""));
        jobTitle.setText(sharedPrefs.getString("jobTitleText", ""));
        jobDescription.setText(sharedPrefs.getString("jobDescriptionText", ""));
        fieldOfStudy.setText(sharedPrefs.getString("fieldOfStudyText", ""));
        companyName.setText(sharedPrefs.getString("companyNameText", ""));
    }

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Review / Submit");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous:
                Log.d("test18", "previous called");
                getActivity().onBackPressed();
                break;
            case R.id.next:
                Log.d("test18", "called");
                //ToDo posting collected info. to database
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        submit.setText("Next");
        submitPhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
