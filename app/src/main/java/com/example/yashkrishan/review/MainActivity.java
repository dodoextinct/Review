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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button registerbutton;
    private EditText mEditText;
    private EditText mEditText2;
    private TextView mTextView;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(MainActivity.this, loginactivity.class));
        }
        progressDialog = new ProgressDialog(this);
        registerbutton = (Button) findViewById(R.id.login);
        mEditText = (EditText) findViewById(R.id.email);
        mEditText2 = (EditText) findViewById(R.id.password);
        mTextView = (TextView) findViewById(R.id.text);

        registerbutton.setOnClickListener(this);
        mTextView.setOnClickListener(this);
    }

    private void registerUser(){
        String email = mEditText.getText().toString().trim();
        String password = mEditText2.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email, Please!!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password, Please!!", Toast.LENGTH_LONG).show();
            return;
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
                            startActivity(new Intent(MainActivity.this, loginactivity.class));

                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Could not registered", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == registerbutton){
            registerUser();
        }
        if(view == mTextView){
            finish();
            startActivity(new Intent(this, loginactivity.class));
        }
    }
}
