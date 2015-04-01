package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;


public class DataTransferActivity extends Activity {

    private SharedPreferences sharedPrefs;
    private static final String TABLE_QUERY = "CREATE TABLE IF NOT EXISTS i_reg_ezee_data_tbl(TUTOR_DETAILS VARCHAR(200))";
    private static final String INSERT_DETAILS_QUERY = "INSERT INTO i_reg_ezee_data_tbl(TUTOR_DETAILS) VALUES(?)";
    private static final String SELECT_DETAILS_QUERY = "SELECT * FROM i_reg_ezee_data_tbl";
    private SQLiteDatabase mydatabase;
    private SQLiteStatement preparedStatement;
    private JSONObject jsonObject;
    private String stringToBeInserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_transfer);

        sharedPrefs = getSharedPreferences("session", MODE_MULTI_PROCESS);

        if (!mydatabase.isOpen())
            mydatabase = openOrCreateDatabase("iRegEzee", MODE_PRIVATE, null);

        mydatabase.execSQL(TABLE_QUERY);//Creating Table

        preparedStatement = mydatabase.compileStatement(INSERT_DETAILS_QUERY);//Inserting details into DB

        mydatabase.execSQL(SELECT_DETAILS_QUERY);//Select data from DB

        createJSONObject(sharedPrefs);
        stringToBeInserted = jsonObject.toString();

        preparedStatement.bindString(1, stringToBeInserted);
        preparedStatement.execute();
    }

    public void createJSONObject(SharedPreferences sharedPrefs) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("storedValue1", sharedPrefs.getString("", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /*For reading JSON from DataBase
    *
    * String json = Read_column_value_logic_here
    * JSONObject jsonObject = new JSONObject(json);
    */
}
