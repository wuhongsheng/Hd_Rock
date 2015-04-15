package com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.hd.app.R;

/**
 * Created by whs on 2014/12/23.
 */
public class fragment_add extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.form_add,null);

    }

    public void onClick(View view)
    {

    }
    public  void submit(View view)
    {

    }
}
