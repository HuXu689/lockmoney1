package com.example.ltdd.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.ltdd.R;
import com.example.ltdd.databinding.ActivityMainBinding;
import com.example.ltdd.fragment.GoalsFragment;
import com.example.ltdd.fragment.HomeFragment;
import com.example.ltdd.fragment.SettingsFragment;
import com.example.ltdd.fragment.StatisticFragment;
import com.example.ltdd.fragment.WriteFragment;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //<editor-fold defaultState="Collapse" desc="Khởi tạo dữ liệu">
    int fragment_id[] = {R.id.Home, R.id.Statistic, R.id.Write, R.id.Goals, R.id.Settings};
    Fragment fragments[] = {new HomeFragment(), new StatisticFragment(), new WriteFragment(), new GoalsFragment(), new SettingsFragment()};
    HashMap<Integer, Fragment> fragMap = new HashMap<Integer, Fragment>();
    //</editor-fold>
    ActivityMainBinding binding;
    public void replaceFragment(int id){
        Fragment fragment = fragMap.get(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.View, fragment);
        fragmentTransaction.commit();
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.View, fragment);
        fragmentTransaction.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        for (int i = 0; i < fragment_id.length; i++) {
            fragMap.put(fragment_id[i], fragments[i]);
        }
        replaceFragment(R.id.Home);
        binding.bottomNav.setOnItemSelectedListener(item -> {
            replaceFragment(item.getItemId());
            return true;
        });
    }
}