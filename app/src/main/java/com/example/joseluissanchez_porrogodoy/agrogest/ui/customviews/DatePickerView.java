package com.example.joseluissanchez_porrogodoy.agrogest.ui.customviews;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.joseluissanchez_porrogodoy.agrogest.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerView extends LinearLayout {

    private String dateFormat;
    private TextView tvDate;
    private Date selectedDate;
    private Date maxDate;
    private Date minDate;

    private String stateToSave;

    public DatePickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI(null);
    }

    public DatePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(null);
    }

    public DatePickerView(Context context) {
        super(context);
        initUI(null);
    }

    private void initUI(String dateFormat) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_add_date, this, true);
        tvDate = (TextView) v.findViewById(R.id.dateEdit);
        if (dateFormat == null || dateFormat.length() <= 0) {
            //Default dd/mm/yyyy
            dateFormat = getContext().getResources().getString(R.string.default_date_format);
        }
        View clickable = v.findViewById(R.id.dateLayout);
        clickable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDate.setTextColor(Color.BLACK);
                Date dat = selectedDate != null ? selectedDate : new Date();
                ViewUtility.showDatePicker(getContext(), dat,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            Calendar fechaSelect = Calendar.getInstance();
                            fechaSelect.set(year, monthOfYear, dayOfMonth);
                            setDate(fechaSelect.getTime());
                        }
                    }, minDate, maxDate);
            }
        });
    }

    public void setErrorMessage(String error) {

        tvDate.setText(error);
        tvDate.setTextColor(Color.RED);
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setDate(Date date) {
        if (dateFormat == null || dateFormat.length() <= 0) {
            //Default dd/mm/yyyy
            dateFormat = getContext().getResources().getString(R.string.default_date_format);
        }
        final SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        this.selectedDate = date;
        stateToSave = format.format(date.getTime());
        tvDate.setText(stateToSave);
    }

    public void setDate(String date) {
        if (date!=null) {
            if (dateFormat == null || dateFormat.length() <= 0) {
                //Default dd/mm/yyyy
                dateFormat = getContext().getResources().getString(R.string.default_date_format);
            }
            selectedDate = ViewUtility.stringToDate(dateFormat, date);
            stateToSave = date;
            tvDate.setText(stateToSave);
        }
    }

    public Date getDate() {
        return this.selectedDate;
    }

    public void setMaxDate(Date date) {
        this.maxDate = date;
    }

    public void setMinDate(Date date) {
        this.minDate = date;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);
        //end

        ss.stateToSave = this.stateToSave;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        //end

        setDate(ss.stateToSave);
    }

    static class SavedState extends BaseSavedState {
        String stateToSave;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.stateToSave = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(this.stateToSave);
        }

        //required field that makes Parcelables from a Parcel
        public static final Parcelable.Creator<SavedState> CREATOR =
            new Parcelable.Creator<SavedState>() {
                public SavedState createFromParcel(Parcel in) {
                    return new SavedState(in);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
    }
}
