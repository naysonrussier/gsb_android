package fr.cned.emdsgil.suividevosfrais;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by emds on 25/02/2018.
 */

public class AccesDistant implements AsyncResponse {

    // constante
    private static final String SERVERADDR = "http://gsb.application.nayson-russier.fr/api.php";

    // propriétés
    private Context activity;

    /**
     * Constructeur
     */
    public AccesDistant(Context activity){
        this.activity = activity;
    }

    /**
     * Retour du serveur HTTP
     * @param output
     */
    @Override
    public void processFinish(String output) {
        // pour vérification, affiche le contenu du retour dans la console
        // découpage du message reçu
        String[] message = output.split("%");
        // contrôle si le retour est correct (au moins 2 cases)
        if (message.length >= 2) {
            if (message[0].equals("connexion_succes")) {

                ((LoginActivity)activity).loginSucces(message[1], message[2], message[3]);
            } else if(message[0].equals("connexion_erreur")) {
                ((LoginActivity)activity).loginErreur();
            } else if (message[0].equals("synchro_succes")) {
                ((TransfertActivity)activity).transfertSucces(message[1]);
            } else if (message[0].equals("synchro_erreur")) {
                ((TransfertActivity)activity).transfertErreur(message[1],message[2]);

            }
        }
    }

    /**
     * Envoi de données vers le serveur distant
     * @param operation information précisant au serveur l'opération à exécuter
     * @param lesDonneesJSON les données à traiter par le serveur
     */
    public void envoi(String operation, JSONArray lesDonneesJSON){
        AccesHTTP accesDonnees = new AccesHTTP();
        // lien avec AccesHTTP pour permettre à delegate d'appeler la méthode processFinish
        // au retour du serveur
        accesDonnees.delegate = this;
        // ajout de paramètres dans l'enveloppe HTTP
        accesDonnees.addParam("action", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON.toString());
        // envoi en post des paramètres, à l'adresse SERVERADDR
        accesDonnees.execute(SERVERADDR);
    }

}
