package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class RegStepsHostActivity extends Activity {


    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private ActionBar actionBar;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_host_layout);

        sharedPrefs = getSharedPreferences("session", MODE_MULTI_PROCESS);
        sharedPrefEdit = sharedPrefs.edit();

        getActionBarProperties();

        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("");

        sharedPrefEdit.putInt("mainView", R.id.main_view);
        sharedPrefEdit.commit();

        fragmentTransaction.add(R.id.main_view, new PersonnelInfoFragment()).commit();
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
}