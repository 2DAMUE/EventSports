package com.sai.eventsports.splash_login_register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.sai.eventsports.CollectData;
import com.sai.eventsports.entidades.Evento;
import com.sai.eventsports.entidades.User;
import com.sai.eventsports.principales.ActivityMain;
import com.sai.eventsports.R;

import java.util.List;
import java.util.Objects;

public class ActivityLogIn extends AppCompatActivity implements CollectData.Comunicacion {
    private TextInputLayout email,password;
    private Button btnLogIn;
    private TextView txt_forgot_password;
    private CollectData.Comunicacion comunicacion = this;
    //Firebase
    private static FirebaseAuth firebaseAuth;
    //Google
    private SignInButton google_login;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 54654;
    //Important!!
    public static String USERUID = null;
    private static int cont = 0;

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
        View view = getLayoutInflater().inflate(R.layout.activity_forgot_password,
                null);
        builder.setView(view);
        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextInputLayout correo = view.findViewById(R.id.emailForgot);
                String emailRecupera = Objects.requireNonNull(correo.getEditText()).getText().toString().trim();
                Log.d("El botoncito", " Funcaaaaa" + emailRecupera);
                firebaseAuth.setLanguageCode("es");
                firebaseAuth.sendPasswordResetEmail(emailRecupera).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Fallo en la Operación", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        // Here we create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        positiveButtonLL.gravity = Gravity.CENTER;
        positiveButton.setLayoutParams(positiveButtonLL);
    }

    //****************** CORREO ****************

    public void loginUser() {
        String correo = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
        String pwd = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
        String msgEmail = null,msgPass = null;

        if (TextUtils.isEmpty(correo)) {
            msgEmail = "Escriba un email";
            email.setError(msgEmail);
        } else if (TextUtils.isEmpty(pwd)) {
            msgPass = "Escriba una Contraseña";
            password.setError(msgPass);
        } else if (pwd.length() < 6) {
            msgPass = "Minimo 6 números";
            password.setError(msgPass);
        } else if (!isValidEmail(correo)) {
            msgEmail = "Este correo no es valido";
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
                        Toast.makeText(getApplicationContext(), "Fallo al Iniciar Sesión", Toast.LENGTH_SHORT).show();
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
                        CollectData.recogerUsers(comunicacion);
                        if (cont == 1){
                            String username = user.getEmail();
                            Intent intent = new Intent(getApplicationContext(), ActivityRegisterAlert.class);
                            intent.putExtra("userGoogle",username);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Elija un correo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void mandarUsuarios(List<User> users) {
        for (User u:users) {
            if (u.getUserID().equals(USERUID)) {
                cont = 1;
                break;
            }
        }
    }

    @Override
    public void mandarEventos(List<Evento> eventos) {}
}