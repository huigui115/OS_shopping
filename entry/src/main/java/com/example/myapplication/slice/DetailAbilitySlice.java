package com.example.myapplication.slice;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.entity.FavProduct;
import com.example.myapplication.entity.MyProduct;
import com.example.myapplication.entity.MyShoppingCart;
import com.example.myapplication.entity.ShoppingCart;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import com.example.myapplication.util.ProgressDialogUtil;
import com.example.myapplication.util.ToastUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;

import java.util.ArrayList;
import java.util.List;


public class DetailAbilitySlice extends AbilitySlice {
    Image proPic,btn_fav_button;
    Text proName,price;
    Long id;
    Button btn_add_button,btn_store,btn_chat;
    Text product_Detail;
    List<MyProduct> myProductList = new ArrayList<>();

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_detail);
        btn_add_button=(Button) findComponentById(ResourceTable.Id_btn_add_cart);
        btn_chat=(Button)findComponentById(ResourceTable.Id_btn_chat);
        btn_store=(Button)findComponentById(ResourceTable.Id_btn_store);
        btn_fav_button=(Image) findComponentById(ResourceTable.Id_attention);
        proPic=(Image) findComponentById(ResourceTable.Id_im_product);
        proName=(Text) findComponentById(ResourceTable.Id_t_name);
        price=(Text) findComponentById(ResourceTable.Id_t_price);
        product_Detail=(Text)findComponentById(ResourceTable.Id_t_name);
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
        //商品详情的轮播功能
        product_Detail.setTruncationMode(Text.TruncationMode.AUTO_SCROLLING);
// 始终处于自动滚动状态
        product_Detail.setAutoScrollingCount(Text.AUTO_SCROLLING_FOREVER);
// 启动跑马灯效果
        product_Detail.startAutoScrolling();
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
                                            //修改点击 加入购物车 以后的效果
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
                                btn_fav_button.setClickedListener(component -> {
                                    ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(DetailAbilitySlice.this);
                                    progressDialogUtil.showProgress(true);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String url = ContainUtil.ADD_FAVORITE_URL+ "?myproduct=" + id + "&user=" + MyApplication.tuser.getId();
                                            String res2 = HttpClientUtil.doGet(url);
                                            FavProduct favProduct=JSON.parseObject(res2,FavProduct.class);
                                            //修改点击收藏
                                            DetailAbilitySlice.this.getUITaskDispatcher().asyncDispatch(new Runnable() {
                                                @Override
                                                public void run() {
                                                    progressDialogUtil.showProgress(false);
                                                    if (favProduct != null && favProduct.getId() != null) {
                                                        ToastUtil.makeToast(DetailAbilitySlice.this,"收藏成功",ToastUtil.TOAST_LONG);
                                                        favProduct.setSelect((favProduct.getSelect() + 1) % 2);
                                                        btn_fav_button.setPixelMap(ContainUtil.SelectArray[favProduct.getSelect()]);
                                                    } else {
                                                        ToastUtil.makeToast(DetailAbilitySlice.this, "收藏失败", ToastUtil.TOAST_LONG);
                                                    }
                                                }
                                            });
                                        }
                                    }).start();
                                });
                                btn_chat.setClickedListener(component -> {
                                    ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(DetailAbilitySlice.this);
                                    progressDialogUtil.showProgress(true);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent1 = new Intent();
                                            Operation operation = new Intent.OperationBuilder()
                                                    .withDeviceId("")
                                                    .withBundleName("com.example.myapplication")
                                                    .withAbilityName("com.example.myapplication.ChatAbility")
                                                    .build();
                                            intent1.setOperation(operation);
                                            DetailAbilitySlice.this.startAbility(intent1);
                                        }
                                    }).start();
                                    progressDialogUtil.showProgress(false);
                                });
                                btn_store.setClickedListener(component -> {
                                    ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(DetailAbilitySlice.this);
                                    progressDialogUtil.showProgress(true);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent1 = new Intent();
                                            Operation operation = new Intent.OperationBuilder()
                                                    .withDeviceId("")
                                                    .withBundleName("com.example.myapplication")
                                                    .withAbilityName("com.example.myapplication.StoreAbility")
                                                    .build();
                                            intent1.setOperation(operation);
                                            DetailAbilitySlice.this.startAbility(intent1);
                                        }
                                    }).start();
                                    progressDialogUtil.showProgress(false);
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
