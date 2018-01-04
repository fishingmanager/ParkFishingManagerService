package com.fishing.namtran.fishingmanagerservice.adapters.basic;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;

import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;
import com.fishing.namtran.fishingmanagerservice.R;
import com.fishing.namtran.fishingmanagerservice.adapters.TableFixHeaderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miguel on 12/02/2016.
 */
public class BasicTableFixHeader {
    private Context context;


    public BasicTableFixHeader(Context context) {
        this.context = context;
    }

    public BaseTableAdapter getInstance() {
        BasicTableFixHeaderAdapter adapter = new BasicTableFixHeaderAdapter(context);
        List<List<String>> body = getBody();

        adapter.setFirstHeader("FH");
        adapter.setHeader(getHeader());
        adapter.setFirstBody(body);
        adapter.setBody(body);
        adapter.setSection(body);

        setListeners(adapter);

        return adapter;
    }

    private void setListeners(BasicTableFixHeaderAdapter adapter) {
        TableFixHeaderAdapter.ClickListener<String, BasicCellViewGroup> clickListenerHeader = new TableFixHeaderAdapter.ClickListener<String, BasicCellViewGroup>() {
            @Override
            public void onClickItem(String s, BasicCellViewGroup viewGroup, int row, int column) {
                viewGroup.vg_root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                Snackbar.make(viewGroup, "Click on " + viewGroup.textView.getText() + " (" + row + "," + column + ")", Snackbar.LENGTH_SHORT).show();
            }
        };
        TableFixHeaderAdapter.ClickListener<List<String>, BasicCellViewGroup> clickListenerBody = new TableFixHeaderAdapter.ClickListener<List<String>, BasicCellViewGroup>() {
            @Override
            public void onClickItem(List<String> array, BasicCellViewGroup viewGroup, int row, int column) {
                viewGroup.vg_root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorYellow));
                Snackbar.make(viewGroup, "Click on " + viewGroup.textView.getText() + " (" + row + "," + column + ")", Snackbar.LENGTH_SHORT).show();
            }
        };

        TableFixHeaderAdapter.LongClickListener<String, BasicCellViewGroup> longClickListenerHeader = new TableFixHeaderAdapter.LongClickListener<String, BasicCellViewGroup >() {
            @Override
            public void onLongClickItem(String s, BasicCellViewGroup viewGroup, int row, int column) {
                viewGroup.vg_root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                Snackbar.make(viewGroup, "LongClick on " + viewGroup.textView.getText() + " (" + row + "," + column + ")", Snackbar.LENGTH_SHORT).show();

            }
        };

        TableFixHeaderAdapter.LongClickListener<List<String>, BasicCellViewGroup> longClickListenerBody = new TableFixHeaderAdapter.LongClickListener<List<String>, BasicCellViewGroup >() {
            @Override
            public void onLongClickItem(List<String> array, BasicCellViewGroup viewGroup, int row, int column) {
                viewGroup.vg_root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlue));
                Snackbar.make(viewGroup, "LongClick on " + viewGroup.textView.getText() + " (" + row + "," + column + ")", Snackbar.LENGTH_SHORT).show();

            }
        };

        adapter.setClickListenerFirstHeader(clickListenerHeader);
        adapter.setLongClickListenerFirstHeader(longClickListenerHeader);
        adapter.setClickListenerHeader(clickListenerHeader);
        adapter.setLongClickListenerHeader(longClickListenerHeader);
        adapter.setClickListenerFirstBody(clickListenerBody);
        adapter.setLongClickListenerFirstBody(longClickListenerBody);
        adapter.setClickListenerBody(clickListenerBody);
        adapter.setLongClickListenerBody(longClickListenerBody);
        adapter.setClickListenerSection(clickListenerBody);
        adapter.setLongClickListenerSection(longClickListenerBody);
    }

    private List<String> getHeader() {
        List<String> header = new ArrayList<>();

        for (int i = 0; i < 20; i++)
            header.add("H " + (i + 1));

        return header;
    }

    private List<List<String>> getBody() {
        List<List<String>> rows = new ArrayList<>();

        for (int row = 1; row <= 100; row++) {
            List<String>  cols = new ArrayList<>();

            for (int col = 0; col < 30; col++) {
                String type = col == 0 ? "FB" : "B";
                cols.add(type + " (" + row + ", " + col + ")");
            }

            rows.add(cols);
        }

        return rows;
    }
}
