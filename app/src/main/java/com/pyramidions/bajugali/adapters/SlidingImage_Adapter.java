package com.pyramidions.bajugali.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.dataModels.ImageModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SONU on 29/08/15.
 */
public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<ImageModel> IMAGES = new ArrayList<ImageModel>();
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList<ImageModel> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        ImageModel dataModels = IMAGES.get(position);
        try {
            Picasso.with(context).load(dataModels.getSlideImage()).into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
