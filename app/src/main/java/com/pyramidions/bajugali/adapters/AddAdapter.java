package com.pyramidions.bajugali.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.pyramidions.bajugali.activities.SubmitViewAdd;
import com.pyramidions.bajugali.activities.SubmitViewAddDetails;
import com.pyramidions.bajugali.dataModels.AddModel;

import java.util.ArrayList;

/**
 * Created by User on 1/24/2017.
 */

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> {
    Context context;
    ArrayList<AddModel> addModelList = new ArrayList<AddModel>();
    public AddAdapter(Context context, ArrayList<AddModel> addModelList) {
        this.context = context;
        this.addModelList = addModelList;
    }

    @Override
    public AddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowadvertisement,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AddAdapter.ViewHolder holder, int position) {
        AddModel addModel = addModelList.get(position);
        holder.tv_title_submitViewAdd.setText(addModel.getAdtitle());
        holder.tv_category_submitViewAdd.setText(addModel.getCategory());
        holder.tv_status_submitViewAdd.setText(addModel.getStatus());
        try {
            Glide.with(context).load(addModel.getAdimage()).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    holder.progressGlide.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    holder.progressGlide.setVisibility(View.GONE);
                    return false;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return addModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_title_submitViewAdd,tv_category_submitViewAdd,tv_status_submitViewAdd;
        private ImageView iv_submitViewAdd;
        private ProgressBar progressGlide;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title_submitViewAdd = (TextView) itemView.findViewById(R.id.tv_title_submitViewAdd);
            tv_category_submitViewAdd = (TextView) itemView.findViewById(R.id.tv_category_submitViewAdd);
            tv_status_submitViewAdd = (TextView) itemView.findViewById(R.id.tv_status_submitViewAdd);
            iv_submitViewAdd = (ImageView) itemView.findViewById(R.id.iv_submitViewAdd);
            progressGlide = (ProgressBar) itemView.findViewById(R.id.progressGlide);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AddModel addModel = addModelList.get(getAdapterPosition());
            Intent intent = new Intent(context,SubmitViewAddDetails.class);
            intent.putExtra("getAdtitle",addModel.getAdtitle());
            intent.putExtra("getAdimage",addModel.getAdimage());
            intent.putExtra("getCategory",addModel.getCategory());
            intent.putExtra("getDescription",addModel.getDescription());
            intent.putExtra("getStatus",addModel.getStatus());
            intent.putExtra("getPrice",addModel.getPrice());
            intent.putExtra("getAdStartDate",addModel.getAdStartDate());
            intent.putExtra("getAdEndDate",addModel.getAdEndDate());
            intent.putExtra("getDisplaycontactnumber",addModel.getDisplaycontactnumber());
            context.startActivity(intent);
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);



        }
    }
}
