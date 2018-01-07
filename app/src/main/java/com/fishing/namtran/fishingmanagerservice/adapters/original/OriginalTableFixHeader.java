package com.fishing.namtran.fishingmanagerservice.adapters.original;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fishing.namtran.fishingmanagerservice.ChangeFishingActivity;
import com.fishing.namtran.fishingmanagerservice.R;
import com.fishing.namtran.fishingmanagerservice.UpdateCustomerActivity;
import com.fishing.namtran.fishingmanagerservice.Utils;
import com.fishing.namtran.fishingmanagerservice.dbconnection.FishingManager;
import com.fishing.namtran.fishingmanagerservice.dbconnection.Fishings;
import com.fishing.namtran.fishingmanagerservice.dbconnection.UserManager;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;
import com.fishing.namtran.fishingmanagerservice.adapters.TableFixHeaderAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by miguel on 12/02/2016.
 */
public class OriginalTableFixHeader {
    private Context context;
    private int totalItems;
    private int totalColumn = 10;

    public OriginalTableFixHeader(Context context) {
        this.context = context;
    }

    public BaseTableAdapter getInstance() {
        OriginalTableFixHeaderAdapter adapter = new OriginalTableFixHeaderAdapter(context);
        List<Nexus> body = getBody();

        adapter.setFirstHeader(context.getString(R.string.order));
        adapter.setHeader(getHeader());
        adapter.setFirstBody(body);
        adapter.setBody(body);
        adapter.setSection(body);

        setListeners(adapter);
        //onLoad(adapter);
        return adapter;
    }

    private void onLoad(final OriginalTableFixHeaderAdapter adapter)
    {
        Nexus ne = adapter.getBody().get(1);
        //Utils.Alert(context, ne.data[1]);
        //adapter.inflateBody().textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));

        TableFixHeaderAdapter.OnLoad<Nexus, OriginalCellViewGroup> load = new TableFixHeaderAdapter.OnLoad<Nexus, OriginalCellViewGroup>()
        {
            @Override
            public void onLoad(Nexus item, OriginalCellViewGroup viewGroup)
            {
                //viewGroup.textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
                Utils.Alert(context, item.data[1]);
            }
        };
    }

    private void setListeners(final OriginalTableFixHeaderAdapter adapter) {
        TableFixHeaderAdapter.ClickListener<String, OriginalCellViewGroup> clickListenerHeader = new TableFixHeaderAdapter.ClickListener<String, OriginalCellViewGroup>() {
            @Override
            public void onClickItem(String s, OriginalCellViewGroup viewGroup, int row, int column) {
                Snackbar.make(viewGroup, "Click on " + s + " (" + row + "," + column + ")", Snackbar.LENGTH_SHORT).show();
            }
        };

        TableFixHeaderAdapter.ClickListener<Nexus, OriginalCellViewGroup> clickListenerBody = new TableFixHeaderAdapter.ClickListener<Nexus, OriginalCellViewGroup>() {
            @Override
            public void onClickItem(Nexus item, OriginalCellViewGroup viewGroup, int row, int column) {
                //Snackbar.make(viewGroup, "Click on " + item.data[column + 1] + " (" + row + "," + column + ")", Snackbar.LENGTH_SHORT).show();
                //viewGroup.vg_root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorYellow));

                if(totalItems != row) {
                    if(item.data[4] == "")
                    {
                        Utils.Redirect(context, UpdateCustomerActivity.class, "fishingId", item.data[1]);
                    }
                    else
                    {
                        callLoginDialog(ChangeFishingActivity.class, item.data[1]);
                    }
                }
            }
        };

        TableFixHeaderAdapter.LongClickListener<Nexus, OriginalCellViewGroup> longClickListenerBody = new TableFixHeaderAdapter.LongClickListener<Nexus, OriginalCellViewGroup >() {
            @Override
            public void onLongClickItem(Nexus item, OriginalCellViewGroup viewGroup, int row, int column) {
            }
        };

        TableFixHeaderAdapter.ClickListener<Nexus, OriginalCellViewGroup> clickListenerSection = new TableFixHeaderAdapter.ClickListener<Nexus, OriginalCellViewGroup>() {
            @Override
            public void onClickItem(Nexus item, OriginalCellViewGroup viewGroup, int row, int column) {
                Snackbar.make(viewGroup, "Click on " + item.type + " (" + row + "," + column + ")", Snackbar.LENGTH_SHORT).show();
            }
        };

        //adapter.setClickListenerFirstHeader(clickListenerHeader);
        //adapter.setClickListenerHeader(clickListenerHeader);
        adapter.setClickListenerFirstBody(clickListenerBody);
        adapter.setClickListenerBody(clickListenerBody);
        //adapter.setClickListenerSection(clickListenerSection);
        //adapter.setLongClickListenerBody(longClickListenerBody);
    }

