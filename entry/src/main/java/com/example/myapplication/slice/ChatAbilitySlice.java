package com.example.myapplication.slice;

import com.example.myapplication.ChatAbility;
import com.example.myapplication.MyApplication;
import com.example.myapplication.ResourceTable;
import com.example.myapplication.adapter.CommonProvider;
import com.example.myapplication.adapter.ViewHolder;
import com.example.myapplication.entity.FavProduct;
import com.example.myapplication.util.ContainUtil;
import com.example.myapplication.util.HttpClientUtil;
import com.example.myapplication.util.LocalDataUtil;
import com.example.myapplication.util.ToastUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.text.Layout;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.CommonDialog;
import ohos.data.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

public class ChatAbilitySlice extends AbilitySlice {
    TextField SendMessage;
    Button Send_button;
    Button Add_button;
    String send_message;
    public static ListContainer listContainer;
    public static List<String> list_message = new ArrayList<>();
    public static CommonProvider<String> productListCommonAdapter;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_chat);
        SendMessage=(TextField) findComponentById(ResourceTable.Id_text_message);
        Send_button=(Button) findComponentById(ResourceTable.Id_session_button_send);
        Add_button=(Button) findComponentById(ResourceTable.Id_session_button_more);
        listContainer = (ListContainer) ChatAbilitySlice.this.findComponentById(ResourceTable.Id_message_item);
        Send_button.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Send_message();
            }
        });
        SendMessage.setText("");
    }
    public void Send_message(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send_message=SendMessage.getText();
                list_message.add(send_message);
                getUITaskDispatcher().asyncDispatch(new Runnable() {
                    @Override
                    public void run() {
                        if (productListCommonAdapter == null) {
                            productListCommonAdapter = new CommonProvider<String>(list_message, ChatAbilitySlice.this,
                                    ResourceTable.Layout_ability_chat_item) {
                                @Override
                                protected void convert(ViewHolder viewHolder, String item, int position) {
                                    viewHolder.setText(ResourceTable.Id_white_text, item);
                                }
                            };
                            listContainer.setItemProvider(productListCommonAdapter);
                        } else {
                            productListCommonAdapter.notifyDataChanged();
                        }
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
