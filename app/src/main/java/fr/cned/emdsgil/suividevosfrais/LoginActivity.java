package fr.cned.emdsgil.suividevosfrais;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker.OnDateChangedListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    // informations affichées dans l'activité
    private Integer annee ;
    private Integer mois ;
    private Integer qte ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("GSB : Connexion");
        // Mise en fonctionnement des différents clics
        cmdLogin_clic();
        cmdLoginLater_clic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.retour_accueil))) {
            retourActivityPrincipale() ;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Connexion à la base distante, afin d'authentifier l'utilisateur
     */
    private void cmdLogin_clic() {
        findViewById(R.id.cmdLogin).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

    /**
     * Ignorer l'authentification. Aucune donnée ne pourra être synchronisée
     */
    private void cmdLoginLater_clic() {
        findViewById(R.id.cmdLoginLater).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

    /**
     * Retour à l'activité principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class) ;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent) ;
    }
}
