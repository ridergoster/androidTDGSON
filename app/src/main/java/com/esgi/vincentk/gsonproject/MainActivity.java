package com.esgi.vincentk.gsonproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import butterknife.Bind;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text) TextView textView;
    static final String URL_GITHUB = "https://api.github.com/users/florent37";
    final Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(URL_GITHUB)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                final GithubUser user = gson.fromJson(json, GithubUser.class);
                System.out.println("Converted Java object : " + user.getEmail());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Email: " + user.getEmail() + "\nName: " + user.getName() + "\nNbRepo: " + user.getPublic_repos() + " NbFollower: " + user.getFollowers());
                    }
                });
            }
        });
    }
}
