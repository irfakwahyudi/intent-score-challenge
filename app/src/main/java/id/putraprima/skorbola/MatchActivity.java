package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import static id.putraprima.skorbola.MainActivity.AWAYIMAGE_KEY;
import static id.putraprima.skorbola.MainActivity.AWAY_KEY;
import static id.putraprima.skorbola.MainActivity.HOMEIMAGE_KEY;
import static id.putraprima.skorbola.MainActivity.HOME_KEY;

public class MatchActivity extends AppCompatActivity {
    public static final String STATUS_KEY = "status" ;

    private TextView homeText, scoreHome;
    private TextView awayText, scoreAway;
    private ImageView homeImage;
    private ImageView awayImage;
    private Uri homeUri, awayUri;
    public int homeScore = 0;
    public int awayScore = 0;
    private String name = "";
    private TextView nameScorerHome, nameScorerAway;
    private String returnString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        awayText = findViewById(R.id.txt_away);
        homeText = findViewById(R.id.txt_home);
        awayImage = findViewById(R.id.away_logo);
        homeImage = findViewById(R.id.home_logo);
        scoreAway = findViewById(R.id.score_away);
        scoreHome = findViewById(R.id.score_home);
        nameScorerAway = findViewById(R.id.nameScorerAway);
        nameScorerHome = findViewById(R.id.nameScorerHome);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            awayText.setText(extras.getString(AWAY_KEY));
            homeText.setText(extras.getString(HOME_KEY));
            awayUri = Uri.parse(extras.getString(AWAYIMAGE_KEY));
            homeUri = Uri.parse(extras.getString(HOMEIMAGE_KEY));
            Bitmap bitmap = null;
            Bitmap bitmap2 = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homeUri);
                bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awayUri);
            }catch (IOException e){
                e.printStackTrace();
            }
            homeImage.setImageBitmap(bitmap);
            awayImage.setImageBitmap(bitmap2);
        }
    }
    //TODO
    //1.Menampilkan detail match sesuai data dari main activity
    //2.Tombol add score menambahkan memindah activity ke scorerActivity dimana pada scorer activity di isikan nama pencetak gol
    //3.Dari activity scorer akan mengirim kembali ke activity matchactivity otomatis nama pencetak gol dan skor bertambah +1
    //4.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang beserta nama pencetak gol ke ResultActivity, jika seri di kirim text "Draw",
    public void handleAddHomeScore(View view) {
        Intent i = new Intent(this, ScorerActivity.class);
        startActivityForResult(i, 1);
    }

    public void handleAddAwayScore(View view) {
        Intent i = new Intent(this, ScorerActivity.class);
        startActivityForResult(i, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK) {
                homeScore++;
                scoreHome.setText(String.valueOf(homeScore));

                returnString = data.getStringExtra("scorerName");
                name = returnString + "\n"+nameScorerHome.getText().toString();
                nameScorerHome.setText(name);
            }
        }else if (requestCode == 2){
            if (resultCode == RESULT_OK) {
                awayScore++;
                scoreAway.setText(String.valueOf(awayScore));

                returnString = data.getStringExtra("scorerName");
                name = returnString + "\n"+nameScorerAway.getText().toString();
                nameScorerAway.setText(name);
            }
        }
    }

    public void handleCek(View view) {
        String status = null;
        if (homeScore == awayScore ){
            status = "Name of Winning Draw";
        }else if (homeScore > awayScore){
            status = "Name of Winning : "+homeText.getText().toString()+"\n " +
                    "Scorer Name : \n"+nameScorerHome.getText().toString();
        }else if (homeScore < awayScore){
            status = "Name of Winning : "+awayText.getText().toString()+"\n " +
                    "Scorer Name : \n"+nameScorerAway.getText().toString();
        }
        Intent i = new Intent(this, ResultActivity.class);
        i.putExtra(STATUS_KEY, status);
        startActivity(i);
    }
}
