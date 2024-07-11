package com.example.myapplication.fraction;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.slice.IDConfirmAbilitySlice;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.LocalDataUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.ai.nlu.OnResultListener;
import ohos.data.preferences.Preferences;


public class MineFraction extends Fraction {
    Component mineFraction;
    AbilitySlice slice;
    //与当前的slice关联上
    Button infobutton;
    Button paybutton;
    Button favoritebutton;

    public MineFraction(AbilitySlice slice){
        this.slice=slice;
    }
    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        mineFraction = scatter.parse(ResourceTable.Layout_fraction_mime,container,false);
        return mineFraction;
    }

    public void update(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        infobutton = (Button) mineFraction.findComponentById(ResourceTable.Id_infobutton);
                        paybutton = (Button) mineFraction.findComponentById(ResourceTable.Id_paysettingb);
                        favoritebutton = (Button) mineFraction.findComponentById(ResourceTable.Id_favorite);
                        infobutton.setClickedListener(new Component.ClickedListener() {
                            @Override
                            public void onClick(Component component) {
                                Intent intent1 = new Intent();
                                Operation operation = new Intent.OperationBuilder()
                                        .withDeviceId("")
                                        .withBundleName("com.example.myapplication")
                                        .withAbilityName("com.example.myapplication.InfoAbility")
                                        .build();
                                intent1.setOperation(operation);
                                slice.startAbility(intent1);
                            }
                        });
                        favoritebutton.setClickedListener(new Component.ClickedListener() {
                            @Override
                            public void onClick(Component component) {
                                Intent intent1 = new Intent();
                                Operation operation = new Intent.OperationBuilder()
                                        .withDeviceId("")
                                        .withBundleName("com.example.myapplication")
                                        .withAbilityName("com.example.myapplication.FavoriteAbility")
                                        .build();
                                intent1.setOperation(operation);
                                slice.startAbility(intent1);
                            }
                        });
                        paybutton.setClickedListener(new Component.ClickedListener() {
                            @Override
                            public void onClick(Component component) {
                                Intent checkerintent = new Intent();
                                Operation operation1 = new Intent.OperationBuilder()
                                        .withDeviceId("")
                                        .withBundleName("com.example.myapplication")
                                        .withAbilityName("com.example.myapplication.IDConfirmAbility")
                                        .build();
                                checkerintent.setOperation(operation1);
                                slice.startAbility(checkerintent);
                            }
                        });
                    }
                }
        ).start();
    }



}
