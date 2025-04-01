package com.example.finalassignment.ui.examples;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalassignment.R;
import com.example.finalassignment.ui.graphdraw.GraphView;
import java.util.List;

public class ExamplesAdapter extends RecyclerView.Adapter<ExamplesAdapter.ViewHolder> {
    private List<Example> examples;

    public ExamplesAdapter(List<Example> examples) {
        this.examples = examples;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_example, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Example example = examples.get(position);
        holder.functionText.setText("y = " + example.getFunction());
        holder.descriptionText.setText(example.getDescription());

        // Plot the function
        holder.graphView.setRange(-10, 10); // Set default range
        holder.graphView.clearFunctions(); // Clear any previous functions
        holder.graphView.addFunction(example.getFunction(), 0); // Add the function to plot
    }

    @Override
    public int getItemCount() {
        return examples.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView functionText;
        TextView descriptionText;
        GraphView graphView;

        ViewHolder(View itemView) {
            super(itemView);
            functionText = itemView.findViewById(R.id.functionText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
            graphView = itemView.findViewById(R.id.graphView);
        }
    }
}