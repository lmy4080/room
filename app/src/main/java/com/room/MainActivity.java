package com.room;

/**
 * MainActivity Functions
 * 1. Creates a DB object
 * 2. Insert, delete, and change data through DB object
 */

import androidx.appcompat.app.AppCompatActivity;
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

        // UI 갱신
        db.todoDao().getAll().observe(this, todos -> {
            tv_result.setText(todos.toString());
        });

        // Add
        btn_add.setOnClickListener(view -> {
            new InsertAsyncTask(db.todoDao()).execute(new Todo(et_todo.getText().toString()));
            Log.d("room", "Insert OK.");
            et_todo.setText("");
        });

        // Delete
        btn_delete.setOnClickListener(view -> {
            new DeleteAsyncTask(db.todoDao()).execute(Long.parseLong(et_delete.getText().toString()));
            Log.d("room", "Delete OK.");
            et_delete.setText("");
        });

        // Update
        btn_update.setOnClickListener(view -> {
            Todo temp = new Todo(et_update_content.getText().toString());
            temp.setId(Integer.parseInt(et_update_id.getText().toString()));
            new UpdateAsyncTask(db.todoDao()).execute(temp);
            Log.d("room", "Update OK.");
            et_update_id.setText("");
            et_update_content.setText("");
        });
    }

    // Async Add
    private static class InsertAsyncTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao mTodoDao;

        public InsertAsyncTask(TodoDao todoDao) {
            mTodoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {

            mTodoDao.insert(todos[0]);
            return null;
        }
    }

    // Async Delete
    private static class DeleteAsyncTask extends AsyncTask<Long, Void, Void> {

        private TodoDao mTodoDao;

        public DeleteAsyncTask(TodoDao todoDao) {
            mTodoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Long... id) {

            mTodoDao.delete(id[0]);
            return null;
        }
    }

    // Async Update
    private static class UpdateAsyncTask extends AsyncTask<Todo, String, Void> {

        private TodoDao mTodoDao;

        public UpdateAsyncTask(TodoDao todoDao) {
            mTodoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {

            mTodoDao.update(todos[0].getId(), todos[0].getTitle());
            return null;
        }
    }
}
