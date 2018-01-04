package com.fishing.namtran.fishingmanagerservice.adapters.original_sortable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fishing.namtran.fishingmanagerservice.R;
import com.fishing.namtran.fishingmanagerservice.adapters.TableFixHeaderAdapter;


/**
 * Created by miguel on 05/03/2016.
 */
public class OriginalHeaderCellViewGroup extends FrameLayout
        implements
        TableFixHeaderAdapter.HeaderBinder<ItemSortable> {

    private Context context;
    public TextView textView;
    public View ll_order, vg_root;
    public ImageView iv_order_asc, iv_order_desc;

    public OriginalHeaderCellViewGroup(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OriginalHeaderCellViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.text_sotable_view_group, this, true);
        vg_root = findViewById(R.id.vg_root);
        textView = (TextView) findViewById(R.id.tv_text);
        ll_order = findViewById(R.id.ll_order);
        iv_order_asc = (ImageView) findViewById(R.id.iv_order_asc);
        iv_order_desc = (ImageView) findViewById(R.id.iv_order_desc);

    }

    @Override
    public void bindHeader(ItemSortable item, int column) {
        textView.setText(item.text.toUpperCase());
        textView.setTypeface(null, Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);
        vg_root.setBackgroundResource(R.drawable.cell_header_border_bottom_right_gray);
        drawOrderArrows(item.order);
    }

    private void drawOrderArrows(int order) {
        ll_order.setVisibility(VISIBLE);
        iv_order_desc.setImageResource(order == -1 ? R.drawable.ic_arrow_drop_up_24dp : R.drawable.ic_arrow_drop_up_24dp_disabled);
        iv_order_asc.setImageResource(order == 1 ? R.drawable.ic_arrow_drop_down_24dp : R.drawable.ic_arrow_drop_down_24dp_disabled);
    }
}
