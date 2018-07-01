package com.example.otuyishime.journalapp;

import android.content.Intent;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.otuyishime.journalapp.model.AppDatabase;
import com.example.otuyishime.journalapp.model.DairyEntry;

public class ViewSingleDairyActivity extends AppCompatActivity {
    public static final String EXTRA_DAIRY_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_DAIRY_ID = "instanceTaskId";
    private static final String TAG = AddDairyEntryActivity.class.getSimpleName();
    private static final int DEFAULT_ENTRY_ID = -1;
    private int mEntryId = DEFAULT_ENTRY_ID;

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_dairy);

        mTitleTextView = findViewById(R.id.single_dairy_title);
        mDescriptionTextView = findViewById(R.id.single_dairy_description);


        mDb = AppDatabase.getInstance(getApplicationContext());


        mDb = AppDatabase.getInstance(getApplicationContext());
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_DAIRY_ID)) {
            mEntryId = savedInstanceState.getInt(INSTANCE_DAIRY_ID, DEFAULT_ENTRY_ID);

        }
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_DAIRY_ID)) {

            if (mEntryId == DEFAULT_ENTRY_ID) {
                mEntryId = intent.getIntExtra(EXTRA_DAIRY_ID, DEFAULT_ENTRY_ID);
                AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final DairyEntry entry = mDb.dairyDao().loadEntryById(mEntryId);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTitleTextView.setText(entry.getEntry_title());
                                mDescriptionTextView.setText(entry.getEntry_body());
                            }
                        });
                    }
                });
            }
        }
    }
}
