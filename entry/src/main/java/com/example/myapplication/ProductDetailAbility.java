package com.example.myapplication;

import com.example.myapplication.slice.ProductDetailAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ProductDetailAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ProductDetailAbilitySlice.class.getName());
    }
}
