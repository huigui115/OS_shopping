package com.example.myapplication;

import com.example.myapplication.slice.FavoriteAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class FavoriteAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(FavoriteAbilitySlice.class.getName());
    }
}
