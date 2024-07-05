package com.example.myapplication;

import com.example.myapplication.slice.OrderAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class OrderAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(OrderAbilitySlice.class.getName());
    }
}
