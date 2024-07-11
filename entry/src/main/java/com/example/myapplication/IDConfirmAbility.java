package com.example.myapplication;

import com.example.myapplication.slice.IDConfirmAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class IDConfirmAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(IDConfirmAbilitySlice.class.getName());
    }

}
