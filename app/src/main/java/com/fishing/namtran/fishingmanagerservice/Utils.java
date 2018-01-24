package com.fishing.namtran.fishingmanagerservice;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.inputmethodservice.Keyboard;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.fishing.namtran.fishingmanagerservice.dbconnection.Fishings;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nam.tran on 11/1/2017.
 */

public class Utils extends FragmentActivity {

    public static void Alert(Context context, String message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void Redirect(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);

        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    public static void Redirect(Context context, Class<?> cls, String parameter, String value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(parameter, value);
        context.startActivity(intent);
    }

    public static void GetTimePicker(Context context, final Object objText)
    {
        //https://www.journaldev.com/9976/android-date-time-picker-dialog
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
                        EditText editText = (EditText) objText;
                        editText.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public static void GetDatePicker(Context context, final Object objText)
    {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        EditText editText = (EditText) objText;
                        editText.setText(String.format("%02d/%02d/%02d", year, monthOfYear, dayOfMonth));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public static Date GetCurrentTimeByRoundFiveMinutes()
    {
        Calendar currentTime = Calendar.getInstance();
        currentTime.set(Calendar.SECOND, 0);
        int minute = currentTime.get(Calendar.MINUTE);

        if(minute % 5 != 0) {
            minute = ((minute / 5) + 1) * 5;
        }

        if(minute >= 60) {
            currentTime.add(Calendar.HOUR_OF_DAY, 1);
            currentTime.set(Calendar.MINUTE, 0);
        } else {
            currentTime.set(Calendar.MINUTE, minute);
        }
        return currentTime.getTime();
    }

    public static boolean saveExcelFile(Context context, String fileName, Cursor cursor)
    {
        //http://cuelogic.com/blog/creatingreading-an-excel-file-in-android/
        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("ExcelLog", "Storage not available or read only");
            return false;
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("Report");

        // Generate column headings
        Row row = sheet1.createRow(0);

        String[] titles = { context.getString(R.string.fullname), context.getString(R.string.date_in), context.getString(R.string.date_out),
                context.getString(R.string.total_hours), context.getString(R.string.buy_fish), context.getString(R.string.money_hire), context.getString(R.string.total_money), context.getString(R.string.note)};

        for (int j = 0; j < titles.length; j++) {
            c = row.createCell(j);
            c.setCellValue(titles[j]);
            c.setCellStyle(cs);
            sheet1.setColumnWidth(j, (15 * 300));
        }

        String[] fieldNames = { Fishings.Properties.FULLNAME, Fishings.Properties.DATE_IN, Fishings.Properties.DATE_OUT,
                "TOTAL_HOURS", Fishings.Properties.BUY_FISH, Fishings.Properties.MONEY_HIRE, Fishings.Properties.TOTAL_MONEY, Fishings.Properties.NOTE};
        while (cursor.moveToNext()) {
            row = sheet1.createRow(cursor.getPosition() + 1);
            for (int i = 0; i < fieldNames.length; i++) {
                c = row.createCell(i);
                if (fieldNames[i] == "TOTAL_HOURS") {
                    String dateIn = cursor.getString(cursor.getColumnIndexOrThrow(Fishings.Properties.DATE_IN));
                    String dateOut = cursor.getString(cursor.getColumnIndexOrThrow(Fishings.Properties.DATE_OUT));
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    try {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dateFormat.parse(dateIn));
                        if(dateOut != null) {
                            cal.setTime(dateFormat.parse(dateOut));
                            long diff = (dateFormat.parse(dateOut).getTime() - dateFormat.parse(dateIn).getTime());
                            long diffMinutes = diff / (60 * 1000) % 60;
                            long diffHours = diff / (60 * 60 * 1000);
                            c.setCellValue(String.format("%02d:%02d", diffHours, diffMinutes));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    c.setCellValue(cursor.getString(cursor.getColumnIndexOrThrow(fieldNames[i])));
                }
            }
        }

        // Create a path where we will place our List of objects on external storage
        File file = new File(context.getExternalFilesDir(null), fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
