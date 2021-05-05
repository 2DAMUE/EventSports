package com.sai.eventsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ActivityLogIn extends AppCompatActivity {
    private TextInputLayout email,password;
    private Button btnLogIn;
    private TextView txt_forgot_password;
    private FirebaseAuth firebaseAuth;

    public static String USERUID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.textInputEmailLogin);
        password = findViewById(R.id.textInputPasswordLogin);
        txt_forgot_password = findViewById(R.id.txt_forget);
        btnLogIn = findViewById(R.id.btn_login);

        firebaseAuth = FirebaseAuth.getInstance();

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
        String correo = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
        String pwd = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
        String msgEmail = null,msgPass = null;

        if (TextUtils.isEmpty(correo)) {
            msgEmail = "Enter your email";
            email.setError(msgEmail);
        } else if (TextUtils.isEmpty(pwd)) {
            msgPass = "Enter your password";
            password.setError(msgPass);
        } else if (pwd.length() < 6) {
            msgPass = "Minimum length of password should be 6";
            password.setError(msgPass);
        } else if (!isValidEmail(correo)) {
            msgEmail = "This is not a valid email";
            email.setError(msgEmail);
        } else {
            firebaseAuth.signInWithEmailAndPassword(correo, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        USERUID = firebaseAuth.getCurrentUser().getUid();
                        Intent accessIntent = new Intent(getApplicationContext(), MainActivity.class);
                        accessIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        accessIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(accessIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Signed in failed", Toast.LENGTH_SHORT).show();
                        Log.d("ERRORLOGIN", task.getException().toString());
                    }
                }
            });
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