package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class DemonstrationActivity extends Activity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demonstration);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new DemonstrationAdapter(getFragmentManager()));
    }

    private class DemonstrationAdapter extends FragmentStatePagerAdapter {
        public DemonstrationAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new DemonstrationFragment(position);
        }

        @Override
        public int getCount() {
            return getIntent().getIntExtra(getString(R.string.size), 0);
        }
    }
}
