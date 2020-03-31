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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    // informations affichées dans l'activité
    private AccesDistant accesDistant;

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
                JSONArray identifiants = new JSONArray();
                TextView utilisateur = findViewById(R.id.txtLoginUtilisateur);
                TextView mdp = findViewById(R.id.txtLoginMdp);
                identifiants.put(utilisateur.getText());
                identifiants.put(mdp.getText());
                accesDistant = new AccesDistant(LoginActivity.this);
                accesDistant.envoi("connexion", identifiants);
            }
        });
    }

    /**
     * Ignorer l'authentification. Aucune donnée ne pourra être synchronisée
     */
    private void cmdLoginLater_clic() {
        findViewById(R.id.cmdLoginLater).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Global.ignorerConnexion = true;
                retourActivityPrincipale();
            }
        });
    }

    /**
     * Valide l'authentification
     * @param token
     * @param nom
     * @param prenom
     */
    public void loginSucces(String token, String nom, String prenom) {
        Global.visiteur.majVisiteur(nom, prenom, token);
        retourActivityPrincipale();
    }

    public void loginErreur() {
        ((TextView)findViewById(R.id.txtLoginIncorrect)).setVisibility(View.VISIBLE);
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
