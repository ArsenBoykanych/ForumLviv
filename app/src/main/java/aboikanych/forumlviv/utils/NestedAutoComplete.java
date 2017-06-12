package aboikanych.forumlviv.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

public class NestedAutoComplete extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    public NestedAutoComplete(Context context) {
        super(context);
    }

    public NestedAutoComplete(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public NestedAutoComplete(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused && getAdapter() != null) {
            performFiltering(getText(), 0);
        }
    }

}