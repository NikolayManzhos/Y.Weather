package com.example.weather.presentation.main.suggest_screen;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.data.entities.autocomplete.Prediction;
import com.example.weather.presentation.di.ActivityContext;
import com.example.weather.presentation.di.scope.PerFragment;
import com.example.weather.utils.KeyboardUtils;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerFragment
public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.SuggestViewHolder> {

    private Context context;
    private KeyboardUtils keyboardUtils;
    private List<Prediction> predictions;
    private OnPlaceClickListener placeClickListener;

    @Inject
    SuggestAdapter(@ActivityContext Context context,
                   KeyboardUtils keyboardUtils) {
        this.context = context;
        this.keyboardUtils = keyboardUtils;
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
        vh.itemView.setOnClickListener(view -> {
            placeClickListener.placeClicked(predictions.get(vh.getAdapterPosition()).getPlaceId());
            keyboardUtils.hideKeyboard(context, view);
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(SuggestViewHolder holder, int position) {
        int aPosition = holder.getAdapterPosition();
        String place = predictions.get(aPosition).getDescription();
        SpannableString s = new SpannableString(place);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        s.setSpan(new ForegroundColorSpan(Color.RED),0,1,Spannable.SPAN_COMPOSING);
        s.setSpan(boldSpan, 0, 1, Spannable.SPAN_COMPOSING);
        holder.cityName.setText(s, TextView.BufferType.SPANNABLE);
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
