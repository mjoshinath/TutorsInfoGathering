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
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;


public class WorkExpFragment extends Fragment implements View.OnClickListener {

    private EditText startDate;
    private EditText endDate;
    private Button next;
    private EditText startDateWorkExp;
    private EditText endDateWorkExp;
    private Button previous;
    private ActionBar actionBar;
    private View datePickerView;
    private EditText degreeName;
    private EditText universityName;
    private EditText fieldOfStudy;
    private EditText location;
    private EditText companyName;
    private EditText jobTitle;
    private EditText jobDescription;
    private EditText locationWorkExp;
    private View view;
    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private View workExpPhase;
    private SharedPreferences userSharedPreference;
    private SharedPreferences.Editor sharedPrefsEditable;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_exp);
        getWidgets();
        applyActions();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_work_exp, null);

        userSharedPreference = getActivity().getSharedPreferences("session", Context.MODE_MULTI_PROCESS);
        sharedPrefsEditable = userSharedPreference.edit();

        sharedPrefsEditable.putBoolean("workExp", true);
        sharedPrefsEditable.commit();

        getWidgets();
        updateUi();
        applyActions();
        setActionBarProperties();

        next = (Button) getActivity().findViewById(R.id.next);
        previous = (Button) getActivity().findViewById(R.id.previous);
        setHasOptionsMenu(true);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (doValidation())
                saveFilledDataInSharedPrefs();
                fragmentReplaceMethod();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        workExpPhase = getActivity().findViewById(R.id.phase_work_exp);
        workExpPhase.setBackgroundColor(Color.parseColor("#FFCB04"));
        workExpPhase.setClickable(false);
        return view;
    }

    private void updateUi() {
        startDate.setText(userSharedPreference.getString("startDateText", ""));
        endDate.setText(userSharedPreference.getString("endDateText", ""));
        startDateWorkExp.setText(userSharedPreference.getString("startDateWorkExpText", ""));
        endDateWorkExp.setText(userSharedPreference.getString("endDateWorkExpText", ""));
        degreeName.setText(userSharedPreference.getString("degreeNameText", ""));
        universityName.setText(userSharedPreference.getString("universityNameText", ""));
        fieldOfStudy.setText(userSharedPreference.getString("fieldOfStudyText", ""));
        location.setText(userSharedPreference.getString("locationText", ""));
        companyName.setText(userSharedPreference.getString("companyNameText", ""));
        jobTitle.setText(userSharedPreference.getString("jobTitleText", ""));
        locationWorkExp.setText(userSharedPreference.getString("locationWorkExpText", ""));
        jobDescription.setText(userSharedPreference.getString("jobDescriptionText", ""));
    }

    private void saveFilledDataInSharedPrefs() {
        sharedPrefsEditable.putString("startDateText", startDate.getText().toString().trim());
        sharedPrefsEditable.putString("endDateText", endDate.getText().toString().trim());
        sharedPrefsEditable.putString("startDateWorkExpText", startDateWorkExp.getText().toString().trim());
        sharedPrefsEditable.putString("endDateWorkExpText", endDateWorkExp.getText().toString().trim());
        sharedPrefsEditable.putString("degreeNameText", degreeName.getText().toString().trim());
        sharedPrefsEditable.putString("universityNameText", universityName.getText().toString().trim());
        sharedPrefsEditable.putString("fieldOfStudyText", fieldOfStudy.getText().toString().trim());
        sharedPrefsEditable.putString("locationText", location.getText().toString().trim());
        sharedPrefsEditable.putString("companyNameText", companyName.getText().toString().trim());
        sharedPrefsEditable.putString("jobTitleText", jobTitle.getText().toString().trim());
        sharedPrefsEditable.putString("locationWorkExpText", locationWorkExp.getText().toString().trim());
        sharedPrefsEditable.putString("jobDescriptionText", jobDescription.getText().toString().trim());

        sharedPrefsEditable.commit();
    }

    private void fragmentReplaceMethod() {
        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("Category");
        fragmentTransaction.replace(R.id.main_view, new SubmitFragment());
        fragmentTransaction.commit();
    }

    private void applyActions() {
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        startDateWorkExp.setOnClickListener(this);
        endDateWorkExp.setOnClickListener(this);
    }

    private void getWidgets() {
        startDate = (EditText) view.findViewById(R.id.start_date);
        endDate = (EditText) view.findViewById(R.id.end_date);
        startDateWorkExp = (EditText) view.findViewById(R.id.start_date_work_exp);
        endDateWorkExp = (EditText) view.findViewById(R.id.end_date_work_exp);
        degreeName = (EditText) view.findViewById(R.id.degree_name);
        universityName = (EditText) view.findViewById(R.id.university_name);
        fieldOfStudy = (EditText) view.findViewById(R.id.field_of_study);
        location = (EditText) view.findViewById(R.id.location);
        companyName = (EditText) view.findViewById(R.id.company_name);
        jobTitle = (EditText) view.findViewById(R.id.job_title);
        locationWorkExp = (EditText) view.findViewById(R.id.location_work_exp);
        jobDescription = (EditText) view.findViewById(R.id.job_description);
    }

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Work Experience");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_date:
            case R.id.start_date_work_exp:
            case R.id.end_date:
            case R.id.end_date_work_exp:
                showDatePickerDialog(v);
                datePickerView = v;
                break;
            case R.id.next:
