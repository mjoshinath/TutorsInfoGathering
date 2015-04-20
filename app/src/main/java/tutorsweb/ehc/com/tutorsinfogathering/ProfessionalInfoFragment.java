package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ProfessionalInfoFragment extends Fragment implements View.OnClickListener {

    private Spinner profExpSpinner;
    private ArrayAdapter<CharSequence> adapter;
    private Button next;
    private Button previous;
    private ActionBar actionBar;
    private EditText tutoringExp;
    private EditText languages;
    private Spinner yrsOfTeachingExp;
    private EditText interests;
    private View view;
    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private View professionalPhase;
    private SharedPreferences userSharedPreference;
    private SharedPreferences.Editor sharedPrefsEditable;
    private String yrsOfTeachingExpText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_professional_info, null);

        userSharedPreference = getActivity().getSharedPreferences("session", Context.MODE_MULTI_PROCESS);
        sharedPrefsEditable = userSharedPreference.edit();

        sharedPrefsEditable.putBoolean("professional", true);
        sharedPrefsEditable.commit();

        profExpSpinner = (Spinner) view.findViewById(R.id.teaching_exp);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.years_of_exp, R.layout.simple_spinner_dropdown);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        profExpSpinner.setAdapter(adapter);

        previous = (Button) getActivity().findViewById(R.id.previous);
        next = (Button) getActivity().findViewById(R.id.next);

        getWidgets();
        updateUi();

        setHasOptionsMenu(true);
        yrsOfTeachingExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yrsOfTeachingExpText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        setActionBarProperties();
        professionalPhase = getActivity().findViewById(R.id.phase_professional);
        professionalPhase.setBackgroundColor(Color.parseColor("#FFCB04"));
        professionalPhase.setClickable(false);
        return view;
    }

    private void updateUi() {
        tutoringExp.setText(userSharedPreference.getString("tutoringExpText", ""));
        languages.setText(userSharedPreference.getString("languagesText", ""));
        interests.setText(userSharedPreference.getString("interestsText", ""));
    }

    private void getWidgets() {
        yrsOfTeachingExp = (Spinner) view.findViewById(R.id.teaching_exp);
        tutoringExp = (EditText) view.findViewById(R.id.tutoring_exp);
        languages = (EditText) view.findViewById(R.id.languages);
        interests = (EditText) view.findViewById(R.id.interests);
    }

    private void saveFilledDataInSharedPrefs() {
        sharedPrefsEditable.putString("yrsOfTeachingExpText", yrsOfTeachingExpText);
        sharedPrefsEditable.putString("tutoringExpText", tutoringExp.getText().toString().trim());
        sharedPrefsEditable.putString("languagesText", languages.getText().toString().trim());
        sharedPrefsEditable.putString("interestsText", interests.getText().toString().trim());

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

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Professional Information");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (doValidation()) {
                    fragmentReplaceMethod();
                }
                break;
            case R.id.previous:
                getActivity().onBackPressed();
                break;
        }
    }

    private void fragmentReplaceMethod() {
        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("Professional");
        fragmentTransaction.replace(R.id.main_view, new WorkExpFragment());
        fragmentTransaction.commit();
    }

    private boolean doValidation() {
        String tutoringExp = this.tutoringExp.getText().toString().trim();
        String languages = this.languages.getText().toString().trim();
        if (tutoringExp.equalsIgnoreCase("")) {
            this.tutoringExp.setError("Required Tutoring experience!");
            this.tutoringExp.requestFocus();
            return false;
        } else {
            this.tutoringExp.setError(null);
        }

        if (languages.equalsIgnoreCase("")) {
            this.languages.setError("Languages field can't be empty!");
            this.languages.requestFocus();
            return false;
        } else {
            this.languages.setError(null);
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        professionalPhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
        professionalPhase.setClickable(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
