package ba.unsa.etf.rma.seminarskiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SignedActivity extends AppCompatActivity {

    private TextView nameTV, emailTV, idTV;
    private Button signOutBtn;
    private ImageView profileIV;

    private GoogleSignInClient gsic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed);

        nameTV = findViewById(R.id.nameTV);
        emailTV = findViewById(R.id.emailTV);
        idTV = findViewById(R.id.idTV);
        signOutBtn = findViewById(R.id.signOutBtn);
        profileIV = findViewById(R.id.profileIV);

        GoogleSignInOptions gsio = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsic = GoogleSignIn.getClient(this, gsio);

        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(SignedActivity.this);

        if(acc != null) {
            String personName = acc.getDisplayName();
            String personGivenName = acc.getGivenName();
            String personFamilyName = acc.getFamilyName();
            String personEmail = acc.getEmail();
            String personId = acc.getId();
            Uri personPhoto = acc.getPhotoUrl();

            nameTV.setText(nameTV.getText() + personName);
            emailTV.setText(emailTV.getText() + personEmail);
            idTV.setText(idTV.getText() + personId);
            Glide.with(this).load(personPhoto).into(profileIV);

        }

        signOutBtn.setOnClickListener(signOutListener);
    }

    private View.OnClickListener signOutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signOut();
        }
    };

    private void signOut() {
        gsic.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SignedActivity.this,"Uspješno ste se odjavili",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignedActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}
