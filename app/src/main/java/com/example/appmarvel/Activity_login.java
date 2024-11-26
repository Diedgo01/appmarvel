package com.example.appmarvel;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmarvel.service.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Activity_login extends AppCompatActivity {

    Button btn_login;
    EditText et_email, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.registerButton);
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                // Validar que los campos no estén vacíos
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Activity_login.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Llamar al método para realizar la petición
                login(email, password);
            }
        });
    }

    private void login(String email, String password) {
        // URL de la API
        String url = "http://10.16.50.167/api-marvel/public/index.php/users";

        // Crear el cuerpo de la petición
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("action", "login");

        // Convertir el cuerpo a JSON
        JSONObject jsonBody = new JSONObject(params);

        // Crear una cola de solicitudes
        RequestQueue queue = Volley.newRequestQueue(this);

        // Crear la solicitud POST
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Manejar la respuesta de la API
                            if (response.has("data")) {
                                String token = response.getString("data");
                                String message = response.getString("message");

                                SharedPrefManager.saveToken(Activity_login.this, token);

                                Toast.makeText(Activity_login.this, "Login exitoso: " + message, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Activity_login.this, MainActivity2.class);
                                startActivity(intent);

                                finish();
                            } else {
                                Toast.makeText(Activity_login.this, "Error desconocido", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Activity_login.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la API
                        try {
                            String responseBody = new String(error.networkResponse.data, "UTF-8");
                            JSONObject errorResponse = new JSONObject(responseBody);
                            String message = errorResponse.getString("message");
                            int statusCode = error.networkResponse.statusCode;

                            Toast.makeText(Activity_login.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            Log.e("LOGIN_ERROR", "Status code: " + statusCode + ", Message: " + message);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(Activity_login.this, "Error en la conexión" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Si necesitas agregar encabezados personalizados
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Agregar la solicitud a la cola
        queue.add(request);
    }
}


