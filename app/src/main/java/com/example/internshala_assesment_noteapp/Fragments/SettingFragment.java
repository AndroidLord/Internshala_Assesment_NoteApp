package com.example.internshala_assesment_noteapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.internshala_assesment_noteapp.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    public SettingFragment() {
    }

    OnSettingFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.OnFragmentInteractionListener) {
            mlistener = (OnSettingFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSettingBinding binding = FragmentSettingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        return view;
    }

    private void Logout() {
        Log.d("TAG", "Logout: Running");
        if (mlistener != null) {
            mlistener.onLogout();
        }
    }

    public interface OnSettingFragmentListener {
        void onLogout();
    }
}