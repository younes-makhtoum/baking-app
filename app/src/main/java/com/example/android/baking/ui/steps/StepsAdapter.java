package com.example.android.baking.ui.steps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.models.Step;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    // Tag for log messages
    public static final String LOG_TAG = StepsAdapter.class.getName();

    private Context context;
    private ArrayList<Step> stepsList;

    public StepsAdapter(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView stepShortDescription;

        private MyViewHolder(View itemView) {
            super(itemView);
            stepShortDescription = itemView.findViewById(R.id.step_short_description);
        }
    }

    @NonNull
    @Override
    public StepsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
        return new StepsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.MyViewHolder holder, final int position) {
        Step currentStep = stepsList.get(position);
        holder.stepShortDescription.setText(currentStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (stepsList == null) {
            return 0;
        } else {
            return stepsList.size();
        }
    }

    // Helper method to set the current steps list into the steps RecyclerView of the activity
    public void setStepsInfoList(ArrayList<Step> stepsList) {
        this.stepsList = stepsList;
    }
}