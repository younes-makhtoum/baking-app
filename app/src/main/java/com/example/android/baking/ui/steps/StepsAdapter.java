package com.example.android.baking.ui.steps;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.models.Step;
import com.example.android.baking.services.events.StepSelectionEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    // Tag for log messages
    public static final String LOG_TAG = StepsAdapter.class.getName();

    private final Context context;
    private ArrayList<Step> stepsList;
    private Step currentStep;

    public StepsAdapter(Context context) {
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView stepShortDescription;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context.getResources().getBoolean(R.bool.has_two_panes)) {
                    /* display recipe step details on the right pane */
                    EventBus.getDefault().postSticky(new StepSelectionEvent(stepsList.get(position)));
                } else {
                    /* start a separate activity */
                    EventBus.getDefault().postSticky(new StepSelectionEvent(stepsList.get(position)));
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