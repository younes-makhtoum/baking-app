package com.example.android.baking.ui.steps;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.models.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    // Tag for log messages
    public static final String LOG_TAG = StepsAdapter.class.getName();

    private Context context;
    private ArrayList<Step> stepsList;
    private Step currentStep;

    private static StepDetailsFragment stepDetailsFragment;

    public StepsAdapter(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView stepShortDescription;

        private MyViewHolder(View itemView) {
            super(itemView);
            stepShortDescription = itemView.findViewById(R.id.step_short_description);
            itemView.setClickable(true);
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
        currentStep = stepsList.get(position);
        holder.stepShortDescription.setText(currentStep.getShortDescription());
        // mark  the view as selected:

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context.getResources().getBoolean(R.bool.has_two_panes)) {
                    /* display recipe step details on the right pane */
                    stepDetailsFragment.displayStepVideo(currentStep);
                    stepDetailsFragment.displayStepFullDescription(currentStep);
                } else {
                    /* start a separate activity */
                    Intent intent = new Intent(context, StepDetailsActivity.class);
                    intent.putExtra("Step", stepsList.get(position));
                    context.startActivity(intent);
                }
            }
        });
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