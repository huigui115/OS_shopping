package com.example.myapplication.fraction;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.adapter.CommonProvider;
import com.example.myapplication.entity.OrderProduct;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.ListContainer;

import java.util.ArrayList;
import java.util.List;

public class OrderFraction extends Fraction {
    private final AbilitySlice slice;
    Component orderFraction;

    static List<OrderProduct> myProducts = new ArrayList<>();
    public static ListContainer listContainer;
    public static CommonProvider<OrderProduct> productListCommonAdapter;

    public OrderFraction(AbilitySlice slice) {
        this.slice = slice;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        orderFraction = scatter.parse(ResourceTable.Layout_fraction_order,container,false);
        listContainer = (ListContainer) orderFraction.findComponentById(ResourceTable.Id_lc_order);
        return orderFraction;
    }

    }
