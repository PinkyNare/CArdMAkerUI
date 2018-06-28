package com.pakkalocal.cardmakerui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pakkalocal.cardmakerui.R;
import com.pakkalocal.cardmakerui.utilsdata.UtilsData;

/**
 * Created by 2117 on 6/15/2018.
 */

public class Venu_Fonts_Adapter extends RecyclerView.Adapter<Venu_Fonts_Adapter.CardViewHolder3> {
    String[] frmimgs;
    Context context;


    public Venu_Fonts_Adapter(Context context, String[] filepaths) {
        this.frmimgs = filepaths;
        this.context = context;
    }

    @Override
    public int getItemCount() {

        return frmimgs.length;
    }

    @Override
    public Venu_Fonts_Adapter.CardViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.font_gallery, parent, false);

        return new Venu_Fonts_Adapter.CardViewHolder3(rowView);
    }


    public void onBindViewHolder(Venu_Fonts_Adapter.CardViewHolder3 holder, int position) {

        holder.font_TextView.setText("ABC");
        holder.font_TextView.setTextSize(15);
        holder.font_TextView.setTypeface(Typeface.createFromAsset(context.getAssets(), frmimgs[position]));


    }


    public class CardViewHolder3 extends RecyclerView.ViewHolder {
        public TextView font_TextView;

        public CardViewHolder3(View itemView) {
            super(itemView);
            font_TextView = (TextView) itemView.findViewById(R.id.font_TextView);
        }
    }


}
