package com.example.myapplication.slice;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.entity.NavigationItem;
import com.example.myapplication.fraction.HomeFraction;
import com.example.myapplication.fraction.MineFraction;
import com.example.myapplication.fraction.OrderFraction;
import com.example.myapplication.fraction.ShoppingCartFraction;
import com.example.myapplication.util.NavigationUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.ability.fraction.FractionAbility;
import ohos.aafwk.ability.fraction.FractionScheduler;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.utils.Color;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    Fraction[] fractions = new Fraction[]{new HomeFraction(this), new ShoppingCartFraction(this), new OrderFraction(this),new MineFraction(this)};
    private static final int INIT_FRACTION_INDEX = 0;


    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        //初始化所有的fraction
        initFractions();
        //设置起始显示页面
        setFraction(0);
        //显示fraction
        initNavigationMenu();

    }
    //设置当前显示的fraction
    private void setFraction(int fractionIndex) {
        FractionScheduler fractionScheduler = ((FractionAbility) getAbility()).getFractionManager()
                .startFractionScheduler();

        for (Fraction fraction : fractions) {
            fractionScheduler.hide(fraction);
            if(fraction instanceof HomeFraction){
                HomeFraction homeFraction=(HomeFraction) fraction;
                homeFraction.update();
            }
            if(fraction instanceof ShoppingCartFraction){
                ShoppingCartFraction shoppingCartFraction=(ShoppingCartFraction) fraction;
                shoppingCartFraction.upDate();
            }
            if(fraction instanceof MineFraction){
                MineFraction mineFraction = (MineFraction) fraction;
                mineFraction.update();
            }
            if(fraction instanceof OrderFraction){
                OrderFraction orderFraction=(OrderFraction) fraction;
                orderFraction.update();
            }
        }
        fractionScheduler.show(fractions[fractionIndex]);
        fractionScheduler.submit();
    }
    private void initFractions() {
        FractionScheduler fractionScheduler =
                ((FractionAbility) getAbility()).getFractionManager()
                        .startFractionScheduler();
        for (Fraction fraction : fractions) {
            fractionScheduler
                    .add(ResourceTable.Id_fraction_container, fraction);
        }
        fractionScheduler.submit();
    }

    private void initNavigationMenu() {
        DirectionalLayout navigationMenu = (DirectionalLayout) findComponentById(ResourceTable.Id_navigation_menu);

        List<NavigationItem> navigationItemList = NavigationUtil.getNavigationItemList(this);
        Color normalTextColor = NavigationUtil.getNormalTextColor(this);
        Color selectedTextColor = NavigationUtil.getSelectedTextColor(this);

        List<Image> navigationItemImageList = new ArrayList<>();
        List<Text> navigationItemTextList = new ArrayList<>();
        for (int i = 0; i < fractions.length; i++) {
            DirectionalLayout navigationItem = (DirectionalLayout) LayoutScatter.getInstance(getContext())
                    .parse(ResourceTable.Layout_navigation_item, navigationMenu, false);
            Image navigationItemImage = (Image)
                    navigationItem.findComponentById(ResourceTable.Id_navigation_item_image);
            Text navigationItemText = (Text) navigationItem.findComponentById(ResourceTable.Id_navigation_item_text);

            navigationItemImage.setPixelMap(i == MainAbilitySlice.INIT_FRACTION_INDEX ?
                    navigationItemList.get(i).getSelectedImageId() : navigationItemList.get(i).getNormalImageId());
            navigationItemText.setTextColor(i == MainAbilitySlice.INIT_FRACTION_INDEX ?
                    selectedTextColor : normalTextColor);
            navigationItemText.setText(navigationItemList.get(i).getText());

            navigationItemImageList.add(navigationItemImage);
            navigationItemTextList.add(navigationItemText);

            final int currentNavigationIndex = i;
            navigationItem.setClickedListener((Component component) -> {
                for (int j = 0; j < navigationItemImageList.size(); j++) {
                    navigationItemImageList.get(j).setPixelMap(navigationItemList.get(j).getNormalImageId());
                    navigationItemTextList.get(j).setTextColor(normalTextColor);
                }
                navigationItemImage.setPixelMap(navigationItemList.get(currentNavigationIndex).getSelectedImageId());
                navigationItemText.setTextColor(selectedTextColor);
                setFraction(currentNavigationIndex);
            });
            navigationMenu.addComponent(navigationItem);
        }
    }
    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}



