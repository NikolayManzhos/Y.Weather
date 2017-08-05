package com.example.weather.presentation.main.suggest_screen;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.data.entities.autocomplete.Prediction;
import com.example.weather.presentation.di.ActivityContext;
import com.example.weather.presentation.di.scope.PerFragment;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerFragment
public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.SuggestViewHolder> {

    private Context context;
    private List<Prediction> predictions;
    private OnPlaceClickListener placeClickListener;

    @Inject
    public SuggestAdapter(@ActivityContext Context context) {
        this.context = context;
        predictions = new LinkedList<>();
    }

    static class SuggestViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cityName)
        TextView cityName;

        SuggestViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public SuggestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SuggestViewHolder vh = new SuggestViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_suggest, parent, false));
        vh.itemView.setOnClickListener(view -> placeClickListener.placeClicked(predictions.get(vh.getAdapterPosition()).getPlaceId()) );
        return vh;
    }

    @Override
    public void onBindViewHolder(SuggestViewHolder holder, int position) {
        int aPosition = holder.getAdapterPosition();
        holder.cityName.setText(predictions.get(aPosition).getDescription());
    }

    @Override
    public int getItemCount() {
        return predictions.size();
    }

    public void setData(List<Prediction> predictions) {
        this.predictions.clear();
        this.predictions.addAll(predictions);
        notifyDataSetChanged();
    }

    void setOnPaceClickListener(OnPlaceClickListener placeClickListener) {
        this.placeClickListener = placeClickListener;
    }

    interface OnPlaceClickListener {
        void placeClicked(String placeId);
    }
}
