package com.fishing.namtran.fishingmanagerservice.adapters.original_sortable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fishing.namtran.fishingmanagerservice.R;
import com.fishing.namtran.fishingmanagerservice.adapters.TableFixHeaderAdapter;


/**
 * Created by miguel on 05/03/2016.
 */
public class OriginalFirstHeaderCellViewGroup extends FrameLayout
        implements
        TableFixHeaderAdapter.FirstHeaderBinder<ItemSortableCheckBox> {

    private Context context;
    public TextView textView;
    public View ll_order, vg_root;
    public ImageView iv_order_asc, iv_order_desc;
    public CheckBox cb_order_sections_asc;

    public OriginalFirstHeaderCellViewGroup(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OriginalFirstHeaderCellViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.text_sotable_checkbox_view_group, this, true);
        vg_root = findViewById(R.id.vg_root);
        textView = (TextView) findViewById(R.id.tv_text);
        ll_order = findViewById(R.id.ll_order);
        iv_order_asc = (ImageView) findViewById(R.id.iv_order_asc);
        iv_order_desc = (ImageView) findViewById(R.id.iv_order_desc);
        cb_order_sections_asc = (CheckBox) findViewById(R.id.cb_order_sections_asc);

    }

    @Override
    public void bindFirstHeader(final ItemSortableCheckBox item) {
        textView.setText(item.text.toUpperCase());
        textView.setTypeface(null, Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);
        vg_root.setBackgroundResource(R.drawable.cell_header_border_bottom_right_gray);
        cb_order_sections_asc.setVisibility(VISIBLE);
        cb_order_sections_asc.setChecked(item.orderSectionsAsc);
        cb_order_sections_asc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.orderSectionsAsc = isChecked;
            }
        });
        drawOrderArrows(item.order);
    }

    private void drawOrderArrows(int order) {
        ll_order.setVisibility(VISIBLE);
        iv_order_desc.setImageResource(order == -1 ? R.drawable.ic_arrow_drop_up_24dp : R.drawable.ic_arrow_drop_up_24dp_disabled);
        iv_order_asc.setImageResource(order == 1 ? R.drawable.ic_arrow_drop_down_24dp : R.drawable.ic_arrow_drop_down_24dp_disabled);
    }
}
