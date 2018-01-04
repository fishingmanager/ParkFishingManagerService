package com.fishing.namtran.fishingmanagerservice.adapters.original_sortable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
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
public class OriginalSectionCellViewGroup extends FrameLayout
        implements
        TableFixHeaderAdapter.SectionBinder<NexusWithImage> {

    private Context context;
    public TextView textView;
    public View vg_root;
    public ImageView iv_image;

    public OriginalSectionCellViewGroup(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OriginalSectionCellViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.text_image_view_group, this, true);
        vg_root = findViewById(R.id.vg_root);
        textView = (TextView) findViewById(R.id.tv_text);
        iv_image = (ImageView) findViewById(R.id.iv_image);

    }

    @Override
    public void bindSection(NexusWithImage item, int row, int column) {
        iv_image.setVisibility(column == 0 ? GONE : VISIBLE);
        iv_image.setImageResource(column > 0 ? item.resImages.get(column - 1) : 0);
        textView.setVisibility(column == 0 ? VISIBLE : GONE);
        textView.setText(column == 0 ? item.type.toUpperCase() : "");
        textView.setTypeface(null, Typeface.BOLD);
        vg_root.setBackgroundResource(R.drawable.cell_blue_border_bottom_right_gray);
    }
}
