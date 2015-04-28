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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class WorkExpFragment extends Fragment implements View.OnClickListener {

    private ActionBar actionBar;
    private EditText startDate;
    private EditText endDate;
    private EditText startDateWorkExp;
    private EditText endDateWorkExp;
    private EditText degreeName;
    private EditText universityName;
    private EditText fieldOfStudy;
    private EditText location;
    private EditText companyName;
    private EditText jobTitle;
    private EditText jobDescription;
    private EditText locationWorkExp;
    private View datePickerView;
    private View view;
    private View workExpPhase;
    private Button next;
    private Button previous;

    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;

    private SharedPreferences userSharedPreference;
    private SharedPreferences.Editor sharedPrefsEditable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_work_exp, null);

        userSharedPreference = getActivity().getSharedPreferences(getActivity().getString(R.string.session), Context.MODE_MULTI_PROCESS);
        sharedPrefsEditable = userSharedPreference.edit();

        sharedPrefsEditable.putBoolean(getActivity().getString(R.string.workExp), true);
        sharedPrefsEditable.commit();

        getWidgets();
        updateUi();
        applyActions();
        setActionBarProperties();
        getWidgetsFromActivity();
        setHasOptionsMenu(true);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);

        workExpPhase.setBackgroundColor(getResources().getColor(R.color.ireg_yellow));
//        workExpPhase.setBackgroundColor(Color.parseColor("#FFCB04"));
        workExpPhase.setClickable(false);
        return view;
    }

    private void getWidgetsFromActivity() {
        next = (Button) getActivity().findViewById(R.id.next);
        previous = (Button) getActivity().findViewById(R.id.previous);
        workExpPhase = getActivity().findViewById(R.id.phase_work_exp);
    }

    private void updateUi() {
        startDate.setText(userSharedPreference.getString(getActivity().getString(R.string.startDateText), ""));
        endDate.setText(userSharedPreference.getString(getActivity().getString(R.string.endDateText), ""));
        startDateWorkExp.setText(userSharedPreference.getString(getActivity().getString(R.string.startDateWorkExpText), ""));
        endDateWorkExp.setText(userSharedPreference.getString(getActivity().getString(R.string.endDateWorkExpText), ""));
        degreeName.setText(userSharedPreference.getString(getActivity().getString(R.string.degreeNameText), ""));
        universityName.setText(userSharedPreference.getString(getActivity().getString(R.string.universityNameText), ""));
        fieldOfStudy.setText(userSharedPreference.getString(getActivity().getString(R.string.fieldOfStudyText), ""));
        location.setText(userSharedPreference.getString(getActivity().getString(R.string.locationText), ""));
        companyName.setText(userSharedPreference.getString(getActivity().getString(R.string.companyNameText), ""));
        jobTitle.setText(userSharedPreference.getString(getActivity().getString(R.string.jobTitleText), ""));
        locationWorkExp.setText(userSharedPreference.getString(getActivity().getString(R.string.locationWorkExpText), ""));
        jobDescription.setText(userSharedPreference.getString(getActivity().getString(R.string.jobDescriptionText), ""));
    }

    private void saveFilledDataInSharedPrefs() {
        sharedPrefsEditable.putString(getActivity().getString(R.string.startDateText), startDate.getText().toString().trim());
        sharedPrefsEditable.putString(getActivity().getString(R.string.endDateText), endDate.getText().toString().trim());
        sharedPrefsEditable.putString(getActivity().getString(R.string.startDateWorkExpText), startDateWorkExp.getText().toString().trim());
        sharedPrefsEditable.putString(getActivity().getString(R.string.endDateWorkExpText), endDateWorkExp.getText().toString().trim());
        sharedPrefsEditable.putString(getActivity().getString(R.string.degreeNameText), degreeName.getText().toString().trim());
        sharedPrefsEditable.putString(getActivity().getString(R.string.universityNameText), universityName.getText().toString().trim());
        sharedPrefsEditable.putString(getActivity().getString(R.string.fieldOfStudyText), fieldOfStudy.getText().toString().trim());
        sharedPrefsEditable.putString(getActivity().getString(R.string.locationText), location.getText().toString().trim());
        sharedPrefsEditable.putString(getActivity().getString(R.string.companyNameText), companyName.getText().toString().trim());
        sharedPrefsEditable.putString(getActivity().getString(R.string.jobTitleText), jobTitle.getText().toString().trim());
        sharedPrefsEditable.putString(getActivity().getString(R.string.locationWorkExpText), locationWorkExp.getText().toString().trim());
        sharedPrefsEditable.putString(getActivity().getString(R.string.jobDescriptionText), jobDescription.getText().toString().trim());

        sharedPrefsEditable.commit();
    }

    private void fragmentReplaceMethod() {
        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack(getActivity().getString(R.string.Category));
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
        actionBar.setTitle(getActivity().getString(R.string.work_experience_title));
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
                saveFilledDataInSharedPrefs();
                fragmentReplaceMethod();
                break;
            case R.id.previous:
                getActivity().onBackPressed();
                break;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), getActivity().getString(R.string.datePicker));
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
        workExpPhase.setBackgroundColor(getResources().getColor(R.color.ireg_grey));
//        workExpPhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
        workExpPhase.setClickable(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
