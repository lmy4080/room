package com.room;

/**
 * MainActivity Functions
 * 1. Creates a DB object
 * 2. Insert, delete, and change data through DB object
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.room.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Data Binding
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        // Create ViewModel To Separate UI From Logic
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.setViewModel(viewModel);

        // Add
        findViewById(R.id.btn_add).setOnClickListener(view -> {
            viewModel.insert(new Todo(binding.etTodo.getText().toString()));
            binding.etTodo.setText("");
        });

        // Delete
        findViewById(R.id.btn_delete).setOnClickListener(view -> {
            viewModel.delete(Long.parseLong(binding.etDelete.getText().toString()));
            binding.etDelete.setText("");
        });

        // Update
        findViewById(R.id.btn_update).setOnClickListener(view -> {
            Todo temp = new Todo(binding.etUpdateContent.getText().toString());
            temp.setId(Integer.parseInt(binding.etUpdateId.getText().toString()));
            viewModel.update(temp);
            binding.etUpdateContent.setText("");
            binding.etUpdateId.setText("");
        });
    }
}
