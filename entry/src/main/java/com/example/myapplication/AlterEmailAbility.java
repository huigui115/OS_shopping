package com.example.myapplication;

import com.example.myapplication.slice.AlterEmailAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AlterEmailAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AlterEmailAbilitySlice.class.getName());
    }
}