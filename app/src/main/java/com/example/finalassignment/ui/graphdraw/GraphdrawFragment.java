package com.example.finalassignment.ui.graphdraw;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.finalassignment.R;
import com.example.finalassignment.ui.SharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class GraphdrawFragment extends Fragment {
    private EditText function1, function2, function3;
    private ImageButton plotButton1, plotButton2, plotButton3;
    private EditText rangeMin, rangeMax;
    private Button saveButton;
    private FrameLayout graphContainer;
    private GraphView graphView;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_graphdraw, container, false);

        // Initialize views
        function1 = root.findViewById(R.id.function1);
        function2 = root.findViewById(R.id.function2);
        function3 = root.findViewById(R.id.function3);
        plotButton1 = root.findViewById(R.id.plotButton1);
        plotButton2 = root.findViewById(R.id.plotButton2);
        plotButton3 = root.findViewById(R.id.plotButton3);
        rangeMin = root.findViewById(R.id.rangeMin);
        rangeMax = root.findViewById(R.id.rangeMax);
        saveButton = root.findViewById(R.id.saveButton);
        graphContainer = root.findViewById(R.id.graphContainer);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Create and add GraphView objects
        graphView = new GraphView(requireContext());
        graphContainer.addView(graphView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Set default range values
        rangeMin.setText("-10");
        rangeMax.setText("10");

        // Setup click listeners for all plot buttons
        setupPlotButton(plotButton1, function1, 0);
        setupPlotButton(plotButton2, function2, 1);
        setupPlotButton(plotButton3, function3, 2);

        saveButton.setOnClickListener(v -> {
            // Get all non-empty functions
            List<String> functionsToSave = new ArrayList<>();
            if (!function1.getText().toString().isEmpty()) {
                functionsToSave.add(function1.getText().toString());
            }
            if (!function2.getText().toString().isEmpty()) {
                functionsToSave.add(function2.getText().toString());
            }
            if (!function3.getText().toString().isEmpty()) {
                functionsToSave.add(function3.getText().toString());
            }

            if (!functionsToSave.isEmpty()) {
                // Show dialog for each function
                for (String function : functionsToSave) {
                    showSaveDialog(function);
                }
            } else {
                Toast.makeText(requireContext(), "No functions to save", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void setupPlotButton(ImageButton button, EditText functionInput, int index) {
        button.setOnClickListener(v -> {
            String function = functionInput.getText().toString();
            if (!function.isEmpty()) {
                try {
                    float min = Float.parseFloat(rangeMin.getText().toString());
                    float max = Float.parseFloat(rangeMax.getText().toString());
                    graphView.setRange(min, max);
                    graphView.addFunction(function, index);
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Invalid range values", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showSaveDialog(String function) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.save_function, null);
        EditText descriptionInput = dialogView.findViewById(R.id.descriptionInput);

        builder.setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String description = descriptionInput.getText().toString();
                    if (description.isEmpty()) {
                        description = "User saved function"; // Default description
                    }
                    sharedViewModel.addExample(function, description);
                    Toast.makeText(requireContext(), "Function saved to Examples", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}