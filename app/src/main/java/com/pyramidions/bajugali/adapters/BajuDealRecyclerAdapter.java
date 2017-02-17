package com.pyramidions.bajugali.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pyramidions.bajugali.R;
//import com.pyramidions.bajugali.activities.DetailedeventsActivity;
import com.pyramidions.bajugali.activities.DetailBajuDeals;
import com.pyramidions.bajugali.activities.DetailedeventsActivity;
import com.pyramidions.bajugali.dataModels.DealModel;
import com.pyramidions.bajugali.dataModels.OnGoingDataModel;

import java.util.ArrayList;

import static com.pyramidions.bajugali.R.id.txt_normalprice;


public class BajuDealRecyclerAdapter extends RecyclerView.Adapter<BajuDealRecyclerAdapter.ViewHolder> {
    ArrayList<DealModel> dealModel = new ArrayList<DealModel>();
    Context context;

    public BajuDealRecyclerAdapter(Context context, ArrayList<DealModel> dealModel) {
        this.context = context;
        this.dealModel = dealModel;
    }


    @Override
    public BajuDealRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment1_baju_deals, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(final BajuDealRecyclerAdapter.ViewHolder holder, int position) {
        DealModel dataModel = dealModel.get(position);

        holder.txt_dealname.setText(dataModel.getDealName());
        holder.txt_normalprice.setText(dataModel.getNormalPrice());
        holder.txt_offerprice.setText(dataModel.getOfferPrice());
        Glide.with(context).load(dataModel.getDealImage()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                holder.progressBar2BajuDeals.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                holder.progressBar2BajuDeals.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.item_image);

        if (position % 2 == 0) {
            holder.cd.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        } else {
            holder.cd.setCardBackgroundColor(context.getResources().getColor(R.color.colorGrey));
        }
    }

    @Override
    public int getItemCount() {
        return dealModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txt_dealname, txt_normalprice, txt_offerprice;
        private CardView cd;
        private ImageView item_image;
        private ProgressBar progressBar2BajuDeals;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_dealname = (TextView) itemView.findViewById(R.id.txt_dealname);
            txt_normalprice = (TextView) itemView.findViewById(R.id.txt_normalprice);
            txt_normalprice.setPaintFlags(txt_normalprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txt_offerprice = (TextView) itemView.findViewById(R.id.txt_offerprice);
            progressBar2BajuDeals = (ProgressBar) itemView.findViewById(R.id.progressBar2BajuDeals);
            item_image = (ImageView) itemView.findViewById(R.id.item_image);
            cd = (CardView) itemView.findViewById(R.id.cd);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            DealModel dataModel = dealModel.get(pos);
            Intent intent = new Intent(context, DetailBajuDeals.class);
            intent.putExtra("DealId", dataModel.getDealId());
            intent.putExtra("DealName", dataModel.getDealName());
            intent.putExtra("NormalPrice", dataModel.getNormalPrice());
            intent.putExtra("OfferPrice", dataModel.getOfferPrice());
            intent.putExtra("DealImage", dataModel.getDealImage());
            intent.putExtra("Description", dataModel.getDescription());
            intent.putExtra("amount", dataModel.getAmount());
            intent.putExtra("DealStartDate", dataModel.getDealStartDate());
            intent.putExtra("DealEndDate", dataModel.getDealEndDate());
            intent.putExtra("DealLocation", dataModel.getDealLocation());
            // intent.putExtra("NoOfRedemption",dataModel.getNoOfRedemption());
            context.startActivity(intent);
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
    }
}
