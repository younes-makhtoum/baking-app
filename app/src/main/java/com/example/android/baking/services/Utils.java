package com.example.android.baking.services;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.ui.PlayerView;

public class Utils {

    public static void showLoadingSpinner(ImageView issueView, ProgressBar loadingSpinner, RecyclerView recyclerView) {
        issueView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        loadingSpinner.setVisibility(View.VISIBLE);
    }

    public static void showResults(ProgressBar loadingSpinner, RecyclerView recyclerView) {
        loadingSpinner.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public static void showIssueDisclaimer(RecyclerView recyclerView, ImageView issueView, int drawable) {
        recyclerView.setVisibility(View.GONE);
        issueView.setImageResource(drawable);
        issueView.setVisibility(View.VISIBLE);
    }

    public static void showStepVideo(PlayerView playerView, ImageView thumbnailView) {
        thumbnailView.setVisibility(View.GONE);
        playerView.setVisibility(View.VISIBLE);
    }

    public static void showStepVideoThumbnail(ImageView thumbnailView, PlayerView playerView) {
        playerView.setVisibility(View.GONE);
        thumbnailView.setVisibility(View.VISIBLE);
    }
}