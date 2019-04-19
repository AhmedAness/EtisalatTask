package com.etisalat.sampletask.bases.Fagments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.etisalat.sampletask.R;
import com.etisalat.sampletask.bases.Activities.CaptureImage;
import com.etisalat.sampletask.bases.Activities.Foods_Information;
import com.etisalat.sampletask.bases.Adapter.MyRecyclerViewAdapter;
import com.etisalat.sampletask.bases.BaseFragment;
import com.etisalat.sampletask.bases.BasePresenter;
import com.etisalat.sampletask.bases.Interface.Api;
import com.etisalat.sampletask.bases.Presenters.IFood_infoPresenter;
import com.etisalat.sampletask.bases.Utility.CustomPair;
import com.etisalat.sampletask.bases.Utility.HelperFN;
import com.etisalat.sampletask.bases.model.item;
import com.etisalat.sampletask.bases.model.menu;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class ListFragment extends BaseFragment  implements MyRecyclerViewAdapter.ItemClickListener {


    IFood_infoPresenter iFood_infoPresenter ;

    List<item> items=null;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    FloatingActionButton Refresh;
    FloatingActionButton Cap_Image;
    private Toolbar mTopToolbar;
    TextView RefreshTime;
    SharedPreferences mPrefs ;
    String LastUpdate;


    @Override
    protected BasePresenter setupPresenter() {

        iFood_infoPresenter= new Foods_Information();
        return (BasePresenter) iFood_infoPresenter;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {





        return inflater.inflate(R.layout.listlayout,container,false);

//        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTopToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
//        getActivity().setSupportActionBar(mTopToolbar);
        Init(view);
    }
    private void Init(View view) {

        recyclerView = view.findViewById(R.id.Menu_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Refresh=view.findViewById(R.id.Refresh);
        Cap_Image=view.findViewById(R.id.Cap_Image);
        RefreshTime=view.findViewById(R.id.Refresh_Time);
        mPrefs = getActivity().getPreferences(MODE_PRIVATE);



        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetFromURL();
            }
        });

        Cap_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CaptureImage.class));
            }
        });
        GetItems();
    }
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
                iFood_infoPresenter.onError("A7A from aniss side " );
            }

            @Override
            public void onFailure(Call<menu> call, Throwable t) {
//                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                iFood_infoPresenter.onError(t.getMessage());
            }

        });
    }

    private void SortAndDisplay(List<item> items) {
        HelperFN.getInstance().SortItemsbyalphabeticallyOrder(items);
        DisplayListOfItems(items);
    }

    private void DisplayListOfItems(final List<item> items) {

        adapter = new MyRecyclerViewAdapter(getActivity(), items);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        hideProgress();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mPrefs = getActivity().getPreferences(MODE_PRIVATE);
                HelperFN.getInstance().Save_Data(items,LastUpdate,mPrefs);
            }
        }).start();

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
    }
}
