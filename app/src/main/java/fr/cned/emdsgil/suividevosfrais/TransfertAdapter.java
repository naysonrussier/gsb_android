package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

class TransfertAdapter extends BaseAdapter {

    private final ArrayList<TransfertResult> resultats ; // liste des frais du mois
    private final LayoutInflater inflater ;
    private final TransfertActivity transfertActivity;

    /**
     * Constructeur de l'adapter pour valoriser les propriétés
     * @param context Accès au contexte de l'application
     * @param resultats Liste des frais hors forfait
     */
    public TransfertAdapter(Context context, ArrayList<TransfertResult> resultats) {
        inflater = LayoutInflater.from(context) ;
        this.resultats = resultats ;
        this.transfertActivity = (TransfertActivity)context;
    }

    /**
     * retourne le nombre d'éléments de la listview
     */
    @Override
    public int getCount() {
        return resultats.size() ;
    }

    /**
     * retourne l'item de la listview à un index précis
     */
    @Override
    public Object getItem(int index) {
        return resultats.get(index) ;
    }

    /**
     * retourne l'index de l'élément actuel
     */
    @Override
    public long getItemId(int index) {
        return index;
    }

    /**
     * structure contenant les éléments d'une ligne
     */
    private class ViewHolder {
        TextView txtListTransfertMois ;
        TextView txtListTransfertCommentaire ;
    }

    /**
     * Affichage dans la liste
     */
    @Override
    public View getView(int index, View convertView, final ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null) {
            holder = new ViewHolder() ;

            convertView = inflater.inflate(R.layout.layout_liste_transfert, parent, false) ;
            holder.txtListTransfertMois = convertView.findViewById(R.id.txtListTransfertMois);
            holder.txtListTransfertCommentaire = convertView.findViewById(R.id.txtListTransfertCommentaire);
            convertView.setTag(holder) ;
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.txtListTransfertMois.setText(resultats.get(index).getMois());
        holder.txtListTransfertCommentaire.setText(resultats.get(index).getCommentaire()) ;
        if (resultats.get(index).getSucces()) {
            holder.txtListTransfertCommentaire.setTextColor(0xff00ff00);
        } else {
            holder.txtListTransfertCommentaire.setTextColor(0xffff0000);
        }
        return convertView ;
    }

}
