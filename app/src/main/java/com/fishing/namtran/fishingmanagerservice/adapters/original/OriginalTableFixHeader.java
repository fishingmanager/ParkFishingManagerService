package com.fishing.namtran.fishingmanagerservice.adapters.original;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

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
        //Utils.Alert(context, ne.data[1]);
        //adapter.inflateBody().textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
        for (int i = 1; i <= adapter.getBody().size(); i++)
        {
            //Nexus ne = adapter.getBody().get(2);
            //Utils.Alert(context, ne.data[1]);
            //adapter.inflateFirstBody().vg_root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorDarkGray));
            //adapter.inflateBody().textView.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
        }
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
                    int fishingId = 0;
                    DateFormat sqlDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date currentDate = new Date();
                    Cursor fishingEntries = (new FishingManager(context)).getFishingEntries(sqlDateFormat.format(currentDate));

                    while (fishingEntries.moveToNext()) {
                        if ((fishingEntries.getPosition() + 1) == Integer.valueOf(item.data[0])) {
                            fishingId = fishingEntries.getInt(fishingEntries.getColumnIndexOrThrow(Fishings.Properties._ID));
                            break;
                        }
                    }
                    fishingEntries.close();

                    if (item.data[4] == "") {
                        Utils.Redirect(context, UpdateCustomerActivity.class, "fishingId", String.valueOf(fishingId));
                    } else {
                        callLoginDialog(ChangeFishingActivity.class, String.valueOf(fishingId));
                    }
                }
            }
        };

        TableFixHeaderAdapter.LongClickListener<Nexus, OriginalCellViewGroup> longClickListenerBody = new TableFixHeaderAdapter.LongClickListener<Nexus, OriginalCellViewGroup >() {
            @Override
            public void onLongClickItem(Nexus item, OriginalCellViewGroup viewGroup, int row, int column) {
                if(column == 2 && item.data[column + 2] == "" && row != adapter.getRowCount()-1) {
                    GetTimePicker(viewGroup, adapter, item);
                }
            }
        };

        TableFixHeaderAdapter.ClickListener<Nexus, OriginalCellViewGroup> clickListenerSection = new TableFixHeaderAdapter.ClickListener<Nexus, OriginalCellViewGroup>() {
            @Override
            public void onClickItem(Nexus item, OriginalCellViewGroup viewGroup, int row, int column) {
                Snackbar.make(viewGroup, "Click on " + item.type + " (" + row + "," + column + ")", Snackbar.LENGTH_SHORT).show();
            }
        };

        TableFixHeaderAdapter.BodyBinder<Nexus> bodyBinder = new TableFixHeaderAdapter.BodyBinder<Nexus>() {
            @Override
            public void bindBody(Nexus item, int row, int column) {
            }
        };

        //adapter.setClickListenerFirstHeader(clickListenerHeader);
        //adapter.setClickListenerHeader(clickListenerHeader);
        adapter.setClickListenerFirstBody(clickListenerBody);
        adapter.setClickListenerBody(clickListenerBody);
        //adapter.setClickListenerSection(clickListenerSection);
        adapter.setLongClickListenerBody(longClickListenerBody);
    }

    private void GetTimePicker(final OriginalCellViewGroup viewGroup, final OriginalTableFixHeaderAdapter adapter, final Nexus item)
    {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String dateOut1 = String.format("%02d:%02d", hourOfDay, minute);
                        viewGroup.textView.setText(dateOut1);

                        int fishingId = 0;
                        DateFormat sqlDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        Date currentDate = new Date();
                        Cursor fishingEntries = (new FishingManager(context)).getFishingEntries(sqlDateFormat.format(currentDate));

                        while (fishingEntries.moveToNext()) {
                            if ((fishingEntries.getPosition() + 1) == Integer.valueOf(item.data[0])) {
                                fishingId = fishingEntries.getInt(fishingEntries.getColumnIndexOrThrow(Fishings.Properties._ID));
                                break;
                            }
                        }
                        fishingEntries.close();
                        (new FishingManager(context)).updateDateOut1(String.valueOf(fishingId), (new SimpleDateFormat("yyyy/MM/dd").format(currentDate) + " " + dateOut1 + ":00"));
                        adapter.setBody(getBody());
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void callLoginDialog(final Class<?> _class, final String fishingId)
    {
        final Dialog myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.activity_login_change_fishing);
        myDialog.setCancelable(true);
        Button login = (Button) myDialog.findViewById(R.id.password_button);

        final EditText password = (EditText) myDialog.findViewById(R.id.password);
        myDialog.show();

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UserManager user = new UserManager(context);
                if(user.UserLoginbyRole("admin@gmail.com", password.getText().toString(), "1"))
                {
                    myDialog.dismiss();
                    Utils.Redirect(context, _class, "fishingId", fishingId);
                }
                else {
                    Toast.makeText(context, context.getString(R.string.error_incorrect_password), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private List<String> getHeader() {
        final String headers[] = {
                context.getString(R.string.fullname),
                context.getString(R.string.date_in),
                context.getString(R.string.date_out_1),
                context.getString(R.string.date_out),
                context.getString(R.string.total_hours),
                context.getString(R.string.total_fish),
                context.getString(R.string.buy_fish),
                context.getString(R.string.money_hire),
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
            String dateOut1 = fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.DATE_OUT_1));
            String dateInView = "", dateOutView = "", dateOutView1 = "", totalHoursView = "";

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

                if(dateOut1 != null) {
                    cal.setTime(dateFormat.parse(dateOut1));
                    dateOutView1 = String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            totalMoney += fishings.getInt(fishings.getColumnIndexOrThrow(Fishings.Properties.TOTAL_MONEY));

            items.add(new Nexus(
                    order + "",
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.FULLNAME)),
                    dateInView,
                    dateOutView1,
                    dateOutView,
                    totalHoursView,
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.TOTAL_FISH)),
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.BUY_FISH)),
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.MONEY_HIRE)),
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.TOTAL_MONEY)),
                    fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.NOTE))));
            order++;
        }
        items.add(new Nexus(context.getString(R.string.total_all) + ": " + onlineCount + "/" + totalFisher, "", "", "", "", "", "", "", "", totalMoney + "", ""));
        totalItems = items.size() - 1;
        fishings.close();
        return items;
    }

}
