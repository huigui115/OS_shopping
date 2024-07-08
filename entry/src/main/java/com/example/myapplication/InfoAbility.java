package com.example.myapplication;

import com.example.myapplication.slice.InfoAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class InfoAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(InfoAbilitySlice.class.getName());
    }
}
