package support;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import model.categories.InstituteDetails;
import model.categories.TutorDetails;
import tutorsweb.ehc.com.tutorsinfogathering.R;

/**
 * Created by ehc on 2/4/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "i_reg_ezee.db";
    private static final int DATABASE_VERSION = 1;

    private static final String MARKETING_CREDENTIALS_TABLE_NAME = "tutors_web_marketing_credentials";
    private static final String TUTOR_DETAILS_TABLE_NAME = "tutor_details_tbl";
    private static final String INSTITUTE_DETAILS_TABLE_NAME = "institute_details_tbl";

    private String[] emailIdResource;
    private String[] passwordResource;
    private ArrayList<TutorDetails> tutorDetails = new ArrayList<TutorDetails>();
    private ArrayList<InstituteDetails> instituteDetails = new ArrayList<InstituteDetails>();
    private String[] userIdResource;
    private int id;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase tutorsWebDataBase) {
        tutorsWebDataBase.execSQL("CREATE TABLE " + MARKETING_CREDENTIALS_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT,password TEXT,userId TEXT); ");
        tutorsWebDataBase.execSQL("CREATE TABLE " + TUTOR_DETAILS_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, tutorCredentials TEXT); ");
        tutorsWebDataBase.execSQL("CREATE TABLE " + INSTITUTE_DETAILS_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, instituteCredentials TEXT); ");

        emailIdResource = context.getResources().getStringArray(R.array.emailIds);
        passwordResource = context.getResources().getStringArray(R.array.passwords);
        userIdResource = context.getResources().getStringArray(R.array.ids);

        for (int i = 0; i < emailIdResource.length; i++) {
            ContentValues values = new ContentValues();
            values.put("email", emailIdResource[i]);
            values.put("password", passwordResource[i]);
            values.put("userId", userIdResource[i]);
            tutorsWebDataBase.insert(MARKETING_CREDENTIALS_TABLE_NAME, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertMarketingCredentials(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        long rowId = db.insert(MARKETING_CREDENTIALS_TABLE_NAME, null, values);
        db.close();
        return rowId;
    }

    public int getAuthentication(String email, String password) {
        String selectQuery = "SELECT * FROM " + MARKETING_CREDENTIALS_TABLE_NAME + " where email='" + email + "' and password='" + password + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getColumnIndex("userId");
                Log.d("test123", "id-->" + id);
            } while (cursor.moveToNext());
        }

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public long insertTutorDetails(String jsonObjectInStringFormat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tutorCredentials", jsonObjectInStringFormat);
        long rowId = db.insert(TUTOR_DETAILS_TABLE_NAME, null, values);
        db.close();
        return rowId;
    }

    public ArrayList<TutorDetails> getTutorDetails() {
        String selectQuery = "SELECT  * FROM " + TUTOR_DETAILS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int count = cursor.getCount();
        Log.d("test08", "count-" + count);
        if (cursor.moveToFirst()) {
            do {
                TutorDetails tutorDetail = new TutorDetails();
                tutorDetail.setId(cursor.getInt(0));
                tutorDetail.setDetails(cursor.getString(1));
//                int i = 0;
                tutorDetails.add(tutorDetail);
//                i++;
                Log.d("test08", "tutorDetail-" + tutorDetail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("test08", "tutorDetails-" + tutorDetails);
        return tutorDetails;
    }

    public void deleteTutor(long id) {
        Log.d("test08", "delete-" + id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TUTOR_DETAILS_TABLE_NAME + " WHERE id=" + id);
    }

    public void deleteInstitute(long id) {
        Log.d("test08", "delete-" + id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + INSTITUTE_DETAILS_TABLE_NAME + " WHERE id=" + id);
    }

    public long insertInstituteDetails(String jsonObjectInStringFormat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("instituteCredentials", jsonObjectInStringFormat);
        long rowId = db.insert(INSTITUTE_DETAILS_TABLE_NAME, null, values);
        db.close();
        return rowId;
    }

    public ArrayList<InstituteDetails> getInstituteDetails() {
        String selectQuery = "SELECT  * FROM " + INSTITUTE_DETAILS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        int count = cursor.getCount();
        Log.d("test08", "count-" + count);
        if (cursor.moveToFirst()) {
            do {
                InstituteDetails instituteDetail = new InstituteDetails();
                instituteDetail.setId(cursor.getInt(0));
                instituteDetail.setDetails(cursor.getString(1));
//                int i = 0;
                instituteDetails.add(instituteDetail);
//                i++;
                Log.d("test08", "tutorDetail-" + instituteDetail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.d("test08", "tutorDetails-" + tutorDetails);
        return instituteDetails;
    }
}
