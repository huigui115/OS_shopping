package com.example.myapplication.slice;

import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.entity.MyShoppingCart;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.components.element.Element;

public class InfoAbilitySlice extends AbilitySlice {
    Button alteremail;
    Button alterpwd;
    static Text uid,username,email;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_info);
        uid = (Text) findComponentById(ResourceTable.Id_uid);
        uid.setText(String.valueOf(MyApplication.tuser.getId()));
        username = (Text) findComponentById(ResourceTable.Id_username);
        username.setText(MyApplication.tuser.getName());
        email = (Text) findComponentById(ResourceTable.Id_email);
        email.setText(MyApplication.tuser.getEmail());
        alteremail = (Button) findComponentById(ResourceTable.Id_alteremail);
        alterpwd = (Button) findComponentById(ResourceTable.Id_alterpwd);
        alteremail.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent1 = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.myapplication")
                        .withAbilityName("com.example.myapplication.AlterEmailAbility")
                        .build();
                intent1.setOperation(operation);
                startAbility(intent1);
            }
        });
        alterpwd.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent1 = new Intent();
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.myapplication")
                        .withAbilityName("com.example.myapplication.AlterPwdAbility")
                        .build();
                intent1.setOperation(operation);
                startAbility(intent1);
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
