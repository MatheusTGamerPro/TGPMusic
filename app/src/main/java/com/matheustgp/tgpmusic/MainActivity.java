package com.matheustgp.tgpmusic;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;
import com.matheustgp.tgpmusic.PlayerActivity;
import com.matheustgp.tgpmusic.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

	private ActivityMainBinding binding;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
    }
    
    public void setupPlayer() {
        Intent screen = new Intent(getApplicationContext(), PlayerActivity.class);
        startActivity(screen);
        finish();
    }
}
