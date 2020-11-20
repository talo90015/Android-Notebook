package com.example.demo;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.awt.font.TextAttribute;
import java.util.List;

//適配器
public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {

    private List<String> mData;
    Myadapter(List <String> data){
        mData = data;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        //元件宣告
        private TextView tetItem;
        private Button buttonRemove;
        ViewHolder(View itemView){
            super(itemView);
            //設置元件
            tetItem = (TextView)itemView.findViewById(R.id.txtItem);
            buttonRemove = (Button)itemView.findViewById(R.id.btn);

            //點擊項目時
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = mData.get(getAdapterPosition());
                    EventBus.getDefault().post(new MessageEvent(msg));
                }
            });
            //點擊按鈕時
            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 移除項目，getAdapterPosition為點擊的項目位置
                    int position = getAdapterPosition();
                    String string = mData.get(position);

                    EventBus.getDefault().post(new RemoveItem(position, string));
                }
            });

        }
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //連結顯示的Layout頁面
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //設置要顯示的內容
        holder.tetItem.setText(mData.get(position));
    }

    @Override
    //得到數量
    public int getItemCount() {
        return mData.size();
    }

    //新增欄位
    public void addItem(int position, String string){
        //新增位置(第0位)
        mData.add(position, string);
        notifyItemInserted(position);
    }

    //刪除欄位
    public void remove(int position){
        mData.remove(position);
        notifyItemRemoved(position);

    }
}
