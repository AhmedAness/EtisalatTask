package com.etisalat.sampletask.bases.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.etisalat.sampletask.R;
import com.etisalat.sampletask.bases.Adapter.MyRecyclerViewAdapter;
import com.etisalat.sampletask.bases.BaseActivity;
import com.etisalat.sampletask.bases.BasePresenter;
import com.etisalat.sampletask.bases.Interface.Api;
import com.etisalat.sampletask.bases.Interface.BasePresenterListener;
import com.etisalat.sampletask.bases.model.item;
import com.etisalat.sampletask.bases.model.menu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Foods_Information extends BaseActivity implements MyRecyclerViewAdapter.ItemClickListener{

    List<item> items;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    Button Refresh;
    private Toolbar mTopToolbar;
    TextView RefreshTime;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods__information);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        Init();

    }

    private void Init() {

        recyclerView = findViewById(R.id.Menu_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Refresh=findViewById(R.id.Refresh);
        RefreshTime=findViewById(R.id.Refresh_Time);
        RefreshTime.setText("asdasdasd");
        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    GetItems();
            }
        });
        GetItems();
    }

    @Override
    protected BasePresenter setupPresenter() {
        return null;
    }

    private void GetItems() {
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
                items =menul.getItem();
                adapter = new MyRecyclerViewAdapter(Foods_Information.this, items);
                adapter.setClickListener(Foods_Information.this);
                recyclerView.setAdapter(adapter);
                hideProgress();

            }

            @Override
            public void onFailure(Call<menu> call, Throwable t) {
                Toast.makeText(Foods_Information.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                showSnackbar("t.getLocalizedMessage()",getWindow().getDecorView().getRootView());
            }

        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
    }
}
