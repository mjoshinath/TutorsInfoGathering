package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

    private boolean logDetect;
    private Intent intent;
    private SharedPreferences categoriesCredentialsPrefs;
    private SharedPreferences.Editor categoriesCredentialsPrefsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        categoriesCredentialsPrefs = getSharedPreferences(getString(R.string.categories), MODE_MULTI_PROCESS);
        categoriesCredentialsPrefsEdit = categoriesCredentialsPrefs.edit();

        logDetect = categoriesCredentialsPrefs.getBoolean(getString(R.string.logDetect), false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (logDetect) {
                    intent = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                }
            }
        }, 2000);

        setActionBarProperties();
    }

    private void setActionBarProperties() {
        getActionBar().hide();
    }

}
