package com.example.myapplication.slice;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.entity.MyProduct;
import com.example.myapplication.entity.MyShoppingCart;
import com.example.myapplication.entity.ShoppingCart;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import com.example.myapplication.util.ProgressDialogUtil;
import com.example.myapplication.util.ToastUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;

import java.util.ArrayList;
import java.util.List;


public class DetailAbilitySlice extends AbilitySlice {
    Image proPic;
    Text proName,price;
    Long id;
    Button btn_add_button;
    List<MyProduct> myProductList = new ArrayList<>();
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_detail);
        btn_add_button=(Button) findComponentById(ResourceTable.Id_btn_add_cart);
        proPic=(Image) findComponentById(ResourceTable.Id_im_product);
        proName=(Text) findComponentById(ResourceTable.Id_t_name);
        price=(Text) findComponentById(ResourceTable.Id_t_price);
        //返回
        findComponentById(ResourceTable.Id_btn_backup).setClickedListener(component -> {
            terminateAbility();
        });
        id = intent.getLongParam("proId",-1);
        //得到上一页面传递过来的id值
        //当没有返回值时，做一个返回上一个页面的设置
        if(id==-1){
            terminateAbility();
            return;
        }
        update();
    }

    private void update() {
        //得到商品的详情信息
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //访问接口
                        String result = HttpClientUtil.doGet(ContainUtil.FIND_PRODUCT_BY_ID_URL +
                                "?id=" + id) ;
                        MyProduct myProduct = JSON.parseObject(result , MyProduct.class) ;
                        getUITaskDispatcher().asyncDispatch(new Runnable() {
                            @Override
                            public void run() {
                                proPic.setPixelMap(ContainUtil.IconArray[myProduct.getIconId()]);
                                price.setText("￥" + myProduct.getPrice());
                                proName.setText(myProduct.getName());

                                btn_add_button.setClickedListener(component -> {
                                    ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(DetailAbilitySlice.this);
                                    progressDialogUtil.showProgress(true);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String res = HttpClientUtil.doGet(ContainUtil.ADD_SHOPPING_CART_URL + "?myproduct=" + id + "&user=" + MyApplication.tuser.getId());
                                            MyShoppingCart myShoppingCart = JSON.parseObject(res, MyShoppingCart.class);
                                            //修改点击以后的效果
                                            DetailAbilitySlice.this.getUITaskDispatcher().asyncDispatch(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressDialogUtil.showProgress(false);
                                                    if (myShoppingCart != null && myShoppingCart.getId() != null) {
                                                        btn_add_button.setText("已添加购物车");
                                                        btn_add_button.setClickable(false);
                                                        ShapeElement shapeElementGray = new ShapeElement();
                                                        shapeElementGray.setRgbColor(new RgbColor(128,128,128));
                                                        btn_add_button.setBackground(shapeElementGray);
                                                    } else {
                                                        ToastUtil.makeToast(DetailAbilitySlice.this, "添加失败", ToastUtil.TOAST_LONG);
                                                    }
                                                }
                                            });

                                        }
                                    }).start();
                                });
                            }
                        }) ;
                    }
                }
        ).start();
    }
    //添加购物车
    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
