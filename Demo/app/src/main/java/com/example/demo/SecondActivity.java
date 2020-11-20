package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  Myadapter adapter;
    private ArrayList<String> mData = new ArrayList<>();
    private FloatingActionButton actionButton;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);

        //放入20筆資料
        for (int i=0; i<20; i++){
            mData.add("項目 " + i);
        }
        //連接元件
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        //設定RecyclerView為列表型態
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 2));    //Grid型態

        //設置格線
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //資料交給adapter
        adapter = new Myadapter(mData);

        //設置adapter給RecyclerView
        recyclerView.setAdapter(adapter);



        actionButton = (FloatingActionButton)findViewById(R.id.actionBtn);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem(0, "New Item");
                // 列表滑到位置0
                recyclerView.scrollToPosition(0);
            }
        });

    }


    //Event應用
    @Override
    public void onStart(){
        super.onStart();
        //在Start階段執行
        EventBus.getDefault().register(this);
    }
    @Override
    public  void onStop(){
        super.onStop();
        //在stop停止，subscribe停止接收
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRemoveItem(final RemoveItem event){
        //移除項目
        adapter.remove(event.getPosition());
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "移除一個項目", Snackbar.LENGTH_SHORT)
                .setAction("復原", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.addItem(event.getPosition(), event.getString());
                    }
                });
        snackbar.show();
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        // 設置要用哪個menu檔案做為選單
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        //點選項目ID
        int id = item.getItemId();

        //點選項目事件判斷
        if (id == R.id.action_setting){
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "設定", Snackbar.LENGTH_SHORT)
                    .setAction("確定", null);
            snackbar.show();
            return true;

        }else if (id == R.id.action_help){
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "說明", Snackbar.LENGTH_SHORT)
                    .setAction("確定", null);
            snackbar.show();
            return true;
        }else if (id == R.id.action_about){
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "其他", Snackbar.LENGTH_SHORT)
                    .setAction("確定", null);
            snackbar.show();
            return true;
        }

        return  super.onOptionsItemSelected(item);
    }

}