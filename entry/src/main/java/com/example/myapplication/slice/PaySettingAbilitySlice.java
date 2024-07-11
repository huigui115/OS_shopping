package com.example.myapplication.slice;

import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.RadioContainer;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;

public class PaySettingAbilitySlice extends AbilitySlice {

    Button changepay,paypassword;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_paysetting);
        changepay = (Button) findComponentById(ResourceTable.Id_change_pay);
        paypassword = (Button) findComponentById(ResourceTable.Id_pay_passord_button);
        changepay.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                CommonDialog dialog = new CommonDialog(PaySettingAbilitySlice.this);
                Component component1 = LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_my_common_dialog,null,false);

                Button confirm = (Button) component1.findComponentById(ResourceTable.Id_paysetting_confirm);
                Button cancel = (Button) component1.findComponentById(ResourceTable.Id_paysetting_cancle);

                RadioContainer radioContainer = (RadioContainer) component1.findComponentById(ResourceTable.Id_radioContainer);
                radioContainer.mark(MyApplication.tuser.getPayment());
                radioContainer.setMarkChangedListener(new RadioContainer.CheckedStateChangedListener() {
                    @Override
                    public void onCheckedChanged(RadioContainer radioContainer, int i) {
                        MyApplication.tuser.setPayment(i);
                    }
                });
                confirm.setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String url = ContainUtil.HOST + ContainUtil.UPDATE_USER_INFO + "?id=" + MyApplication.tuser.getId() +
                                        "&name=" + MyApplication.tuser.getName() + "&password=" + MyApplication.tuser.getPassword() + "&email=" +
                                        MyApplication.tuser.getEmail() + "&payment=" + MyApplication.tuser.getPayment();
                                String result = HttpClientUtil.doGet(url);
                            }
                        }).start();
                        dialog.destroy();
                    }
                });
                cancel.setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        dialog.destroy();
                    }
                });
                dialog.setCornerRadius(15);
                dialog.setContentCustomComponent(component1);
                dialog.setAutoClosable(true);
                dialog.setAlignment(LayoutAlignment.BOTTOM);
                dialog.show();

            }
        });
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
