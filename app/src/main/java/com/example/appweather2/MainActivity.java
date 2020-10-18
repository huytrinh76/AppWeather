package com.example.appweather2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch, btnChangeActivity;
    TextView txtName;
    TextView txtCountry;
    TextView txtTemp;
    TextView txtStatus;
    TextView txtHumidity;
    TextView txtCloud;
    TextView txtWind;
    TextView txtDay;
    ImageView imgIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city=edtSearch.getText().toString();
                GetCurrentWeatherData(city);
            }
        });
    }

    public void GetCurrentWeatherData(String data){
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url="http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=506f7fc40a0f8d5126b01c345fac7fbf";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String day=jsonObject.getString("dt");
                            String name=jsonObject.getString("name");
                            txtName.setText("Tên thành phố: "+name);

                            long l=Long.valueOf(day);
                            Date date=new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day=simpleDateFormat.format(date);

                            txtDay.setText(Day);
                            JSONArray jsonArrayWeather=jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0);
                            String status=jsonObjectWeather.getString("main");
                            String icon=jsonObjectWeather.getString("icon");

                            Picasso.get() .load("http://openweathermap.org/img/wn/"+icon+"@2x.png") .into(imgIcon);
                            txtStatus.setText(status);

                            JSONObject jsonObjectMain=jsonObject.getJSONObject("main");
                            String nhietdo=jsonObjectMain.getString("temp");
                            String doam=jsonObjectMain.getString("humidity");

                            Double a=Double.valueOf(nhietdo);
                            String Nhietdo=String.valueOf(a.intValue());

                            txtTemp.setText(Nhietdo+"℃");
                            txtHumidity.setText(doam+"%");

                            JSONObject jsonObjectWind=jsonObject.getJSONObject("wind");
                            String gio=jsonObjectWind.getString("speed");
                            txtWind.setText(gio+"m/s");

                            JSONObject jsonObjectClouds=jsonObject.getJSONObject("clouds");
                            String may=jsonObjectClouds.getString("all");
                            txtCloud.setText(may+"%");

                            JSONObject jsonObjectSys=jsonObject.getJSONObject("sys");
                            String country=jsonObjectSys.getString("country");
                            txtCountry.setText("Tên quốc gia: "+country);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    private void Anhxa(){
        edtSearch=(EditText) findViewById(R.id.edittextSearch);
        btnSearch=(Button) findViewById(R.id.buttonSearch);
        btnChangeActivity=(Button) findViewById(R.id.buttonChangeActivity);
        txtName=(TextView) findViewById(R.id.textviewName);
        txtCountry=(TextView) findViewById(R.id.textviewCountry);
        txtTemp=(TextView) findViewById(R.id.textviewTemp);
        txtStatus=(TextView) findViewById(R.id.textiewStatus);
        txtHumidity=(TextView) findViewById(R.id.textviewHumidity);
        txtCloud=(TextView) findViewById(R.id.textviewCloud);
        txtWind=(TextView) findViewById(R.id.textviewWind);
        txtDay=(TextView) findViewById(R.id.textviewDay);
        imgIcon=(ImageView) findViewById(R.id.imageIcon);
    }
}