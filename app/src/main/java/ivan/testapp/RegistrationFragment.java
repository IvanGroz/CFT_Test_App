package ivan.testapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.regex.Pattern;

public class RegistrationFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private TextView dateText;
    private Button registration;
    private EditText materialNameText;
    private EditText materialFamilyText;
    private EditText materialPasswordText;
    private EditText materialPasswordRepeatText;

    private TextInputLayout materialNameLayout;
    private TextInputLayout materialFamilyLayout;
    private TextInputLayout materialPasswordLayout;
    private TextInputLayout materialPasswordRepeatLayout;

    private boolean validOfName = false;
    private boolean validationOfFamily = false;
    private boolean validationOfPasswordCorrect = false;
    private boolean validationOfPasswordCompare = false;
    private boolean validationOfSetDate = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.registration_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateText = (TextView) (view.findViewById(R.id.dateOfBirth_TextView));
        materialFamilyText = (EditText) (view.findViewById(R.id.materialFamilyEdit));
        materialNameText = (EditText) (view.findViewById(R.id.materialNameEdit));
        materialPasswordText = (EditText) (view.findViewById(R.id.materialPasswordEdit));
        materialPasswordRepeatText = (EditText) (view.findViewById(R.id.materialPasswordRepeatEdit));

        materialPasswordLayout = (TextInputLayout) view.findViewById(R.id.materialPasswordLayout);
        materialPasswordRepeatLayout = (TextInputLayout) view.findViewById(R.id.materialPasswordRepeatLayout);
        materialNameLayout = (TextInputLayout) view.findViewById(R.id.materialNameLayout);
        materialFamilyLayout = (TextInputLayout) view.findViewById(R.id.materialFamilyLayout);

        registration = (Button) view.findViewById(R.id.button_registation);
        registration.setEnabled(false);

        registration.setOnClickListener(view1 -> {

            Bundle args = new Bundle();
            args.putString("NAME", materialNameText.getText().toString());
            NavHostFragment.findNavController(RegistrationFragment.this)
                    .navigate(R.id.action_RegistrationFragment_to_HelloFragment, args);
        });

        materialNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                materialNameLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (materialNameText.getText().length() == 0) {
                    validOfName = false;
                    return;
                }
                if (!materialNameText.getText().toString().matches("[А-Я][а-я]*")) {

                    materialNameLayout.setError(getString(R.string.errror_name));
                    validOfName = false;
                } else
                    validOfName = true;
                registration.setEnabled(allValidated());
            }
        });

        materialFamilyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                materialFamilyLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (materialFamilyText.getText().length() == 0) {
                    validationOfFamily = false;
                    return;
                }

                if (!materialFamilyText.getText().toString().matches("[А-Я][а-я]*")) {

                    materialFamilyLayout.setError(getString(R.string.error_family));
                    validationOfFamily = false;
                } else validationOfFamily = true;
                registration.setEnabled(allValidated());
            }
        });

        materialPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                materialPasswordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (materialPasswordText.getText().length() == 0) {
                    validationOfPasswordCorrect = false;
                    return;
                }
                if (!materialPasswordText.getText().toString().matches(PASSWORD_PATTERN.pattern())) {
                    materialPasswordLayout.setError(getString(R.string.error_correct_password));
                    validationOfPasswordCorrect = false;
                } else validationOfPasswordCorrect = true;
                registration.setEnabled(allValidated());
            }
        });

        materialPasswordRepeatText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                materialPasswordRepeatLayout.setError(null);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (materialPasswordRepeatText.getText().toString().length() == 0) {
                    validationOfPasswordCompare = false;
                    return;
                }
                if (!materialPasswordRepeatText.getText().toString().
                        equals(materialPasswordText.getText().toString())) {
                    validationOfPasswordCompare = false;
                    materialPasswordRepeatLayout.setError(getString(R.string.error_compare_password));
                } else
                    validationOfPasswordCompare = true;
                registration.setEnabled(allValidated());
            }
        });

        view.findViewById(R.id.setDateButton).setOnClickListener(view2 -> showDatePickerDialog());
    }

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])" +
            "(?=.*[A-Z])" +
            "(?=.*[a-z])" +
            "(?=\\S+$)" +
            ".{6,}$");

    private boolean allValidated() {
        return validOfName &&
                validationOfFamily &&
                validationOfPasswordCorrect &&
                validationOfPasswordCompare &&
                validationOfSetDate;
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this.getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMaxDate((System.currentTimeMillis()));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

        month++;
        dateText.setText(
                getString(
                        R.string.dateFormat,
                        dayOfMonth / 10,
                        dayOfMonth % 10,
                        month / 10,
                        month % 10,
                        year
                )
        );
        validationOfSetDate = true;
        registration.setEnabled(allValidated());
    }
}