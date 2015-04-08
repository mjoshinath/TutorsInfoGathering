package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class InstituteInfoFragment extends Fragment implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1;

    private View view;
    private View institutePhase;
    private Button previous;
    private Button next;
    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private ActionBar actionBar;

    private EditText instituteName;
    private EditText address1;
    private EditText address2;
    private EditText city;
    private EditText state;
    private EditText zipCode;
    private EditText country;
    private EditText username;
    private EditText dateOfBirth;
    private EditText jobDescription;
    private EditText emailId;
    private EditText mobileNumber;
    private Button captureImage;
    private Bitmap photo;
    private ImageView instituteImage;
    private String userImageString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_institute_info, container, false);

        institutePhase = getActivity().findViewById(R.id.phase_institute);
        previous = (Button) getActivity().findViewById(R.id.previous);
        next = (Button) getActivity().findViewById(R.id.next);

        getWidgets(view);

        next.setOnClickListener(this);
        dateOfBirth.setOnClickListener(this);
        captureImage.setOnClickListener(this);

        institutePhase.setBackgroundColor(Color.parseColor("#FFCB04"));
        previous.setVisibility(View.INVISIBLE);

        setHasOptionsMenu(true);
        setActionBarProperties();

        return view;
    }

    private void getWidgets(View view) {
        instituteName = (EditText) view.findViewById(R.id.institute_name);
        address1 = (EditText) view.findViewById(R.id.address1);
        address2 = (EditText) view.findViewById(R.id.address2);
        city = (EditText) view.findViewById(R.id.city);
        state = (EditText) view.findViewById(R.id.state);
        zipCode = (EditText) view.findViewById(R.id.zip_code);
        country = (EditText) view.findViewById(R.id.country);
        dateOfBirth = (EditText) view.findViewById(R.id.date_of_birth);
        jobDescription = (EditText) view.findViewById(R.id.job_description);
        username = (EditText) view.findViewById(R.id.username);
        emailId = (EditText) view.findViewById(R.id.email_id);
        mobileNumber = (EditText) view.findViewById(R.id.mobile_number);
        captureImage = (Button) view.findViewById(R.id.capture_image);
        instituteImage = (ImageView) view.findViewById(R.id.institute_image);
    }

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Institute Information");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                fragmentReplaceMethod();
                break;
            case R.id.date_of_birth:
                showDatePickerDialog(v);
                break;
            case R.id.capture_image:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            instituteImage.setImageBitmap(photo);
            userImageString = BitMapToString(photo);
            Log.d("test18", "on capture" + userImageString);
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(getActivity(), HomePage.class);
                startActivity(intent1);
                /*sharedPrefsEditable.clear();
                sharedPrefsEditable.commit();*/
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void fragmentReplaceMethod() {
        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("Institute");
        fragmentTransaction.replace(R.id.main_view, new AddMemberFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        institutePhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        private int year;
        private int month;
        private int day;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
            dateOfBirth.setText(day + " / " + (month + 1) + " / " + year);
        }
    }
}
