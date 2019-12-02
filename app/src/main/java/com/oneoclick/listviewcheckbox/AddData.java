package com.oneoclick.listviewcheckbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



public class AddData extends AppCompatActivity {
    ConnectionClass connectionClass;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        connectionClass = new ConnectionClass();

        final Button btn1 = (Button) findViewById(R.id.buttonAdd);
        final TextView edtTxtId = (TextView) findViewById(R.id.editTextId);
        final TextView edtTxtName = (TextView) findViewById(R.id.editTextName);
        final TextView txtMsg = (TextView) findViewById(R.id.textViewMsg);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrData[] = {"aaa","bbb","ccc"};
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        message = "db not connected!!";
                        Toast.makeText(AddData.this, message, Toast.LENGTH_LONG);

                    } else {
                        String userId = edtTxtId.getText().toString();
                        String userName = edtTxtName.getText().toString();
                        for (int i = 0; i < arrData.length; i++) {
                            String strInsert = "Insert Into addName(name) Values('" + arrData[i] + "')";
                            Statement stmt1 = con.createStatement();
                            stmt1.executeUpdate(strInsert);
                        }


                        //message = " 1 Record Added";
                        //Toast.makeText(AddMember.this, message, Toast.LENGTH_SHORT);
                        txtMsg.setText("1 Record Added!");

                    } //if

                } catch (Exception ex ) {
                    message = " Exceptions";
                    Toast.makeText(AddData.this, message, Toast.LENGTH_SHORT);
                }//try

            }
        });


    }//void

}//main
