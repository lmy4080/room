package com.room;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Room Object
    private AppDatabase db;

    public MainViewModel(@NonNull Application application) {
        super(application);

        db = Room.databaseBuilder(application, AppDatabase.class, "todo-db").build();
    }

    public LiveData<List<Todo>> getAll() {
        return db.todoDao().getAll();
    }

    public void insert(Todo todo) {
        new InsertAsyncTask(db.todoDao()).execute(todo);
    }

    public void delete(Long id) {
        new DeleteAsyncTask(db.todoDao()).execute(id);
    }

    public void update(Todo todo) {
        new UpdateAsyncTask(db.todoDao()).execute(todo);
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
            Log.d("room", "Insert OK.");
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
            Log.d("room", "Delete OK.");
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
            Log.d("room", "Update OK.");
            return null;
        }
    }
}
