package com.example.internshala_assesment_noteapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.internshala_assesment_noteapp.Database.SessionManager;
import com.example.internshala_assesment_noteapp.Model.NotesEntity;
import com.example.internshala_assesment_noteapp.R;
import com.example.internshala_assesment_noteapp.ViewModel.NotesViewModel;
import com.example.internshala_assesment_noteapp.databinding.FragmentCreateNoteBinding;

import java.util.Date;


public class CreateNoteFragment extends Fragment {

    String title, noteDetail;
    NotesViewModel notesViewModel;
    FragmentCreateNoteBinding binding;
    private FragmentManager fragmentManager;

    private Boolean isUpdate = false;
    private NotesEntity notesEntity;

    public CreateNoteFragment() {
        isUpdate = false;
    }

    public CreateNoteFragment(NotesEntity notesEntity) {
        isUpdate = true;
        this.notesEntity = notesEntity;
    }

    public CreateNoteFragment(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        if (isUpdate) {
            binding.title.setText(notesEntity.notesTitle);
            binding.notedetail.setText(notesEntity.notes);
        }

        binding.donebutton.setOnClickListener(v -> {

            title = binding.title.getText().toString();
            noteDetail = binding.notedetail.getText().toString();

            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all the details", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isUpdate) {
                UpdateNotes(title, noteDetail);
            } else
                CreatNotes(title, noteDetail);

        });


        return view;
    }

    private void UpdateNotes(String title, String noteDetail) {
        Date date = new Date();
        CharSequence charSequence = DateFormat.format("dd,mm,yyy", date.getTime());

        SessionManager sessionManager = new SessionManager(requireContext());

        notesEntity.notesTitle = title;
        notesEntity.notes = noteDetail;
        notesEntity.notesDate = charSequence.toString();
        notesViewModel.updateNotes(notesEntity);
        Toast.makeText(requireContext(), "Note Updated successfully", Toast.LENGTH_SHORT).show();

    }


    private void CreatNotes(String title, String note) {

        Date date = new Date();
        CharSequence charSequence = DateFormat.format("dd,mm,yyy", date.getTime());

        SessionManager sessionManager = new SessionManager(requireContext());

        NotesEntity notes1 = new NotesEntity();
        notes1.notesTitle = title;
        notes1.isBookmarked = false;
        notes1.notes = note;
        notes1.notesDate = charSequence.toString();
        notes1.userId = sessionManager.getUserId();
        notesViewModel.insertNotes(notes1);

        Toast.makeText(requireContext(), "Note Created successfully", Toast.LENGTH_SHORT).show();

        if (fragmentManager != null) {
            fragmentManager.popBackStack();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new NotesFragment(fragmentManager)).commit();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isUpdate = false;
    }
}