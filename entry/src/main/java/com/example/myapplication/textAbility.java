package com.example.myapplication;

import com.example.myapplication.slice.textAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class textAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(textAbilitySlice.class.getName());
    }
}
