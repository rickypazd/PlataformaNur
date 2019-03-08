package com.example.ricardopazdemiquel.plataformanur.layouts;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.ricardopazdemiquel.plataformanur.Objs.Periodo;
import com.example.ricardopazdemiquel.plataformanur.R;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;
import com.example.ricardopazdemiquel.plataformanur.dto.Notas;
import com.github.akashandroid90.imageletter.MaterialLetterIcon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorNotas extends RecyclerView.Adapter<AdaptadorNotas.MyViewHolder> {

    // private JSONArray listaNotas;
    private List<Notas> listaNotas;
    private Context contexto;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AdaptadorNotas(Context contexto, JSONArray notasJson) {
        this.contexto = contexto;

        listaNotas = new ArrayList<>();

        try {
            for (int i = 0; i < notasJson.length(); i++) {
                JSONObject cursor = notasJson.getJSONObject(i);

                Notas objNotas = new Notas();

                objNotas.setLGRUPO_ID(cursor.getInt("LGRUPO_ID"));
                objNotas.setLCENTRO_ID(cursor.getInt("LCENTRO_ID"));
                objNotas.setSCODCENTRO(cursor.getString("SCODCENTRO"));
                objNotas.setSCENTRO_DSC(cursor.getString("SCENTRO_DSC"));
                objNotas.setSCODMATERIA(cursor.getString("SCODMATERIA"));
                objNotas.setSSIGLA(cursor.getString("SSIGLA"));
                objNotas.setSMATERIA_DSC(cursor.getString("SMATERIA_DSC"));
                objNotas.setSCODGRUPO(cursor.getString("SCODGRUPO"));
                objNotas.setDOCENTE(cursor.getString("DOCENTE"));
                objNotas.setPAR1(cursor.getString("PAR1"));
                objNotas.setPAR2(cursor.getString("PAR2"));
                objNotas.setEXFINAL(cursor.getString("EXFINAL"));
                objNotas.setFINAL(cursor.getString("FINAL"));
                objNotas.setFMES1(cursor.getInt("FMES1"));
                objNotas.setFMES2(cursor.getInt("FMES2"));
                objNotas.setFMES3(cursor.getInt("FMES3"));
                objNotas.setFMES4(cursor.getInt("FMES4"));
                objNotas.setFMES5(cursor.getInt("FMES5"));
                objNotas.setFMES6(cursor.getInt("FMES6"));
                objNotas.setFMES7(cursor.getInt("FMES7"));
                objNotas.setFMES8(cursor.getInt("FMES8"));
                objNotas.setFMES9(cursor.getInt("FMES9"));
                objNotas.setFMES10(cursor.getInt("FMES10"));
                objNotas.setFMES11(cursor.getInt("FMES11"));
                objNotas.setFMES12(cursor.getInt("FMES12"));
                objNotas.setLPERIODO_ID(cursor.getInt("LPERIODO_ID"));
                objNotas.setLCARRERA_ID(cursor.getInt("LCARRERA_ID"));

                listaNotas.add(objNotas);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public AdaptadorNotas(Context contexto, List<Notas> lista) {
        this.listaNotas = lista;
        this.contexto = contexto;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_nota, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        RecyclerView.ViewHolder view = (MyViewHolder) holder;
        final Notas obj = listaNotas.get(position);

        holder.tvNombreMateria.setText(obj.getSMATERIA_DSC());
        holder.tvNombreDocente.setText(obj.getDOCENTE());
        holder.tvPeriodoMateria.setText(getPeriodo(obj.getLPERIODO_ID()));
        holder.image.setImageDrawable(TextDrawable.builder().buildRound(obj.getSMATERIA_DSC().charAt(0) + "", Color.LTGRAY));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, obj, position);
                }
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemClickListener == null) return false;
                onItemClickListener.onItemLongClick(view, obj, position);
                return true;
            }
        });

        if (obj.getSCENTRO_DSC().equals("PRESENCIAL")) {
            int faltas = getFaltas(obj);

            if (faltas > 0) {// amarillo
                holder.vEstadoAsistencia.setBackgroundColor(contexto.getResources().getColor(R.color.asistenciaAmarillo));
            }

            if (faltas > 3) {// naranja
                holder.vEstadoAsistencia.setBackgroundColor(contexto.getResources().getColor(R.color.asistenciaNaranja));
            }

            if (faltas >= 5) {// rojo
                holder.vEstadoAsistencia.setBackgroundColor(contexto.getResources().getColor(R.color.asistenciaRojo));
            }
        } else {
            int faltas = getFaltas(obj);

            if (faltas > 0) {
                holder.vEstadoAsistencia.setBackgroundColor(contexto.getResources().getColor(R.color.asistenciaRojo));
            }
        }
    }

    public int getFaltas(Notas nota) {
        return nota.getFMES1() + nota.getFMES2() + nota.getFMES3() + nota.getFMES4() +
                nota.getFMES5() + nota.getFMES6() +nota.getFMES7() +  nota.getFMES8() +
                nota.getFMES9() + nota.getFMES10() + nota.getFMES11() + nota.getFMES12();
    }

    public String getPeriodo(int id) {
        ArrayList<Periodo> periodos = Preferences.getPeriodos(contexto);

        for (Periodo periodo: periodos) {
            if (periodo.getLPERIODO_ID() == id) {
                return periodo.getSPERIODO_DSC();
            }
        }

        return "";
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNombreMateria;
        public TextView tvNombreDocente;
        public TextView tvPeriodoMateria;
        public ImageView image;
        public View vEstadoAsistencia;
        public CardView cardView;

        public MyViewHolder(View v) {
            super(v);

            image = v.findViewById(R.id.image_view);
            tvNombreMateria = v.findViewById(R.id.nombreMateria);
            tvNombreDocente = v.findViewById(R.id.nombreDocente);
            tvPeriodoMateria = v.findViewById(R.id.periodoMateria);
            vEstadoAsistencia = v.findViewById(R.id.estadoAsistencia);
            cardView = v.findViewById(R.id.cardView);
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listaNotas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Notas obj, int pos);
        void onItemLongClick(View view, Notas obj, int pos);
    }
}
