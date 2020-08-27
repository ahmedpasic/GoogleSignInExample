package ba.unsa.etf.rma.seminarskiapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private SignInButton signInBtn;
    private GoogleSignInClient gsic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gsio = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsic = GoogleSignIn.getClient(this, gsio);

        signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(signInListener);
    }

    private View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent signInIntent = gsic.getSignInIntent();
            startActivityForResult(signInIntent, 0);
        }
    };

    @Override
    protected void onStart() {
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if(acc != null)
            startActivity(new Intent(MainActivity.this, SignedActivity.class));
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(MainActivity.this, SignedActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(MainActivity.this, "Greška pri prijavljivanju na Google račun", Toast.LENGTH_LONG).show();
        }
    }
}
