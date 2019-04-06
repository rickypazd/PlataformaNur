package com.example.ricardopazdemiquel.plataformanur;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ricardopazdemiquel.plataformanur.dao.FactoryDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.HorariosOfertadosDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.MateriasOfertadasDAO;
import com.example.ricardopazdemiquel.plataformanur.dto.HorariosOfertados;
import com.example.ricardopazdemiquel.plataformanur.dto.MateriasOfertadas;

import org.w3c.dom.Text;

import java.util.List;

public class OfertaDialog extends AppCompatDialogFragment {

    private TextView tvPresencialOferta;
    private TextView tvSemestreOferta;
    private TextView tvCreditosOferta;
    private TextView tvDocenteOferta;
    private TextView tvGrupoOferta;
    private TextView tvNombreMateria;

    private TextView tvHorarioLunes;
    private TextView tvHorarioMartes;
    private TextView tvHorarioMiercoles;
    private TextView tvHorarioJueves;
    private TextView tvHorarioViernes;
    private TextView tvHorarioSabado;
    private TextView tvHorarioDomingo;

    private LinearLayout lLLunes;
    private LinearLayout lLMartes;
    private LinearLayout lLMiercoles;
    private LinearLayout lLJueves;
    private LinearLayout lLViernes;
    private LinearLayout lLSabado;
    private LinearLayout lLDomingo;

    private final int ADMINISTRACION = 3;
    private final int COMERCIAL = 6;
    private final int COMUNICACION_SOCIAL = 2;
    private final int DERECHO = 58;
    private final int IDIOMAS = 5;
    private final int RELACIONES_INTERNACIONALES = 10;
    private final int SISTEMAS = 1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_oferta, null);

        tvPresencialOferta = view.findViewById(R.id.tvPresencialOferta);
        tvSemestreOferta = view.findViewById(R.id.tvSemestreOferta);
        tvCreditosOferta = view.findViewById(R.id.tvCreditosOferta);
        tvDocenteOferta = view.findViewById(R.id.tvDocenteOferta);
        tvGrupoOferta = view.findViewById(R.id.tvGrupoOferta);
        tvNombreMateria = view.findViewById(R.id.tvNombreMateria);

        tvHorarioLunes = view.findViewById(R.id.tvHorarioLunes);
        tvHorarioMartes = view.findViewById(R.id.tvHorarioMartes);
        tvHorarioMiercoles = view.findViewById(R.id.tvHorarioMiercoles);
        tvHorarioJueves = view.findViewById(R.id.tvHorarioJueves);
        tvHorarioViernes = view.findViewById(R.id.tvHorarioViernes);
        tvHorarioSabado = view.findViewById(R.id.tvHorarioSabado);
        tvHorarioDomingo = view.findViewById(R.id.tvHorarioDomingo);

        lLLunes = view.findViewById(R.id.lLLunes);
        lLMartes = view.findViewById(R.id.lLMartes);
        lLMiercoles = view.findViewById(R.id.lLMiercoles);
        lLJueves = view.findViewById(R.id.lLJueves);
        lLViernes = view.findViewById(R.id.lLViernes);
        lLSabado = view.findViewById(R.id.lLSabado);
        lLDomingo = view.findViewById(R.id.lLDomingo);

        TextView tvGrupo = view.findViewById(R.id.tvGrupo);
        LinearLayout llOferta = view.findViewById(R.id.llOferta);

        int idCarrera = getArguments().getInt("idCarrera");
        int idPeriodo = getArguments().getInt("idPeriodo");
        int idMateria = getArguments().getInt("idMateria");
        MateriasOfertadasDAO dao = FactoryDAO.getOrCreate().newMateriasOfertadasDAO();
        MateriasOfertadas materia = dao.seleccionar(idCarrera, idPeriodo, idMateria);

        tvPresencialOferta.setText(materia.getSCENTRO_DSC());

        tvSemestreOferta.setText(String.valueOf(materia.getLSEMESTRE()));
        tvCreditosOferta.setText(String.valueOf(materia.getLCREDITOS()));
        tvDocenteOferta.setText(materia.getDOCENTE());
        tvGrupoOferta.setText(materia.getSCODGRUPO());
        tvNombreMateria.setText(materia.getSMATERIA_DSC());

        HorariosOfertadosDAO daoHorario = FactoryDAO.getOrCreate().newHorariosOfertadosDAO();
        List<HorariosOfertados> horariosOfertados = daoHorario.seleccionar(materia.getID());
        String horario = "";
        for (int i = 0; i < horariosOfertados.size(); i++) {
            horario = horariosOfertados.get(i).getDTHRENTRADA() + " - " + horariosOfertados.get(i).getDTHRSALIDA();
            String abr = String.valueOf(horariosOfertados.get(i).getSDIA_DSC().trim().charAt(0)) + String.valueOf(horariosOfertados.get(i).getSDIA_DSC().trim().charAt(1));
            abr = abr.toUpperCase();
            String dia = abr;
            if (dia.equals("LU")) {
                tvHorarioLunes.setText(horario);
                tvHorarioLunes.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLLunes.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.backgroundcolorcardprofile));
            } else if (dia.equals("MA")) {
                tvHorarioMartes.setText(horario);
                tvHorarioMartes.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLMartes.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.backgroundcolorcardprofile));
            } else if (dia.equals("MI")) {
                tvHorarioMiercoles.setText(horario);
                tvHorarioMiercoles.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLMiercoles.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.backgroundcolorcardprofile));
            } else if (dia.equals("JU")) {
                tvHorarioJueves.setText(horario);
                tvHorarioJueves.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLJueves.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.backgroundcolorcardprofile));
            } else if (dia.equals("VI")) {
                tvHorarioViernes.setText(horario);
                tvHorarioViernes.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLViernes.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.backgroundcolorcardprofile));
            } else if (dia.equals("SA") || dia.equals("SÁ")) {
                tvHorarioSabado.setText(horario);
                tvHorarioSabado.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLSabado.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.backgroundcolorcardprofile));
            } else if (dia.equals("DO")) {
                tvHorarioDomingo.setText(horario);
                tvHorarioDomingo.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLDomingo.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.backgroundcolorcardprofile));
            }
        }

        if (materia.getSCODGRUPO().equals("")) {
            tvGrupo.setVisibility(View.GONE);
            tvGrupoOferta.setVisibility(View.GONE);
        }

        switch (materia.getLCARRERA_ID()) {
            case ADMINISTRACION:
                llOferta.setBackground(getActivity().getDrawable(R.drawable.bg_adm));
                break;

            case COMERCIAL:
                llOferta.setBackground(getActivity().getDrawable(R.drawable.bg_comer));
                break;

            case DERECHO:
                llOferta.setBackground(getActivity().getDrawable(R.drawable.bg_der));
                break;

            case COMUNICACION_SOCIAL:
                llOferta.setBackground(getActivity().getDrawable(R.drawable.bg_comun));
                break;

            case IDIOMAS:
                break;

            case RELACIONES_INTERNACIONALES:
                llOferta.setBackground(getActivity().getDrawable(R.drawable.bg_int));
                break;

            case SISTEMAS:
                llOferta.setBackground(getActivity().getDrawable(R.drawable.bg_sis));
                break;

            default:
                break;
        }

        builder.setView(view);

        return builder.create();
    }

}
