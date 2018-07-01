package com.example.otuyishime.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.otuyishime.journalapp.R;
import com.example.otuyishime.journalapp.adapter.DairyEntryAdapter;
import com.example.otuyishime.journalapp.model.AppDatabase;
import com.example.otuyishime.journalapp.model.DairyEntry;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class DairyActivity extends AppCompatActivity implements DairyEntryAdapter.ItemClickListener {
    private RecyclerView mEntryList;

    private DairyEntryAdapter mAdapter;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy);
        Toolbar toolbar =  findViewById(R.id.toolbar);

        mAdapter= new DairyEntryAdapter(this,this);
        mDb = AppDatabase.getInstance(getApplicationContext());
        mEntryList = findViewById(R.id.dairy_recycler);
        mEntryList.setAdapter(mAdapter);

        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                final List<DairyEntry> entries= mDb.dairyDao().loadAllDairyEntries();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(entries);
                    }
                });
            }

        });


        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mEntryList.addItemDecoration(decoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mEntryList.setLayoutManager(layoutManager);
        mDb = AppDatabase.getInstance(getApplicationContext());


        FloatingActionButton fabButton = findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(DairyActivity.this, AddDairyEntryActivity.class);
                startActivity(addTaskIntent);
            }
        });

    }

    @Override
    public void onItemClickListener(int itemId) {

    }
}
