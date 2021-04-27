package com.sai.eventsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class ActivityLogIn extends AppCompatActivity {
    TextInputLayout email,password;
    Button btnLogIn;
    TextView txt_forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.textInputEmailLogin);
        password = findViewById(R.id.textInputPasswordLogin);
        txt_forgot_password = findViewById(R.id.txt_forget);
        btnLogIn = findViewById(R.id.btn_login);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogForgotPassword();
            }
        });

    }

    public void loginUser() {
        startActivity(new Intent(this, MainActivity.class));
        /*String correo = email.getEditText().getText().toString().trim();
        String pwd = password.getEditText().getText().toString().trim();
        String msgEmail = null,msgPass = null;

        if (TextUtils.isEmpty(correo)) {
            msgEmail = "Enter your email";
            email.setError(msgEmail);
        } else if (TextUtils.isEmpty(pwd)) {
            msgPass = "Enter your password";
            password.setError(msgPass);
        } else if (pwd.length() < 6) {
            passwd.setError("Minimum length of password should be 6");
            return;
        } else if (!isValidEmail(correo)) {
            email.setError("This is not a valid email");
            return;
        } else {
            mAuth.signInWithEmailAndPassword(correo, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        email.setErrorEnabled(msgEmail != null);
                        password.setErrorEnabled(msgPass != null);
                        USERUID = mAuth.getCurrentUser().getUid();
                        Intent accessIntent = new Intent(getApplicationContext(), MapsActivity.class);
                        accessIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        accessIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(accessIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Signed in failed", Toast.LENGTH_SHORT).show();
                        Log.d("ERRORLOGIN", task.getException().toString());
                    }
                }
            });
        }*/
    }

    /**
     * Este método sirve para hacer la animación hacia el sign up activity
     *
     * @param view
     */
    public void loginToRegisterSidebarClick(View view) {
        startActivity(new Intent(this, ActivitySignUp.class));
        overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out);
    }

    /**
     * En este método mostramos la alerta de recuperar la contraseña
     */
    private void showAlertDialogForgotPassword() {
        // Here we setup the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.activity_forgot_password,
                null));

        // Here we create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}