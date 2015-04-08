package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddMemberFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Spinner memberTypeSpinner;
    private ArrayAdapter<CharSequence> adapter;
    private Button previousButton;
    private Button nextButton;
    private View addMemberPhase;
    private ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_member, container, false);

        memberTypeSpinner = (Spinner) view.findViewById(R.id.member_type);
        previousButton = (Button) getActivity().findViewById(R.id.previous);
        nextButton = (Button) getActivity().findViewById(R.id.next);
        addMemberPhase = getActivity().findViewById(R.id.phase_add_member);

        addMemberPhase.setBackgroundColor(Color.parseColor("#FFCB04"));

        nextButton.setText("Submit");

        previousButton.setVisibility(View.VISIBLE);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.member_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberTypeSpinner.setAdapter(adapter);

        setHasOptionsMenu(true);
        setActionBarProperties();

        return view;
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
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(getActivity(), HomePage.class);
                startActivity(intent1);
                /*sharedPrefsEditable.clear();
                sharedPrefsEditable.commit();*/
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
}
