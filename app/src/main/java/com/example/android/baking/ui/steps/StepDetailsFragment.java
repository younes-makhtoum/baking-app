package com.example.android.baking.ui.steps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.android.baking.R;
import com.example.android.baking.databinding.FragmentStepDetailsBinding;
import com.example.android.baking.models.Step;
import com.squareup.picasso.Picasso;

public class StepDetailsFragment extends Fragment {

    private FragmentStepDetailsBinding binding;

    // Required empty public constructor
    public StepDetailsFragment() {
        super();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_details, container, false);
    }

    /**
     * Displays a particular step video.
     * @param step : the step about which we would like to display the related video
     */
    public void displayStepVideo(Step step) {
        Picasso.get()
                .load(step.getThumbnailURL())
                .into(binding.stepVideoThumbnail);
    }

    /**
     * Displays a particular step video.
     * @param step : the step about which we would like to display the full description
     */
    public void displayStepFullDescription(Step step) {
        binding.stepFullDescription.setText(step.getFullDescription());
    }
}