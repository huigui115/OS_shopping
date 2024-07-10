package com.example.myapplication.fraction;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.adapter.CommonProvider;
import com.example.myapplication.adapter.ViewHolder;
import com.example.myapplication.entity.MyProduct;
import com.example.myapplication.entity.Order;
import com.example.myapplication.entity.OrderProduct;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.ListContainer;
import ohos.global.icu.text.DateFormat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderFraction extends Fraction {
    private final AbilitySlice slice;
    Component orderFraction;

    static List<Order> myOrder = new ArrayList<>();
    public static ListContainer listContainer;
    public static CommonProvider<Order> productListCommonAdapter;

    public OrderFraction(AbilitySlice slice) {
        this.slice = slice;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        orderFraction = scatter.parse(ResourceTable.Layout_fraction_order,container,false);
        listContainer = (ListContainer) orderFraction.findComponentById(ResourceTable.Id_lc_order);
        //给listContainer添加一个选择事件，保证用户点击某个订单的时候可以触发此事件
        listContainer.setItemSelectedListener(new ListContainer.ItemSelectedListener() {
            @Override
            public void onItemSelected(ListContainer listContainer, Component component, int i, long l) {
                Intent intent1 = new Intent();

                // 通过Intent中的OperationBuilder类构造operation对象，指定设备标识（空串表示当前设备）、应用包名、Ability名称
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.myapplication")
                        .withAbilityName("com.example.myapplication.DetailOrder")
                        .build();
                // 把operation设置到intent中
                intent1.setOperation(operation);
                //将当前订单的id值进行传递
                intent1.setParam("proId" , myOrder.get(i).getId()) ;
                slice.startAbility(intent1);
            }
        });
        update();
        return orderFraction;
    }

    public void update(){
        DecimalFormat df = new DecimalFormat("#.00");
        long id = MyApplication.tuser.getId();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //从数据库中得到所有的订单
                        String result = HttpClientUtil.doGet( ContainUtil.FIND_ALL_ORDER_BYUSER_URL+"?id="+id) ;
                        //将数据库中得到的json转换为Order对象
                        myOrder = JSON.parseArray(result , Order.class) ;
                        //显示页面数据需要使用ui线程
                        slice.getUITaskDispatcher().asyncDispatch(new Runnable() {
                            @Override
                            public void run() {
                                productListCommonAdapter = new CommonProvider<Order>(myOrder , slice.getContext() ,
                                        ResourceTable.Layout_fraction_order_item) {
                                    @Override
                                    protected void convert(ViewHolder holder, Order item, int position) {
                                        //从数据库中得到当前对象的图片下标，从而找到此图片显示在页面对应的组件中
                                        holder.setImageResource(ResourceTable.Id_product_iv_order1 , ContainUtil.IconArray[item.getList().get(0).getMyproduct().getIconId()]) ;
                                        holder.setText(ResourceTable.Id_name_tv_order1 , item.getList().get(0).getMyproduct().getName()) ;
                                        holder.setText(ResourceTable.Id_order_id,item.getOrdernumber());
                                        Date date=item.getDate();
                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                        String dateString = formatter.format(date);
                                        holder.setText(ResourceTable.Id_date_order1,dateString);
                                        holder.setText(ResourceTable.Id_price_tv_order1 , df.format(item.getSum())+ "") ;
                                    }
                                } ;
                                listContainer.setItemProvider(productListCommonAdapter);
                            }
                        }) ;
                    }
                }
        ).start();
    }
}
