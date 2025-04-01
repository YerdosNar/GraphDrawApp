package com.example.finalassignment.ui.examples;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalassignment.R;
import com.example.finalassignment.ui.SharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExamplesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ExamplesAdapter adapter;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_examples, container, false);

        recyclerView = root.findViewById(R.id.examplesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize SharedViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Create adapter with empty list
        adapter = new ExamplesAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Observe changes to examples list
        sharedViewModel.getExamples().observe(getViewLifecycleOwner(), examples -> {
            adapter.notifyDataSetChanged();
            adapter = new ExamplesAdapter(examples);
            recyclerView.setAdapter(adapter);
        });

        return root;
    }
}