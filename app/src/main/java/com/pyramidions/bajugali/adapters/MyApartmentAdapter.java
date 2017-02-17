package com.pyramidions.bajugali.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.dataModels.MyApartmentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by User on 1/10/2017.
 */

public class MyApartmentAdapter extends PagerAdapter {

    Context context;
    ArrayList<MyApartmentModel> myApartmentModelList = new ArrayList<MyApartmentModel>();

    public MyApartmentAdapter(Context context, ArrayList<MyApartmentModel> myApartmentModelList) {
        this.context = context;
        this.myApartmentModelList = myApartmentModelList;
    }

    @Override
    public int getCount() {
        return myApartmentModelList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.mycommitee,container,false);
        final TextView tv_Name = (TextView) view.findViewById(R.id.tv_Name);
        final TextView tv_Name2 = (TextView) view.findViewById(R.id.tv_Name2);
        final TextView tv_Designation = (TextView) view.findViewById(R.id.tv_Designation);
        final TextView tv_Designation2 = (TextView) view.findViewById(R.id.tv_Designation2);
        final ImageView iv_apartmentProfile = (ImageView) view.findViewById(R.id.iv_apartmentProfile);
        final ImageView iv_apartmentProfile2 = (ImageView) view.findViewById(R.id.iv_apartmentProfile2);
        MyApartmentModel apartmentModel = myApartmentModelList.get(position);
        tv_Name.setText(apartmentModel.getName());
        tv_Name2.setText(apartmentModel.getName());
        tv_Designation.setText(apartmentModel.getDesignation());
        tv_Designation2.setText(apartmentModel.getDesignation());
        try{
            Picasso.with(context).load(apartmentModel.getMemberimage()).into(iv_apartmentProfile);
            Picasso.with(context).load(apartmentModel.getMemberimage()).into(iv_apartmentProfile2);

        }catch (Exception e){

        }
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
