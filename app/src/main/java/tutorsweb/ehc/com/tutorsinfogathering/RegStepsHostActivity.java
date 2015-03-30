package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class RegStepsHostActivity extends Activity implements View.OnClickListener {


    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private ActionBar actionBar;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefEdit;
    private View phasePersonnel;
    private View phaseCategories;
    private View phaseProfessional;
    private View phaseWorkExp;
    private View phaseSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_host_layout);

        getWidgets();

        phasePersonnel.setOnClickListener(this);
        phaseCategories.setOnClickListener(this);
        phaseProfessional.setOnClickListener(this);
        phaseWorkExp.setOnClickListener(this);
        phaseSubmit.setOnClickListener(this);

        sharedPrefs = getSharedPreferences("session", MODE_MULTI_PROCESS);
        sharedPrefEdit = sharedPrefs.edit();

        sharedPrefEdit.putBoolean("personnel", false);
        sharedPrefEdit.putBoolean("categories", false);
        sharedPrefEdit.putBoolean("professional", false);
        sharedPrefEdit.putBoolean("workExp", false);
        sharedPrefEdit.putBoolean("submit", false);

        getActionBarProperties();

        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("");

        sharedPrefEdit.putInt("mainView", R.id.main_view);
        sharedPrefEdit.commit();

        fragmentTransaction.add(R.id.main_view, new PersonnelInfoFragment()).commit();
    }

    private void getWidgets() {
        phasePersonnel = findViewById(R.id.phase_personnel);
        phaseCategories = findViewById(R.id.phase_categories);
        phaseProfessional = findViewById(R.id.phase_professional);
        phaseWorkExp = findViewById(R.id.phase_work_exp);
        phaseSubmit = findViewById(R.id.phase_submit);
    }

    private void getActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("RegEzee");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();

        switch (v.getId()) {
            case R.id.phase_personnel:
                if (sharedPrefs.getBoolean("personnel", false))
                    fragmentTransaction.replace(R.id.main_view, new PersonnelInfoFragment());
                break;
            case R.id.phase_categories:
                if (sharedPrefs.getBoolean("categories", false))
                    fragmentTransaction.replace(R.id.main_view, new CategoriesFragment());
                break;
            case R.id.phase_professional:
                if (sharedPrefs.getBoolean("professional", false))
                    fragmentTransaction.replace(R.id.main_view, new ProfessionalInfoFragment());
                break;
            case R.id.phase_work_exp:
                if (sharedPrefs.getBoolean("workExp", false))
                    fragmentTransaction.replace(R.id.main_view, new WorkExpFragment());
                break;
            case R.id.phase_submit:
                if (sharedPrefs.getBoolean("submit", false))
                    fragmentTransaction.replace(R.id.main_view, new SubmitFragment());
                break;
        }
        fragmentTransaction.commit();
    }
}