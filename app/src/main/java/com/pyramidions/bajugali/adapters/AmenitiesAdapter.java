package com.pyramidions.bajugali.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pyramidions.bajugali.R;
import com.pyramidions.bajugali.activities.Amenities;
import com.pyramidions.bajugali.activities.AmenitiesDetails;
import com.pyramidions.bajugali.dataModels.AmenitiesModel;

import java.util.ArrayList;



public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.ViewHolder> {
    private ArrayList<AmenitiesModel> amenitiesModels = new ArrayList<AmenitiesModel>();
    Context context;

    public AmenitiesAdapter(Context context, ArrayList<AmenitiesModel> amenitiesModelList) {
        this.context = context;
        this.amenitiesModels = amenitiesModelList;
    }

    @Override
    public AmenitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.amenities_list, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(AmenitiesAdapter.ViewHolder holder, int position) {

        AmenitiesModel amenitiesModel = amenitiesModels.get(position);
        holder.amenitiesName.setText(amenitiesModel.getAmenityname());
    //    holder.amenitiesDescription.setText(amenitiesModel.getDescription());
        if (position % 2==0){
            holder.amenitiesCardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        }else {
            holder.amenitiesCardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorGrey));
        }

    }

    @Override
    public int getItemCount() {
        return amenitiesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView amenitiesName,amenitiesDescription;
        private CardView amenitiesCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            amenitiesName = (TextView) itemView.findViewById(R.id.amenitiesName);
            amenitiesDescription = (TextView) itemView.findViewById(R.id.amenitiesDescription);
            amenitiesCardView = (CardView) itemView.findViewById(R.id.amenitiesCardView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            AmenitiesModel amenitiesModel = amenitiesModels.get(pos);
            Intent intent = new Intent(context, AmenitiesDetails.class);
            intent.putExtra("Amenityname",amenitiesModel.getAmenityname());
            intent.putExtra("Description",amenitiesModel.getDescription());
            intent.putExtra("Firstshift",amenitiesModel.getFirstshift());
            intent.putExtra("Seconfdshift",amenitiesModel.getSeconfdshift());
            intent.putExtra("Rulesandregulations",amenitiesModel.getRulesandregulations());
            intent.putExtra("Price",amenitiesModel.getPrice());
            intent.putExtra("WorkingDays",amenitiesModel.getWorkingDays());
            intent.putExtra("booking",amenitiesModel.getBooking());
            context.startActivity(intent);
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);


        }
    }
}
