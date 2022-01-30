package com.example.netflixproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflixproject.R;
import com.example.netflixproject.mainscreens.MainScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
TextView d,c;
ProgressBar pb;
FirebaseAuth auth;
FirebaseDatabase database;
EditText email,password;
String authmail,authpassword;
TextView buttonsignin,siginup,forgotpasswordextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        email=findViewById(R.id.emailedittext);
        password=findViewById(R.id.passwordedittext);
        d=findViewById(R.id.d);
        c=findViewById(R.id.c);
        pb=findViewById(R.id.pb);
        pb.setVisibility(View.GONE);
        forgotpasswordextview=findViewById(R.id.forgotpasswordtextview);
        siginup=findViewById(R.id.textview);
        siginup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SwipeScreen.class));
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setText("");
                d.setText("The information collected by Google reCAPTACHA is subjected to the Google Privary Policy and Terms of Service,and is used for providing, maintaining and improving the reCAPTCHA service and for general security purpose (it is not used for personalised advertising by Google)");
            }
        });
        buttonsignin=findViewById(R.id.signinbutton);
        buttonsignin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pb.setVisibility(View.VISIBLE);
                if(email.getText().toString().length()<10 || email.getText().toString().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$") && password.getText().toString().length()>7)
                {
                    auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(SignInActivity.this, MainScreen.class));
                                        pb.setVisibility(View.GONE);
                                        Toast.makeText(SignInActivity.this, "Login is Successful", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(SignInActivity.this, task.getException().getMessage().toString() + "", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                {
                    if(email.getText().toString().length()==0)
                        email.setError("Feild cannot be empty");
                    if(password.getText().toString().length()==0)
                        password.setError("Feild cannot be empty");

                    if(email.getText().toString().length()<7 ||!email.getText().toString().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
                        email.setError("Enter Valid email");
                    else if(password.getText().toString().length()<8)
                        password.setError("Enter a valid password");
                    else
                    {
                        email.setError("Enter Valid email");
                        password.setError("Enter a valid password");
                    }
                }
            }
        });
        forgotpasswordextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignInActivity.this, "Forgot password", Toast.LENGTH_SHORT).show();
            }
        });


//        if(auth.getCurrentUser()!=null)
//            startActivity(new Intent(SignInActivity.this,MainScreen.class));

    }
}