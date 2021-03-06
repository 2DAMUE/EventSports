package com.sai.eventsports.splash_login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sai.eventsports.R;

import java.util.Objects;

public class ActivitySignUp extends AppCompatActivity {
    private TextInputLayout email,username,passwd,confirmPasswd;
    private Button btnSignUp;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.textInputEmail);
        username = findViewById(R.id.textInputUsername);
        passwd = findViewById(R.id.textInputPassword);
        confirmPasswd = findViewById(R.id.textInputConfirmPassword);
        btnSignUp = findViewById(R.id.RegistrationButton);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }
    //****************** CORREO ****************
    private void signUp() {
        String correo = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
        String usuario = Objects.requireNonNull(username.getEditText()).getText().toString().trim();
        String pwd = Objects.requireNonNull(passwd.getEditText()).getText().toString().trim();
        String confirmPwd = Objects.requireNonNull(confirmPasswd.getEditText()).getText().toString().trim();

        if (TextUtils.isEmpty(correo)){
            email.setError("Escriba un email");
        } else if (TextUtils.isEmpty(pwd)) {
            passwd.setError("Escriba una Contrase??a");
        } else if (pwd.length() < 6) {
            passwd.setError("Minimo 6 n??meros");
        } else if (TextUtils.isEmpty(usuario)) {
            username.setError("Escriba un usuario");
        } else if (TextUtils.isEmpty(confirmPwd)) {
            confirmPasswd.setError("Confirma Contrase??a");
        } else if (!pwd.equals(confirmPwd)) {
            confirmPasswd.setError("La contrase??a es diferente");
        } else if (!isValidEmail(correo)) {
            email.setError("Este correo no es valido");
        } else {
            firebaseAuthentification(correo,pwd);
        }
    }

    private void firebaseAuthentification(String correo, String pwd) {
        firebaseAuth.createUserWithEmailAndPassword(correo,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Email y Contrase??a a??adido", Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    ActivityLogIn.USERUID = userId;
                    String userName = Objects.requireNonNull(username.getEditText()).getText().toString().trim();
                    String correo = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
                    Intent intent = new Intent(getApplicationContext(), ActivityRegisterAlert.class);
                    intent.putExtra("userName",userName);
                    intent.putExtra("userGoogle",correo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isValidEmail(String correo) {
        return (!TextUtils.isEmpty(correo) && Patterns.EMAIL_ADDRESS.matcher(correo).matches());
    }

    //****************** ACCIONES ***************

    /**
     * Este m??todo sirve para hacer la animaci??n hacia el sign up activity
     *
     * @param view
     */
    public void registerToLoginSidebarClick(View view) {
        startActivity(new Intent(this, ActivityLogIn.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}