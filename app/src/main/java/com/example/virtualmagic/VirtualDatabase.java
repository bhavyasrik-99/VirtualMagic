package com.example.virtualmagic;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {VirtualModel.class}, version = 1, exportSchema = false)
public abstract class VirtualDatabase extends RoomDatabase {

    public static VirtualDatabase virtualDatabase;

    public abstract VirtualDao virtualDao();

    public static VirtualDatabase getVirtualDatabase(Context context) {

        if (virtualDatabase == null) {
            virtualDatabase = Room.databaseBuilder(context, VirtualDatabase.class, context.getString(R.string.fav))
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return virtualDatabase;

    }
}
