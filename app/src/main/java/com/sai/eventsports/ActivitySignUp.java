package com.sai.eventsports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class ActivitySignUp extends AppCompatActivity {
    private TextInputLayout email,username,passwd,confirmPasswd;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.textInputEmail);
        username = findViewById(R.id.textInputUsername);
        passwd = findViewById(R.id.textInputPassword);
        confirmPasswd = findViewById(R.id.textInputConfirmPassword);
        btnSignUp = findViewById(R.id.RegistrationButton);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String correo = email.getEditText().getText().toString().trim();
        String usuario = username.getEditText().getText().toString().trim();
        String pwd = passwd.getEditText().getText().toString().trim();
        String confirmPwd = confirmPasswd.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(correo)){
            email.setError("Enter your email");
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            passwd.setError("Enter your password");
            return;
        } else if (pwd.length() < 6) {
            passwd.setError("Minimum length of password should be 6");
            return;
        } else if (TextUtils.isEmpty(usuario)) {
            username.setError("Enter your username");
            return;
        } else if (TextUtils.isEmpty(confirmPwd)) {
            confirmPasswd.setError("Confirm your password");
            return;
        } else if (!pwd.equals(confirmPwd)) {
            confirmPasswd.setError("Passwords are different");
            return;
        } else if (!isValidEmail(correo)) {
            email.setError("This is not a valid email");
            return;
        } else {
            Toast.makeText(getApplicationContext(), "Email and Password added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
    private boolean isValidEmail(String correo) {
        return (!TextUtils.isEmpty(correo) && Patterns.EMAIL_ADDRESS.matcher(correo).matches());
    }

    /**
     * Este método sirve para hacer la animación hacia el sign up activity
     *
     * @param view
     */
    public void registerToLoginSidebarClick(View view) {
        startActivity(new Intent(this, ActivityLogIn.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}