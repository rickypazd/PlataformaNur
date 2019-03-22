package com.example.ricardopazdemiquel.plataformanur.layouts;

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
import com.example.ricardopazdemiquel.plataformanur.R;
import com.example.ricardopazdemiquel.plataformanur.dao.FactoryDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.HorariosOfertadosDAO;
import com.example.ricardopazdemiquel.plataformanur.dto.HorariosOfertados;
import com.example.ricardopazdemiquel.plataformanur.dto.MateriasOfertadas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorOfertas extends RecyclerView.Adapter<AdaptadorOfertas.MyViewHolder> {

    private List<MateriasOfertadas> listaOferta;
    private Context contexto;
    private AdaptadorOfertas.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AdaptadorOfertas(Context contexto, List<MateriasOfertadas> ofertas) {
        this.contexto = contexto;
        listaOferta = ofertas;
    }

    public AdaptadorOfertas(Context contexto, JSONArray ofertasJson) {
        this.contexto = contexto;

        listaOferta = new ArrayList<>();

        try {
            for (int i = 0; i < ofertasJson.length(); i++) {
                JSONObject obj = ofertasJson.getJSONObject(i);

                MateriasOfertadas objMateriasOfertadas = new MateriasOfertadas();

                objMateriasOfertadas.setLGRUPO_ID(obj.getInt("LGRUPO_ID"));
                objMateriasOfertadas.setLCENTRO_ID(obj.getInt("LCENTRO_ID"));
                objMateriasOfertadas.setSCENTRO_DSC(obj.getString("SCENTRO_DSC"));
                objMateriasOfertadas.setSMATERIA_DSC(obj.getString("SMATERIA_DSC"));
                objMateriasOfertadas.setLSEMESTRE(obj.getInt("LSEMESTRE"));
                objMateriasOfertadas.setLCREDITOS(obj.getInt("LCREDITOS"));
                objMateriasOfertadas.setLLABORATORIO(obj.getInt("LLABORATORIO"));
                objMateriasOfertadas.setDOCENTE(obj.getString("DOCENTE"));
                objMateriasOfertadas.setSCODMATERIA(obj.getString("SCODMATERIA"));
                objMateriasOfertadas.setCASILLA(obj.getString("CASILLA"));
                objMateriasOfertadas.setSCODGRUPO(obj.getString("SCODGRUPO"));
                objMateriasOfertadas.setSSEMANA(obj.getString("SSEMANA"));
                objMateriasOfertadas.setLESTADOGRUPO_ID(obj.getInt("LESTADOGRUPO_ID"));
                objMateriasOfertadas.setSESTADOGRUPO_DSC(obj.getString("SESTADOGRUPO_DSC"));
                objMateriasOfertadas.setSOBS1(obj.getString("SOBS1"));
                objMateriasOfertadas.setLPERIODO_ID(obj.getInt("LPERIODO_ID"));
                objMateriasOfertadas.setLCARRERA_ID(obj.getInt("LCARRERA_ID"));

                listaOferta.add(objMateriasOfertadas);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_oferta, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MateriasOfertadas obj = listaOferta.get(position);
        holder.tvNombreMateriaOfertada.setText(obj.getSMATERIA_DSC());
        holder.tvNombreDocente.setText(obj.getDOCENTE().trim());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, obj);
                }
            }
        });

        holder.image.setImageDrawable(TextDrawable.builder().buildRound(obj.getSMATERIA_DSC().charAt(0) + "", Color.LTGRAY));

        HorariosOfertadosDAO dao = FactoryDAO.getOrCreate().newHorariosOfertadosDAO();
        List<HorariosOfertados> horariosOfertados = dao.seleccionar(obj.getID());
        String dia = "";
        for (int i = 0; i < horariosOfertados.size(); i++) {
            String abr = String.valueOf(horariosOfertados.get(i).getSDIA_DSC().trim().charAt(0)) + String.valueOf(horariosOfertados.get(i).getSDIA_DSC().trim().charAt(1));
            abr = abr.toUpperCase();
            dia +=  abr + "  ";
        }
        holder.tvHorarioOf.setText(dia.trim());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNombreMateriaOfertada;
        public TextView tvNombreDocente;
        public TextView tvHorarioOf;
        public ImageView image;
        public CardView cardView;

        public MyViewHolder(View v) {
            super(v);
            tvNombreMateriaOfertada = v.findViewById(R.id.nombreMateriaOf);
            image = v.findViewById(R.id.image_view);
            tvNombreDocente = v.findViewById(R.id.nombreDocenteOf);
            tvHorarioOf = v.findViewById(R.id.horarioMateriaOf);

            cardView = v.findViewById(R.id.cardView);
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listaOferta.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, MateriasOfertadas obj);
    }

}
