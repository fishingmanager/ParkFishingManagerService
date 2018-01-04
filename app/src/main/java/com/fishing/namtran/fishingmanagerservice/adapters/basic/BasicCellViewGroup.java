package com.fishing.namtran.fishingmanagerservice.adapters.basic;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fishing.namtran.fishingmanagerservice.R;
import com.fishing.namtran.fishingmanagerservice.adapters.TableFixHeaderAdapter;

import java.util.List;


/**
 * Created by miguel on 09/02/2016.
 */
public class BasicCellViewGroup extends FrameLayout
        implements
        TableFixHeaderAdapter.FirstHeaderBinder<String>,
        TableFixHeaderAdapter.HeaderBinder<String>,
        TableFixHeaderAdapter.FirstBodyBinder<List<String>>,
        TableFixHeaderAdapter.BodyBinder<List<String>>,
        TableFixHeaderAdapter.SectionBinder<List<String>> {

    private Context context;
    public TextView textView;
    public View vg_root;

    public BasicCellViewGroup(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BasicCellViewGroup(Context context, AttributeSet attrs) {
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
    public void bindFirstHeader(String headerName) {
        textView.setText(headerName);
        textView.setTypeface(null, Typeface.BOLD);
        vg_root.setBackgroundResource(R.drawable.cell_lightgray_border_bottom_right_gray);
    }

    @Override
    public void bindHeader(String headerName, int column) {
        textView.setText(headerName);
        textView.setTypeface(null, Typeface.BOLD);
        vg_root.setBackgroundResource(R.drawable.cell_lightgray_border_bottom_right_gray);
    }

    @Override
    public void bindFirstBody(List<String> items, int row) {
        textView.setText(items.get(0));
        textView.setTypeface(null, Typeface.NORMAL);
        vg_root.setBackgroundResource(R.drawable.cell_lightgray_border_bottom_right_gray);
    }

    @Override
    public void bindBody(List<String> items, int row, int column) {
        textView.setText(items.get(column + 1));
        textView.setTypeface(null, Typeface.NORMAL);
        vg_root.setBackgroundResource(R.drawable.cell_lightgray_border_bottom_right_gray);
    }

    @Override
    public void bindSection(List<String> item, int row, int column) {
        textView.setText(column == 0 ? "Section" : "");
        vg_root.setBackgroundResource(R.drawable.cell_lightgray_border_bottom_right_gray);
    }
}
