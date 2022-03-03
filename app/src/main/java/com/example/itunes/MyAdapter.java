package com.example.itunes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems,Context context){
        this.listItems=listItems;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 1:return new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item, parent, false));
            case 2:return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.line_divider, parent, false));
        }
        return new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListItem listItem=listItems.get(position);
        switch (holder.getItemViewType()) {
            case 1:
                ViewHolder1 viewHolder1 = (ViewHolder1)holder;
                viewHolder1.textViewHead.setText("Artist Name"+listItem.getHead());
                viewHolder1.textViewDesc.setText("Artist Id"+listItem.getDesc());
                break;

            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2)holder;
                viewHolder2.textSectionHeader.setText(listItem.getHead());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return listItems.get(position).getDesc()==null ? 2 : 1;
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewHead;
        public TextView textViewDesc;
        public ViewHolder1(@NonNull View itemView) {
            super(itemView);

            textViewHead=(TextView)itemView.findViewById(R.id.textViewHead);
            textViewDesc=(TextView)itemView.findViewById(R.id.textViewDesc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            String id=listItems.get(getAdapterPosition()).getId();
            String value=listItems.get(getAdapterPosition()).getValue();

            Intent intent = new Intent(context, DetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra("id",id);
            intent.putExtra("value",value);

            System.out.println("This is id:"+id);
            System.out.println("This is value:"+value);

            context.startActivity(intent);

        }

    }
    public void detailInfo(String value,String id){
         RequestQueue queue= Volley.newRequestQueue(context);


    }
    public class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView textSectionHeader;

        public ViewHolder2(View itemView){
            super(itemView);
            textSectionHeader=(TextView)itemView.findViewById(R.id.textSectionHeader);
        }
    }
}

