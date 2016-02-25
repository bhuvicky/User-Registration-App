package com.bhuvanesh.sqlitetraining;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayUserProfileActivity extends AppCompatActivity {

    private String[] userInfo, title;
    private int[] icon = {R.drawable.id, R.drawable.user, R.drawable.email, R.drawable.password, R.drawable.dialer, R.drawable.id, R.drawable.dialer};
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_profile);
        Resources res = getResources();
        title = res.getStringArray(R.array.title);
        lv = (ListView) findViewById(R.id.lvUserProfile);

        Intent intent = getIntent();
        userInfo = new String[7];
        userInfo[0] = intent.getStringExtra("ID_KEY");
        userInfo[1] = intent.getStringExtra("NAME_KEY");
        userInfo[2] = intent.getStringExtra("EMAIL_KEY");
        userInfo[3] = intent.getStringExtra("PASSWORD_KEY");
        userInfo[4] = intent.getStringExtra("MOBILE_NO_KEY");
        userInfo[5] = intent.getStringExtra("STATE_KEY");
        userInfo[6] = intent.getStringExtra("DISTRICT_KEY");

        UserProfileInformationAdapter adapter = this.new UserProfileInformationAdapter(this);
        lv.setAdapter(adapter);
    }

    class UserProfileInformationAdapter extends ArrayAdapter {

        Context context;

        UserProfileInformationAdapter(Context context) {
            super(context, R.layout.single_row, R.id.textView1, title);
            this.context = context;
        }

        class MyViewHolder {
            ImageView ivIcon;
            TextView tvTitle;
            TextView tvDescription;
            MyViewHolder(View row) {
                ivIcon = (ImageView) row.findViewById(R.id.imageView);
                tvTitle = (TextView) row.findViewById(R.id.textView1);
                tvDescription = (TextView) row.findViewById(R.id.textView2);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            MyViewHolder holder;
            if(row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.single_row, parent, false);
                holder = new MyViewHolder(row);
                row.setTag(holder);
                Log.d("view holder", "first time row created");
            }

            /*ImageView ivIcon = (ImageView) row.findViewById(R.id.imageView);
            TextView tvTitle = (TextView) row.findViewById(R.id.textView1);
            TextView tvDescription = (TextView) row.findViewById(R.id.textView2);*/
            else {
                holder = (MyViewHolder) row.getTag();
                Log.d("view holder", "recycling happens");
            }
            holder.ivIcon.setImageResource(icon[position]);
            holder.tvTitle.setText(title[position]);
            holder.tvDescription.setText(userInfo[position]);
            return row;
        }
    }
}