//                if (doValidation()) {
//                    Intent intent = new Intent(getActivity(), SubmitFragment.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.previous:
                getActivity().onBackPressed();
                break;
        }
    }

    private boolean doValidation() {
        String degreeName = this.degreeName.getText().toString().trim();
        String universityName = this.universityName.getText().toString().trim();
        String startDate = this.startDate.getText().toString().trim();
        String endDate = this.endDate.getText().toString().trim();
        String fieldOfStudy = this.fieldOfStudy.getText().toString().trim();
        String location = this.location.getText().toString().trim();

        String companyName = this.companyName.getText().toString().trim();
        String jobTitle = this.jobTitle.getText().toString().trim();
        String startDateWorkExp = this.startDateWorkExp.getText().toString().trim();
        String endDateWorkExp = this.endDateWorkExp.getText().toString().trim();
        String locationWorkExp = this.locationWorkExp.getText().toString().trim();
        String jobDescription = this.jobDescription.getText().toString().trim();

        if (degreeName.equalsIgnoreCase("")) {
            this.degreeName.setError("Required Degree Name!");
            this.degreeName.requestFocus();
            return false;
        } else {
            this.degreeName.setError(null);
        }
        if (degreeName.length() > 20) {
            this.degreeName.setError("Degree name should not exceed 20 characters!");
            this.degreeName.requestFocus();
            return false;
        } else {
            this.degreeName.setError(null);
        }
        if (universityName.equalsIgnoreCase("")) {
            this.universityName.setError("Required University Name!");
            this.universityName.requestFocus();
            return false;
        } else {
            this.universityName.setError(null);
        }

        if (universityName.length() > 20) {
            this.universityName.setError("University Name should not exceed 20 characters!");
            this.universityName.requestFocus();
            return false;
        } else {
            this.universityName.setError(null);
        }

        if (startDate.equalsIgnoreCase("")) {
            this.startDate.setError("Required Start Date!");
            this.startDate.requestFocus();
            return false;
        } else {
            this.startDate.setError(null);
        }

        if (endDate.equalsIgnoreCase("")) {
            this.endDate.setError("Required End Date!");
            this.endDate.requestFocus();
            return false;
        } else {
            this.endDate.setError(null);
        }

        if (fieldOfStudy.equalsIgnoreCase("")) {
            this.fieldOfStudy.setError("Required Field of Study!");
            this.fieldOfStudy.requestFocus();
            return false;
        } else {
            this.fieldOfStudy.setError(null);
        }

        if (location.equalsIgnoreCase("")) {
            this.location.setError("Required Location!");
            this.location.requestFocus();
            return false;
        } else {
            this.location.setError(null);
        }

        if (companyName.equalsIgnoreCase("")) {
            this.companyName.setError("Required Company Name!");
            this.companyName.requestFocus();
            return false;
        } else {
            this.companyName.setError(null);
        }

        if (companyName.length() > 20) {
            this.companyName.setError("Company Name should not exceed 20 characters!");
            this.companyName.requestFocus();
            return false;
        } else {
            this.companyName.setError(null);
        }

        if (jobTitle.equalsIgnoreCase("")) {
            this.jobTitle.setError("Required Job Title!");
            this.jobTitle.requestFocus();
            return false;
        } else {
            this.jobTitle.setError(null);
        }

        if (startDateWorkExp.equalsIgnoreCase("")) {
            this.startDateWorkExp.setError("Required Start Date!");
            this.startDateWorkExp.requestFocus();
            return false;
        } else {
            this.startDateWorkExp.setError(null);
        }

        if (endDateWorkExp.equalsIgnoreCase("")) {
            this.endDateWorkExp.setError("Required End Date!");
            this.endDateWorkExp.requestFocus();
            return false;
        } else {
            this.endDateWorkExp.setError(null);
        }

        if (locationWorkExp.equalsIgnoreCase("")) {
            this.locationWorkExp.setError("Required Location!");
            this.locationWorkExp.requestFocus();
            return false;
        } else {
            this.locationWorkExp.setError(null);
        }

        if (jobDescription.equalsIgnoreCase("")) {
            this.jobDescription.setError("Required Job Description!");
            this.jobDescription.requestFocus();
            return false;
        } else {
            this.jobDescription.setError(null);
        }
        return true;
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
    }*/

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

            String date = day + " / " + (month + 1) + " / " + year;

            switch (datePickerView.getId()) {
                case R.id.start_date:
                    startDate.setText(date);
                    break;
                case R.id.end_date:
                    endDate.setText(date);
                    break;
                case R.id.start_date_work_exp:
                    startDateWorkExp.setText(date);
                    break;
                case R.id.end_date_work_exp:
                    endDateWorkExp.setText(date);
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        workExpPhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
        workExpPhase.setClickable(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
