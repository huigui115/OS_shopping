package com.example.myapplication.slice;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.adapter.CommonProvider;
import com.example.myapplication.adapter.ViewHolder;
import com.example.myapplication.entity.MyProduct;
import com.example.myapplication.entity.MyShoppingCart;
import com.example.myapplication.entity.Order;
import com.example.myapplication.entity.OrderProduct;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import com.example.myapplication.util.ProgressDialogUtil;
import com.example.myapplication.util.ToastUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailOrderSlice extends AbilitySlice {
    Text t_name;
    Text t_sum;
    Text t_phone;
    Text t_address;

    ListContainer order_product;
    private AbilitySlice slice;

    CommonProvider<OrderProduct> commonProvider;

    public static ListContainer listContainer;

    @Override
    public void onStart(Intent intent) {



        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_DetailOrder);

        t_name = (Text) findComponentById(ResourceTable.Id_t_name1);
        t_sum = (Text) findComponentById(ResourceTable.Id_t_sum_order);
        t_phone=(Text)findComponentById(ResourceTable.Id_t_phone1);
        t_address=(Text)findComponentById(ResourceTable.Id_t_address1);
        order_product=(ListContainer)findComponentById(ResourceTable.Id_listContainer_order_details);
        //返回
        findComponentById(ResourceTable.Id_btn_backup).setClickedListener(component -> terminateAbility());

        //返回界面
        Long id = intent.getLongParam("proId", -1);
        if (id == -1) {
            terminateAbility();
            return;
        }
        update(id);
    }


    private void update(Long id) {
        DecimalFormat df = new DecimalFormat("#.00");
        //得到商品的详情信息
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //访问接口
                        String result = HttpClientUtil.doGet(ContainUtil.FIND_ORDER_BYID_URL +
                                "?id=" + id) ;
                        Order order = JSON.parseObject(result , Order.class) ;
                        getUITaskDispatcher().asyncDispatch(new Runnable() {
                            @Override
                            public void run() {
                                t_sum.setText("￥" + df.format(order.getSum()));
                                t_name.setText(order.getName());
                                t_address.setText(order.getAddress());
                                t_phone.setText(order.getPhone());
                                getUITaskDispatcher().asyncDispatch(new Runnable() {
                                    @Override
                                    public void run() {
                                        commonProvider = new CommonProvider<OrderProduct>(order.getList(), getContext() ,
                                                ResourceTable.Layout_Order_item){
                                            @Override
                                            protected void convert(ViewHolder holder, OrderProduct item, int position) {
                                                holder.setImageResource(ResourceTable.Id_product_iv_order , ContainUtil.IconArray[item.getMyproduct().getIconId()]) ;
                                                holder.setText(ResourceTable.Id_name_tv_order , item.getMyproduct().getName()) ;
                                                holder.setText(ResourceTable.Id_count_order , item.getMyproduct().getProductCount() + "") ;
                                                holder.setText(ResourceTable.Id_price_tv_order , df.format(item.getMyproduct().getPrice() )+ "") ;
                                            }
                                        } ;
                                        order_product.setItemProvider(commonProvider);
                                    }
                                }) ;
                            }
                        }) ;
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
