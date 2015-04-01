package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class HomePage extends Activity implements View.OnClickListener {

    private Button signUpTutorButton;
    private ActionBar actionBar;
    private Button addMettingLog;
    private SQLiteDatabase mydatabase;

    private static final String SELECT_DETAILS_QUERY = "SELECT * FROM i_reg_ezee_data_tbl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        signUpTutorButton = (Button) findViewById(R.id.signup_tutor);
        addMettingLog = (Button) findViewById(R.id.add_meeting_log);

        signUpTutorButton.setOnClickListener(this);
        addMettingLog.setOnClickListener(this);

        setActionBarProperties();
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("IRegEzee");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_tutor:
                Intent intent = new Intent(this, RegStepsHostActivity.class);
                startActivity(intent);
                break;
            case R.id.add_meeting_log:
                Intent webViewIntent = new Intent(this, ShowWebView.class);
                startActivity(webViewIntent);
                break;
            case R.id.sync_data:
                if (!mydatabase.isOpen())
                    mydatabase = openOrCreateDatabase("iRegEzee", MODE_PRIVATE, null);
                Cursor cursor = mydatabase.rawQuery(SELECT_DETAILS_QUERY, null);
                String data = "";
                if (cursor.moveToFirst()) {
                    do {
                        data = cursor.getString(0);
                        Log.d("test18", data);
                    } while (cursor.moveToNext());
                }
                mydatabase.close();
                break;
        }
    }


}
