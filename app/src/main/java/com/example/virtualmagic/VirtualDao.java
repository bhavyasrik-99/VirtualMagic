package com.example.virtualmagic;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface VirtualDao {

    @Insert
    void Inserting(VirtualModel virtualModel);
    @Delete
    void Deleting(VirtualModel virtualModel);

    @Query("select * from VirtualModel")
    LiveData<List<VirtualModel>> getAllHeroes();

    @Query("select id1 from VirtualModel where id1=:mid")
    String isFav(String mid);




}

