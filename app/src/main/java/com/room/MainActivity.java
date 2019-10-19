package com.room;

/**
 * MainActivity Functions
 * 1. Creates a DB object
 * 2. Insert, delete, and change data through DB object
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    // Room Object
    private AppDatabase db;

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

        // Create DB Object
        db = Room.databaseBuilder(this, AppDatabase.class, "todo-db")
                .allowMainThreadQueries()
                .build();

        // Retrieve Existing Data
        showData();

        // Add
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.todoDao().insert(new Todo(et_todo.getText().toString()));
                showData();
                Log.d("room", "Insert OK.");
                et_todo.setText("");
            }
        });

        // Delete
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.todoDao().delete(Integer.parseInt(et_delete.getText().toString()));
                showData();
                Log.d("room", "Delete OK.");
                et_delete.setText("");
            }
        });

        // Update
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.todoDao().update(Integer.parseInt(et_update_id.getText().toString()), et_update_content.getText().toString());
                showData();
                Log.d("room", "Update OK.");
                et_update_id.setText("");
                et_update_content.setText("");
            }
        });
    }

    private void showData() {
        tv_result.setText(db.todoDao().getAll().toString());
    }
}
