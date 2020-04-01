package fr.cned.emdsgil.suividevosfrais;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe métier contenant les informations des frais d'un mois
 */
class FraisMois implements Serializable {

    private Integer mois; // mois concerné
    private Integer annee; // année concernée
    private Integer etape; // nombre d'étapes du mois
    private Integer km; // nombre de km du mois
    private Integer nuitee; // nombre de nuitées du mois
    private Integer repas; // nombre de repas du mois
    private Boolean envoyee; // transféré à la base distante
    private final ArrayList<FraisHf> lesFraisHf; // liste des frais hors forfait du mois


    public FraisMois(Integer annee, Integer mois) {
        this.annee = annee;
        this.mois = mois;
        this.etape = 0;
        this.km = 0;
        this.nuitee = 0;
        this.repas = 0;
        lesFraisHf = new ArrayList<>();
        /* Retrait du type de l'ArrayList (Optimisation Android Studio)
		 * Original : Typage explicit =
		 * lesFraisHf = new ArrayList<FraisHf>() ;
		*/
    }

    /**
     * Ajout d'un frais hors forfait
     *
     * @param montant Montant en euros du frais hors forfait
     * @param motif Justification du frais hors forfait
     */
    public void addFraisHf(Float montant, String motif, Integer jour) {
        lesFraisHf.add(new FraisHf(montant, motif, jour));
        envoyee = false;
    }

    /**
     * Suppression d'un frais hors forfait
     *
     * @param index Indice du frais hors forfait à supprimer
     */
    public void supprFraisHf(int index) {
        lesFraisHf.remove(index);
        envoyee = false;
    }

    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getEtape() {
        return etape;
    }

    public void setEtape(Integer etape) {
        this.etape = etape;
        envoyee = false;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
        envoyee = false;
    }

    public Integer getNuitee() {
        return nuitee;
    }

    public void setNuitee(Integer nuitee) {
        this.nuitee = nuitee;
        envoyee = false;
    }

    public Integer getRepas() {
        return repas;
    }

    public void setRepas(Integer repas) {
        this.repas = repas;
        envoyee = false;
    }

    public ArrayList<FraisHf> getLesFraisHf() {
        return lesFraisHf;
    }

    public Boolean getEnvoyee() {
        return envoyee;
    }

    public void setEnvoyee() {
        this.envoyee = true;
    }

    public JSONObject getFraisJson() {
        JSONObject donnees = new JSONObject();
        try {
            donnees.put("KM", this.km);
            donnees.put("ETP", this.etape);
            donnees.put("NUI", this.nuitee);
            donnees.put("REP", this.repas);
        } catch (JSONException e) {
        }
        return donnees;
    }

    public JSONArray getFraisHFJson() {
        JSONArray fraisHF = new JSONArray();
        this.lesFraisHf.size();
        for(int i = 0; i < this.lesFraisHf.size(); i++) {
            FraisHf unFrais = this.lesFraisHf.get(i);
            JSONObject fraisJson = new JSONObject();
            try {
                fraisJson.put("montant", unFrais.getMontant());
                fraisJson.put("motif", unFrais.getMotif());
                fraisJson.put("jour", unFrais.getJour());
            } catch (JSONException e) {
            }
            fraisHF.put(fraisJson);
        }
        return fraisHF;
    }
}
