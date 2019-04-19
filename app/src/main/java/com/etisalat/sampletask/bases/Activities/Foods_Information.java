package com.etisalat.sampletask.bases.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.etisalat.sampletask.R;
import com.etisalat.sampletask.bases.Adapter.MyRecyclerViewAdapter;
import com.etisalat.sampletask.bases.BaseActivity;
import com.etisalat.sampletask.bases.BasePresenter;
import com.etisalat.sampletask.bases.Fagments.ListFragment;
import com.etisalat.sampletask.bases.Interface.Api;
import com.etisalat.sampletask.bases.Interface.BasePresenterListener;
import com.etisalat.sampletask.bases.Presenters.Food_infoPresenter;
import com.etisalat.sampletask.bases.Presenters.IFood_infoPresenter;
import com.etisalat.sampletask.bases.Utility.CustomPair;
import com.etisalat.sampletask.bases.Utility.HelperFN;
import com.etisalat.sampletask.bases.model.item;
import com.etisalat.sampletask.bases.model.menu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Foods_Information extends BaseActivity implements IFood_infoPresenter {

    RelativeLayout root;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods__information);
        init();
        getSupportFragmentManager().beginTransaction().replace(R.id.handlerFragment,new ListFragment()).commit();

    }

    private void init() {
        root= findViewById(R.id.rootview);
    }


    @Override
    protected BasePresenter setupPresenter() {
        return null;
    }


    @Override
    public void onError(String Message) {
        showSnackbar(Message,root);
    }
}
