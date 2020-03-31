package fr.cned.emdsgil.suividevosfrais;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

public class Visiteur {

    // propriétés de connexion
    private String nomBase = "bd.sqlite";
    private Integer versionBase = 1;
    private MySQLiteOpenHelper accesBD;
    private SQLiteDatabase bd;

    // propiétés du visiteur
    private String nom;
    private String prenom;
    private String id;
    private String token;

    /**
     * Constructeur
     * création de la connection à la bdd sqlite
     * @param context
     */
    public Visiteur(Context context){
        this.accesBD = new MySQLiteOpenHelper(context, nomBase, versionBase);
        this.getInfo();
    }

    /**
     * Met à jour les données du visiteur
     * @param nom
     * @param prenom
     * @param token
     */
    public void majVisiteur(String nom, String prenom, String token){
        this.bd = accesBD.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nom", nom);
        cv.put("prenom", prenom);
        cv.put("token", token);
        // exécution de la requête
        bd.update("visiteur", cv, "",null);
        this.getInfo();
    }

    /**
     * Récupère les infos du visiteur connecté
     */
    private void getInfo(){
        // accès en lecture à la BDD
        this.bd = accesBD.getReadableDatabase();
        String req = "select nom, prenom, token from visiteur;";
        // curseur pour récupérer le résultat de l'exécution de la requête
        Cursor curseur = bd.rawQuery(req, null);
        curseur.moveToFirst();
        // contrôle s'il y a au moins une ligne (donc un visiteur)
        if(!curseur.isAfterLast()){
            // récupération du champ
            nom = curseur.getString(0);
            prenom = curseur.getString(1);
            token = curseur.getString(2);
        }
        curseur.close();
    }


    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }


    public String getToken() {
        return token;
    }
}
