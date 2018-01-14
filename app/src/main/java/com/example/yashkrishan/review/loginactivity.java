package com.example.yashkrishan.review;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginactivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog progressDialog;
    private Button msigninButton;
    private EditText mEditTextView1;
    private EditText mEditTextView2;
    private TextView textView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(loginactivity.this, profile.class));
        }
            mEditTextView1 = (EditText) findViewById(R.id.email);
        mEditTextView2 = (EditText) findViewById(R.id.password);
        msigninButton = (Button) findViewById(R.id.login);
        textView = (TextView) findViewById(R.id.text);
        msigninButton.setOnClickListener(this);
        textView.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
    }

    private void userlogin(){
        String email = mEditTextView1.getText().toString().trim();
        String password = mEditTextView2.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email!!", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password!!", Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Registering...");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(loginactivity.this, profile.class));

                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(loginactivity.this, "Could not registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view == msigninButton){
            userlogin();
        }
        if(view == textView){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
