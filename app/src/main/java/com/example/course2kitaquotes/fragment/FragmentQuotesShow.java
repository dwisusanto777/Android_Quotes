package com.example.course2kitaquotes.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class FragmentQuotesShow extends Fragment {

    public static FragmentQuotesShow newInstance(Bundle bundle){
        FragmentQuotesShow fragment = new FragmentQuotesShow();
        fragment.setArguments(bundle);
        return  fragment;
    }
}
