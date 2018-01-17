package com.fishing.namtran.fishingmanagerservice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.fishing.namtran.fishingmanagerservice.dbconnection.FishingManager;
import com.fishing.namtran.fishingmanagerservice.dbconnection.Fishings;
import com.fishing.namtran.fishingmanagerservice.dbconnection.Settings;
import com.fishing.namtran.fishingmanagerservice.dbconnection.SettingsManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A login screen that offers login via email/password.
 */
public class ChangeFishingActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private ChangeFishingEntryActionTask mChangeFishingEntryTask = null;

    // UI references.
    private AutoCompleteTextView mFullNameView;
    private EditText mDatInView;
    private EditText mDateOutView;
    private EditText mBuyFishView;
    private EditText mTotalFishView;
    private EditText mTotalHoursView;
    private EditText mTotalMoneyView;
    private EditText mNoteView;
    private View mProgressView;
    private View mSubmitFormView;
    private String mFishingId;
    private String mDateIn;
    private String mDateOut;
    private double mBuyFish;
    private long mFeePackage;
    private int mPriceBuyFish;
    private int mPriceFishing;
    private double mTotalFish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_fishing);

        mFishingId = getIntent().getStringExtra("fishingId");

        // Set up the login form.
        mFullNameView = (AutoCompleteTextView) findViewById(R.id.fullname);
        mDatInView = (EditText) findViewById(R.id.date_in);
        mDateOutView = (EditText) findViewById(R.id.date_out);
        mTotalHoursView = (EditText) findViewById(R.id.total_hours);
        mBuyFishView = (EditText) findViewById(R.id.buy_fish);
        mTotalFishView = (EditText) findViewById(R.id.total_fish);
        mTotalMoneyView = (EditText) findViewById(R.id.total_money);
        mNoteView = (EditText) findViewById(R.id.note);
        mSubmitFormView = findViewById(R.id.update_customer_form);
        mProgressView = findViewById(R.id.update_customer_progress);

        Cursor fishings = (new FishingManager(getApplicationContext())).getFishingEntriesById(mFishingId);
        Cursor settings = (new SettingsManager(getApplicationContext())).getSettingEntry("1");

        if (settings.moveToNext()) {
            mPriceFishing = settings.getInt(settings.getColumnIndexOrThrow(Settings.Properties.PRICE_FISHING));
            mPriceBuyFish = settings.getInt(settings.getColumnIndexOrThrow(Settings.Properties.PRICE_BUY_FISH));
        }
        settings.close();

        if(fishings.moveToNext())
        {
            Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            mFullNameView.setText(fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.FULLNAME)));
            mDateIn = fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.DATE_IN));
            mTotalFishView.setText(fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.TOTAL_FISH)));
            mBuyFishView.setText(fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.BUY_FISH)));
            mTotalMoneyView.setText(fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.TOTAL_MONEY)));
            mDateOut = fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.DATE_OUT));

            try {
                cal.setTime(dateFormat.parse(mDateIn));
                mDateOutView.setText(String.format("%02d:%02d", dateFormat.parse(mDateOut).getHours(), dateFormat.parse(mDateOut).getMinutes()));
                long diff = (dateFormat.parse(mDateOut).getTime() - dateFormat.parse(mDateIn).getTime());
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000);
                mTotalHoursView.setText(String.format("%02d:%02d", diffHours, diffMinutes));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            mDatInView.setText(String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE)));
            mBuyFish = fishings.getDouble(fishings.getColumnIndexOrThrow(Fishings.Properties.BUY_FISH));
            mNoteView.setText(fishings.getString(fishings.getColumnIndexOrThrow(Fishings.Properties.NOTE)));
        }
        fishings.close();

        //Events action
        Button mUpdateCustomerButton = (Button) findViewById(R.id.update_customer_button);
        mUpdateCustomerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmit();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmit() {
        if (mChangeFishingEntryTask != null) {
            return;
        }

        // Reset errors.
        mDateOutView.setError(null);

        // Store values at the time of the login attempt.
        String dateIn = mDatInView.getText().toString();
        String dateOut = mDateOutView.getText().toString();
        String fullname = mFullNameView.getText().toString();
        String fishingId = mFishingId;
        String totalFish = mTotalFishView.getText().toString();
        String buyFish = mBuyFishView.getText().toString();
        String totalMoney = mTotalMoneyView.getText().toString();
        String note = mNoteView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mChangeFishingEntryTask = new ChangeFishingEntryActionTask(fishingId, fullname, dateIn, dateOut, totalFish, buyFish, totalMoney, note);
            mChangeFishingEntryTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSubmitFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSubmitFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSubmitFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSubmitFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous action task
     */
    public class ChangeFishingEntryActionTask extends AsyncTask<Void, Void, Boolean> {

        private final String mFishingId;
        private final String mDateOut;
        private final String mDateIn;
        private final String mFullName;
        private final String mBuyFish;
        private final String mTotalFish;
        private final String mTotalMoney;
        private final String mNote;

        ChangeFishingEntryActionTask(String fishingId, String fullname, String dateIn, String dateOut, String totalFish, String buyFish, String totalMoney, String note) {
            mFishingId = fishingId;
            mDateOut = dateOut;
            mDateIn = dateIn;
            mFullName = fullname;
            mBuyFish = buyFish;
            mTotalFish = totalFish;
            mTotalMoney = totalMoney;
            mNote = note;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mChangeFishingEntryTask = null;
            showProgress(false);

            DateFormat currentDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date currentDate = new Date();
            String fullDateOut = currentDateFormat.format(currentDate) + " " + mDateOut + ":00";
            String fullDateIn = currentDateFormat.format(currentDate) + " " + mDateIn + ":00";

            if (success) {
                finish();
                FishingManager fishingManager = new FishingManager(getApplicationContext());

                if(fishingManager.changeCloseFishingEntry(mFishingId, mFullName, fullDateIn, fullDateOut, fullDateOut, mBuyFish, mTotalFish, mTotalMoney, mNote)) {
                    Utils.Redirect(getApplicationContext(), ManagerCustomerActivity.class);
                }
                else {
                    Utils.Alert(getApplicationContext(), getString(R.string.action_error));
                }
            } else {
                Utils.Alert(getApplicationContext(), getString(R.string.action_error));
            }
        }

        @Override
        protected void onCancelled() {
            mChangeFishingEntryTask = null;
            showProgress(false);
        }
    }
}

