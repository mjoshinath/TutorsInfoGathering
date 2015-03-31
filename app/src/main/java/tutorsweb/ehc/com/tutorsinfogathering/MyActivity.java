package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MyActivity extends Activity {

    private static String INSERT_DETAILS_QUERY;
    private SQLiteDatabase mydatabase;
    private static String TABLE_QUERY;
    private SQLiteStatement preparedStatement;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        sharedPrefs = getSharedPreferences("session", MODE_MULTI_PROCESS);

        TABLE_QUERY = "CREATE TABLE IF NOT EXISTS i_reg_ezee_data_tbl(FIRST_NAME VARCHAR(20),\n" +
                "LAST_NAME VARCHAR(20),\n" +
                "USER_NAME VARCHAR(20),\n" +
                "DATE_OF_BIRTH VARCHAR(15),\n" +
                "ADDRESS VARCHAR(30),\n" +
                "CITY VARCHAR(20),\n" +
                "STATE VARCHAR(20),\n" +
                "ZIP_CODE NUMBER(6),\n" +
                "COUNTRY VARCHAR(20),\n" +
                "USER_IMAGE VARCHAR(20),\n" +
                "CATEGORIES VARCHAR(100),\n" +
                "YRS_OF_TEACHING_EXP VARCHAR(15),\n" +
                "TUTORING_EXP VARCHAR(5),\n" +
                "LANGUAGES VARCHAR(30),\n" +
                "INTERESTS VARCHAR(50),\n" +
                "DEGREE_NAME VARCHAR(50),\n" +
                "UNIVERSITY_NAME VARCHAR(50),\n" +
                "START_DATE VARCHAR(15),\n" +
                "END_DATE VARCHAR(15),\n" +
                "FIELD_OF_STUDY VARCHAR(20),\n" +
                "LOCATION VARCHAR(20),\n" +
                "COMPANY_NAME VARCHAR(25),\n" +
                "JOB_TITLE VARCHAR(20),\n" +
                "START_DATE_EXP VARCHAR(15),\n" +
                "END_DATE_EXP VARCHAR(15),\n" +
                "LOCATION_EXP VARCHAR(20),\n" +
                "JOB_DESCRIPTION VARCHAR(150))";

        INSERT_DETAILS_QUERY = "INSERT INTO i_reg_ezee_data_tbl(FIRST_NAME," +
                "LAST_NAME," +
                "USER_NAME," +
                "DATE_OF_BIRTH," +
                "ADDRESS,CITY,STATE,ZIP_CODE,COUNTRY,USER_IMAGE,CATEGORIES,YRS_OF_TEACHING_EXP,TUTORING_EXP," +
                "LANGUAGES,INTERESTS,DEGREE_NAME,UNIVERSITY_NAME,START_DATE,END_DATE,FIELD_OF_STUDY,LOCATION," +
                "COMPANY_NAME,JOB_TITLE,START_DATE_EXP,END_DATE_EXP,LOCATION_EXP,JOB_DESCRIPTION) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        if (!mydatabase.isOpen())
            mydatabase = openOrCreateDatabase("iRegEzee", MODE_PRIVATE, null);

        mydatabase.execSQL(TABLE_QUERY);

        preparedStatement = mydatabase.compileStatement(INSERT_DETAILS_QUERY);

        preparedStatement.bindString(1, sharedPrefs.getString("", ""));
        preparedStatement.bindString(2, sharedPrefs.getString("", ""));
        preparedStatement.bindString(3, sharedPrefs.getString("", ""));
        preparedStatement.bindString(4, sharedPrefs.getString("", ""));
        preparedStatement.bindString(5, sharedPrefs.getString("", ""));
        preparedStatement.bindString(6, sharedPrefs.getString("", ""));
        preparedStatement.bindString(7, sharedPrefs.getString("", ""));
        preparedStatement.bindString(8, sharedPrefs.getString("", ""));
        preparedStatement.bindString(9, sharedPrefs.getString("", ""));
        preparedStatement.bindString(10, sharedPrefs.getString("", ""));
        preparedStatement.bindString(11, sharedPrefs.getString("", ""));
        preparedStatement.bindString(12, sharedPrefs.getString("", ""));
        preparedStatement.bindString(13, sharedPrefs.getString("", ""));
        preparedStatement.bindString(14, sharedPrefs.getString("", ""));
        preparedStatement.bindString(15, sharedPrefs.getString("", ""));
        preparedStatement.bindString(16, sharedPrefs.getString("", ""));
        preparedStatement.bindString(17, sharedPrefs.getString("", ""));
        preparedStatement.bindString(18, sharedPrefs.getString("", ""));
        preparedStatement.bindString(19, sharedPrefs.getString("", ""));
        preparedStatement.bindString(20, sharedPrefs.getString("", ""));
        preparedStatement.bindString(21, sharedPrefs.getString("", ""));
        preparedStatement.bindString(22, sharedPrefs.getString("", ""));
        preparedStatement.bindString(23, sharedPrefs.getString("", ""));
        preparedStatement.bindString(24, sharedPrefs.getString("", ""));
        preparedStatement.bindString(25, sharedPrefs.getString("", ""));
        preparedStatement.bindString(26, sharedPrefs.getString("", ""));
        preparedStatement.bindString(27, sharedPrefs.getString("", ""));

        preparedStatement.execute();
    }

}
