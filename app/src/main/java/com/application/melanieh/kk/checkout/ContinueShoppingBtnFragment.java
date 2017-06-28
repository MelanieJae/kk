package com.application.melanieh.kk.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.application.melanieh.kk.EventBus;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.shopping.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by melanieh on 6/21/17.
 */

public class ContinueShoppingBtnFragment extends Fragment {

    Unbinder unbinder;

    @OnClick(R.id.continue_shopping_btn)
    public void onClick() {
        Intent returnToMainScreen = new Intent(getActivity(), MainActivity.class);
        startActivity(returnToMainScreen);
    }

    public static ContinueShoppingBtnFragment newInstance() {
        ContinueShoppingBtnFragment fragment = new ContinueShoppingBtnFragment();
        return fragment;
    }

    public ContinueShoppingBtnFragment() {
        //
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_continue_shopping_btn, container, false);
        unbinder = ButterKnife.bind(getActivity(), rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
