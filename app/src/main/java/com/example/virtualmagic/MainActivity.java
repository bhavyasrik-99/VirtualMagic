package com.example.virtualmagic;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_main)
    RecyclerView recyclerView;
    VirtualViewHolder vvh;
    @BindView(R.id.avl)
    AVLoadingIndicatorView indicator;
    List<VirtualModel> vmodellist;
    private static final String shapi="https://superheroapi.com/api.php/408363463222029/";
    @BindView(R.id.linear_mainxml)
    LinearLayout linearLayout;
    static boolean isIt=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        vmodellist=new ArrayList<>();
        Intent i;
        i=new Intent(this
                ,LoginActivity.class);
        if(!isIt) {

            startActivity(i);
        }

        if(savedInstanceState!=null)
        {
            vmodellist=(List<VirtualModel>)savedInstanceState.getSerializable("DATA");
            recyclerView.setAdapter(new VirtualAdapter(vmodellist,this));
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        }
       else {

            for (int k = 1; k <= 10; k++) {
                new VirtualTask().execute(shapi + k);
            }
        }
         vvh = ViewModelProviders.of(this).get(VirtualViewHolder.class);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.viewfav_upload:
                vmodellist.clear();
                loadFav();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFav() {
        vvh.getAllSuperHero.observe(this, new Observer<List<VirtualModel>>() {
            @Override
            public void onChanged(@Nullable List<VirtualModel> virtualModels) {
                if( (virtualModels==null)||(virtualModels.size()==0)){
                    Snackbar.make(linearLayout,"no favourites",Snackbar.LENGTH_LONG).show();

                }else {
                    recyclerView.setAdapter(new VirtualAdapter(virtualModels, getApplication()));
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                }
            }
        });
    }

    class VirtualTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    return scanner.next();
                } else {
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {

            indicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           parseJson(s);
        }
    }

    private void parseJson(String s) {
        if (s != null) {
            try {
                JSONObject object = new JSONObject(s);
                String id = object.getString("id");
                Log.i("CHECK",id);
                String name = object.getString("name");
                JSONObject powerstats = object.getJSONObject("powerstats");
                String strength = powerstats.getString("strength");
                String speed = powerstats.getString("speed");
                String power = powerstats.getString("power");
                JSONObject appearance = object.getJSONObject("appearance");
                String eye = appearance.getString("eye-color");
                String hair = appearance.getString("hair-color");
                JSONObject image = object.getJSONObject("image");
                String url = image.getString("url");
                VirtualModel vm = new VirtualModel();
                vm.setId1(id);
                vm.setName(name);
                vm.setStrength(strength);
                vm.setSpeed(speed);
                vm.setPower(power);
                vm.setEye(eye);
                vm.setHair(hair);
                vm.setImage1post(url);
                vmodellist.add(vm);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            indicator.setVisibility(View.GONE);
            recyclerView.setAdapter(new VirtualAdapter(vmodellist,this));
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("DATA", (Serializable) vmodellist);
    }
}
