package cn.com.lws.androidknowlogion.recycledemo;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 设置分割线
 */
public class MeiziItemDecoration extends RecyclerView.ItemDecoration {
    private final int verticalSapceHeight;


    public MeiziItemDecoration(int verticalSapceHeight) {
        this.verticalSapceHeight = verticalSapceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() -1) {
            outRect.bottom = verticalSapceHeight;
        }
    }
}
