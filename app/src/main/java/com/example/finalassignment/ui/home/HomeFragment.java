package com.example.finalassignment.ui.home;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.finalassignment.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton instagramButton = root.findViewById(R.id.instagramButton);
        ImageButton githubButton = root.findViewById(R.id.githubButton);
        ImageButton tiktokButton = root.findViewById(R.id.tiktokButton);

        instagramButton.setOnClickListener(v -> openUrl("https://www.instagram.com/uvenni"));
        githubButton.setOnClickListener(v -> openUrl("https://github.com/YerdosNar"));
        tiktokButton.setOnClickListener(v -> onTiktok());

        return root;
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(), "No application can handle this request", Toast.LENGTH_SHORT).show();
        }
    }

    private void onTiktok() {
        Toast.makeText(requireContext(), "I don't have a tiktok. I'm a normal person.", Toast.LENGTH_SHORT).show();
    }
}