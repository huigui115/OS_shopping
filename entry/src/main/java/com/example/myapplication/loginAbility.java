package com.example.myapplication;

import com.example.myapplication.slice.loginAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class loginAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(loginAbilitySlice.class.getName());
    }
}
