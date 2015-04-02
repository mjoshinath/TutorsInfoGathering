package tutorsweb.ehc.com.tutorsinfogathering;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import helper.WebServiceCallBack;
import helper.WebserviceHelper;
import model.categories.Category;
import model.categories.SubCategory;


public class CategoriesFragment extends Fragment implements View.OnClickListener, WebServiceCallBack {

    private ExpandableListView expListView;
    private HashMap<String, String[]> mainCategoriesAndChilds;
    private HashSet<String> checkedCategories = new HashSet<String>();
    private HashSet<Integer> checkedCategoriesIds = new HashSet<Integer>();
    //    private String[] mainCategoryNames;
    private ArrayList<String> mainCategoryNames;
    private Button next;
    private ActionBar actionBar;
    private View view;
    private FragmentManager fragmentMngr;
    private FragmentTransaction fragmentTransaction;
    private Button nextButton;
    private Button previous;
    private View categoriesPhase;
    private SharedPreferences userSharedPreference;
    private SharedPreferences.Editor sharedPrefsEditable;
    private int categoriesCheckedSizes;
    private Iterator<String> iterator;
    private StringBuffer categoriesString = new StringBuffer();
    private String categoriesStringTemp;
    private String url;
    private SubCategory subCategoriesResponse;
    private Integer id;
    private String name;
    private String response;
    ArrayList<Category> categoryResponse;
    private SharedPreferences categorySharedPref;
    private SharedPreferences.Editor categoryEditor;

    /*@Override
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

        *//*expListView.setDivider(null);
        expListView.setDividerHeight(0);*//*
main_view
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
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_categories, null);

        userSharedPreference = getActivity().getSharedPreferences("session", Context.MODE_MULTI_PROCESS);
        categorySharedPref = getActivity().getSharedPreferences("categories", Context.MODE_MULTI_PROCESS);
        categoryEditor = categorySharedPref.edit();
        sharedPrefsEditable = userSharedPreference.edit();

        sharedPrefsEditable.putBoolean("categories", true);
        sharedPrefsEditable.commit();


        /*mainCategoryNames = new String[]{"mathematics", "arts_and_humanities", "business", "engineering_and_technology", "foreign_languages", "history", "science", "social_science"};
        mainCategoriesAndChilds = new HashMap<String, String[]>();

        mainCategoriesAndChilds.put("mathematics", getResources().getStringArray(R.array.mathematics));
        mainCategoriesAndChilds.put("arts_and_humanities", getResources().getStringArray(R.array.arts_and_humanities));
        mainCategoriesAndChilds.put("business", getResources().getStringArray(R.array.business));
        mainCategoriesAndChilds.put("engineering_and_technology", getResources().getStringArray(R.array.engineering_and_technology));
        mainCategoriesAndChilds.put("foreign_languages", getResources().getStringArray(R.array.foreign_languages));
        mainCategoriesAndChilds.put("history", getResources().getStringArray(R.array.history));
        mainCategoriesAndChilds.put("science", getResources().getStringArray(R.array.science));
        mainCategoriesAndChilds.put("social_science", getResources().getStringArray(R.array.social_science));*/

        expListView = (ExpandableListView) view.findViewById(R.id.categories_exp_listview);
        String json = categorySharedPref.getString("categories", "");
        if (json != null && !json.isEmpty()) {
            ArrayList<Category> categories = new Gson().fromJson(json, new TypeToken<ArrayList<Category>>() {
            }.getType());
            ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(getActivity().getApplicationContext(), categories);
            expListView.setAdapter(adapter);
        } else {
            new WebserviceHelper(getActivity().getApplicationContext()).getData(this);
        }

        setActionBarProperties();
        nextButton = (Button) getActivity().findViewById(R.id.next);
        previous = (Button) getActivity().findViewById(R.id.previous);
        previous.setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
        nextButton.setOnClickListener(this);

        /*nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (doValidation())
                if (categoriesCheckedSizes >= 3) {
                    sharedPrefsEditable.putStringSet("checkedCategories", checkedCategories);
                    sharedPrefsEditable.commit();
                    fragmentReplaceMethod();
                } else {
                    Toast.makeText(getActivity(), "Select minimum of three categories", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        categoriesPhase = getActivity().findViewById(R.id.phase_categories);
        categoriesPhase.setBackgroundColor(Color.parseColor("#32B1D2"));
//        url = "http://192.168.1.124:5000/api/v1/categories";
//        new WebserviceHelper(getActivity()).getData(this);

        return view;
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
                Log.d("test18", "next called");
                    /*Intent intent = new Intent(this, ProfessionalInfoFragment.class);
                    startActivity(intent);*/
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
    public void hideProgressBarOnFailure() {

    }


   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
    }*/

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
            Log.d("test18", "childCount" + categories.get(groupPosition).getSubCategories().size());
            return categories.get(groupPosition).getSubCategories().size();
        }

        @Override
        public Category getGroup(int groupPosition) {
//            Log.d("test18", "groupPosition" + groupPosition);
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
            lblListHeader.setTypeface(null, Typeface.BOLD);
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
            if(convertView==null) {
                LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }
            CheckBox txtListChild = (CheckBox) convertView.findViewById(R.id.lblListItem);
            txtListChild.setText(subCategory.getName());
            txtListChild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        checkedCategories.add(buttonView.getText().toString().trim());
                        checkedCategoriesIds.add(subCategory.getId());
                    } else {
                        checkedCategories.remove(buttonView.getText().toString().trim());
                        checkedCategoriesIds.remove(subCategory.getId());
                    }
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
