package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    public static final String AWAY_KEY = "awayTeam";
    public static final String HOME_KEY = "homeTeam";
    public static final String AWAYIMAGE_KEY = "awayLogo";
    public static final String HOMEIMAGE_KEY = "homeLogo";

    private EditText awayInput;
    private EditText homeInput;
    private ImageView awayImage;
    private ImageView homeImage;

    public Uri homeUri;
    public Uri awayUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        awayInput = findViewById(R.id.away_team);
        homeInput = findViewById(R.id.home_team);
        awayImage = findViewById(R.id.away_logo);
        homeImage = findViewById(R.id.home_logo);
    }
    //TODO
    //Fitur Main Activity
    //1. Validasi Input Home Team
    //2. Validasi Input Away Team
    //3. Ganti Logo Home Team
    //4. Ganti Logo Away Team
    //5. Next Button Pindah Ke MatchActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            return;
        }

        if (requestCode == 1){
            if (data != null){
                try{
                    homeUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homeUri);
                    homeImage.setImageBitmap(bitmap);
                }catch (IOException e){
                    Toast.makeText(this, "Can't Load Image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }else if (requestCode == 2){
            if (data != null){
                try{
                    awayUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awayUri);
                    awayImage.setImageBitmap(bitmap);
                }catch (IOException e){
                    Toast.makeText(this, "Can't Load Image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleChangeHome(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    public void handleChangeAway(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 2);
    }

    public void handleNext(View view) {
        String homeName = homeInput.getText().toString();
        String awayName = awayInput.getText().toString();

        Intent i = new Intent(this, MatchActivity.class);

        if (homeName.isEmpty()){
            homeInput.setError("HomeTeam Name Can't Empty");
        }else if (awayName.isEmpty()){
            awayInput.setError("AwayTeam Name Can't Empty");
        }else if (homeUri == null){
            Toast.makeText(this, "Choose a Picture", Toast.LENGTH_SHORT).show();
            handleChangeHome(view);
        }else if ( awayUri == null){
            Toast.makeText(this, "Choose a Picture", Toast.LENGTH_SHORT).show();
            handleChangeAway(view);
        }else {
            i.putExtra(HOME_KEY, homeName);
            i.putExtra(AWAY_KEY, awayName);
            i.putExtra(HOMEIMAGE_KEY, homeUri.toString());
            i.putExtra(AWAYIMAGE_KEY, awayUri.toString());
            startActivity(i);
        }
    }
}
