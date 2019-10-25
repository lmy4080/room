package com.room;

/**
 * MainActivity Functions
 * 1. Creates a DB object
 * 2. Insert, delete, and change data through DB object
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Insert Variables
    private EditText et_todo;
    private Button btn_add;

    // Delete Variables
    private EditText et_delete;
    private Button btn_delete;

    // Update Variables
    private EditText et_update_id;
    private EditText et_update_content;
    private Button btn_update;

    // Result Variable
    private TextView tv_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Variables For Insert, Delete And Update
        et_todo = findViewById(R.id.et_todo);
        btn_add = findViewById(R.id.btn_add);
        et_delete = findViewById(R.id.et_delete);
        btn_delete = findViewById(R.id.btn_delete);
        et_update_id = findViewById(R.id.et_update_id);
        et_update_content = findViewById(R.id.et_update_content);
        btn_update = findViewById(R.id.btn_update);
        tv_result = findViewById(R.id.tv_result);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        // UI 갱신
        viewModel.getAll().observe(this, todos -> {
            tv_result.setText(todos.toString());
        });

        // Add
        btn_add.setOnClickListener(view -> {
            viewModel.insert(new Todo(et_todo.getText().toString()));
            et_todo.setText("");
        });

        // Delete
        btn_delete.setOnClickListener(view -> {
            viewModel.delete(Long.parseLong(et_delete.getText().toString()));
            et_delete.setText("");
        });

        // Update
        btn_update.setOnClickListener(view -> {
            Todo temp = new Todo(et_update_content.getText().toString());
            temp.setId(Integer.parseInt(et_update_id.getText().toString()));
            viewModel.update(temp);
            et_update_id.setText("");
            et_update_content.setText("");
        });
    }
}
