package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class DemonstrationFragment extends Fragment {

    private View view;
    private ImageView imageSlides;
    private String imageSlidesType;
    private int position;
    private TypedArray tutorImagesArray;
    private Drawable imageDrawable;

    public DemonstrationFragment(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.demonstration_layout, null);

        tutorImagesArray = getResources().obtainTypedArray(R.array.tutor);
        imageSlidesType = getActivity().getIntent().getStringExtra("imageSlidesType");
        imageSlides = (ImageView) view.findViewById(R.id.image_slides);

        imageDrawable=tutorImagesArray.getDrawable(position);

        imageSlides.setImageDrawable(imageDrawable);
        return view;
    }

}
