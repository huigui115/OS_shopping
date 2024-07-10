package com.example.myapplication.slice;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.adapter.CommonProvider;
import com.example.myapplication.adapter.ViewHolder;
import com.example.myapplication.entity.FavProduct;
import com.example.myapplication.entity.MyProduct;
import com.example.myapplication.entity.MyShoppingCart;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAbilitySlice extends AbilitySlice {
    Long id;
    public static ListContainer listContainer;
    public static List<FavProduct> my_fav_products = new ArrayList<>();
    public static CommonProvider<FavProduct> productListCommonAdapter;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_favorite);
        //返回
        findComponentById(ResourceTable.Id_btn_fav_backup).setClickedListener(component -> {
            terminateAbility();
        });
        upDate();
    }
    public void upDate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取当前用户的收藏商品信息列表
                String res1= HttpClientUtil.doGet(ContainUtil.FIND_FAVORITE_BY_USER+"?id="+ MyApplication.tuser.getId());
                my_fav_products= JSON.parseArray(res1,FavProduct.class);
                if(my_fav_products == null)
                    my_fav_products = new ArrayList<>();
                getUITaskDispatcher().asyncDispatch(new Runnable() {
                    @Override
                    public void run() {
                        productListCommonAdapter = new CommonProvider<FavProduct>(my_fav_products, FavoriteAbilitySlice.this,
                                ResourceTable.Layout_ability_favorite_item){
                            protected void convert(ViewHolder viewHolder, FavProduct item, int position){
                                viewHolder.setText(ResourceTable.Id_name_tv_favorite, item.getMyproduct().getName());
                                viewHolder.setText(ResourceTable.Id_price_fav_tv, item.getMyproduct().getPrice() + "");
                                viewHolder.setImageResource(ResourceTable.Id_product_fav_iv, ContainUtil.IconArray[item.getMyproduct().getIconId()]);
                                //收藏商品删除
                                viewHolder.getView(ResourceTable.Id_image_favor).setClickedListener(component -> {
                                    my_fav_products.remove(item);
                                    listContainer.getItemProvider().notifyDataChanged();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HttpClientUtil.doGet(ContainUtil.DELETE_FAVORITE_PRODUCT+"?id="+item.getId());
                                        }
                                    }).start();
                                });
                                //收藏商品清空
                                findComponentById(ResourceTable.Id_btn_delete_all).setClickedListener(component -> {
                                    my_fav_products=null;
                                });
                            }
                        };
                        listContainer.setItemProvider(productListCommonAdapter);
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
