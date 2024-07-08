package com.example.myapplication.fraction;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.MyApplication;
import com.example.myapplication.OrderAbility;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.adapter.CommonProvider;
import com.example.myapplication.adapter.ViewHolder;
import com.example.myapplication.entity.MyShoppingCart;
import com.example.myapplication.util.HttpClientUtil;
import com.example.myapplication.util.ToastUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import com.example.myapplication.util.ContainUtil;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFraction extends Fraction {
    Component shoppingCartFraction;
    private final AbilitySlice slice;
    public static double sum = 0;
    public static Text t_sum;

    public static List<MyShoppingCart> myShoppingCarts = new ArrayList<>();
    public static ListContainer listContainer;
    public static MyShoppingCart item;
    public static CommonProvider<MyShoppingCart> productListCommonAdapter;
    public ShoppingCartFraction(AbilitySlice slice) {
        this.slice = slice;
    }

    @Override
    protected Component onComponentAttached(LayoutScatter scatter, ComponentContainer container, Intent intent) {
        shoppingCartFraction = scatter.parse(ResourceTable.Layout_fraction_shopping_cart,container,false);

        listContainer = (ListContainer) shoppingCartFraction.findComponentById(ResourceTable.Id_shoppingCart_lv);
        t_sum = (Text) shoppingCartFraction.findComponentById(ResourceTable.Id_t_sum);

        //结算下单
        shoppingCartFraction.findComponentById(ResourceTable.Id_btn_settlement).setClickedListener(component -> {

        });
        //购物车中商品点击事件
        listContainer.setItemClickedListener((listContainer1, component, i, l) -> {
            item = myShoppingCarts.get(i);
            item.setSelect((item.getSelect() + 1) % 2);
            listContainer.getItemProvider().notifyDataChanged();//刷新界面
            settlementPrice();

        });
        upDate();
        //跳转
        shoppingCartFraction.findComponentById(ResourceTable.Id_btn_settlement).setClickedListener(component -> {
                    int selected = 0;
                    for (MyShoppingCart item : myShoppingCarts) {
                        if (item.getSelect() == 1)
                            selected++;
                    }
                    if (selected == 0) {
                        ToastUtil.makeToast(slice.getContext(), "请选择商品", ToastUtil.TOAST_LONG);
                        return;
                    } else {
                        Intent intent1 = new Intent();
                        Operation operation = new Intent.OperationBuilder().withBundleName(getBundleName()).withAbilityName(OrderAbility.class).build();
                        intent1.setOperation(operation);
                        intent1.setParam("price", sum);
                        slice.startAbility(intent1);
                    }
                });

        return shoppingCartFraction;
    }

    public void upDate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取当前用户的购物车商品信息
                String res = HttpClientUtil.doGet(ContainUtil.FIND_SHOPPING_CART_BY_USER+"?id="+ MyApplication.tuser.getId());

                myShoppingCarts = JSON.parseArray(res, MyShoppingCart.class);
                if(myShoppingCarts == null)
                    myShoppingCarts = new ArrayList<>();


                slice.getUITaskDispatcher().asyncDispatch(new Runnable() {
                    @Override
                    public void run() {
                        productListCommonAdapter = new CommonProvider<MyShoppingCart>(myShoppingCarts, slice.getContext(),
                                ResourceTable.Layout_fraction_shopping_cart_item) {
                            @Override
                            protected void convert(ViewHolder viewHolder, MyShoppingCart item, int position) {
                                viewHolder.setText(ResourceTable.Id_name_tv_shopping_cart, item.getMyproduct().getName());
                                viewHolder.setImageResource(ResourceTable.Id_im_select, ContainUtil.SelectIconArray[item.getSelect()]);
                                viewHolder.setText(ResourceTable.Id_t_item_sum, String.format("%.1f", item.getMyproduct().getPrice() * item.getCount()));
                                viewHolder.setImageResource(ResourceTable.Id_product_iv_shopping_cart, ContainUtil.IconArray[item.getMyproduct().getIconId()]);
                                viewHolder.setText(ResourceTable.Id_count, item.getCount() + "");
                                Button button = viewHolder.getView(ResourceTable.Id_delete_shopping_cart);
                                //商品增加
                                viewHolder.getView(ResourceTable.Id_btn_count_plus).setClickedListener(component -> {
                                    item.setCount(item.getCount() + 1);
                                    listContainer.getItemProvider().notifyDataChanged();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String res = HttpClientUtil.doGet(ContainUtil.ADD_SHOPPING_CART_URL+"?myproduct="
                                                    + item.getMyproduct().getId() + "&user=" + item.getUser().getId()
                                                    + "&id=" + item.getId()
                                                    + "&count=" + item.getCount());
                                        }
                                    }).start();
                                    settlementPrice();
                                });
                                //商品减少
                                viewHolder.getView(ResourceTable.Id_btn_count_minus).setClickedListener(component -> {
                                    if (item.getCount() <= 1) {
                                        component.setEnabled(false);
                                    } else {
                                        component.setEnabled(true);
                                        item.setCount(item.getCount() == 1 ? 1 : item.getCount() - 1);
                                        listContainer.getItemProvider().notifyDataChanged();
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                String res = HttpClientUtil.doGet(ContainUtil.ADD_SHOPPING_CART_URL+"?myproduct="  + item.getMyproduct().getId() + "&user=" + item.getUser().getId()
                                                        + "&id=" + item.getId()       + "&count=" + item.getCount());
                                            }
                                        }).start();
                                        settlementPrice();
                                    }
                                });
                                //商品删除
                                viewHolder.getView(ResourceTable.Id_delete_shopping_cart).setClickedListener(component -> {
                                    myShoppingCarts.remove(item);
                                    listContainer.getItemProvider().notifyDataChanged();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HttpClientUtil.doGet(ContainUtil.DELETE_SHOPPING_CART_PRODUCT+"?id="+item.getId());
                                        }
                                    }).start();
                                });


                            }
                        };
                        listContainer.setItemProvider(productListCommonAdapter);
                    }
                });
            }
        }).start();
    }
    public void settlementPrice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sum = 0;
                for (MyShoppingCart myShoppingCart : myShoppingCarts) {
                    if (myShoppingCart.getSelect() == 1) {
                        sum += myShoppingCart.getMyproduct().getPrice() * myShoppingCart.getCount();
                    }
                }
                slice.getUITaskDispatcher().asyncDispatch(new Runnable() {
                    @Override
                    public void run() {
                        t_sum.setText( String.format("%.2f", sum));
                    }
                });
            }
        }).start();
    }

}
