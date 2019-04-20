package com.etisalat.sampletask.bases.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.etisalat.sampletask.R;
import com.etisalat.sampletask.bases.Adapter.MyRecyclerViewAdapter;
import com.etisalat.sampletask.bases.BasePresenter;
import com.etisalat.sampletask.bases.Interface.Api;
import com.etisalat.sampletask.bases.Fragments.ListFragment;
import com.etisalat.sampletask.bases.Utility.CustomPair;
import com.etisalat.sampletask.bases.Utility.HelperFN;
import com.etisalat.sampletask.bases.model.item;
import com.etisalat.sampletask.bases.model.menu;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * @author ahmed aniss
 *  this java activity contains fragment which display list items after getting this items from,
 *  cach or from url ,
 *  contains two buttons one for refresh data ftom url and other to open secon activity
 */
public class Foods_Information extends BaseActivity implements ListFragment.OnFragmentInteractionListener{

    List<item> items=null;
    FloatingActionButton Refresh;
    FloatingActionButton Cap_Image;
    private Toolbar mTopToolbar;
    TextView RefreshTime;
    SharedPreferences  mPrefs ;
    String LastUpdate;
    BasePresenter basePresenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods__information);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        Init();
    }

    /**
     * this function to define activity items and have item's click
     */
    private void Init() {
        Refresh=findViewById(R.id.Refresh);
        Cap_Image=findViewById(R.id.Cap_Image);
        RefreshTime=findViewById(R.id.Refresh_Time);
        mPrefs = getPreferences(MODE_PRIVATE);

        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetFromURL();
            }
        });

        Cap_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Foods_Information.this,CaptureImage.class));
            }
        });
        GetItems();
    }

    @Override
    protected BasePresenter setupPresenter() {
        return basePresenter;
    }

    /**
     * this function to get items from url or from cach and also to get last update time
     */
    private void GetItems() {
        CustomPair<List<item>, String> pair = HelperFN.getInstance().Get_Data(mPrefs);
        if (pair!=null){
            items=pair.getLift();
            LastUpdate= pair.getRight();
            RefreshTime.setText(LastUpdate);
            SortAndDisplay(items);
        }else {
            GetFromURL();
        }
    }

    /**
     * this function to get data from url
     */
    private void GetFromURL() {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<menu> call = api.GetFoods();
        call.enqueue(new Callback<menu>() {
            @Override
            public void onResponse(Call<menu> call, Response<menu> response) {
                menu menul = response.body();
                items = menul.getItem();
                SortAndDisplay(items);
                Date currentTime = Calendar.getInstance().getTime();
                LastUpdate="Last Update :"+currentTime.getHours()+":"+currentTime.getMinutes();
                RefreshTime.setText(LastUpdate);
                SaveToSharedPref();
            }

            @Override
            public void onFailure(Call<menu> call, Throwable t) {
                Toast.makeText(Foods_Information.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                showSnackbar("t.getLocalizedMessage()", getWindow().getDecorView().getRootView());
            }

        });
    }

    /**
     * this function to Start new thread to save data in shared pref
     */
    private void SaveToSharedPref() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mPrefs = getPreferences(MODE_PRIVATE);
                HelperFN.getInstance().Save_Data(items,LastUpdate,mPrefs);
            }
        }).start();
    }

    /**
     *
     * @param items
     * this function used to Sort Data alphabitically
     */
    private void SortAndDisplay(List<item> items) {
        HelperFN.getInstance().SortItemsbyalphabeticallyOrder(items);
        DisplayListOfItems(items);
    }

    /**
     *
     * @param items
     * this to Call fragment to refresh it's list data
     */
    private void DisplayListOfItems(final List<item> items) {

        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        menu menu = new menu(items);
        String json = gson.toJson(menu);
        bundle.putString("ListData", json);
// set Fragmentclass Arguments
        ListFragment fragobj = new ListFragment();
        fragobj.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentLayout,fragobj).commit();
    }

    @Override
    public void onFragmentInteractionItemClicked(item item) {
        Toast.makeText(this, item.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteractionHideProgress() {
        hideProgress();
    }
}
