
package com.example.virtualmagic;

        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Toast;

        import com.google.android.gms.auth.api.signin.GoogleSignIn;
        import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
        import com.google.android.gms.auth.api.signin.GoogleSignInClient;
        import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
        import com.google.android.gms.common.GooglePlayServicesRepairableException;
        import com.google.android.gms.common.SignInButton;
        import com.google.android.gms.common.api.ApiException;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthCredential;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.GoogleAuthCredential;
        import com.google.firebase.FirebaseApp;
        import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 3744;
    private static final String TAG = MainActivity.class.getName();
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signInButton = findViewById(R.id.sigin_button);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("255018045797-erq887edpkku0pjm4152c3mh9r5e0286.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            Toast.makeText(this, "loging_success", Toast.LENGTH_SHORT).show();
            MainActivity.isIt=!MainActivity.isIt;
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {

        signIn();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {


            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            startActivity(new Intent(this, MainActivity.class));
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            Toast.makeText(this, "exception occured" + e.getStatusCode(), Toast.LENGTH_SHORT).show();


        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {


        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        finish();
    }
}




