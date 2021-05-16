package com.sai.eventsports.splash_login_register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sai.eventsports.ActivityMain;
import com.sai.eventsports.R;

import java.util.Objects;

public class ActivityLogIn extends AppCompatActivity {
    private TextInputLayout email,password;
    private Button btnLogIn;
    private TextView txt_forgot_password;
    //Firebase
    private static FirebaseAuth firebaseAuth;
    //Google
    private SignInButton google_login;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 54654;
    //Important!!
    public static String USERUID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.textInputEmailLogin);
        password = findViewById(R.id.textInputPasswordLogin);
        txt_forgot_password = findViewById(R.id.txt_forget);
        btnLogIn = findViewById(R.id.btn_login);
        google_login = findViewById(R.id.google_login);

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

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleLoginClick();

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            USERUID = firebaseUser.getUid();
            Intent accessIntent = new Intent(getApplicationContext(), ActivityMain.class);
            accessIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            accessIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(accessIntent);
        }
    }

    /**
     * @return FirebaseAuth Icreated
     */
    public static FirebaseAuth recogerInstancia() {
        FirebaseAuth mAuth_creado = firebaseAuth;
        return mAuth_creado;
    }

    //****************** ACCIONES ***************

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

    //****************** CORREO ****************

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
                        Intent accessIntent = new Intent(getApplicationContext(), ActivityMain.class);
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

    //****************** GOOGLE ****************

    /**
     * In this method we have put the setOnClickListener of the Google login ImageView.
     */
    private void googleLoginClick() {
        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    /**
     * In this method we do an Itent to look for our google address
     */
    private void signIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    /**
     * Here is the signed account of google
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleResult(task);
        }
    }

    /**
     * Here we take the client account and result from the api
     *
     * @param task
     */
    private void handleGoogleResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(getApplicationContext(), "Log In Successfully", Toast.LENGTH_SHORT).show();
            firebaseGoogleAuth(account);
        } catch (ApiException e) {
            Toast.makeText(getApplicationContext(), "Log In Failed", Toast.LENGTH_SHORT).show();
            firebaseGoogleAuth(null);
            e.printStackTrace();
        }
    }

    /**
     * in this method if the account is success it change to the mainActivity
     *
     * @param account
     */
    private void firebaseGoogleAuth(GoogleSignInAccount account) {
        try {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        USERUID = user.getUid();
                        //CollectUserData.updateUI(user);
                        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                }
            });
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Elija un correo", Toast.LENGTH_SHORT).show();
        }
    }
}