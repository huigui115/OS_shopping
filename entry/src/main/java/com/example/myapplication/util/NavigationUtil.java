package com.example.myapplication.util;

import com.example.myapplication.ResourceTable;
import com.example.myapplication.entity.NavigationItem;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.utils.Color;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NavigationUtil {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.DEBUG, 0, "NavigationUtil");

    public static List<NavigationItem> getNavigationItemList(AbilitySlice slice) {
        List<NavigationItem> navigationItemList = new ArrayList<>();
        try {
            navigationItemList.add(new NavigationItem(ResourceTable.Media_navigation_homne,
                    ResourceTable.Media_navigation_homne_click,
                    slice.getResourceManager().getElement(ResourceTable.String_home).getString()));
            navigationItemList.add(new NavigationItem(ResourceTable.Media_navigation_shopcart,
                    ResourceTable.Media_navigation_shopcart_click,
                    slice.getResourceManager().getElement(ResourceTable.String_shopping_Cart).getString()));
            navigationItemList.add(new NavigationItem(ResourceTable.Media_navigation_order,
                    ResourceTable.Media_navigation_order_click,
                    slice.getResourceManager().getElement(ResourceTable.String_order).getString()));
            //我的导航栏
            navigationItemList.add(new NavigationItem(ResourceTable.Media_navigation_homne,
                    ResourceTable.Media_navigation_homne_click,
                    slice.getResourceManager().getElement(ResourceTable.String_my).getString()));

        } catch (IOException | NotExistException | WrongTypeException e) {
            HiLog.info(LABEL_LOG, "get navigation item string fail");
        }
        return navigationItemList;
    }
    public static Color getNormalTextColor(AbilitySlice slice) {
        Color normalTextColor = null;
        try {
            normalTextColor = new Color(Color.getIntColor(
                    slice.getResourceManager().getElement(ResourceTable.Color_navigation_unselected).getString()));
        } catch (IOException | NotExistException | WrongTypeException e) {
            HiLog.info(LABEL_LOG, "get normal text color fail");
        }
        return normalTextColor;
    }
    public static Color getSelectedTextColor(AbilitySlice slice) {
        Color selectedTextColor = null;
        try {
            selectedTextColor = new Color(Color.getIntColor(
                    slice.getResourceManager().getElement(ResourceTable.Color_navigation_selected).getString()));
        } catch (IOException | NotExistException | WrongTypeException e) {
            HiLog.info(LABEL_LOG, "get selected text color fail");
        }
        return selectedTextColor;
    }
}
