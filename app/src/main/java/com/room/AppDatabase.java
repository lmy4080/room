package com.room;

/**
 * AppDatabase Class - { Data Access Object }
 */

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();
}
