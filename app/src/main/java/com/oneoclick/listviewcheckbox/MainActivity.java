package com.oneoclick.listviewcheckbox;

import android.app.ActionBar;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



public class MainActivity extends AppCompatActivity {

    ConnectionClass connectionClass;
    EditText edtUserId, edtPass;
    Button btnTest;
    String message = "";

    ArrayList<HashMap<String, String>> MyArrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectionClass = new ConnectionClass();
// listView1
        final ListView lisView1 = (ListView)findViewById(R.id.listView1);

        MyArrList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;

        /*** Rows 1 ***/
        map = new HashMap<String, String>();
        map.put("ID", "1");
        map.put("Code", "TH");
        map.put("Country", "Thailand");
        MyArrList.add(map);

        /*** Rows 2 ***/
        map = new HashMap<String, String>();
        map.put("ID", "2");
        map.put("Code", "VN");
        map.put("Country", "Vietnam");
        MyArrList.add(map);

        /*** Rows 3 ***/
        map = new HashMap<String, String>();
        map.put("ID", "3");
        map.put("Code", "ID");
        map.put("Country", "Indonesia");
        MyArrList.add(map);

        /*** Rows 4 ***/
        map = new HashMap<String, String>();
        map.put("ID", "4");
        map.put("Code", "LA");
        map.put("Country", "Laos");
        MyArrList.add(map);

        /*** Rows 5 ***/
        map = new HashMap<String, String>();
        map.put("ID", "5");
        map.put("Code", "MY");
        map.put("Country", "Malaysia");
        MyArrList.add(map);

        lisView1.setAdapter(new CountryAdapter(this));

        // Check All
        Button btnCheckAll = (Button) findViewById(R.id.btnCheckAll);
        CheckBox checkbox = (CheckBox)findViewById(R.id.ColChk);
        btnCheckAll.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = lisView1.getAdapter().getCount();
                for (int i = 0; i < count; i++) {
                    LinearLayout itemLayout = (LinearLayout)lisView1.getChildAt(i); // Find by under LinearLayout
                    CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.ColChk);
                    checkbox.setChecked(true);
                }
            }
        });

        // Clear All
        Button btnClearAll = (Button) findViewById(R.id.btnClearAll);
        btnClearAll.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = lisView1.getAdapter().getCount();
                for (int i = 0; i < count; i++) {
                    LinearLayout itemLayout = (LinearLayout)lisView1.getChildAt(i); // Find by under LinearLayout
                    CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.ColChk);
                    checkbox.setChecked(false);
                }
            }
        });
        // Get Item Checked

        Button btnGetItem = (Button) findViewById(R.id.btnGetItem);
        final TextView textView1 = (TextView) findViewById(R.id.textView3);

        btnGetItem.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int count = lisView1.getAdapter().getCount();
                int cntForComma = 1;
                String sqlInsert = "insert into addName(name) values('";
                for (int i = 0; i < count; i++) {
                    LinearLayout itemLayout = (LinearLayout)lisView1.getChildAt(i); // Find by under LinearLayout
                    CheckBox checkbox = (CheckBox)itemLayout.findViewById(R.id.ColChk);
                    if(checkbox.isChecked())
                    {
                        if (cntForComma > 1) {
                            sqlInsert = sqlInsert.toString() + ",('";
                    };
                        Log.d("Item "+String.valueOf(i), checkbox.getTag().toString());
                        sqlInsert = sqlInsert.toString() + checkbox.getTag().toString()+"')";
                        cntForComma +=1;
                        //Toast.makeText(MainActivity.this,checkbox.getTag().toString() ,Toast.LENGTH_LONG).show();
                    }
                }
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        message = "db not connected!!";
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG);

                    } else {
                        Statement stmt1 = con.createStatement();
                        stmt1.executeUpdate(sqlInsert);
                        textView1.setText("");  //บรรทัดนี้เอาไว้ดีบัก แสดงสตริงที่สร้างขึ้นว่าเป็นชุดคำสั่งที่สมบูรณ์หรือไม่
                        textView1.setText(sqlInsert.toString()); //เอาไปแสดงที่ textview3 ที่จอ
                   };
                } catch (Exception ex) {
                    message = ex.getMessage();
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
                }
            }
        });

    }


    public class CountryAdapter extends BaseAdapter
    {
        private Context context;

        public CountryAdapter(Context c)
        {
            //super( c, R.layout.activity_column, R.id.rowTextView, );
            // TODO Auto-generated method stub
            context = c;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return MyArrList.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.activity_column, null);

            }

            // ColID
            TextView txtID = (TextView) convertView.findViewById(R.id.ColID);
            txtID.setText(MyArrList.get(position).get("ID") +".");

            // ColCode
            TextView txtCode = (TextView) convertView.findViewById(R.id.ColCode);
            txtCode.setText(MyArrList.get(position).get("Code"));

            // ColCountry
            TextView txtCountry = (TextView) convertView.findViewById(R.id.ColCountry);
            txtCountry.setText(MyArrList.get(position).get("Country"));

            // ColChk
            CheckBox Chk = (CheckBox) convertView.findViewById(R.id.ColChk);
            Chk.setTag(MyArrList.get(position).get("Code"));

            return convertView;

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


    //ไม่ได้ใช้
    public class DoInsert extends AsyncTask {
        String msg1 = "11";

      //  String name1 = checkbox.getTag().toString();

        @Override
        protected Object doInBackground(Object[] objects) {
            Connection con = connectionClass.CONN();
            try {
                String insert1 = "insert into addName values(" + msg1 + ")";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(insert1);

            } catch (Exception ex) {
                 msg1 = "Fail";

            }
            return msg1;
        }
    }

}//main



