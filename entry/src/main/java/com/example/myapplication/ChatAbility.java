package com.example.myapplication;

import com.example.myapplication.slice.ChatAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ChatAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ChatAbilitySlice.class.getName());
    }
}
