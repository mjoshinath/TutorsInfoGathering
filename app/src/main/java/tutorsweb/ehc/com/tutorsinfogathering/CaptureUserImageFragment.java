package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class CaptureUserImageFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button next;
    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private Button captureImage;
    private Bitmap photo;
    private ImageView userImage;
    private String userImageString;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEdit;
    private Button previous;
    private ActionBar actionBar;
    private String userImageStringFormat;
    private Bitmap userImageInBitFormat;
    private View captureImagePhase;

    private static final int CAMERA_REQUEST = 1888;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_capture_user_image, container, false);

        sharedPrefs = getActivity().getSharedPreferences("session", Context.MODE_MULTI_PROCESS);
        sharedPrefsEdit = sharedPrefs.edit();

        sharedPrefsEdit.putBoolean("captureImage", false);
        sharedPrefsEdit.commit();

        next = (Button) getActivity().findViewById(R.id.next);
        previous = (Button) getActivity().findViewById(R.id.previous);
        captureImagePhase = (View) getActivity().findViewById(R.id.phase_capture_image);
        captureImage = (Button) view.findViewById(R.id.capture_image);
        userImage = (ImageView) view.findViewById(R.id.user_image);

        captureImagePhase.setBackgroundColor(Color.parseColor("#32B1D2"));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentReplaceMethod();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        setHasOptionsMenu(true);

        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        updateUi();

        setActionBarProperties();

        return view;
    }

    private void updateUi() {
        userImageStringFormat = sharedPrefs.getString("userImageString", "");
        userImageInBitFormat = stringToBitMap(userImageStringFormat);
        userImage.setImageBitmap(userImageInBitFormat);
    }

    public Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Capture User Image");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(getActivity(), HomePage.class);
                startActivity(intent1);
                sharedPrefsEdit.clear();
                sharedPrefsEdit.commit();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void fragmentReplaceMethod() {
        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("CaptureImage");
        fragmentTransaction.replace(R.id.main_view, new CategoriesFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.capture_image:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            userImage.setImageBitmap(photo);
            userImageString = BitMapToString(photo);
            Log.d("test18", "on capture" + userImageString);
            sharedPrefsEdit.putString("userImageString", userImageString);
            sharedPrefsEdit.commit();
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
    public void onDestroyView() {
        super.onDestroyView();
        captureImagePhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}