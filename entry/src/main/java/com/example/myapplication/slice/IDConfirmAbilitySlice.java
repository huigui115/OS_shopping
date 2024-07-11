package com.example.myapplication.slice;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.IDConfirmAbility;
import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.entity.Tuser;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import com.example.myapplication.util.ToastUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;

import java.util.Objects;

public class IDConfirmAbilitySlice extends AbilitySlice {
    TextField pwd_input;
    Text username_text;
    Button confirm, back;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_confirm);
        pwd_input = (TextField) findComponentById(ResourceTable.Id_pwd_input);
        confirm = (Button) findComponentById(ResourceTable.Id_confirm_idconfirm);
        back = (Button) findComponentById(ResourceTable.Id_back_confirmid);
        username_text = (Text) findComponentById(ResourceTable.Id_username_text);
        username_text.setText(MyApplication.tuser.getName());
        confirm.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String pwd = pwd_input.getText();
                String username = username_text.getText();
                if(pwd==null||pwd.trim().length()==0){
                    ToastUtil.makeToast(IDConfirmAbilitySlice.this,"密码不能为空",ToastUtil.TOAST_LONG);
                }else{
                    Confirm(pwd);
                }
            }
        });
        back.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                terminate();
            }
        });
    }

    public void Confirm(String pwd){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = ContainUtil.HOST + ContainUtil.LOGIN + "?name=" + MyApplication.tuser.getName() +
                        "&password=" + pwd;
                String result = HttpClientUtil.doGet(url);
                getUITaskDispatcher().asyncDispatch(new Runnable() {
                    @Override
                    public void run() {
                        Tuser tuser = JSON.parseObject(result,Tuser.class);
                        if(tuser == null){
                            ToastUtil.makeToast(IDConfirmAbilitySlice.this,"密码输入错误,请重新输入",ToastUtil.TOAST_LONG);
                        }
                        else if(Objects.equals(tuser.getPassword(), MyApplication.tuser.getPassword())){
                            Intent intent1 = new Intent();
                            Operation operation = new Intent.OperationBuilder()
                                    .withDeviceId("")
                                    .withBundleName("com.example.myapplication")
                                    .withAbilityName("com.example.myapplication.PaySettingAbility")
                                    .build();
                            intent1.setOperation(operation);
                            startAbility(intent1);
                            terminate();
                        }
                    }
                });
            }
        }).start();
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
