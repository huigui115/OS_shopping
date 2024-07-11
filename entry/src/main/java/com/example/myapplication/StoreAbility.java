package com.example.myapplication;

import com.example.myapplication.slice.StoreAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class StoreAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(StoreAbilitySlice.class.getName());
    }
}
