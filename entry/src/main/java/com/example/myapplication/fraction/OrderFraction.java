package com.example.myapplication.fraction;

import com.example.myapplication.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;

public class OrderFraction extends Fraction {
    private final AbilitySlice slice;

    Component orderFraction;

    public OrderFraction(AbilitySlice slice) {
        this.slice = slice;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        orderFraction = scatter.parse(ResourceTable.Layout_ability_order,container,false);
        return orderFraction;
    }


    }
