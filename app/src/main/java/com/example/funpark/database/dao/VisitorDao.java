package com.example.funpark.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.funpark.database.entity.VisitorEntity;

import java.util.List;

/**
 * https://developer.android.com/topic/libraries/architecture/room.html#no-object-references
 */
@Dao
public interface VisitorDao {

    @Query("SELECT * FROM visitors WHERE id = :id")
    LiveData<VisitorEntity> getById(int id);

    @Query("SELECT * FROM visitors")
    LiveData<List<VisitorEntity>> getAll();

    @Insert
    long insert(VisitorEntity visitor) throws SQLiteConstraintException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<VisitorEntity> visitors);

    @Update
    void update(VisitorEntity visitor);

    @Delete
    void delete(VisitorEntity visitor);

    @Query("DELETE FROM visitors")
    void deleteAll();
}