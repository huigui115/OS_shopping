package com.example.myapplication.util;

import com.example.myapplication.ResourceTable;

public class ContainUtil {
   public static final String HOST="http://192.168.227.185:8080/";
   public static final String LOGIN="Tuser/login";

   public static final String REGISTER_USER_URL=HOST+"user/save";

   //初始化本地数据库需要的常量
   public static final String USER_NAME_KEY = "user_name_key";
   public static final String USER_PASSWORD_KEY = "user_password_key";
   public static final String USER_REMEMBER_KEY = "user_remember_key";
   public static final String USER_OBJECT_KEY = "user_object_key";
   //得到所有商品的接口
   public static final String FIND_ALL_PRODUCT_URL=HOST+ "product/findAll";

   public static final String ADD_SHOPPING_CART_URL = HOST + "shopping_cart/save";
   public static final String FIND_SHOPPING_CART_BY_USER=HOST+"shopping_+3cart/findShoppingCartByUser";
   public static final String DELETE_SHOPPING_CART_PRODUCT = HOST+"shopping_cart/deleteById";

   public static final int[] IconArray={
           ResourceTable.Media_product_iv,  //电脑
           ResourceTable.Media_product_iv2,  //手机
           ResourceTable.Media_product_iv3, //手表
           ResourceTable.Media_product_iv4,//金葫芦
           ResourceTable.Media_product_iv5,//手机架
           ResourceTable.Media_product_iv6,//罗西尼手表男士全自动机械表
           ResourceTable.Media_product_iv7,//MNO梦梭手表
   };
   public static final int[] SelectIconArray={
           ResourceTable.Media_select_normal,
           ResourceTable.Media_select_select
   };

}
