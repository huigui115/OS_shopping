package com.example.myapplication.slice;
import com.alibaba.fastjson.JSON;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.util.*;
import com.example.myapplication.agreement;
import com.example.myapplication.entity.Tuser;
import com.example.myapplication.util.HttpClientUtil;
import com.example.myapplication.util.ProgressDialogUtil;
import com.example.myapplication.util.ToastUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Checkbox;
import ohos.agp.components.TextField;

public class registerSlice extends AbilitySlice {
    TextField tf_name;
    TextField tf_password;
    TextField tf_password2;
    Button btn_register,btn_agrement;
    ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(this);
    Checkbox agree;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_register);
        tf_name = (TextField) findComponentById(ResourceTable.Id_reg_name);
        tf_password = (TextField) findComponentById(ResourceTable.Id_reg_password1);
        tf_password2 = (TextField) findComponentById(ResourceTable.Id_reg_password2);
        btn_register = (Button) findComponentById(ResourceTable.Id_reg_btn);
        btn_agrement = (Button) findComponentById(ResourceTable.Id_reg_agreement);
        findComponentById(ResourceTable.Id_reg_back).setClickedListener(component -> {
            terminateAbility();
        });
        btn_agrement.setClickedListener(component -> {
            Intent intent1 = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withBundleName(getBundleName())
                    .withAbilityName(agreement.class)
                    .build();
            intent1.setOperation(operation);
            startAbility(intent1);
        });
        btn_register.setClickedListener(component -> register());
    }

    private void register() {
        agree=(Checkbox)findComponentById(ResourceTable.Id_reg_agree);

            String name = tf_name.getText();
            if (name == null || name.trim().length() == 0) {
                ToastUtil.makeToast(getContext(), "帐号不能为空", ToastUtil.TOAST_LONG);
                return;
            }
            String password = tf_password.getText();
            String password2 = tf_password2.getText();
            if (password == null) {
                ToastUtil.makeToast(getContext(), "密码不能为空", ToastUtil.TOAST_LONG);
                return;
            }
            if (!password.equals(password2)) {
                ToastUtil.makeToast(getContext(), "密码不一样", ToastUtil.TOAST_LONG);
                return;
            }
             if(agree.isChecked()){
                 progressDialogUtil.showProgress(true);
                 new Thread(new Runnable() {
                     @Override
                     public void run() {
                         String res = HttpClientUtil.doGet(ContainUtil.REGISTER_USER_URL + "?name=" + name + "&password=" + password);
                         getUITaskDispatcher().asyncDispatch(new Runnable() {
                             @Override
                             public void run() {
                                 progressDialogUtil.showProgress(false);
                                 Tuser user = null;
                                 try {
                                     user = JSON.parseObject(res, Tuser.class);
                                 } catch (Exception e) {
                                 }
                                 if (user != null) {
                                     ToastUtil.makeToast(getContext(), "注册成功", ToastUtil.TOAST_LONG);
                                     terminateAbility();
                                     return;
                                 } else {
                                     ToastUtil.makeToast(getContext(), "注册失败", ToastUtil.TOAST_LONG);
                                 }
                             }
                         });
                     }
                 }).start();
             }else{
                 ToastUtil.makeToast(getContext(), "请同意《张三商城使用协议》", ToastUtil.TOAST_LONG);
                 return;
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
