package com.example.myapplication;

import com.example.myapplication.slice.AlterPwdAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class AlterPwdAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(AlterPwdAbilitySlice.class.getName());
    }
}
