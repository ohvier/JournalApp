package com.example.otuyishime.journalapp.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DairyDao {

    @Query("SELECT * FROM dairy order by entry_date")
    List<DairyEntry> loadAllDairyEntries();

    @Insert
    void insertEntry(DairyEntry dairyEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(DairyEntry dairyEntry);

    @Delete
    void deleteEntry(DairyEntry dairyEntry);

}
