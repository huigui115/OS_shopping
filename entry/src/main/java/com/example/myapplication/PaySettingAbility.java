package com.example.myapplication;

import com.example.myapplication.slice.PaySettingAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class PaySettingAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(PaySettingAbilitySlice.class.getName());
    }
}
