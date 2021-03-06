package com.example.apptreineeongrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private ViewHolder mViewHolder = new ViewHolder();
    Typeface fonte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.mViewHolder.b_jogo = findViewById(R.id.botao_iniciar_game);

        fonte = Typeface.createFromAsset(getAssets(),"Press Start K.ttf");
        mViewHolder.b_jogo.setTypeface(fonte);

        mViewHolder.b_jogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TelaDificuldade.class);
               // intent.putExtra("iniciar", true);
                startActivity(intent);
            }
        });

    }

    public static class ViewHolder{
        Button b_jogo;
    }

}