package com.example.ltdd.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ltdd.Activity.StatisticDetailActivity;
import com.example.ltdd.Model.Item;
import com.example.ltdd.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        view.findViewById(R.id.btnXemChiTiet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(requireActivity(), StatisticDetailActivity.class);
                startActivity(intent);*/
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.View, new StatisticDetailFragment()).commit();
            }
        });
        return view;
    }
}