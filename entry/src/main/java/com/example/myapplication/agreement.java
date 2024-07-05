package com.example.myapplication;

import com.example.myapplication.slice.agreementSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class agreement extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(agreementSlice.class.getName());
    }
}
