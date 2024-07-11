package com.example.myapplication.slice;

import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import com.example.myapplication.util.ToastUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;

public class AlterPwdAbilitySlice extends AbilitySlice {
    Text old_pwd;
    TextField new_pwd;
    Button confirm;
    Button back;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_alterpwd);
        old_pwd = (Text) findComponentById(ResourceTable.Id_old_pwd);
        old_pwd.setText(MyApplication.tuser.getPassword());
        new_pwd = (TextField) findComponentById(ResourceTable.Id_new_pwd);
        confirm = (Button) findComponentById(ResourceTable.Id_confirm_alterpwd);
        back = (Button) findComponentById(ResourceTable.Id_back_Altpwd);
        confirm.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String pwd = new_pwd.getText();
                if(pwd==null||pwd.trim().length()==0){
                    ToastUtil.makeToast(AlterPwdAbilitySlice.this,"新密码不能为空!!!",ToastUtil.TOAST_LONG);
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpClientUtil.doGet(ContainUtil.HOST + ContainUtil.UPDATE_USER_INFO + "?id=" + MyApplication.tuser.getId() +
                                "&name=" + MyApplication.tuser.getName() + "&password=" + pwd + "&email=" + MyApplication.tuser.getEmail()
                         + "&payment=" + MyApplication.tuser.getPayment());
                        MyApplication.tuser.setPassword(pwd);
                    }
                }).start();
                ToastUtil.makeToast(AlterPwdAbilitySlice.this,"修改成功",ToastUtil.TOAST_LONG);
                terminateAbility();
            }
        });
        back.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminate();
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
