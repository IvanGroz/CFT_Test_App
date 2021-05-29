package ivan.testapp;

import android.app.DatePickerDialog;
import android.icu.text.TimeZoneFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.BundleCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class RegistrationFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private TextView dateText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.registration_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle f = new Bundle();
                f.putString("NAME", "Ivan");
                NavHostFragment.findNavController(RegistrationFragment.this)
                        .navigate(R.id.action_RegistrationFragment_to_HelloFragment, f);
            }
        });

        dateText = (TextView) (view.findViewById(R.id.dateOfBirth_TextView));

        view.findViewById(R.id.setDateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this.getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMaxDate((System.currentTimeMillis() ));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        month++;
        String dayOfMonthRepresent = (dayOfMonth<10)?"0"+dayOfMonth:Integer.toString(dayOfMonth);
        String monthRepresent = (month<10)? "0"+ month : Integer.toString(month);
        String date = "Ваша дата рождения:\n " + dayOfMonthRepresent  + "." + monthRepresent + "." +  year;

        Log.d("prov",Calendar.getInstance().getTime().toString());
        dateText.setText(date);



    }
}