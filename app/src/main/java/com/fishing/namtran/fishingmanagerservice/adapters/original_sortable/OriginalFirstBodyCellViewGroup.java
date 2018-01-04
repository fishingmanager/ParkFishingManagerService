package com.fishing.namtran.fishingmanagerservice.adapters.original_sortable;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fishing.namtran.fishingmanagerservice.R;
import com.fishing.namtran.fishingmanagerservice.adapters.TableFixHeaderAdapter;


/**
 * Created by miguel on 06/03/2016.
 */
public class OriginalFirstBodyCellViewGroup extends FrameLayout
        implements
        TableFixHeaderAdapter.FirstBodyBinder<NexusWithImage> {

    private Context context;
    public TextView textView;
    public View vg_root;

    public OriginalFirstBodyCellViewGroup(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OriginalFirstBodyCellViewGroup(Context context, AttributeSet attrs) {
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
    public void bindFirstBody(NexusWithImage item, int row) {
        textView.setText(item.data[0]);
        textView.setTypeface(null, Typeface.BOLD_ITALIC);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        vg_root.setBackgroundResource(R.drawable.cell_gray_border_bottom_right_gray);
    }
}
