package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;

public class InstituteSignUpHostActivity extends Activity {

    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_signup_host);
        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.add(R.id.main_view, new InstituteInfoFragment()).commit();

        sharedPrefs = getSharedPreferences("session", MODE_MULTI_PROCESS);
        sharedPrefEdit = sharedPrefs.edit();

        sharedPrefEdit.putBoolean("instituteInfo", false);
        sharedPrefEdit.putBoolean("addMember", false);
    }

}
