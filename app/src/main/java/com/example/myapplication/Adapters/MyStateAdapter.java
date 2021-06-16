package com.example.myapplication.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Callbacks.IRecyclerClickListener;
import com.example.myapplication.EventBus.SheetClick;
import com.example.myapplication.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyStateAdapter extends RecyclerView.Adapter<MyStateAdapter.MyViewHolder> {

    private Context context;
    private String[] strings;
    private int operation;

    public MyStateAdapter(Context context, String[] strings, int operation) {
        this.context = context;
        this.strings = strings;
        this.operation = operation;
    }

    @NonNull
    @Override
    public MyStateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyStateAdapter.MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_sheet, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyStateAdapter.MyViewHolder holder, int position) {
        holder.txt_state.setText(strings[position]);

        holder.setListener((view, pos) -> {
            EventBus.getDefault().postSticky(new SheetClick(true, operation, strings[pos]));
        });

    }

    @Override
    public int getItemCount() {
        return strings.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private Unbinder unbinder;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.txt_state)
        TextView txt_state;

        IRecyclerClickListener listener;

        public void setListener(IRecyclerClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClickListener(view, getAdapterPosition());
        }
    }
}
