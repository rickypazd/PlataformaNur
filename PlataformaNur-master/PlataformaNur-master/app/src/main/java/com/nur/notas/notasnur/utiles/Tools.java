package com.nur.notas.notasnur.utiles;

import android.content.Context;
import androidx.core.widget.NestedScrollView;
import android.view.View;
public class Tools {

    public static void nestedScrollTo(final NestedScrollView nested, final View targetView) {
        nested.post(new Runnable() {
            @Override
            public void run() {
                nested.scrollTo(500, targetView.getBottom());
            }
        });
    }

    public static void getDrawable(Context context, int drawableResId) {

    }

    public static void getDrawable(Context context, int drawableResId, int colorFilter) {

    }

}
