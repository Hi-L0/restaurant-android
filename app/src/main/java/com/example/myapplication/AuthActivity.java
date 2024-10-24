package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private GoogleSignInClient myGoogleSignInClient;
    int RC_SIGN_IN= 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        Button loginBtn = findViewById(R.id.loginBtn);
        EditText emailInput = findViewById(R.id.loginEmailInput);
        EditText passwordInput = findViewById(R.id.loginPasswordInput);

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();

        myGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        Button googleSignInBtn = findViewById(R.id.gmailSignInBtn);

        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }


        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                if(email.isEmpty() || password.isEmpty() ){
                    Toast.makeText(AuthActivity.this,"please fill in the email and password", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(AuthActivity.this, CuisineListActivity.class);
                    startActivity(intent);
                }
//                finish();
            }
        });

        }
    private void googleSignIn() {
        Intent signInIntent = myGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            }catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
//                    map.put("profile",user.getPhotoUrl().toString());
                    if (user != null) {
                        Toast.makeText(AuthActivity.this, "Connecté : " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    }

//                    HashMap<String,Object> map = new HashMap<>();
//                    map.put("id",user.getUid());
//                    map.put("_name",user.getDisplayName());
//                    map.put("email",user.getEmail());
                }
                else {
                // Connexion échouée
                Toast.makeText(AuthActivity.this, "Échec de la connexion", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}