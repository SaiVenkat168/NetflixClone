package com.example.netflixproject.mainscreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflixproject.R;
import com.example.netflixproject.activities.SignInActivity;
import com.example.netflixproject.modals.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Settings extends AppCompatActivity {
    ImageView logo;
    BottomNavigationView bottomNavigationView;
    TextView resetbtn,signoutbtn;
    EditText resetpassword;
    TextView email,plan,date;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user;
    FirebaseDatabase database;
    DocumentReference reference;
    String userId,mail,pla,time;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent l=new Intent(Settings.this, MainScreen.class);
        startActivity(l);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        auth=FirebaseAuth.getInstance();
        userId=auth.getCurrentUser().getUid();
        database=FirebaseDatabase.getInstance();
        logo=findViewById(R.id.setnetflixlogo);
        resetbtn=findViewById(R.id.resetpasswordbutton);
        signoutbtn=findViewById(R.id.signoutbutton);
        resetpassword=findViewById(R.id.resetpasswordedittext);
        email=findViewById(R.id.emailsettings);
        plan=findViewById(R.id.plansettings);
        date=findViewById(R.id.datesettings);

        user= FirebaseAuth.getInstance().getCurrentUser();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,MainScreen.class));
            }
        });

        bottomNavigationView=findViewById(R.id.navset);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.homeicon:
                        startActivity(new Intent(Settings.this,MainScreen.class));
                        break;
                    case R.id.searchicon:
                        startActivity(new Intent(Settings.this,Search.class));
                        break;
                    case R.id.settingsicon:
                        //startActivity(new Intent(MainScreen.this,Settings.class));
                        break;
                }
                return false;
            }
        });


        reference=firebaseFirestore.collection("Users").document(userId);
        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                mail= documentSnapshot.getString("Email");
                //Toast.makeText(getApplicationContext(), ""+mail, Toast.LENGTH_SHORT).show();
                pla= documentSnapshot.getString("Plan_cost");
                pla="₹ "+pla+"/month";
                email.setText(mail);
                plan.setText(pla);
                time=documentSnapshot.getDate("Valid_date").toString();
                date.setText(time);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseNetworkException)
                    Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Settings.this, SignInActivity.class));
            }
        });



        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             ProgressDialog progressDialog=new ProgressDialog(Settings.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if(resetpassword.getText().toString().length()>7)
                {
                    auth.signInWithEmailAndPassword(mail,resetpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            EditText changepassword = new EditText(view.getContext());
                            AlertDialog.Builder updatepassword = new AlertDialog.Builder(view.getContext());
                            updatepassword.setTitle("Update Password?");
                            updatepassword.setCancelable(false);
                            changepassword.setHint("New password");
                            changepassword.setSingleLine();
                            updatepassword.setView(changepassword);
                            updatepassword.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    progressDialog.show();
                                    String newpasswordstring = changepassword.getText().toString();
                                    if (newpasswordstring.length() > 7) {
                                        user.updatePassword(newpasswordstring).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                                                resetpassword.setText("");
                                                progressDialog.cancel();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                if (e instanceof FirebaseNetworkException)
                                                    Toast.makeText(getApplicationContext(), "NO internet connection", Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getApplicationContext(), "Password not updated", Toast.LENGTH_SHORT).show();
                                                progressDialog.cancel();
                                            }
                                        });

                                    } else {
                                        progressDialog.cancel();
                                        Toast.makeText(getApplicationContext(), "Password to short please retry ", Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });
                            updatepassword.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    resetpassword.setText("");
                                    progressDialog.cancel();
                                }
                            });
                            updatepassword.create().show();
                        }

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(e instanceof FirebaseNetworkException)
                            Toast.makeText(getApplicationContext(),"NO internet connection",Toast.LENGTH_SHORT).show();
                        if(e instanceof FirebaseAuthInvalidCredentialsException)
                            resetpassword.setError("Incorrect password");
                        else
                            resetpassword.setError("Incorrect password");
                        progressDialog.cancel();

                    }
                });

                }
                else
                {
                    resetpassword.setError("Password to short");
                    progressDialog.cancel();
                }

            }
        });

    }
}