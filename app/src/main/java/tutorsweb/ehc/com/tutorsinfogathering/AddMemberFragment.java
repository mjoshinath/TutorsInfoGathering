package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import model.categories.MemberInfo;

public class AddMemberFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private View view;
    private Spinner memberTypeSpinner;
    private ArrayAdapter<CharSequence> adapter;
    private Button previousButton;
    private Button nextButton;
    private View addMemberPhase;
    private ActionBar actionBar;
    private Button saveButton;
    private Button addMemberButton;
    private ArrayList<MemberInfo> membersList;
    private EditText firstName;
    private EditText lastName;
    private EditText emailId;
    private String selectedMemberType;
    private boolean isDataSaved = false;

    private String firstNameText;
    private String lastNameText;
    private String emailIdText;

    private SharedPreferences instituteSharedPrefs;
    private SharedPreferences.Editor instituteSharedPrefsEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_member, container, false);

        instituteSharedPrefs = getActivity().getSharedPreferences("instituteSession", Context.MODE_MULTI_PROCESS);
        instituteSharedPrefsEdit = instituteSharedPrefs.edit();

        memberTypeSpinner = (Spinner) view.findViewById(R.id.member_type);
        previousButton = (Button) getActivity().findViewById(R.id.previous);
        nextButton = (Button) getActivity().findViewById(R.id.next);
        addMemberPhase = getActivity().findViewById(R.id.phase_add_member);
        saveButton = (Button) view.findViewById(R.id.save);
        addMemberButton = (Button) view.findViewById(R.id.add_member);

        getWidgets(view);
        getFieldsData();
        maintainSharedPrefs();
        updateUi();

        membersList = new ArrayList<MemberInfo>();

        addMemberPhase.setBackgroundColor(Color.parseColor("#FFCB04"));

        nextButton.setText("Submit");

        previousButton.setVisibility(View.VISIBLE);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        addMemberButton.setOnClickListener(this);
        memberTypeSpinner.setOnItemSelectedListener(this);

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.member_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberTypeSpinner.setAdapter(adapter);

        setHasOptionsMenu(true);
        setActionBarProperties();

        return view;
    }

    private void getFieldsData() {
        firstNameText = firstName.getText().toString();
        lastNameText = lastName.getText().toString();
        emailIdText = emailId.getText().toString();
    }

    private void maintainSharedPrefs() {
        instituteSharedPrefsEdit.putString("firstNameText", firstNameText);
        instituteSharedPrefsEdit.putString("lastNameText", lastNameText);
        instituteSharedPrefsEdit.putString("emailIdText", emailIdText);

        instituteSharedPrefsEdit.commit();
    }

    private void updateUi() {
        firstName.setText(instituteSharedPrefs.getString("firstNameText", ""));
        lastName.setText(instituteSharedPrefs.getString("lastNameText", ""));
        emailId.setText(instituteSharedPrefs.getString("emailIdText", ""));
    }

    private void getWidgets(View view) {
        firstName = (EditText) view.findViewById(R.id.first_name);
        lastName = (EditText) view.findViewById(R.id.last_name);
        emailId = (EditText) view.findViewById(R.id.email);
    }

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Add Member");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                break;
            case R.id.previous:
                getActivity().onBackPressed();
                break;
            case R.id.save:
                addNewMember();
                break;
            case R.id.add_member:
                if (isDataSaved)
                    clearFields();
                break;
        }
    }

    private void addNewMember() {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setFirstName(firstName.getText().toString());
        memberInfo.setLastName(lastName.getText().toString());
        memberInfo.setEmailId(emailId.getText().toString());
        memberInfo.setMemberType(selectedMemberType);
        if (!(emailId.getText().toString().equals(""))) {
            membersList.add(memberInfo);
            Toast.makeText(getActivity(), "Member Saved", Toast.LENGTH_SHORT).show();
            isDataSaved = true;
        }
    }

    private void clearFields() {
        firstName.setText("");
        lastName.setText("");
        emailId.setText("");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        nextButton.setText("Next");
        addMemberPhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedMemberType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
