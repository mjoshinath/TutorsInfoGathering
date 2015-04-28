package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DemonstrationFragment extends Fragment {

    private View view;
    private ImageView imageSlides;
    private int position;
    private TypedArray tutorImagesArray;
    private Drawable imageDrawable;
    private ActionBar actionBar;

    public DemonstrationFragment(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.demonstration_layout, null);

        tutorImagesArray = getResources().obtainTypedArray(R.array.tutor);

        imageSlides = (ImageView) view.findViewById(R.id.image_slides);

        imageDrawable = tutorImagesArray.getDrawable(position);

        imageSlides.setImageDrawable(imageDrawable);

        setHasOptionsMenu(true);
        setActionBarProperties();
        return view;
    }

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle(getActivity().getString(R.string.demonstration_title));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
