package com.cursoandroid.geovoz;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.cursoandroid.geovoz.CapitalDAO;

import com.cursoandroid.geovoz.CapitalEntity;

@Database(entities = {CapitalEntity.class}, version = 1)
public abstract class CapitalDatabase extends RoomDatabase {
    public abstract CapitalDAO capitalDAO();

    private static volatile CapitalDatabase INSTANCE;

    public static CapitalDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CapitalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            CapitalDatabase.class,
                            "capital_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}