package com.application.melanieh.kk.shopping;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.application.melanieh.kk.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by melanieh on 6/21/17.
 */

public class SetUpAndroidPayBtnFragment extends Fragment {

    Unbinder unbinder;
    @OnClick(R.id.set_up_android_pay_btn)

    public void onClick(View view) {
        Toast.makeText(getActivity(), "set up Android Pay button onClick works", Toast.LENGTH_SHORT).show();
        Intent redirectToPlayStore = new Intent(Intent.ACTION_VIEW);

        try {
            startActivity(redirectToPlayStore.
                    setData(Uri.parse("market://details?id=" + getActivity().getString(R.string.android_pay_pkg_name))));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(redirectToPlayStore.setData(Uri.parse("https://play.google.com/store/apps/details?id="
                    + getActivity().getString(R.string.android_pay_pkg_name))));
        }
        startActivity(redirectToPlayStore);
    }

    public static SetUpAndroidPayBtnFragment newInstance() {
        SetUpAndroidPayBtnFragment fragment = new SetUpAndroidPayBtnFragment();
        return fragment;
    }

    public SetUpAndroidPayBtnFragment() {
        // use newInstance instead
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_set_up_android_pay_btn, container, false);
        unbinder = ButterKnife.bind(getActivity(), rootView);
        return rootView;
    }
}
