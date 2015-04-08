package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class InstituteInfoFragment extends Fragment implements View.OnClickListener {

    private View view;
    private View institutePhase;
    private Button previous;
    private Button next;
    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_institute_info, container, false);

        institutePhase = getActivity().findViewById(R.id.phase_institute);
        previous = (Button) getActivity().findViewById(R.id.previous);
        next = (Button) getActivity().findViewById(R.id.next);

        next.setOnClickListener(this);
        institutePhase.setBackgroundColor(Color.parseColor("#FFCB04"));
        previous.setVisibility(View.INVISIBLE);

        setHasOptionsMenu(true);
        setActionBarProperties();

        return view;
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
                fragmentReplaceMethod();
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        institutePhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
    }
}
