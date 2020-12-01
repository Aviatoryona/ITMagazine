package com.magazineapp.main;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarginDecor extends RecyclerView.ItemDecoration {

    int dim;

    public MarginDecor(int dim) {
        this.dim = dim;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom=dim;
        outRect.top=dim;
    }
}
