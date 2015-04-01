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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

import helper.Network;

public class SubmitFragment extends Fragment implements View.OnClickListener {

    private Button previous;
    private Button submit;
    private ActionBar actionBar;
    private View view;
    private View submitPhase;
    private SharedPreferences sharedPrefs;
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
    private SharedPreferences.Editor sharedPrefsEdit;
    private ImageView userImage;
    private String userImageStringFormat;
    private Bitmap userImageInBitFormat;
    private TextView categories;
    private Set<String> categoriesSet;
    private Object category;
    private StringBuilder categoriesString = new StringBuilder();
    private Iterator<String> iterator;

    private AsyncHttpClient asyncHttpClient;
    private RequestParams requestParams;
    private String url;

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
    private JSONObject jsonObject;

    private static final String TABLE_QUERY = "CREATE TABLE IF NOT EXISTS i_reg_ezee_data_tbl(TUTOR_DETAILS VARCHAR(200))";
    private static final String INSERT_DETAILS_QUERY = "INSERT INTO i_reg_ezee_data_tbl(TUTOR_DETAILS) VALUES(?)";

    private SQLiteDatabase mydatabase;
    private SQLiteStatement preparedStatement;
    private String stringToBeInserted;


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

        sharedPrefsEdit.putBoolean("submit", true);
        sharedPrefsEdit.commit();

        getSharedPrefsData();

        setSharedPrefsDataToReview();
        setActionBarProperties();
        setHasOptionsMenu(true);

        submit = (Button) getActivity().findViewById(R.id.next);
        previous = (Button) getActivity().findViewById(R.id.previous);

        submit.setText("Submit");
        submit.setOnClickListener(this);
        previous.setOnClickListener(this);
        /*previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });*/
        submitPhase = getActivity().findViewById(R.id.phase_submit);
        submitPhase.setBackgroundColor(Color.parseColor("#32B1D2"));
        return view;
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

        Log.d("test18", "image in string->" + userImageString);
        userImageInBitFormat = stringToBitMap(userImageString);
        userImage.setImageBitmap(userImageInBitFormat);

        categoriesSet = sharedPrefs.getStringSet("checkedCategories", null);
        if (categoriesSet != null) {
            iterator = categoriesSet.iterator();
            while (iterator.hasNext()) {
                categoriesString.append(iterator.next().toString() + ", ");
            }
            String string = new String(categoriesString);
            categories.setText(string.substring(0, string.length() - 2));
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
                Log.d("test18", "called");
                createJSONObject();
                url = "192.168.1.124:5000/api/v1/tutors?tutor";
                requestParams = new RequestParams();
                requestParams.put("jsonObject", jsonObject);
                if (Network.isConnected(getActivity())) {
                    asyncHttpClient = new AsyncHttpClient();

                    asyncHttpClient.post(url, requestParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            sharedPrefsEdit.clear();
                            sharedPrefsEdit.commit();
                            Log.d("test18", "Success!");
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Log.d("test18", "Failure");
                        }
                    });
                } else {
                    if (!mydatabase.isOpen())
                        mydatabase = getActivity().openOrCreateDatabase("iRegEzee", getActivity().MODE_PRIVATE, null);
                    mydatabase.execSQL(TABLE_QUERY);//Creating Table
                    preparedStatement = mydatabase.compileStatement(INSERT_DETAILS_QUERY);//Inserting details into DB
                    preparedStatement.bindString(1, jsonObject.toString());
                    preparedStatement.execute();
                }
                /*sharedPrefsEdit.clear();
                sharedPrefsEdit.commit();*/
                break;
        }
    }

    public void createJSONObject() {
        jsonObject = new JSONObject();
        setJsonObjectAttributes();
    }

    private void setJsonObjectAttributes() {
        try {
            jsonObject.put("first_name", firstNameText);
            jsonObject.put("last_name", lastNameText);
            jsonObject.put("dob", dateOfBirthText);
            jsonObject.put("address", addressText);
//            jsonObject.put("stateText", stateText);
//            jsonObject.put("cityText", cityText);
//            jsonObject.put("zipCodeText", zipCodeText);
//            jsonObject.put("countryText", countryText);
            jsonObject.put("display_name", userNameText);
            jsonObject.put("email", emailIdText);
            jsonObject.put("primary_contact_number", mobileNumberText);
            jsonObject.put("gender", "male");

//            jsonObject.put("userImageString", userImageString);

//            jsonObject.put("yrsOfTeachingExpText", yrsOfTeachingExpText);
//            jsonObject.put("tutoringExpText", tutoringExpText);
            jsonObject.put("languages", languagesText);
//            jsonObject.put("interestsText", interestsText);

//            jsonObject.put("degreeNameText", degreeNameText);
//            jsonObject.put("universityNameText", universityNameText);
//            jsonObject.put("startDateText", startDateText);
//            jsonObject.put("endDateText", endDateText);
//            jsonObject.put("startDateWorkExpText", startDateWorkExpText);
//            jsonObject.put("endDateWorkExpText", endDateWorkExpText);
//            jsonObject.put("locationText", locationText);
//            jsonObject.put("locationWorkExpText", locationWorkExpText);
//            jsonObject.put("jobTitleText", jobTitleText);
            jsonObject.put("description", jobDescriptionText);
//            jsonObject.put("fieldOfStudyText", fieldOfStudyText);
//            jsonObject.put("companyNameText", companyNameText);
        } catch (JSONException e) {
            e.printStackTrace();
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
