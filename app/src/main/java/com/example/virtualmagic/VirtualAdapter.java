package com.example.virtualmagic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VirtualAdapter extends RecyclerView.Adapter<VirtualAdapter.MyViewHolder> {
List<VirtualModel> vmodels;
Context context;

    public VirtualAdapter(List<VirtualModel> vmodels, Context context) {
        this.vmodels = vmodels;
        this.context = context;
    }

    @NonNull
    @Override
    public VirtualAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v= LayoutInflater.from(context).inflate(R.layout.design,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VirtualAdapter.MyViewHolder myViewHolder,final  int i) {
        int is=myViewHolder.getAdapterPosition();
        VirtualModel model = vmodels.get(is);

        myViewHolder.tv.setText(model.name);
        Picasso.with(context).load(model.image1post).into(myViewHolder.iv);
        myViewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = new String[8];
                strings[0] = vmodels.get(i).getImage1post();
                strings[1] = vmodels.get(i).getId1();
                strings[2] = vmodels.get(i).getName();
                strings[3] =  vmodels.get(i).getStrength();
                strings[4] =  vmodels.get(i).getSpeed();
                strings[5] =  vmodels.get(i).getPower();
                strings[6] =  vmodels.get(i).getEye();
                strings[7] =  vmodels.get(i).getHair();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(context.getString(R.string.bhavya), strings);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return vmodels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
         TextView tv;
         ImageView iv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.img_design);
            tv=itemView.findViewById(R.id.text1_design);
            int is=getAdapterPosition();
        }
    }
}
