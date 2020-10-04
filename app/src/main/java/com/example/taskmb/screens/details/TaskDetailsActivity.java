package com.example.taskmb.screens.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskmb.R;
import com.example.taskmb.model.App;
import com.example.taskmb.model.Task;

public class TaskDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_TASK = "TaskDetailsActivity.EXTRA_TASK";

    private Task task;

    private EditText editText;

    public static void start(Activity caller, Task task) {
        Intent intent = new Intent(caller, TaskDetailsActivity.class);
        if (task != null) {
            intent.putExtra(EXTRA_TASK, task);
        }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) { //
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(getString(R.string.task_details_tille));

        editText = findViewById(R.id.text);

        if (getIntent().hasExtra(EXTRA_TASK)) {
            task = getIntent().getParcelableExtra(EXTRA_TASK);
            editText.setText(task.text);
        } else {
            task = new Task();
        }


    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {        // модуль для редактирования заметок
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                if (editText.getText().length() > 0) {
                    task.text = editText.getText().toString();
                    task.ready = false;
                    task.taskTime = System.currentTimeMillis();
                    if (getIntent().hasExtra(EXTRA_TASK)) {
                        App.getInstance().getTaskDao().update(task);
                    } else {
                        App.getInstance().getTaskDao().insert(task);
                    }
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
