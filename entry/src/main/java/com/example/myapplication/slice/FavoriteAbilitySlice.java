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
import com.example.myapplication.util.ToastUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAbilitySlice extends AbilitySlice {
    FavProduct favProduct;
    public static ListContainer listContainer;
    public static List<FavProduct> my_fav_products = new ArrayList<>();
    public static CommonProvider<FavProduct> productListCommonAdapter;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_favorite);
        listContainer = (ListContainer) FavoriteAbilitySlice.this.findComponentById(ResourceTable.Id_favorite_lv);
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
                                    ToastUtil.makeToast(FavoriteAbilitySlice.this,"取消收藏成功",ToastUtil.TOAST_LONG);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HttpClientUtil.doGet(ContainUtil.DELETE_FAVORITE_PRODUCT+"?id="+item.getId());
                                        }
                                    }).start();
                                });
                                //收藏商品清空
                                findComponentById(ResourceTable.Id_btn_delete_all).setClickedListener(component -> {
                                    //清空弹窗 的初始化
                                    CommonDialog dialog = new CommonDialog(FavoriteAbilitySlice.this);
                                    Component component1 = LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_fraction_sure_window,null,false);
                                    Button confirm = (Button) component1.findComponentById(ResourceTable.Id_confirm_fav_button);
                                    Button cancle = (Button) component1.findComponentById(ResourceTable.Id_cancel_fav_button);
                                    confirm.setClickedListener(new Component.ClickedListener() {
                                        @Override
                                        public void onClick(Component component) {
                                            dialog.destroy();
                                            if(my_fav_products==null||my_fav_products.size()==0)
                                                ToastUtil.makeToast(FavoriteAbilitySlice.this,"收藏夹已经为空！",ToastUtil.TOAST_LONG);
                                            else {
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        HttpClientUtil.doGet(ContainUtil.DELETE_FAVORITE_PRODUCT_ALL+"?id="+MyApplication.tuser.getId());
                                                    }
                                                }).start();
                                                ToastUtil.makeToast(FavoriteAbilitySlice.this, "收藏清空成功！", ToastUtil.TOAST_LONG);
                                            }
                                        }
                                    });
                                    cancle.setClickedListener(new Component.ClickedListener() {
                                        @Override
                                        public void onClick(Component component) {
                                            dialog.destroy();
                                        }
                                    });
                                    dialog.setContentCustomComponent(component1);
                                    dialog.setAutoClosable(true);
                                    dialog.setAlignment(LayoutAlignment.BOTTOM);
                                    dialog.show();
                                });
                                //再调用一遍即可完成更新操作
                                upDate();
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
