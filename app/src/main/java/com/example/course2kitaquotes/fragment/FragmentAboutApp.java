package com.example.course2kitaquotes.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.course2kitaquotes.R;
import com.example.course2kitaquotes.databinding.FragmentAboutAppBinding;

public class FragmentAboutApp extends Fragment {
    FragmentAboutAppBinding binding;
    View view;
    String text;
    DocumentView dummyData;

    public static FragmentAboutApp newInstance(Bundle bundle){
        FragmentAboutApp fragment = new FragmentAboutApp();
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutAppBinding.inflate(getLayoutInflater());
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_about_app, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        text = "Halo, ini adalah aplikasi buatan saya, silahkan feedbeack ke email dwie.oke@gmail.com";
        dummyData = addDummyData(new StringBuilder().append(text).toString(), DocumentView.PLAIN_TEXT);
        dummyData.getDocumentLayoutParams().setHyphenated(true);
        RelativeLayout rl = view.findViewById(R.id.relative_app);
        rl.removeAllViews();
        rl.addView(dummyData);
        YoYo.with(Techniques.FadeInUp).duration(600).playOn(dummyData);
    }

    public DocumentView dummyData(CharSequence charSequence, int type, boolean bool){
        DocumentView d = new DocumentView(getActivity(), type);
//        d.getDocumentLayoutParams().setTextColor(getResources().getColor(R.color.dashboard));
        d.getDocumentLayoutParams().setTextColor(ContextCompat.getColor(getActivity(), R.color.dashboard));
        d.getDocumentLayoutParams().setTextTypeface(Typeface.SANS_SERIF);
        d.getDocumentLayoutParams().setTextAlignment(TextAlignment.CENTER);
        d.getDocumentLayoutParams().setLineHeightMultiplier(2f);
        d.getDocumentLayoutParams().setReverse(bool);
        d.setText(charSequence);
        d.getDocumentLayoutParams().setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
        return d;
    }

    public DocumentView addDummyData(CharSequence charSequence, int type ){
        return dummyData(charSequence, type, true);
    }
}
