package fr.cned.emdsgil.suividevosfrais;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class TransfertActivity extends AppCompatActivity {

    private int nbEnvoye = 0;
    private int nbRecu = 0;
    private ArrayList<TransfertResult> resultats;
    private AccesDistant accesDistant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfert);
        setTitle("GSB : Transfert vers site");
        accesDistant = new AccesDistant(TransfertActivity.this);
        String nomVisiteur = Global.visiteur.getNom() + " " + Global.visiteur.getPrenom();
        ((TextView)findViewById(R.id.txtTransfertUtilisateur)).setText(nomVisiteur);
        transfertClic();
        deconnexionClic();
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
     * Transférer les données
     */
    private void commencerTransfert() {
        this.nbEnvoye = 0;
        this.nbRecu = 0;
        resultats = new ArrayList<>();
        Set<Integer> setMois = Global.listFraisMois.keySet();
        for(Integer mois : setMois) {
            FraisMois unFrais = Global.listFraisMois.get(mois);
            if (!unFrais.getEnvoyee()) {
                JSONArray donnees = new JSONArray();
                donnees.put(Global.visiteur.getToken());
                donnees.put(mois);
                donnees.put(unFrais.getFraisJson());
                donnees.put(unFrais.getFraisHFJson());
                accesDistant.envoi("synchro", donnees);
                this.nbEnvoye++;
                unFrais.setEnvoyee();
            }
        }
        Serializer.serialize(Global.listFraisMois, TransfertActivity.this) ;
        afficheListe();
    }

    /**
     * Sur le le clic du bouton transfert
     */
    private void transfertClic() {
        findViewById(R.id.cmdTransfertMaintenant).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                commencerTransfert();
            }
        }) ;
    }

    /**
     * Sur le clic du bouton déconnexion
     */
    private void deconnexionClic() {
        findViewById(R.id.cmdDeconnexion).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Global.visiteur.majVisiteur("","","");
                retourActivityPrincipale();
            }
        }) ;
    }
    public void afficheListe() {
        Collections.sort(resultats);
        TextView etat = findViewById(R.id.txtTransfertEtat);
        etat.setText("Envoyé " + nbRecu + " mois sur " + nbEnvoye);
        ListView listView = findViewById(R.id.lstTransfertFait);
        TransfertAdapter adapter = new TransfertAdapter(TransfertActivity.this, resultats);
        listView.setAdapter(adapter) ;
    }

    public void transfertSucces(String mois) {
        TransfertResult succes = new TransfertResult(mois,"OK", true);
        nbRecu++;
        resultats.add(succes);
        afficheListe();
    }

    public void transfertErreur(String mois, String message) {
        TransfertResult erreur = new TransfertResult(mois,message, false);
        nbRecu++;
        resultats.add(erreur);
        afficheListe();

    }

    /**
     * Retour à l'activité principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(TransfertActivity.this, MainActivity.class) ;
        startActivity(intent) ;
    }
}
