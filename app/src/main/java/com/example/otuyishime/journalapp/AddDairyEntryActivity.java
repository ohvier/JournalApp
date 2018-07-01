package com.example.otuyishime.journalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.otuyishime.journalapp.model.AppDatabase;
import com.example.otuyishime.journalapp.model.DairyEntry;

import java.util.Date;

public class AddDairyEntryActivity extends AppCompatActivity {

    public static final String EXTRA_DAIRY_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_DAIRY_ID = "instanceTaskId";
    private static final String TAG = AddDairyEntryActivity.class.getSimpleName();
    private static final int DEFAULT_ENTRY_ID = -1;
    EditText titleEditText;
    EditText descriptionEditText;
    Button saveButton;
    private int mEntryId = DEFAULT_ENTRY_ID;
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dairy_entry);
        mDb = AppDatabase.getInstance(getApplicationContext());
        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_DAIRY_ID)) {
            mEntryId = savedInstanceState.getInt(INSTANCE_DAIRY_ID, DEFAULT_ENTRY_ID);

        }
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_DAIRY_ID)) {
            saveButton.setText(R.string.update_button);
            if (mEntryId == DEFAULT_ENTRY_ID) {
                mEntryId = intent.getIntExtra(EXTRA_DAIRY_ID, DEFAULT_ENTRY_ID);
                AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final DairyEntry entry = mDb.dairyDao().loadEntryById(mEntryId);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateUI(entry);
                            }
                        });
                    }
                });
            }
        }

    }

    private void populateUI(DairyEntry entry) {
        if (entry == null) {
            return;
        }
        titleEditText.setText(entry.getEntry_title());
        descriptionEditText.setText(entry.getEntry_body());
    }

    private void initViews() {
        titleEditText = findViewById(R.id.editTextTaskTitle);
        descriptionEditText = findViewById(R.id.editTextTaskDescription);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        Date date = new Date();
        final DairyEntry entry = new DairyEntry(title, date, description);

        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mEntryId==DEFAULT_ENTRY_ID){

                    mDb.dairyDao().insertEntry(entry);
                }else {
                    entry.setId(mEntryId);
                    mDb.dairyDao().updateEntry(entry);
                }
                finish();
            }
        });


    }
}
