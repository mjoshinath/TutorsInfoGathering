package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
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


public class SubmitFragment extends Fragment implements View.OnClickListener {

    private Button previous;
    private Button submit;
    private ActionBar actionBar;
    private View view;
    private View submitPhase;

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
}
