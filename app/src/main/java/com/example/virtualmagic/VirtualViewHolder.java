package com.example.virtualmagic;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class VirtualViewHolder extends AndroidViewModel {
    LiveData<List<VirtualModel>> getAllSuperHero;
    public VirtualRepo virtualRepo;

    public VirtualViewHolder(@NonNull Application application) {
        super(application);
        virtualRepo = new VirtualRepo(application);
        getAllSuperHero = virtualRepo.getAll();
    }

    public void insertModel(VirtualModel virtualModel) {
        virtualRepo.insert(virtualModel);
    }

    public void DeletetModel(VirtualModel virtualModel) {
        virtualRepo.Delete(virtualModel);
    }

    public String Favourites(String md) {
        return virtualRepo.checkHero(md);
    }
}
