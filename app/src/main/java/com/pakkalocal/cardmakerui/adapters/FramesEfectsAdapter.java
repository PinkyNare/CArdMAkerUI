package com.pakkalocal.cardmakerui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pakkalocal.cardmakerui.R;
import com.pakkalocal.cardmakerui.utilsdata.UtilsData;

/**
 * Created by 2117 on 6/13/2018.
 */

public class FramesEfectsAdapter extends RecyclerView.Adapter<FramesEfectsAdapter.CardViewHolder3> {
    int[] frmimgs;
    Context context;
    int chk_ver_hor = 0;

    public FramesEfectsAdapter(Context context, int[] filepaths) {
        this.frmimgs = filepaths;
        this.context = context;
    }

    @Override
    public int getItemCount() {

        return frmimgs.length;
    }

    @Override
    public CardViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_cardviewfils, parent, false);

        return new CardViewHolder3(rowView);
    }


    public void onBindViewHolder(CardViewHolder3 holder, int position) {

        switch (chk_ver_hor) {

            case 1:
                // for horizontal images
                holder.ivPic.getLayoutParams().height = (int) (UtilsData.scheight_u / 5.5);
                holder.ivPic.getLayoutParams().width = UtilsData.scwidth_u / 2;
                break;
            case 2:
//                for vertical devices
                holder.ivPic.getLayoutParams().height = (int) (UtilsData.scheight_u / 2.7);
                holder.ivPic.getLayoutParams().width = UtilsData.scwidth_u / 2;
                break;
            case 3:
                holder.ivPic.getLayoutParams().height = (int) (UtilsData.scheight_u / 4.3);
                holder.ivPic.getLayoutParams().width = (int) (UtilsData.scwidth_u / 3.5);

                break;

            case 4:
                holder.ivPic.getLayoutParams().height = (int) (UtilsData.scwidth_u / 5);
                holder.ivPic.getLayoutParams().width = (int) (UtilsData.scwidth_u / 5);

                break;

            default:
                holder.ivPic.getLayoutParams().height = (int) (UtilsData.scheight_u / 5.5);
                holder.ivPic.getLayoutParams().width = UtilsData.scwidth_u / 2;
                break;
        }


        Glide.with(context).load(frmimgs[position])
                .placeholder(R.drawable.ic_stub).error(R.mipmap.ic_launcher)
                .into(holder.ivPic);


    }

    public void setTypeFrames(int chk, int[] frams_allverdata) {
        chk_ver_hor = chk;
        this.frmimgs = frams_allverdata;
    }

    public class CardViewHolder3 extends RecyclerView.ViewHolder {
        public ImageView ivPic;

        public CardViewHolder3(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.ivPic);
        }
    }

}

