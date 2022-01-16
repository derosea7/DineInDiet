package com.dooragami.dineindiet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;


public class MyDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String KEY_INTENT_DATEDIALOG = "KEY_INTENT_DATEDIALOG";
    public static final int RESULT_CODE_DATEDIALOG = 1234;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

//        get the calendar day, month, year
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

//        create the date picker
        DatePickerDialog myDatePicker =
                new DatePickerDialog(getActivity(), this, year, month, day);

        return myDatePicker;
    }

    private void passBackRowId(long RowId) {
        Intent intent = new Intent();
        intent.putExtra(KEY_INTENT_DATEDIALOG, RowId);
        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_CODE_DATEDIALOG, intent);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        //Toast.makeText(getActivity(), "ok", Toast.LENGTH_LONG).show();
        // passBackRowId(4);

        Intent i = new Intent();
        i.putExtra(KEY_PURCH_DATE_YEAR, year);
        i.putExtra(KEY_PURCH_DATE_MONTH, monthOfYear);
        i.putExtra(KEY_PURCH_DATE_DAY, dayOfMonth);

        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_CODE_DATEDIALOG, i);

    }

    public static final String KEY_PURCH_DATE_YEAR = "KEY_PURCH_DATE_YEAR";
    public static final String KEY_PURCH_DATE_MONTH = "KEY_PURCH_DATE_MONTH";
    public static final String KEY_PURCH_DATE_DAY = "KEY_PURCH_DATE_DAY";

}