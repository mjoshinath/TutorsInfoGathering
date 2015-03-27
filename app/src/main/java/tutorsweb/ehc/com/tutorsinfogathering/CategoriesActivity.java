package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CategoriesActivity extends Activity implements View.OnClickListener {

    private ExpandableListView expListView;
    private HashMap<String, String[]> mainCategoriesAndChilds;
    private String[] mainCategoryNames;
    private Button next;
    private Button previous;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        mainCategoryNames = new String[]{"mathematics", "arts_and_humanities", "business", "engineering_and_technology", "foreign_languages", "history", "science", "social_science"};
        mainCategoriesAndChilds = new HashMap<String, String[]>();

        mainCategoriesAndChilds.put("mathematics", getResources().getStringArray(R.array.mathematics));
        mainCategoriesAndChilds.put("arts_and_humanities", getResources().getStringArray(R.array.arts_and_humanities));
        mainCategoriesAndChilds.put("business", getResources().getStringArray(R.array.business));
        mainCategoriesAndChilds.put("engineering_and_technology", getResources().getStringArray(R.array.engineering_and_technology));
        mainCategoriesAndChilds.put("foreign_languages", getResources().getStringArray(R.array.foreign_languages));
        mainCategoriesAndChilds.put("history", getResources().getStringArray(R.array.history));
        mainCategoriesAndChilds.put("science", getResources().getStringArray(R.array.science));
        mainCategoriesAndChilds.put("social_science", getResources().getStringArray(R.array.social_science));

        expListView = (ExpandableListView) findViewById(R.id.categories_exp_listview);
        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(getApplicationContext(), mainCategoryNames, mainCategoriesAndChilds);
        expListView.setAdapter(adapter);

        /*expListView.setDivider(null);
        expListView.setDividerHeight(0);*/

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        View footer = getLayoutInflater().inflate(R.layout.navigation_footer, null);
        expListView.addFooterView(footer);

        next = (Button) footer.findViewById(R.id.next);
        previous = (Button) footer.findViewById(R.id.previous);

        previous.setOnClickListener(this);
        next.setOnClickListener(this);

        setActionBarProperties();
    }

    private void setActionBarProperties() {
        actionBar = getActionBar();
        actionBar.setTitle("Categories");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                Intent intent = new Intent(this, ProfessionalInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.previous:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class ExpandableListViewAdapter extends BaseExpandableListAdapter {

        private Context context;
        private String[] listDataHeader;
        private HashMap<String, String[]> listDataChild;

        public ExpandableListViewAdapter(Context context, String[] listDataHeader,
                                         HashMap<String, String[]> listChildData) {
            this.context = context;
            this.listDataHeader = mainCategoryNames;
            this.listDataChild = listChildData;
        }

        private TextView lblListHeader;

        @Override
        public int getGroupCount() {
            return mainCategoryNames.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mainCategoriesAndChilds.get(mainCategoryNames[groupPosition]).length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            Log.d("test18", "groupPosition" + groupPosition);
            return mainCategoryNames[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            Log.d("test18", "childPosition" + childPosition);
            return mainCategoriesAndChilds.get(mainCategoryNames[groupPosition])[childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            String headerTitleTemp = headerTitle.toString().replace("_", " ");
            headerTitleTemp = headerTitleTemp.substring(0, 1).toUpperCase() + headerTitleTemp.substring(1);
            lblListHeader.setText(headerTitleTemp);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }

            CheckBox txtListChild = (CheckBox) convertView.findViewById(R.id.lblListItem);
            txtListChild.setText(childText);

            return convertView;
        }


    }
}
