package com.pyramidions.bajugali.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.dataModels.MyApartmentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by User on 1/11/2017.
 */

public class MyApartmentRecyclerAdapter extends RecyclerView.Adapter<MyApartmentRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<MyApartmentModel> myApartmentModelList = new ArrayList<MyApartmentModel>();

    public MyApartmentRecyclerAdapter(Context context, ArrayList<MyApartmentModel> myApartmentModelList) {
        this.context = context;
        this.myApartmentModelList = myApartmentModelList;
    }

    @Override
    public MyApartmentRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.committerecycler,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyApartmentRecyclerAdapter.ViewHolder holder, int position) {
        MyApartmentModel apartmentModel = myApartmentModelList.get(position);
        holder.tv_Name.setText(apartmentModel.getName());
        holder.tv_Designation.setText(apartmentModel.getDesignation());
        try {
            Picasso.with(context).load(apartmentModel.getMemberimage()).into(holder.iv_apartmentProfile);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return myApartmentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_Name,tv_Designation;
        private  ImageView iv_apartmentProfile;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_Name = (TextView) itemView.findViewById(R.id.tv_Name);
            tv_Designation = (TextView) itemView.findViewById(R.id.tv_Designation);
            iv_apartmentProfile = (ImageView) itemView.findViewById(R.id.iv_apartmentProfile);
        }
    }
}
