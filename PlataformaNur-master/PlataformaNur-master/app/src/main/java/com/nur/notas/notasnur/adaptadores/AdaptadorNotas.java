package com.nur.notas.notasnur.adaptadores;

import android.content.Context;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.nur.notas.notasnur.objetos.Periodo;
import com.nur.notas.notasnur.R;
import com.nur.notas.notasnur.utiles.Preferences;
import com.nur.notas.notasnur.dao.FactoryDAO;
import com.nur.notas.notasnur.dao.HorariosMateriasDAO;
import com.nur.notas.notasnur.dto.HorariosMaterias;
import com.nur.notas.notasnur.dto.Notas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorNotas extends RecyclerView.Adapter<AdaptadorNotas.MyViewHolder> {

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
        holder.tvPeriodoMateria.setText(getHorario(obj));
        holder.image.setImageDrawable(TextDrawable.builder().buildRound(obj.getSMATERIA_DSC().charAt(0) + "", Color.LTGRAY));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, obj, position);
                }
            }
        });


        int[] imagenes = {R.drawable.diagram,
                R.drawable.flask,
                R.drawable.form,
                R.drawable.laptop,
                R.drawable.rocket,
                R.drawable.trophy};

        int random = (int) (Math.random() * imagenes.length);

        if (position >= imagenes.length) {
            Glide.with(contexto).load(imagenes[random]).into(holder.imgMateria);
        } else {
            Glide.with(contexto).load(imagenes[position]).into(holder.imgMateria);
        }


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

    private int getFaltas(Notas nota) {
        return nota.getFMES1() + nota.getFMES2() + nota.getFMES3() + nota.getFMES4() +
                nota.getFMES5() + nota.getFMES6() +nota.getFMES7() +  nota.getFMES8() +
                nota.getFMES9() + nota.getFMES10() + nota.getFMES11() + nota.getFMES12();
    }

    private String getHorario(Notas objNota) {
        HorariosMateriasDAO horarioDao = FactoryDAO.getOrCreate().newHorariosMateriasDAO();
        List<HorariosMaterias> listaHorarios = horarioDao.seleccionar(objNota.getLGRUPO_ID() + "");

        String horarios = "";
        // caso 1: dif dias, mismo horario, misma aula EJ: lun-jue 07:00 - 09:00 Aula 303
        // caso 2: dif dias, mismo horario, dif aulas EJ: lun 07:00 - 09:00 Aula 301\njue 07:00 - 09:00 Aula 303
        // caso 3: dif dias, dif horarios, misma aula EJ: lun 07:00 - 09:00 Aula \njue 07:30 - 09
        boolean mismoHorario = true;
        boolean mismaAula = true;

        if (listaHorarios.size() > 1) {
            String entrada = "", salida = "", aula = "";

            for (int i = 0; i < listaHorarios.size(); i++) {
                HorariosMaterias horario = listaHorarios.get(i);

                if (entrada.equals("")) {
                    entrada = horario.getENTRADA();
                }

                if (salida.equals("")) {
                    salida = horario.getSALIDA();
                }

                if (aula.equals("")) {
                    aula = horario.getSAULA_DSC();
                }

                if (i == 0) {
                    continue;
                }

                if (!horario.getENTRADA().equals(entrada) || !horario.getSALIDA() .equals(salida)) {
                    mismoHorario = false;
                }

                if (!horario.getSAULA_DSC().equals(aula)) {
                    mismaAula = false;
                }
            }
        }

        for (int i = 0; i < listaHorarios.size(); i++) {
            HorariosMaterias horario = listaHorarios.get(i);

            if (mismoHorario && mismaAula) {
                horarios += getDiaAbreviado(horario.getSDIA_DSC()) + " ";

                if (i == listaHorarios.size() - 1) {
                    if (objNota.getSCENTRO_DSC().equals("A DISTANCIA")) {
                        horarios += "Semana " + objNota.getSCODGRUPO() + " ";
                    }

                    horarios += horario.getENTRADA() + " - " + horario.getSALIDA() + " Aula " + horario.getSAULA_DSC() + "\n";
                }
            } else {
                horarios += horario.getSDIA_DSC() + " ";

                if (objNota.getSCENTRO_DSC().equals("A DISTANCIA")) {
                    horarios += "Semana " + objNota.getSCODGRUPO() + " ";
                }

                horarios += horario.getENTRADA() + " - " + horario.getSALIDA() + " Aula " + horario.getSAULA_DSC() + "\n";
            }
        }

        return horarios;
    }

    private String getDiaAbreviado(String dia) {
        if (dia.isEmpty()) {
            return "";
        }

        return dia.substring(0, 3);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNombreMateria;
        public TextView tvNombreDocente;
        public TextView tvPeriodoMateria;
        public ImageView image, imgMateria;
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
            imgMateria = v.findViewById(R.id.imgMateria);
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
    }
}
