package driber.bitsplease.com.driberdriver;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JobInfo extends AppCompatActivity implements View.OnClickListener
{
    String locationURL;
    String dateTime;
    String userID;
    TextView userIDTxt;
    TextView dateAndTime;
    Button openMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_info);
        locationURL = getIntent().getStringExtra("locationURL");
        dateTime = getIntent().getStringExtra("dateTime");
        userID = getIntent().getStringExtra("userID");
        userIDTxt = (TextView) findViewById(R.id.userIDTxt);
        userIDTxt.setText(userID);
        dateAndTime = (TextView) findViewById(R.id.dateAndTime);
        dateAndTime.setText(dateTime);
        openMaps = (Button) findViewById(R.id.openMaps);
        openMaps.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.openMaps)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationURL));
            startActivity(browserIntent);
        }
    }
}
