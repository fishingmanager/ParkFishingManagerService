package com.fishing.namtran.fishingmanagerservice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.fishing.namtran.fishingmanagerservice.dbconnection.FishingManager;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A login screen that offers login via email/password.
 */
public class ReportActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private CustomerActionTask mCustomerTask = null;

    // UI references.
    private EditText mDatePickerView;
    private View mProgressView;
    private View mSubmitFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Set up the login form.
        mDatePickerView = (EditText) findViewById(R.id.date_report);
        mSubmitFormView = findViewById(R.id.report_form);
        mProgressView = findViewById(R.id.report_progress);

        Button mReportButton = (Button) findViewById(R.id.report_button);
        mReportButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSubmit();
            }
        });

        /*mDatePickerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    EditText editText = (EditText) mDatePickerView;
                    GetDatePicker(editText);
                }
            }
        });*/

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date currentDate = new Date();
        mDatePickerView.setText(dateFormat.format(currentDate));

        mDatePickerView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) mDatePickerView;
                GetDatePicker(editText);
            }
        });
    }

    public void GetDatePicker(final Object objText)
    {
        // Get Current Date
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        EditText editText = (EditText) objText;
                        editText.setText(String.format("%02d/%02d/%02d", year, monthOfYear + 1, dayOfMonth));
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSubmit() {
        if (mCustomerTask != null) {
            return;
        }

        // Reset errors.
        mDatePickerView.setError(null);

        // Store values at the time of the login attempt.
        String datePicker = mDatePickerView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(datePicker)) {
            mDatePickerView.setError(getString(R.string.error_field_required));
            focusView = mDatePickerView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mCustomerTask = new CustomerActionTask(datePicker);
            mCustomerTask.execute((Void) null);
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
    public class CustomerActionTask extends AsyncTask<Void, Void, Boolean> {

        private final String mDatePicker;

        CustomerActionTask(String datePicker) {
            mDatePicker = datePicker;
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
            mCustomerTask = null;
            showProgress(false);

            if (success) {
                FishingManager fishing = new FishingManager(getApplicationContext());
                Cursor cursor = fishing.getFishingEntries(mDatePicker);

                DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                Date currentDate = new Date();
                final String fileName = "BaoCao_" + dateFormat.format(currentDate) + ".xlsx";

                if(Utils.saveExcelFile(getApplicationContext(), fileName, cursor))
                {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                GMailSender sender = new GMailSender(
                                        "parkfishingmanagerservice@gmail.com",
                                        "t260gOm3g");
                                String env = getApplicationContext().getExternalFilesDir(null).getPath();
                                sender.addAttachment(getApplicationContext().getExternalFilesDir(null) + "/" + fileName);
                                sender.sendMail("Test mail", "This mail has been sent from android app along with attachment",
                                        "parkfishingmanagerservice@gmail.com",
                                        "parkfishingmanagerservice@gmail.com");
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                            }
                        }
                    }).start();
                }
                finish();
            } else {
                Utils.Alert(ReportActivity.this, getString(R.string.action_error));
            }
        }

        @Override
        protected void onCancelled() {
            mCustomerTask = null;
            showProgress(false);
        }
    }
}

