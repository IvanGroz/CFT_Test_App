package ivan.testapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class HelloFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.hello_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_third).setOnClickListener(view1 -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Привет,")
                    .setMessage(getArguments().getString("NAME"))
                    .setPositiveButton("OK", null)
                    .create()
                    .show();
        });

        view.findViewById(R.id.button_second).setOnClickListener(view1 -> {
            findNavController(HelloFragment.this)
                    .navigate(R.id.action_HelloFragment_to_RegistrationFragment);
        });
    }
}