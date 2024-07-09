package com.example.myapplication;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.entity.MyProduct;
import com.example.myapplication.slice.FavoriteAbilitySlice;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(FavoriteAbilitySlice.class.getName());
    }


}
