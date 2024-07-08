package com.example.myapplication.slice;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.adapter.CommonProvider;
import com.example.myapplication.adapter.ViewHolder;
import com.example.myapplication.entity.MyShoppingCart;
import com.example.myapplication.entity.Order;
import com.example.myapplication.fraction.ShoppingCartFraction;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import com.example.myapplication.util.ProgressDialogUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;

import java.util.ArrayList;
import java.util.List;

public class OrderAbilitySlice extends AbilitySlice {
    Text t_sum;
    ListContainer listContainer;
    MyShoppingCart item;
    List<MyShoppingCart> myShoppingCarts;
    CommonProvider<MyShoppingCart> productListCommonAdapter;
    ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil(this);
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_order);
        initView();
        showProducts();
    }

    private void initView() {
        listContainer = (ListContainer) findComponentById(ResourceTable.Id_listContainer_order);
        t_sum = (Text) findComponentById(ResourceTable.Id_t_sum);
        TextField tf_name = (TextField) findComponentById(ResourceTable.Id_tf_name);
        TextField tf_phone = (TextField) findComponentById(ResourceTable.Id_tf_phone);
        TextField tf_address = (TextField) findComponentById(ResourceTable.Id_tf_address);

        //返回按钮
        findComponentById(ResourceTable.Id_btn_backup).setClickedListener(component -> terminateAbility());

        //订单提交按钮
        findComponentById(ResourceTable.Id_settlement_button_order).setClickedListener(lis -> {
            progressDialogUtil.showProgress(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String orderNumber = String.format("dd_%d", System.currentTimeMillis());
                    String res = HttpClientUtil.doGet(ContainUtil.ORDER_SAVE_URL+"?ordernumber=" + orderNumber
                            + "&sum=" + ShoppingCartFraction.sum
                            + "&user=" + MyApplication.tuser.getId()
                            + "&phone=" + tf_phone.getText()
                            + "&name=" + tf_name.getText()
                            + "&address=" + tf_address.getText());
                    Order order = JSON.parseObject(res, Order.class);
                    if (order != null && order.getId() != null) {
                        for (MyShoppingCart myShoppingCart : myShoppingCarts) {
                            String res2 = HttpClientUtil.doGet(ContainUtil.ORDER_PRODUCT_SAVE_URL+"?myproduct=" + myShoppingCart.getMyproduct().getId()
                                    + "&orderprice=" + myShoppingCart.getMyproduct().getPrice()   + "&count=" + myShoppingCart.getCount()   + "&order=" + order.getId()
                            );
                        }
                    }
                    getUITaskDispatcher().asyncDispatch(new Runnable() {
                        @Override
                        public void run() {
                            progressDialogUtil.showProgress(false);
                            terminateAbility();
                        }
                    });
                }
            }).start();
        });

        t_sum.setText(String.format("%.1f", ShoppingCartFraction.sum));
    }

    private List<MyShoppingCart> getList() {
        List<MyShoppingCart> list = new ArrayList<MyShoppingCart>();

        for (MyShoppingCart myShoppingCart : ShoppingCartFraction.myShoppingCarts) {
            if (myShoppingCart.getSelect() == 1) {
                list.add(myShoppingCart);
            }
        }
        return list;
    }

    public void showProducts () {
        myShoppingCarts = getList();

        productListCommonAdapter = new CommonProvider<MyShoppingCart>(myShoppingCarts, this,
                ResourceTable.Layout_Order_item) {
            @Override
            protected void convert(ViewHolder viewHolder, MyShoppingCart item, int position) {
                viewHolder.setText(ResourceTable.Id_name_tv_order, item.getMyproduct().getName());
                viewHolder.setText(ResourceTable.Id_price_tv_order, String.format("%.1f", item.getMyproduct().getPrice() * item.getCount()));
                viewHolder.setImageResource(ResourceTable.Id_product_iv_order, ContainUtil.IconArray[item.getMyproduct().getIconId()]);
                viewHolder.setText(ResourceTable.Id_count_order, item.getCount() + "");
            }
        };
        listContainer.setItemProvider(productListCommonAdapter);
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
