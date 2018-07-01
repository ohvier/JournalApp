package com.example.otuyishime.journalapp;

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
        mDb= AppDatabase.getInstance(getApplicationContext());
        initViews();

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

        DairyEntry entry = new DairyEntry(title, date, description);

        mDb.dairyDao().insertEntry(entry);
        finish();
    }
}
