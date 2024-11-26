package com.btec.fpt.campus_expense_manager.fragments;


import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.btec.fpt.campus_expense_manager.R;

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);

        Button loginButton = view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, new LoginFragment()); // R.id.fragment_container là layout chứa fragment
                transaction.addToBackStack(null); // Thêm vào BackStack để có thể quay lại
                transaction.commit();
            }
        });
        // Set up button to go to ForgotPasswordFragment
        Button forgotPasswordButton = view.findViewById(R.id.forgot_password_button);
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCustomAlertDialog();
            }

            private void showCustomAlertDialog() {
                // Create an instance of LayoutInflater
                LayoutInflater inflater = getLayoutInflater();

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.dialog_custom, null);

                // Build the AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(customView);  // Set the custom layout

                // Get references to the views in the custom layout
                final EditText input = customView.findViewById(R.id.dialog_input);
                TextView title = customView.findViewById(R.id.dialog_title);
                Button okButton = customView.findViewById(R.id.dialog_button_ok);

                // Optionally customize the title
                title.setText("Forgot password!");

                // Create and show the dialog
                AlertDialog dialog = builder.create();

                // Handle the OK button click event
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get the input text and show it in a Toast
                        String userInput = input.getText().toString();
                        //  Toast.makeText(getContext(), "Input: " + userInput, Toast.LENGTH_SHORT).show();

                        if (isValidEmail(userInput)) {

                            loadFragment(new ForgotPasswordFragment());

                        } else {

                            showToastCustom("Email is invalid !");
                        }


                        // loadFragment(new ForgotPasswordFragment());

                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });

                // Show the dialog
                dialog.show();
            }
        });
        return view;
    }

    void showToastCustom(String message) {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, view.findViewById(R.id.custom_toast_layout));
// Set the icon
        ImageView icon = layout.findViewById(R.id.toast_icon);
        icon.setImageResource(R.drawable.icon_x);  // Set your desired icon

// Set the text
        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

// Create and show the toast
        Toast toast = new Toast(getContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }

    private void loadFragment(ForgotPasswordFragment forgotPasswordFragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, forgotPasswordFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private boolean isValidEmail(String userInput) {
        // Use Android's built-in Patterns utility to validate the email format
        return !userInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(userInput).matches();
    }
}
