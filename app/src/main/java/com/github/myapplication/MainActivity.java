package com.github.myapplication;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView lv_main_list;
    private ListView lv_menu_list;
    private DragLayout dl;
    private ImageView iv_head;
    private MainNoTouch main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initView();
        initData();
    }

    private void initData() {
        ArrayAdapter<String>  adpter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cheeses.NAMES);
        lv_main_list.setAdapter(adpter);
        adpter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,Cheeses.CHEESE_STRINGS){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };

        lv_menu_list.setAdapter(adpter);
        dl.setOnDragStateChangedListener(new DragLayout.OnDragStateChangedListenter() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onDragging(float percent) {
            iv_head.setAlpha(1-percent);
            }

            @Override
            public void onClose() {
                ObjectAnimator oa = ObjectAnimator.ofFloat(iv_head, "translationX", 10f);
                oa.setDuration(500);
                oa.setInterpolator(new CycleInterpolator(7));
                oa.start();
            }
        });
        main_layout.setOnMainNoTouchListener(new MainNoTouch.OnMainNoTouchListener() {
            @Override
            public boolean onMenuOpen() {
                return dl.isOpen();
            }
        });
    }

    private void initView() {
        lv_main_list = (ListView) findViewById(R.id.lv_main_list);
        lv_menu_list = (ListView) findViewById(R.id.lv_menu_list);
        dl = (DragLayout) findViewById(R.id.dg);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        main_layout = (MainNoTouch) findViewById(R.id.main_layout);
    }


}
