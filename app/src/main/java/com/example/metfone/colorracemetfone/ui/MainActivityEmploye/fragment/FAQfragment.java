package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;

public class FAQfragment extends Fragment {
    private TextView tvFAQ;
//    private WebView webView;
    private ImageView imgFaq;
    private SharePreferenceUtils sharePreferenceUtils;
    public static FAQfragment newInstance() {
        FAQfragment fragmentFirst = new FAQfragment();
        return fragmentFirst;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_faq, container , false );

        tvFAQ = view.findViewById(R.id.tvFAQ);
        imgFaq = view.findViewById(R.id.imgFaq);
//        webView = view.findViewById(R.id.webView);

        sharePreferenceUtils = new SharePreferenceUtils(getActivity());
        String language = sharePreferenceUtils.getLanguage();

        if ("kh".equals(language)){
            imgFaq.setBackground(getActivity().getResources().getDrawable(R.drawable.faq_kh));
        }else {
            imgFaq.setBackground(getActivity().getResources().getDrawable(R.drawable.faq_en));
        }

        return view;
    }
}
