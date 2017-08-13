package com.example.amitdasgupta.realtimechatapplicationusingfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.text.format.DateFormat;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
   private static int sign_in_request=1;
    private FirebaseListAdapter<ChatAdapter> adapter;
    ConstraintLayout activity_main;
    FloatingActionButton fab;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main=(ConstraintLayout)findViewById(R.id.constraint);
        fab=(FloatingActionButton)findViewById(R.id.send);
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),sign_in_request);
        }
        else 
        {
            Snackbar.make(activity_main,"Welcome"+FirebaseAuth.getInstance().getCurrentUser().getEmail(),Snackbar.LENGTH_LONG).show();
            displayChatMessage();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et=(EditText)findViewById(R.id.message);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatAdapter(et.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                et.setText("");

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==sign_in_request && resultCode==RESULT_OK)
        {
            Snackbar.make(activity_main,"Succesfully signed in welcome",Snackbar.LENGTH_SHORT).show();
            displayChatMessage();
        }
        else{
            Snackbar.make(activity_main,"Please try again later...........",Snackbar.LENGTH_SHORT).show();
            finish();}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_sign_out)
        {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {    Snackbar.make(activity_main,"You have been sign_out",Snackbar.LENGTH_SHORT).show();
                        finish();}
                    else {
                        Snackbar.make(activity_main,"Please try again",Snackbar.LENGTH_SHORT).show();
                    }


                }
            });
        }
        return true;
    }

    private void displayChatMessage() {
        ListView lv=(ListView)findViewById(R.id.listview);
        adapter=new FirebaseListAdapter<ChatAdapter>(this,ChatAdapter.class,R.layout.list_item,FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatAdapter model, int position) {
                TextView tm,tu,td;
                tu=(TextView)v.findViewById(R.id.username);
                tm=(TextView)v.findViewById(R.id.message);
                td=(TextView)v.findViewById(R.id.time);
                tm.setText(model.getMessagetext());
                tu.setText(model.getMessageuser());
                td.setText(DateFormat.format("dd-MM--yyyy (HH:mm:ss)",model.getMessagetime()));
            }
        };
        lv.setAdapter(adapter);
    }
}
