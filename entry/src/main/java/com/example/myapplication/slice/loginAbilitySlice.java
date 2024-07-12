package com.example.myapplication.slice;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.entity.Tuser;
import com.example.myapplication.register;
import com.example.myapplication.util.*;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;

import ohos.data.preferences.Preferences;

public class loginAbilitySlice extends AbilitySlice {
    TextField username,pwd;
    Button loginBtn,reg_btn;
    Checkbox remeberMe;
    ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(this);
    Preferences preferences=LocalDataUtil.preferences;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_login);
        username=(TextField)findComponentById(ResourceTable.Id_login_name);
        pwd=(TextField)findComponentById(ResourceTable.Id_login_pwd) ;
        loginBtn=(Button) findComponentById(ResourceTable.Id_login_btn);
        remeberMe=(Checkbox)findComponentById(ResourceTable.Id_reMe);
        reg_btn=(Button) findComponentById(ResourceTable.Id_login_reg_btn);

        Boolean reMe= preferences.getBoolean(ContainUtil.USER_REMEMBER_KEY,false);
        if(reMe){
            //用户点击了 记住我
            username.setText(preferences.getString(ContainUtil.USER_NAME_KEY,""));
            pwd.setText(preferences.getString(ContainUtil.USER_PASSWORD_KEY,""));
            remeberMe.setChecked(reMe);
            login();
        }

        loginBtn.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                login();
            }
        });
        reg_btn.setClickedListener(component -> {
            Intent intent1 = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withBundleName(getBundleName())
                    .withAbilityName(register.class)
                    .build();
            intent1.setOperation(operation);
            startAbility(intent1);
        });
    }
    public void login(){
        String name= username.getText();
        String password=pwd.getText();
        if(name==null||name.trim().length()==0){
            ToastUtil.makeToast(this,"用户名不能为空!!!",ToastUtil.TOAST_LONG);
            return;
        }
        if(password==null||password.trim().length()==0){
            ToastUtil.makeToast(this,"密码不能为空!!!",ToastUtil.TOAST_LONG);
            return;
        }
        //添加进度条
        progressDialogUtil.showProgress(true);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        String result= HttpClientUtil.doGet(ContainUtil.HOST+
                                ContainUtil.LOGIN+"?name="+name+"&password="+password);
                        getUITaskDispatcher().asyncDispatch(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialogUtil.showProgress(false);
                                        Tuser tuser= JSON.parseObject(result, Tuser.class);
                                        if(tuser==null){
                                            ToastUtil.makeToast(loginAbilitySlice.this,"该用户不存在!!!",ToastUtil.TOAST_LONG);
                                        }else{
                                            //登录成功,将登录成功的用户信息存储到MyApplicant的tuser对象中
                                            MyApplication.tuser=tuser;
                                            //同时在preference数据库中存储当前的用户信息
                                            preferences.putString(ContainUtil.USER_OBJECT_KEY,JSON.toJSONString(tuser));
                                            if(remeberMe.isChecked()){
                                                preferences.putString(ContainUtil.USER_NAME_KEY,name);
                                                preferences.putString(ContainUtil.USER_PASSWORD_KEY,password);
                                                preferences.putBoolean(ContainUtil.USER_REMEMBER_KEY,true);
                                            }else{
                                                preferences.putString(ContainUtil.USER_NAME_KEY,"");
                                                preferences.putString(ContainUtil.USER_PASSWORD_KEY,"");
                                                preferences.putBoolean(ContainUtil.USER_REMEMBER_KEY,false);
                                            }
                                            ToastUtil.makeToast(loginAbilitySlice.this,"登录成功!",ToastUtil.TOAST_LONG);
                                            Intent intent = new Intent();

// 通过Intent中的OperationBuilder类构造operation对象，指定设备标识（空串表示当前设备）、应用包名、Ability名称
                                            Operation operation = new Intent.OperationBuilder()
                                                    .withDeviceId("")
                                                    .withBundleName("com.example.myapplication")
                                                    .withAbilityName("com.example.myapplication.MainAbility")
                                                    .build();
// 把operation设置到intent中
                                            intent.setOperation(operation);
                                            startAbility(intent);
                                        }
                                    }
                                }
                        );
                    }
                }
        ).start();
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
