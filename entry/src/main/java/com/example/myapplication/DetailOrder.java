package com.example.myapplication;

import com.example.myapplication.slice.DetailOrderSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class DetailOrder extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DetailOrderSlice.class.getName());
    }
}
