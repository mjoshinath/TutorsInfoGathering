package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class RegStepsHostActivity extends Activity implements View.OnClickListener {

    private ActionBar actionBar;
    private View phasePersonnel;
    private View phaseCategories;
    private View phaseProfessional;
    private View phaseWorkExp;
    private View phaseSubmit;
    private View phaseCaptureImage;
    private Button previousButton;

    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_host_layout);

        getWidgets();

        /*phasePersonnel.setOnClickListener(this);
        phaseCategories.setOnClickListener(this);
        phaseProfessional.setOnClickListener(this);
        phaseWorkExp.setOnClickListener(this);
        phaseSubmit.setOnClickListener(this);
        phaseCaptureImage.setOnClickListener(this);
        previousButton.setOnClickListener(this);*/

        sharedPrefs = getSharedPreferences(getString(R.string.session), MODE_MULTI_PROCESS);
        sharedPrefEdit = sharedPrefs.edit();

        sharedPrefEdit.putBoolean(getString(R.string.personnel), false);
        sharedPrefEdit.putBoolean(getString(R.string.captureImage), false);
        sharedPrefEdit.putBoolean(getString(R.string.categories), false);
        sharedPrefEdit.putBoolean(getString(R.string.professional), false);
        sharedPrefEdit.putBoolean(getString(R.string.workExp), false);
        sharedPrefEdit.putBoolean(getString(R.string.submit), false);

        getActionBarProperties();

        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("");

        sharedPrefEdit.putInt(getString(R.string.mainView), R.id.main_view);
        sharedPrefEdit.commit();

        fragmentTransaction.add(R.id.main_view, new PersonnelInfoFragment()).commit();
    }

    private void getWidgets() {
        phasePersonnel = findViewById(R.id.phase_personnel);
        phaseCaptureImage = findViewById(R.id.phase_capture_image);
        phaseCategories = findViewById(R.id.phase_categories);
        phaseProfessional = findViewById(R.id.phase_professional);
        phaseWorkExp = findViewById(R.id.phase_work_exp);
        phaseSubmit = findViewById(R.id.phase_submit);
        previousButton = (Button) findViewById(R.id.previous);
    }

    private void getActionBarProperties() {
        actionBar = getActionBar();
//        actionBar.setTitle("RegEzee");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("");
        switch (v.getId()) {
            case R.id.phase_personnel:
                if (sharedPrefs.getBoolean(getString(R.string.personnel), false))
                    fragmentTransaction.replace(R.id.main_view, new PersonnelInfoFragment());
                break;
            case R.id.phase_capture_image:
                if (sharedPrefs.getBoolean(getString(R.string.captureImage), false))
                    fragmentTransaction.replace(R.id.main_view, new CaptureUserImageFragment());
                break;
            case R.id.phase_categories:
                if (sharedPrefs.getBoolean(getString(R.string.categories), false))
                    fragmentTransaction.replace(R.id.main_view, new CategoriesFragment());
                break;
            case R.id.phase_professional:
                if (sharedPrefs.getBoolean(getString(R.string.professional), false))
                    fragmentTransaction.replace(R.id.main_view, new ProfessionalInfoFragment());
                break;
            case R.id.phase_work_exp:
                if (sharedPrefs.getBoolean(getString(R.string.workExp), false))
                    fragmentTransaction.replace(R.id.main_view, new WorkExpFragment());
                break;
            case R.id.phase_submit:
                if (sharedPrefs.getBoolean(getString(R.string.submit), false))
                    fragmentTransaction.replace(R.id.main_view, new SubmitFragment());
                break;
            case R.id.previous:
                onBackPressed();
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

}
