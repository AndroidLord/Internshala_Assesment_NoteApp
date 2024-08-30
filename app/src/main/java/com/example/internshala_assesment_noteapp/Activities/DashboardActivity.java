package com.example.internshala_assesment_noteapp.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.internshala_assesment_noteapp.Adapter.NotesAdapter;
import com.example.internshala_assesment_noteapp.Database.SessionManager;
import com.example.internshala_assesment_noteapp.Fragments.CreateNoteFragment;
import com.example.internshala_assesment_noteapp.Fragments.FavoriteFragment;
import com.example.internshala_assesment_noteapp.Fragments.LoginFragment;
import com.example.internshala_assesment_noteapp.Fragments.NotesFragment;
import com.example.internshala_assesment_noteapp.Fragments.SettingFragment;
import com.example.internshala_assesment_noteapp.Model.NotesEntity;
import com.example.internshala_assesment_noteapp.R;
import com.example.internshala_assesment_noteapp.databinding.ActivityDashboardBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity
        implements
        LoginFragment.OnFragmentInteractionListener,
        SettingFragment.OnSettingFragmentListener
{

    ActivityDashboardBinding dashboardBinding;
    private SessionManager sessionManager;

    private FragmentManager fragmentManager;

    private FirebaseAuth mAuth;

    // Appbar
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(dashboardBinding.getRoot());

        sessionManager = new SessionManager(this);
        mAuth = FirebaseAuth.getInstance();

        // setting up the appbar
        setSupportActionBar(dashboardBinding.toolbar);
        getSupportActionBar().setTitle("Notes App");


        fragmentManager = getSupportFragmentManager();
        bottomNav(fragmentManager);

        Fragment startFragment;
        if (!sessionManager.getLoginStatus()) {
            startFragment = new LoginFragment(fragmentManager);
            if (dashboardBinding.fabHome.getVisibility() != View.GONE)
                switchViewVisibility(false);

        } else {
            startFragment = new NotesFragment(fragmentManager);
            if (dashboardBinding.fabHome.getVisibility() != View.VISIBLE)
                switchViewVisibility(true);
        }

        dashboardBinding.fabHome.setOnClickListener(view -> {
            ReplaceFragment(new NotesFragment(fragmentManager));
        });

        ReplaceFragment(startFragment);
    }

    public void ReplaceFragment(Fragment selectedFragment) {
        // Snackbar.make(dashboardBinding.fragmentContainer, "Welcome to Dashboard " + selectedFragment.toString(), Snackbar.LENGTH_SHORT).show();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void bottomNav(FragmentManager fragmentManager) {
        dashboardBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int id = item.getItemId();
                if (id == R.id.fvrt) {
                    selectedFragment = new FavoriteFragment();
                } else if (id == R.id.settings) {
                    selectedFragment = new SettingFragment();
                }

                ReplaceFragment(selectedFragment);
                return false;
            }
        });
    }


    private void switchViewVisibility(boolean visible) {
        if (visible) {
            dashboardBinding.fabHome.setVisibility(View.VISIBLE);
            dashboardBinding.bottomappbar.setVisibility(View.VISIBLE);
        } else {
            dashboardBinding.fabHome.setVisibility(View.GONE);
            dashboardBinding.bottomappbar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // User is not logged in
        if (mAuth.getCurrentUser() == null) {
            Log.d("DashboardActivity", "onStart: User is not logged in");
            sessionManager.clearSession();
            switchViewVisibility(false);
            ReplaceFragment(new LoginFragment(fragmentManager));
        }
        // User is logged in
        else if (mAuth.getCurrentUser() != null) {
            Log.d("DashboardActivity", "onStart: User is logged in ->");
            sessionManager.setLoginStatus(true);
            sessionManager.setUserId(mAuth.getCurrentUser().getUid());
            Log.d("DashboardActivity", "onStart: User is logged in ->" + sessionManager.getUserId());
            switchViewVisibility(true);
            printUserDetails();
        }
    }

    @Override
    public void onFragmentInteraction() {
        Log.d("DashboardInterface", "onFragmentInteraction: ");
        if (sessionManager.getLoginStatus()) {
            ReplaceFragment(new NotesFragment(fragmentManager));
            switchViewVisibility(true);
        }
    }

    // print all current user details
    private void printUserDetails() {
        Log.d("DashboardActivity", "printUserDetails: Name -" + mAuth.getCurrentUser().getDisplayName());
        Log.d("DashboardActivity", "printUserDetails: Email -" + mAuth.getCurrentUser().getEmail());
        Log.d("DashboardActivity", "printUserDetails: PhoneNo -" + mAuth.getCurrentUser().getPhoneNumber());
        Log.d("DashboardActivity", "printUserDetails: Uid -" + mAuth.getCurrentUser().getUid());
    }

    @Override
    public void onLogout() {
        Log.d("DashboardActivity", "onLogout: ");
        sessionManager.clearSession();
        mAuth.signOut();
        switchViewVisibility(false);
        ReplaceFragment(new LoginFragment(fragmentManager));
    }


}