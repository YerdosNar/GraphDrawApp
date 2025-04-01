package com.example.finalassignment.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.finalassignment.ui.examples.Example;
import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<Example>> examples = new MutableLiveData<>(new ArrayList<>());

    public SharedViewModel() {
        // Add predefined examples
        List<Example> initialExamples = new ArrayList<>();
        initialExamples.add(new Example("x^2", "Quadratic Function (Parabola)", true));
        initialExamples.add(new Example("1/(1+e^(-x))", "Sigmoid Function", true));
        initialExamples.add(new Example("(abs(x) + x)/2", "ReLU (Rectified Linear Unit)", true));
        examples.setValue(initialExamples);
    }

    public void addExample(String function, String description) {
        List<Example> currentExamples = examples.getValue();
        if (currentExamples != null) {
            currentExamples.add(new Example(function, description, false));
            examples.setValue(currentExamples);
        }
    }

    public LiveData<List<Example>> getExamples() {
        return examples;
    }
}