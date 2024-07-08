package com.example.myapplication.fraction;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.LocalDataUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.data.preferences.Preferences;


public class MineFraction extends Fraction {
    Component mineFraction;
    AbilitySlice slice;
    //与当前的slice关联上
    Preferences preferences = LocalDataUtil.preferences;
    Button infobutton;
    Button paybutton;
    Button securitybutton;

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
                        String username=preferences.getString(ContainUtil.USER_NAME_KEY,"");
                        infobutton = (Button) mineFraction.findComponentById(ResourceTable.Id_infobutton);
                        paybutton = (Button) mineFraction.findComponentById(ResourceTable.Id_paysettingb);
                        securitybutton = (Button) mineFraction.findComponentById(ResourceTable.Id_securitysetting);
                        infobutton.setClickedListener(new Component.ClickedListener() {
                            @Override
                            public void onClick(Component component) {

                            }
                        });
                        paybutton.setClickedListener(new Component.ClickedListener() {
                            @Override
                            public void onClick(Component component) {

                            }
                        });
                        securitybutton.setClickedListener(new Component.ClickedListener() {
                            @Override
                            public void onClick(Component component) {

                            }
                        });
                    }
                }
        ).start();
    }
}
