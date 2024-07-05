package com.example.myapplication.fraction;

import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;

public class MineFraction extends Fraction {
    Component mineFraction;
    AbilitySlice slice;
    //与当前的slice关联上
    public MineFraction(AbilitySlice slice){
        this.slice=slice;
    }
    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        mineFraction = scatter.parse(ResourceTable.Layout_fraction_mime,container,false);
        return mineFraction;
    }
}
