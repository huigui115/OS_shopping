package com.example.myapplication;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.entity.MyProduct;
import com.example.myapplication.slice.DetailAbilitySlice;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.media.image.Image;

public class DetailAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(DetailAbilitySlice.class.getName());
    }

}
