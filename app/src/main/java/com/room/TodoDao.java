package com.room;

/**
 * TodoDao Interface
 */

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM Todo")
    List<Todo> getAll();

    @Insert
    void insert(Todo todo);

    @Query("UPDATE Todo SET title = :title WHERE id = :id")
    void update(long id, String title);

    @Query("DELETE FROM Todo WHERE id = :id")
    void delete(long id);
}

