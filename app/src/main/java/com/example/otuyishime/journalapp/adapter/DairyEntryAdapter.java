package com.example.otuyishime.journalapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.otuyishime.journalapp.R;
import com.example.otuyishime.journalapp.model.DairyEntry;
import com.example.otuyishime.journalapp.model.DateConverter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class DairyEntryAdapter extends RecyclerView.Adapter<DairyEntryAdapter.MyViewHolder> {
    private static final String DATE_FORMAT = "yyy-MM-dd";
    private List<DairyEntry> mDairyEntry;

    private Context mContext;

    final private ItemClickListener mItemClickListener;
    final private ItemLongClickListener mItemLongClickListener;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


//    public DairyEntryAdapter(Context context, List<DairyEntry> dairy) {
//        this.mDairyEntry = dairy;
//        this.mInflater = LayoutInflater.from(context);
//        mItemClickListener = null;
//    }

    public DairyEntryAdapter(Context context, ItemClickListener listener,ItemLongClickListener itemLongClickListener) {
        mContext = context;
        mItemClickListener = listener;
        mItemLongClickListener=itemLongClickListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.diary_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DairyEntry currentDairy = mDairyEntry.get(position);
        holder.setEntry(currentDairy);
    }

    @Override
    public int getItemCount() {
        if (mDairyEntry == null) {
            return 0;
        }
        return mDairyEntry.size();
    }

    public void setEntries(List<DairyEntry> dairyEntries) {
        mDairyEntry = dairyEntries;
        notifyDataSetChanged();
    }

    public List<DairyEntry> getEntries() {
        return mDairyEntry;
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public interface ItemLongClickListener{
        void onItemLongClickListener(int itemId);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        TextView dairy_entry_title;
        TextView dairy_entry_date;
//        TextView dairy_entry_body;

        public MyViewHolder(View itemView) {
            super(itemView);
            dairy_entry_title = itemView.findViewById(R.id.dairy_title);
            dairy_entry_date = itemView.findViewById(R.id.dairy_date);
//            dairy_entry_body = itemView.findViewById(R.id.dairy_body);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        void setEntry(DairyEntry entry) {
            this.dairy_entry_title.setText(entry.getEntry_title());
            this.dairy_entry_date.setText(dateFormat.format(entry.getEntry_date()));
//            this.dairy_entry_body.setText(entry.getEntry_body());
        }


        public void onClick(View view) {
            int elementId = mDairyEntry.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }


        public boolean onLongClick(View view) {
            int elementId=mDairyEntry.get(getAdapterPosition()).getId();
            mItemLongClickListener.onItemLongClickListener(elementId);
            return true;
        }
    }
}
