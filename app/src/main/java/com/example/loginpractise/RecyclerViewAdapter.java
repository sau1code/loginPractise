package com.example.loginpractise;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Adapter類別 (中有Holder類別)
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    // Adapter建構式傳進來的資料用List接
    private List<Map<String, String>> mapList;

    // 用Holder類別 包住自製View (implements Listener 就有監聽方法可以用)
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewCardName, textViewCardInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCardName = (TextView)itemView.findViewById(R.id.textView_card_name);
            textViewCardInfo = (TextView)itemView.findViewById(R.id.textView_card_info);
            itemView.setOnClickListener(this);  // ?
        }
        // 卡片監聽
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), memberActivity.class);
            intent.putExtra("name", mapList.get(getAdapterPosition()).get("user"));
            view.getContext().startActivity(intent);    // 傳送至member分頁
        }
    }

    // Adapter建構子 將List附值
    public RecyclerViewAdapter(List<Map<String, String>> mapList) {
        this.mapList = mapList;
    }

    // Adapter類的super()會叫這方法return new ViewHolder(v); 引數(v)的內容要自己設定
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    // 有點像LOOP 將每張Card執行一次 可在這將List內容附給每張Card上的View
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] mapKey = new String[] { "userid", "user", "password", "username", "userbirth", "cellphone", "useremail", "useraddress" };
        String[] mapKeyChinese = new String[] { "編號ID: ", "帳號: ", "密碼: ", "姓名: ", "生日: ", "電話: ", "Email: ", "地址: " };
        Map<String, String> memberMap = mapList.get(position);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mapKey.length; i++) {
            stringBuilder.append(mapKeyChinese[i] + memberMap.get(mapKey[i]) + "\n");
        }
        holder.textViewCardName.setText(memberMap.get("user"));
        holder.textViewCardInfo.setText(stringBuilder.toString());
    }

    // 返回Card數量
    @Override
    public int getItemCount() {
        return mapList.size();
    }
}



