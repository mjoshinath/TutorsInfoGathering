package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import helper.WebServiceCallBack;
import helper.WebserviceHelper;
import model.categories.Category;
import model.categories.SubCategory;

public class CategoriesFragment extends Fragment implements View.OnClickListener, WebServiceCallBack {

    private ExpandableListView expListView;
    private HashSet<String> checkedCategories = new HashSet<String>();
    private HashSet<Integer> checkedCategoriesIds = new HashSet<Integer>();
    private ActionBar actionBar;
    private View view;
    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private View categoriesPhase;
    private int categoriesCheckedSizes;

    ArrayList<Category> categoryResponse;

    private Button nextButton;
    private Button previous;

    private SharedPreferences userSharedPreference;
    private SharedPreferences.Editor sharedPrefsEditable;

    private SharedPreferences categorySharedPref;
    private SharedPreferences.Editor categoryEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_categories, null);

        getSharedPreferences();

        sharedPrefsEditable.putBoolean("categories", true);
        sharedPrefsEditable.commit();

        expListView = (ExpandableListView) view.findViewById(R.id.categories_exp_listview);
        getCategoriesData();

        setActionBarProperties();
        nextButton = (Button) getActivity().findViewById(R.id.next);
        previous = (Button) getActivity().findViewById(R.id.previous);
        setHasOptionsMenu(true);

        nextButton.setOnClickListener(this);
        previous.setOnClickListener(this);

        categoriesPhase = getActivity().findViewById(R.id.phase_categories);
        categoriesPhase.setBackgroundColor(Color.parseColor("#FFCB04"));
        categoriesPhase.setClickable(false);

        return view;
    }

    private void getCategoriesData() {
        String json = categorySharedPref.getString("categories", "");
        if (json != null && !json.isEmpty()) {
            ArrayList<Category> categories = new Gson().fromJson(json, new TypeToken<ArrayList<Category>>() {
            }.getType());
            ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(getActivity().getApplicationContext(), categories);
            expListView.setAdapter(adapter);
        } else {
            new WebserviceHelper(getActivity().getApplicationContext()).getData(this, "categories");
        }
    }

    private void getSharedPreferences() {
        userSharedPreference = getActivity().getSharedPreferences("session", Context.MODE_MULTI_PROCESS);
        sharedPrefsEditable = userSharedPreference.edit();
        categorySharedPref = getActivity().getSharedPreferences("categories", Context.MODE_MULTI_PROCESS);
        categoryEditor = categorySharedPref.edit();
    }

    private void fragmentReplaceMethod() {
        fragmentMngr = getFragmentManager();
        fragmentTransaction = fragmentMngr.beginTransaction();
        fragmentTransaction.addToBackStack("Category");
        fragmentTransaction.replace(R.id.main_view, new ProfessionalInfoFragment());
        fragmentTransaction.commit();
    }

    private void setActionBarProperties() {
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Categories");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(getActivity(), HomePage.class);
                startActivity(intent1);
                sharedPrefsEditable.clear();
                sharedPrefsEditable.commit();
                break;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
//                if (categoriesCheckedSizes >= 3) {
                sharedPrefsEditable.putStringSet("checkedCategories", checkedCategories);
                sharedPrefsEditable.commit();
                fragmentReplaceMethod();
//                } else {
                Toast.makeText(getActivity(), "Select minimum of three categories", Toast.LENGTH_SHORT);
//                }
                break;
            case R.id.previous:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void populateData(String jsonResponse) {
        categoryResponse = new Gson().fromJson(jsonResponse, new TypeToken<List<Category>>() {
        }.getType());
        categoryEditor.putString("categories", jsonResponse).commit();
        Log.d("test18", "categories" + categoryResponse);
        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(getActivity().getApplicationContext(), categoryResponse);
        expListView.setAdapter(adapter);
    }

    @Override
    public void hideProgressBarOnFailure(String response) {

    }

    private class ExpandableListViewAdapter extends BaseExpandableListAdapter {

        private Context context;
        private ArrayList<Category> categories;
        private TextView lblListHeader;

        public ExpandableListViewAdapter(Context applicationContext, ArrayList<Category> categoryResponse) {
            categories = categoryResponse;
            context = applicationContext;
        }

        @Override
        public int getGroupCount() {
            return categories.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return categories.get(groupPosition).getSubCategories().size();
        }

        @Override
        public Category getGroup(int groupPosition) {
            return categories.get(groupPosition);
        }

        @Override
        public SubCategory getChild(int groupPosition, int childPosition) {
            Log.d("test18", "childPosition" + childPosition);
            return categories.get(groupPosition).getSubCategories().get(childPosition);
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
            Category category = getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }
            lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
            lblListHeader.setText(category.getName());
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final SubCategory subCategory = getChild(groupPosition, childPosition);
            Log.d("test18", "subCategories :" + subCategory);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }
            CheckBox txtListChild = (CheckBox) convertView.findViewById(R.id.lblListItem);

            txtListChild.setText(subCategory.getName());
            txtListChild.setChecked(userSharedPreference.getBoolean(txtListChild.getText().toString().trim(), false));
            txtListChild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        checkedCategories.add(buttonView.getText().toString().trim());
                        checkedCategoriesIds.add(subCategory.getId());
                        sharedPrefsEditable.putBoolean(buttonView.getText().toString().trim(), true);
                    } else {
                        checkedCategories.remove(buttonView.getText().toString().trim());
                        checkedCategoriesIds.remove(subCategory.getId());
                        sharedPrefsEditable.putBoolean(buttonView.getText().toString().trim(), false);
                    }
                    sharedPrefsEditable.commit();
                    Log.d("test18", "checkedCategories :" + checkedCategories);
                    categoriesCheckedSizes = checkedCategories.size();
                }
            });
            return convertView;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        categoriesPhase.setBackgroundColor(Color.parseColor("#B0B6BC"));
        categoriesPhase.setClickable(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
