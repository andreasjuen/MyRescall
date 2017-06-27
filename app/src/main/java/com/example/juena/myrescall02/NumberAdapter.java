package com.example.juena.myrescall02;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juena.myrescall02.data.NumberContract;

/**
 * Created by juena on 25.06.2017.
 */

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumberViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    final private ListItemClickListener mOnClickListener;

    public NumberAdapter(Context context, Cursor cursor, ListItemClickListener listener)
    {
        this.mContext = context;
        this.mCursor = cursor;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener
    {
        void onListItemClick(int clickedItemIndex, TextView ps, TextView kw, TextView datum, TextView woche);
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.number_list_item, parent, false);
        return new  NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))
            return;

        String ps = mCursor.getString(mCursor.getColumnIndex(NumberContract.NumberEntry.COLUMN_ps));
        String kw = mCursor.getString(mCursor.getColumnIndex(NumberContract.NumberEntry.COLUMN_kw));
        String datum = mCursor.getString(mCursor.getColumnIndex(NumberContract.NumberEntry.COLUMN_datum));
        String woche = mCursor.getString(mCursor.getColumnIndex(NumberContract.NumberEntry.COLUMN_woche));
        long id = mCursor.getLong(mCursor.getColumnIndex(NumberContract.NumberEntry._ID));

        holder.psTextView.setText(ps);
        holder.kwTextView.setText(kw);
        holder.datumTextView.setText(datum);
        holder.wocheTextView.setText(woche);
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView psTextView;
        TextView kwTextView;
        TextView datumTextView;
        TextView wocheTextView;

        public NumberViewHolder(View itemView) {
            super(itemView);

            psTextView = (TextView) itemView.findViewById(R.id.ps_text_view);
            kwTextView = (TextView) itemView.findViewById(R.id.kw_text_view);
            datumTextView = (TextView) itemView.findViewById(R.id.datum_text_view);
            wocheTextView = (TextView) itemView.findViewById(R.id.woche_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition, psTextView, kwTextView, datumTextView, wocheTextView);
        }
    }

    public void swapCursor(Cursor newCursor) {

        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

}