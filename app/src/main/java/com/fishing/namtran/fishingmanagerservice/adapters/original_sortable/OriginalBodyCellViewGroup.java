package com.fishing.namtran.fishingmanagerservice.adapters.original_sortable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fishing.namtran.fishingmanagerservice.R;
import com.fishing.namtran.fishingmanagerservice.adapters.TableFixHeaderAdapter;


/**
 * Created by miguel on 09/02/2016.
 */
public class OriginalBodyCellViewGroup extends FrameLayout
        implements
        TableFixHeaderAdapter.BodyBinder<NexusWithImage> {

    private Context context;
    public TextView textView;
    public View vg_root;

    public OriginalBodyCellViewGroup(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OriginalBodyCellViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.text_view_group, this, true);
        textView = (TextView) findViewById(R.id.tv_text);
        vg_root = findViewById(R.id.vg_root);
    }

    @Override
    public void bindBody(NexusWithImage item, int row, int column) {
        textView.setText(item.data[column + 1]);
        textView.setTypeface(null, Typeface.NORMAL);
        textView.setGravity(Gravity.CENTER);
        vg_root.setBackgroundResource(row % 2 == 0 ? R.drawable.cell_lightgray_border_bottom_right_gray : R.drawable.cell_white_border_bottom_right_gray);
    }
}
