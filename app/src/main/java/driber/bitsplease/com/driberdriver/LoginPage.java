package driber.bitsplease.com.driberdriver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginPage extends AppCompatActivity implements View.OnClickListener
{
    Button loginButton;
    EditText emailAddress;
    EditText password;
    EditText driverID;

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
                String goodReply = "OK";
                String badReply = "Invalid email/password!";

                if (response.equals(goodReply))
                {
                    Log.i("DriberLoginEvent", "Credentials matches! Logging in.....");
                    Intent jobIntent = new Intent(LoginPage.this, JobActivity.class);
                    jobIntent.putExtra("emailAddress", emailAddress.getText().toString());
                    jobIntent.putExtra("password", password.getText().toString());
                    startActivity(jobIntent);
                }

                else if (response.equals(badReply))
                {
                    Log.i("DriberLoginEvent", badReply);
                    Toast.makeText(LoginPage.this, badReply, Toast.LENGTH_LONG).show();
                }

                else
                {
                    Log.i("DriberBackendReply", response);
                }
            }
        };
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.loginButton)
        {
            //Login using Mafuyu's backend serivce and Volley
            RequestQueue queue = Volley.newRequestQueue(this);
            String mafuyuServiceUrl = "http://mafuyu.atwebpages.com/driber_api/database.php?action=driverlogin" + "&email=" + emailAddress.getText().toString() + "&password=" + password.getText().toString() + "&driverid=" + driverID.getText().toString();
            StringRequest strRequest = new StringRequest(Request.Method.GET, mafuyuServiceUrl, createMyReqSuccessListener(), createMyReqErrorListener());
            queue.add(strRequest);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i("DriberServiceStart", "Starting Driber driver app...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        password = (EditText) findViewById(R.id.password);
        driverID = (EditText) findViewById(R.id.driverID);
        Log.i("DriberServiceStart", "App started!");
    }
}
