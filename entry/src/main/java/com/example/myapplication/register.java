package com.example.myapplication;

import com.example.myapplication.slice.registerSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class register extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(registerSlice.class.getName());
    }
}