    private void callLoginDialog(final Class<?> _class, final String fishingId)
    {
        final Dialog myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.activity_login);
        myDialog.setCancelable(true);
        Button login = (Button) myDialog.findViewById(R.id.email_sign_in_button);

        final EditText emailaddr = (EditText) myDialog.findViewById(R.id.email);
        final EditText password = (EditText) myDialog.findViewById(R.id.password);
        myDialog.show();

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UserManager user = new UserManager(context);
                if(user.UserLoginbyRole(emailaddr.getText().toString(), password.getText().toString(), "1"))
                {
                    myDialog.dismiss();
                    Utils.Redirect(context, _class, "fishingId", fishingId);
                }
                myDialog.setTitle("ERROR 123");
            }
        });
    }

    private List<String> getHeader() {
        final String headers[] = {
                context.getString(R.string.number_fishing),
                context.getString(R.string.fullname),
                context.getString(R.string.date_in),
                context.getString(R.string.date_out),
                context.getString(R.string.total_hours),
                context.getString(R.string.total_fish),
                context.getString(R.string.buy_fish),
                context.getString(R.string.total_money),
                context.getString(R.string.note)
        };

        return Arrays.asList(headers);
    }

    private List<Nexus> getBody() {
        List<Nexus> items = new ArrayList<>();
        int onlineCount = 0;
        int totalMoney = 0;

        DateFormat currentDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat sqlDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        Date currentDate = new Date();
        String dateFishing = currentDateFormat.format(currentDate);
        items.add(new Nexus(dateFishing));

        Cursor fishings = (new FishingManager(context)).getFishingEntries(sqlDateFormat.format(currentDate));
        int totalFisher = fishings.getCount();

        int order = 1;
        while (fishings.moveToNext()) {
            String dateIn = fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.DATE_IN));
            String dateOut = fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.DATE_OUT));
            String dateInView = "", dateOutView = "", totalHoursView = "";

            try {
                Calendar cal = Calendar.getInstance();

                //Date in & out
                cal.setTime(dateFormat.parse(dateIn));
                dateInView = String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

                if(dateOut != null) {
                    cal.setTime(dateFormat.parse(dateOut));
                    dateOutView = String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

                    long diff = (dateFormat.parse(dateOut).getTime() - dateFormat.parse(dateIn).getTime());
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000);
                    totalHoursView = String.format("%02d:%02d", diffHours, diffMinutes);
                }
                else onlineCount++;

            } catch (ParseException e) {
                e.printStackTrace();
            }

            totalMoney += fishings.getInt(fishings.getColumnIndexOrThrow(Fishings.Properties.TOTAL_MONEY));

            items.add(new Nexus(
                    order + "",
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties._ID)),
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.FULLNAME)),
                    dateInView,
                    dateOutView,
                    totalHoursView,
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.TOTAL_FISH)),
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.BUY_FISH)),
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.TOTAL_MONEY)),
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.NOTE))));
            order++;
        }
        items.add(new Nexus(context.getString(R.string.total_all) + ": " + onlineCount + "/" + totalFisher, "", "", "", "", "", "", "", totalMoney + "", ""));
        totalItems = items.size() - 1;
        fishings.close();
        return items;
    }

}
