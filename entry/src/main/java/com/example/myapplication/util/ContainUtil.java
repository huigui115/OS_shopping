package com.example.myapplication.util;

import com.example.myapplication.ResourceTable;

public class ContainUtil {
   public static final String HOST="http://192.168.146.185:8080/";
   public static final String LOGIN="Tuser/login";
   public static final String REGISTER_USER_URL=HOST+"Tuser/save";
   public static final String UPDATE_USER_INFO = "Tuser/save";
   //初始化本地数据库需要的常量
   public static final String USER_NAME_KEY = "user_name_key";
   public static final String USER_PASSWORD_KEY = "user_password_key";
   public static final String USER_REMEMBER_KEY = "user_remember_key";
   public static final String USER_OBJECT_KEY = "user_object_key";
   //得到所有商品的接口
   public static final String FIND_ALL_PRODUCT_URL=HOST+ "product/findAll";
   //显示商品详情
   public static final String FIND_PRODUCT_BY_ID_URL = HOST + "product/findProductById";

   public static final String FIND_FAVORITE_BY_USER=HOST+"fav_product/findfavproductByUser";
   public static final String ADD_FAVORITE_URL=HOST+"fav_product/save";
   public static final String DELETE_FAVORITE_PRODUCT=HOST+"fav_product/deleteById";

   public static final String FIND_ALL_ORDER_URL=HOST+"order/findAll";

   public static final String FIND_ALL_ORDER_BYUSER_URL=HOST+"order/findAllByUser";

   public static final String ORDER_SAVE=HOST+"order/save";

   public static final String FIND_ORDER_BYID_URL=HOST+"order/findById";

   public static final String DELETE_ORDER_BY_ID_URL=HOST+"order/deleteById";

   public static final String DELETE_ORDER_PRODUCT_BY_ID_URL=HOST+"op/deleteById";
   public static final String DELETE_FAVORITE_PRODUCT_ALL=HOST+"fav_product/deleteAll";

   public static final String ADD_SHOPPING_CART_URL = HOST + "shopping_cart/save";
   public static final String FIND_SHOPPING_CART_BY_USER=HOST+"shopping_cart/findShoppingCartByUser";
   public static final String DELETE_SHOPPING_CART_PRODUCT = HOST+"shopping_cart/deleteById";
   public static final String ORDER_SAVE_URL =HOST+"order/save" ;
   public static final String ORDER_PRODUCT_SAVE_URL =HOST+"op/save" ;

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
   public static final int[] SelectArray={
           ResourceTable.Media_fav_icon,
           ResourceTable.Media_fav_icon1
   };

}
