package com.example.myapplication;

import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.entity.Tuser;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.LocalDataUtil;
import ohos.aafwk.ability.AbilityPackage;

public class MyApplication extends AbilityPackage {
    //定义一个用户对象
    public static Tuser tuser;
    @Override
    public void onInitialize() {
        super.onInitialize();
        //初始化本地数据库信息
        LocalDataUtil.initDb(this,"root");
        //对Tuser对象进行异常处理，因为第一次进入项目的时候用户信息为空
        try{
        if(tuser==null){
            tuser= JSONObject.parseObject(ContainUtil.USER_OBJECT_KEY,Tuser.class);
        }
        }catch(Exception e){

        }
    }
}
