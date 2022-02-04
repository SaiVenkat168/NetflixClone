package com.example.netflixproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SignInActivity extends AppCompatActivity {
TextView d,c1;
ProgressBar pb;
FirebaseAuth auth;
FirebaseDatabase database;
FirebaseFirestore firebaseFirestore;
DocumentReference documentReference;
Date today,validdate;
EditText email,password;
String authmail,authpassword,Userid,vadate,resetemail;
TextView buttonsignin,siginup,forgotpasswordextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        firebaseFirestore=FirebaseFirestore.getInstance();
        Calendar c=Calendar.getInstance();
        today=c.getTime();
        c.add(Calendar.MONTH,1);
        validdate=c.getTime();


        email=findViewById(R.id.emailedittext);
        password=findViewById(R.id.passwordedittext);
        d=findViewById(R.id.d);
        c1=findViewById(R.id.c);
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
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setText("");
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
                                        Userid=auth.getCurrentUser().getUid();
                                        documentReference=firebaseFirestore.collection("Users").document(Userid);
                                                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                                        validdate=documentSnapshot.getDate("Valid_date");
                                                        if(validdate.compareTo(today)>=0)
                                                        {
                                                            startActivity(new Intent(SignInActivity.this, MainScreen.class));
                                                            pb.setVisibility(View.GONE);
                                                            Toast.makeText(SignInActivity.this, "Login is Successful", Toast.LENGTH_SHORT).show();

                                                        }
                                                        else
                                                        {
                                                            //startActivity(new Intent(SignInActivity.this, MainScreen.class));
                                                            pb.setVisibility(View.GONE);
                                                            Toast.makeText(SignInActivity.this, "Plan Expired", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(SignInActivity.this,PaymentOverdue.class));
                                                        }
                                                    }
                                                });
                                       }
                                    else
                                    {
                                        if(task.getException() instanceof FirebaseNetworkException) {
                                            pb.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                                        }
                                        if(task.getException() instanceof FirebaseAuthInvalidUserException) {
                                            pb.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "User doesn't exist", Toast.LENGTH_SHORT).show();
                                        }
                                        if(task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                            pb.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "Wrong email id and password", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        Toast.makeText(SignInActivity.this, task.getException().getMessage().toString() + "", Toast.LENGTH_SHORT).show();
                                    }
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
                if(email.getText().toString().length()<10 || email.getText().toString().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
                {
                    AlertDialog.Builder passwordreset=new AlertDialog.Builder(v.getContext());
                    passwordreset.setTitle("Reset Password?");
                    passwordreset.setMessage("Press Yes to recieve reset link");
                    passwordreset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            resetemail=email.getText().toString();
                            auth.sendPasswordResetEmail(resetemail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {
                                    Toast.makeText(getApplicationContext(), "Email reset link se", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed to send reset link", Toast.LENGTH_SHORT).show();
                                    
                                }
                            });

                        }
                    });
                    passwordreset.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    passwordreset.create().show();
                }
                else
                {
                    email.setError("Enter a valid email");
                }

                //Toast.makeText(SignInActivity.this, "Forgot password", Toast.LENGTH_SHORT).show();
            }
        });


//        if(auth.getCurrentUser()!=null)
//            startActivity(new Intent(SignInActivity.this,MainScreen.class));

    }
}