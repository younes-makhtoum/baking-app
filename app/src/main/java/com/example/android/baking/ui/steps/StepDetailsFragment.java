package com.example.android.baking.ui.steps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.baking.R;
import com.example.android.baking.databinding.FragmentStepDetailsBinding;
import com.example.android.baking.models.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import static com.example.android.baking.services.Utils.showStepVideo;
import static com.example.android.baking.services.Utils.showStepVideoThumbnail;

public class StepDetailsFragment extends Fragment {

    // Tag for log messages
    private static final String LOG_TAG = StepDetailsFragment.class.getName();

    private Context context;
    private FragmentStepDetailsBinding binding;

    // ExoPlayer variables
    private SimpleExoPlayer player;
    private boolean shouldAutoPlay;
    private TrackSelector trackSelector;
    private long playbackPosition;
    private int currentWindow;

    // Measures bandwidth during playback. Can be null if not required.
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    // Key for saving video position in case of a screen orientation change
    private static final String STATE_PLAYBACK_POSITION = "STATE_PLAYBACK_POSITION";

    // Required empty public constructor
    public StepDetailsFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the video position after an orientation change
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(STATE_PLAYBACK_POSITION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStepDetailsBinding
                .bind(inflater.inflate(R.layout.fragment_step_details, container, false));
        shouldAutoPlay = true;
        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    public void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    /**
     * Displays a particular step video.
     * @param step : the step about which we would like to display the related video
     */
    public void displayStepVideo(Step step) {
        context = getContext();
        if(!step.getVideoURL().isEmpty()) {
            showStepVideo(binding.stepVideo, binding.stepVideoThumbnail);
            loadStepVideo(Uri.parse(step.getVideoURL()));
        } else {
            showStepVideoThumbnail(binding.stepVideoThumbnail, binding.stepVideo);
        }
        if (!step.getThumbnailURL().isEmpty()) {
            Picasso.get()
                    .load(step.getThumbnailURL())
                    .placeholder(R.drawable.thumbnail_not_available)
                    .into(binding.stepVideoThumbnail);
        } else {
            binding.stepVideoThumbnail.setImageResource(R.drawable.thumbnail_not_available);
        }
    }

    public void loadStepVideo(Uri stepVideoURI) {
        boolean needNewPlayer = player == null;
        if(needNewPlayer) {
            // 1. Create a default TrackSelector
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            // 2. Create the player
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            player.setPlayWhenReady(shouldAutoPlay);
            // 3. Bind the player to the view.
            binding.stepVideo.setPlayer(player);
         }
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "Baking"), BANDWIDTH_METER);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(stepVideoURI);
        // Prepare the player with the source.
        player.prepare(videoSource);
        // Start from the beginning of resume to the last saved position
        player.seekTo(currentWindow, playbackPosition);
    }

    // This method is used to provide a full screen experience,
    // when changing the orientation to landscape on phones.
    public void hideSystemUi() {
        binding.stepVideo.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        binding.stepVideo.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
    }

    /**
     * Displays a particular step video.
     * @param step : the step about which we would like to display the full description
     */
    public void displayStepFullDescription(Step step) {
        binding.stepFullDescription.setText(step.getFullDescription());
    }

    // Saves the video's playback position on orientation change
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(STATE_PLAYBACK_POSITION, playbackPosition);
    }
}