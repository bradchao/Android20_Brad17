package tw.org.iii.brad.brad17;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText account, passwd, realname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mesg = findViewById(R.id.mesg);

        account = findViewById(R.id.account);
        passwd = findViewById(R.id.passwd);
        realname = findViewById(R.id.realname);
    }
    // GET
    public void add(View view){
        String url = "http://10.0.103.206:8080/Brad/brad59.jsp?account="+
                account.getText().toString() + "&" +
                "passwd=" + passwd.getText().toString() + "&"+
                "realname=" + realname.getText().toString();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,
                                "OK",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("brad", error.toString());
                    }
                }
        );
        MainApp.queue.add(request);
    }

    //post
    public void add2(View view){
        String url = "http://10.0.103.39:8080/Brad/brad60.jsp";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,
                                "OK",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("brad", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("account",account.getText().toString());
                params.put("passwd",passwd.getText().toString());
                params.put("realname",realname.getText().toString());

                return params;
            }
        };
        MainApp.queue.add(request);
    }

    private TextView mesg;
    public void test1(View view){
        JsonArrayRequest request = new JsonArrayRequest(
                "http://10.0.103.39:8080/Brad/Brad24",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        parseJSON(response);
                    }
                },
                null
        );
        MainApp.queue.add(request);
    }

    private void parseJSON(JSONArray root){
        try{
            mesg.setText("");
            for (int i=0; i<root.length(); i++){
                JSONObject row = root.getJSONObject(i);
                mesg.append(row.getString("account") + "\n");
            }
        }catch (Exception e){
            Log.v("brad", e.toString());
        }
    }




}
