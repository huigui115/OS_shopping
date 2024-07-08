package com.example.myapplication.fraction;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.adapter.CommonProvider;
import com.example.myapplication.adapter.ViewHolder;
import com.example.myapplication.entity.MyProduct;
import com.example.myapplication.entity.ShoppingCart;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import com.example.myapplication.util.ToastUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;

import java.util.ArrayList;
import java.util.List;

public class HomeFraction extends Fraction {
    Component homeFraction;
    AbilitySlice slice;
    //定义一个集合用来关联数据库中的商品信息
    List<MyProduct> myProductList = new ArrayList<>();
    ListContainer listContainer;
    //定义一个适配器，用来关联容器和服务器
    CommonProvider<MyProduct> commonProvider;
    //与当前的slice关联上
    public HomeFraction(AbilitySlice slice) {
        this.slice = slice;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {

        homeFraction = scatter.parse(ResourceTable.Layout_fraction_home, container, false);
        listContainer = (ListContainer) homeFraction.findComponentById(ResourceTable.Id_lc_product);
        //给listContainer添加一个选择事件，保证用户点击某个商品的时候可以触发此事件
        listContainer.setItemSelectedListener(new ListContainer.ItemSelectedListener() {
            @Override
            public void onItemSelected(ListContainer listContainer, Component component, int i, long l) {
                Intent intent1 = new Intent();

// 通过Intent中的OperationBuilder类构造operation对象，指定设备标识（空串表示当前设备）、应用包名、Ability名称
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.myapplication")
                        .withAbilityName("com.example.myapplication.DetailAbility")
                        .build();
// 把operation设置到intent中
                intent1.setOperation(operation);
                //将当前商品的id值进行传递
                intent1.setParam("proId" , myProductList.get(i).getId()) ;
                slice.startAbility(intent1);
            }
        });
        update();
        return homeFraction;
    }
    //添加购物车
    public void addCar(Long productId){
        //可自己添加进度条
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //调用后台接口完成购物车添加
                        String result =  HttpClientUtil.doGet(ContainUtil.ADD_SHOPPING_CART_URL + "?myproduct="
                                + productId + "&user=" + MyApplication.tuser.getId()) ;
                        slice.getUITaskDispatcher().asyncDispatch(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        ShoppingCart shoppingCart = JSON.parseObject(result , ShoppingCart.class) ;
                                        if(shoppingCart == null || shoppingCart.getId() == null){
                                            ToastUtil.makeToast(slice , "添加失败了呀" , ToastUtil.TOAST_LONG);
                                        }else{
                                            ToastUtil.makeToast(slice , "添加成功啦" , ToastUtil.TOAST_LONG);
                                        }
                                    }
                                }
                        ) ;

                    }
                }
        ).start();
    }

    //更新fraction显示数据
    public void update(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //从数据库中得到所有的商品
                        String result = HttpClientUtil.doGet( ContainUtil.FIND_ALL_PRODUCT_URL) ;
                        //将数据库中得到的json转换为MyProduct对象
                        myProductList = JSON.parseArray(result , MyProduct.class) ;
                        //显示页面数据需要使用ui线程
                        slice.getUITaskDispatcher().asyncDispatch(new Runnable() {
                            @Override
                            public void run() {
                                commonProvider = new CommonProvider<MyProduct>(myProductList , slice.getContext() ,
                                        ResourceTable.Layout_fraction_home_item) {
                                    @Override
                                    protected void convert(ViewHolder holder, MyProduct item, int position) {
                                        //从数据库中得到当前对象的图片下标，从而找到此图片显示在页面对应的组件中
                                        holder.setImageResource(ResourceTable.Id_product_iv , ContainUtil.IconArray[item.getIconId()]) ;
                                        holder.setText(ResourceTable.Id_name_tv , item.getName()) ;
                                        holder.setText(ResourceTable.Id_commrate_tv , item.getProductCount() + "") ;
                                        holder.setText(ResourceTable.Id_price_tv , item.getPrice() + "") ;
                                        holder.getView(ResourceTable.Id_image_car).setClickedListener(
                                                new Component.ClickedListener() {
                                                    @Override
                                                    public void onClick(Component component) {
                                                        addCar(item.getId());
                                                    }
                                                }
                                        );
                                    }
                                } ;
                                listContainer.setItemProvider(commonProvider);
                            }
                        }) ;

                    }
                }
        ).start();
    }
}
