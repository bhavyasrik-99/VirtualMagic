package com.example.virtualmagic;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.id)
    TextView id1;
    @BindView(R.id.name)
    TextView name2;
    @BindView(R.id.strength)
    TextView strength2;
    @BindView(R.id.speed)
    TextView speed2;
    @BindView(R.id.power)
    TextView power2;
    @BindView(R.id.eye_clor)
    TextView eyecolor2;
    @BindView(R.id.hair_color)
    TextView haircolor2;
    @BindView(R.id.addmob)
    AdView addmobview2;
    @BindView(R.id.himage_detail)
    ImageView image2;
    @BindView(R.id.button_detail)
    Button button;
    //DatabaseReference dbr;
    static boolean stat = false;
    static boolean s;
    VirtualModel virtualModel;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    VirtualViewHolder virtualViewHolder;

    String[] str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        str = getIntent().getStringArrayExtra("bhavya");
        Picasso.with(this).load(str[0]).into(image2);
        image2.setContentDescription(str[0]);
        id1.setText(str[1]);
        name2.setText(str[2]);
        speed2.setText(str[4]);
        strength2.setText(str[3]);
        power2.setText(str[5]);
        eyecolor2.setText(str[6]);
        haircolor2.setText(str[7]);
        virtualViewHolder = ViewModelProviders.of(this).get(VirtualViewHolder.class);
        inFavourite(str[1]);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");
        AdRequest adr;
        adr = new AdRequest.Builder().build();
        addmobview2.loadAd(adr);


    }

    public static void setStat(boolean stat) {
        DetailActivity.stat = stat;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void favorite(View view) {

        s = inFavourite(str[1]);

        VirtualModel vm = new VirtualModel();
        vm.setImage1post(str[0]);
        vm.setId1(str[1]);
        vm.setName(str[2]);
        vm.setStrength(str[3]);
        vm.setSpeed(str[4]);
        vm.setPower(str[5]);
        vm.setEye(str[6]);
        vm.setHair(str[7]);
        if (!s) {
            virtualViewHolder.insertModel(vm);
            setStat(true);
            upload(view);
            widgetclick(view);
            Toast.makeText(this, "insertion success", Toast.LENGTH_SHORT).show();
            button.setText("added to favourites and uploaded to database");

        } else {
            virtualViewHolder.DeletetModel(vm);
            setStat(false);
            Toast.makeText(this, "deletion success", Toast.LENGTH_SHORT).show();
            button.setText("add to favourites and upload to the database");
        }


    }

    public boolean inFavourite(String id) {
        String hrid;
        hrid = virtualViewHolder.Favourites(id);
        if (hrid!=null) {
            setStat(true);
            button.setText("added to favourites and uploaded in database");
            Toast.makeText(this, "THIS IS FAV", Toast.LENGTH_SHORT).show();
        } else {
            setStat(false);
            button.setText("Add to favourites and upload to the database");
            Toast.makeText(this, "THIS IS NOT FAV", Toast.LENGTH_SHORT).show();
        }
        return stat;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void upload(View view) {
        String image = image2.getContentDescription().toString();
        String id = id1.getText().toString();
        String name = name2.getText().toString();
        String spe = speed2.getText().toString();
        String strength = strength2.getText().toString();
        String power = power2.getText().toString();
        String eyecolor = eyecolor2.getText().toString();
        String haircolor = haircolor2.getText().toString();
        VirtualModel virtualModel;
        virtualModel = new VirtualModel();
        virtualModel.setId1(id);
        virtualModel.setName(name);
        virtualModel.setStrength(strength);
        virtualModel.setSpeed(spe);
        virtualModel.setPower(power);
        virtualModel.setEye(eyecolor);
        virtualModel.setHair(haircolor);
        virtualModel.setImage1post(image);
        String ins;
       // ins = dbr.push().getKey();
        //dbr.child(Objects.requireNonNull(ins)).setValue(virtualModel);
    }

    public void widgetclick(View view) {
        sp = getSharedPreferences("bhavya", MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("titlename", name2.getText().toString());
        editor.apply();
        Intent i;
        i = new Intent(DetailActivity.this, Widget.class);
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(DetailActivity.this).getAppWidgetIds(new ComponentName(getApplicationContext(), Widget.class));
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ids);
        sendBroadcast(i);
        Snackbar.make(view, "Widgets are added", Snackbar.LENGTH_LONG).setAction("Action", null).show();


    }
}
