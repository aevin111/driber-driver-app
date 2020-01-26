package driber.bitsplease.com.driberdriver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class JobActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText jobID;
    Button findJob;
    String emailAddress;
    String password;
    String locationURL;
    String dateTime;
    String userID;

    private Response.ErrorListener createMyReqErrorListener()
    {
        return new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                String mafuyuServiceError = error.toString();
                Log.e("DriberBackendReply", mafuyuServiceError);
            }
        };
    }

    private Response.Listener<String> createMyReqSuccessListener()
    {
        return new Response.Listener<String>()
        {
            @Override
            public void  onResponse(String response)
            {
                JobActivity.this.locationURL = response;
                //Get DateTime
                RequestQueue queue2 = Volley.newRequestQueue(getApplicationContext());
                String mafuyuServiceUrl2 = "http://mafuyu.atwebpages.com/driber_api/database.php?action=getdatetime&email=" + emailAddress + "&password=" + password + "&jobid=" + jobID.getText().toString();
                StringRequest strRequest2 = new StringRequest(Request.Method.GET, mafuyuServiceUrl2, createMyReqSuccessListener2(), createMyReqErrorListener());
                queue2.add(strRequest2);
            }
        };
    }

    private Response.Listener<String> createMyReqSuccessListener2()
    {
        return new Response.Listener<String>()
        {
            @Override
            public void  onResponse(String response)
            {
                JobActivity.this.dateTime = response;
                //Get UserID
                RequestQueue queue3 = Volley.newRequestQueue(getApplicationContext());
                String mafuyuServiceUrl3 = "http://mafuyu.atwebpages.com/driber_api/database.php?action=getuserid&email=" + emailAddress + "&password=" + password + "&jobid=" + jobID.getText().toString();
                StringRequest strRequest3 = new StringRequest(Request.Method.GET, mafuyuServiceUrl3, createMyReqSuccessListener3(), createMyReqErrorListener());
                queue3.add(strRequest3);
            }
        };
    }

    private Response.Listener<String> createMyReqSuccessListener3()
    {
        return new Response.Listener<String>()
        {
            @Override
            public void  onResponse(String response)
            {
                JobActivity.this.userID = response;
                Intent intent = new Intent(JobActivity.this, JobInfo.class);
                intent.putExtra("locationURL", locationURL);
                intent.putExtra("dateTime", dateTime);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        };
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i("DriberJobFinder", "Starting job finder...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        jobID = (EditText) findViewById(R.id.jobID);
        findJob = (Button) findViewById(R.id.findJob);
        findJob.setOnClickListener(this);
        emailAddress = getIntent().getStringExtra("emailAddress");
        password = getIntent().getStringExtra("password");
        Log.i("DriberJobFinder", "Job finder started!");
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.findJob)
        {
            //Get URL coordinates
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String mafuyuServiceUrl = "http://mafuyu.atwebpages.com/driber_api/database.php?action=getcoords&email=" + emailAddress + "&password=" + password + "&jobid=" + jobID.getText().toString();
            StringRequest strRequest = new StringRequest(Request.Method.GET, mafuyuServiceUrl, createMyReqSuccessListener(), createMyReqErrorListener());
            queue.add(strRequest);
        }
    }
}
