package com.fishing.namtran.fishingmanagerservice.adapters.original;

import android.content.Context;

import com.fishing.namtran.fishingmanagerservice.R;
import com.fishing.namtran.fishingmanagerservice.adapters.TableFixHeaderAdapter;

import java.util.Arrays;
import java.util.List;


/**
 * Created by miguel on 11/02/2016.
 */
public class OriginalTableFixHeaderAdapter extends TableFixHeaderAdapter<
        String, OriginalCellViewGroup,
        String, OriginalCellViewGroup,
        Nexus,
        OriginalCellViewGroup,
        OriginalCellViewGroup,
        OriginalCellViewGroup> {
    private Context context;

    public OriginalTableFixHeaderAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected OriginalCellViewGroup inflateFirstHeader() {
        return new OriginalCellViewGroup(context);
    }

    @Override
    protected OriginalCellViewGroup inflateHeader() {
        return new OriginalCellViewGroup(context);
    }

    @Override
    protected OriginalCellViewGroup inflateFirstBody() {
        return new OriginalCellViewGroup(context);
    }

    @Override
    protected OriginalCellViewGroup inflateBody() {
        return new OriginalCellViewGroup(context);
    }

    @Override
    protected OriginalCellViewGroup inflateSection() {
        return new OriginalCellViewGroup(context);
    }

    @Override
    protected List<Integer> getHeaderWidths() {
        Integer[] witdhs = {
                (int) context.getResources().getDimension(R.dimen._100dp),
                (int) context.getResources().getDimension(R.dimen._120dp),
                (int) context.getResources().getDimension(R.dimen._80dp),
                (int) context.getResources().getDimension(R.dimen._60dp),
                (int) context.getResources().getDimension(R.dimen._60dp),
                (int) context.getResources().getDimension(R.dimen._70dp),
                (int) context.getResources().getDimension(R.dimen._80dp),
                (int) context.getResources().getDimension(R.dimen._80dp),
                (int) context.getResources().getDimension(R.dimen._120dp),
                (int) context.getResources().getDimension(R.dimen._200dp)
        };

        return Arrays.asList(witdhs);
    }

    @Override
    protected int getHeaderHeight() {
        return (int) context.getResources().getDimension(R.dimen._35dp);
    }

    @Override
    protected int getSectionHeight() {
        return (int) context.getResources().getDimension(R.dimen._25dp);
    }

    @Override
    protected int getBodyHeight() {
        return (int) context.getResources().getDimension(R.dimen._45dp);
    }

    @Override
    protected boolean isSection(List<Nexus> items, int row) {
        return items.get(row).isSection();
    }
}
