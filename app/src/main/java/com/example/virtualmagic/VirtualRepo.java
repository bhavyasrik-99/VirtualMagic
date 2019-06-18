package com.example.virtualmagic;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class VirtualRepo {


    public static VirtualDao virtualDao;
    LiveData<List<VirtualModel>> getAll;

    public VirtualRepo(Context context){
        VirtualDatabase virtualDatabase;
        virtualDatabase=VirtualDatabase.getVirtualDatabase(context);
        virtualDao=virtualDatabase.virtualDao();
        getAll=virtualDao.getAllHeroes();
    }
    public LiveData<List<VirtualModel>> getAll()
    {
        return getAll;
    }
    public void insert(VirtualModel virtualModel)
    {
        new InsTask().execute(virtualModel);

    }
    public void Delete(VirtualModel virtualModel)
    {
        new DelTask().execute(virtualModel);

    }
    public class DelTask extends AsyncTask<VirtualModel,Void,Void>
    {

        @Override
        protected Void doInBackground(VirtualModel... virtualModels) {
            virtualDao.Deleting(virtualModels[0]);
            return null;
        }
    }
    public class InsTask extends AsyncTask<VirtualModel,Void,Void>
    {

        @Override
        protected Void doInBackground(VirtualModel... virtualModels) {
            virtualDao.Inserting(virtualModels[0]);
            return null;
        }
    }
public String checkHero(String mid){
        return virtualDao.isFav(mid);
}
}
