package com.nur.notas.notasnur;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nur.notas.notasnur.dao.FactoryDAO;
import com.nur.notas.notasnur.dao.HorariosOfertadosDAO;
import com.nur.notas.notasnur.dao.MateriasOfertadasDAO;
import com.nur.notas.notasnur.dto.HorariosOfertados;
import com.nur.notas.notasnur.dto.MateriasOfertadas;
import com.nur.notas.notasnur.utiles.Constantes;
import com.nur.notas.notasnur.utiles.Util;

import java.util.List;

public class OfertaDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_oferta, null);

        TextView tvPresencialOferta = view.findViewById(R.id.tvPresencialOferta);
        TextView tvSemestreOferta = view.findViewById(R.id.tvSemestreOferta);
        TextView tvCreditosOferta = view.findViewById(R.id.tvCreditosOferta);
        TextView tvDocenteOferta = view.findViewById(R.id.tvDocenteOferta);
        TextView tvGrupoOferta = view.findViewById(R.id.tvGrupoOferta);
        TextView tvNombreMateria = view.findViewById(R.id.tvNombreMateria);

        TextView tvHorarioLunes = view.findViewById(R.id.tvHorarioLunes);
        TextView tvHorarioMartes = view.findViewById(R.id.tvHorarioMartes);
        TextView tvHorarioMiercoles = view.findViewById(R.id.tvHorarioMiercoles);
        TextView tvHorarioJueves = view.findViewById(R.id.tvHorarioJueves);
        TextView tvHorarioViernes = view.findViewById(R.id.tvHorarioViernes);
        TextView tvHorarioSabado = view.findViewById(R.id.tvHorarioSabado);
        TextView tvHorarioDomingo = view.findViewById(R.id.tvHorarioDomingo);

        TextView lLLunes = view.findViewById(R.id.lLLunes);
        TextView lLMartes = view.findViewById(R.id.lLMartes);
        TextView lLMiercoles = view.findViewById(R.id.lLMiercoles);
        TextView lLJueves = view.findViewById(R.id.lLJueves);
        TextView lLViernes = view.findViewById(R.id.lLViernes);
        TextView lLSabado = view.findViewById(R.id.lLSabado);
        TextView lLDomingo = view.findViewById(R.id.lLDomingo);

        TextView tvGrupo = view.findViewById(R.id.tvGrupo);
        LinearLayout llOferta = view.findViewById(R.id.llOferta);
        TextView tvLunes = view.findViewById(R.id.tvLunes);
        TextView tvMartes = view.findViewById(R.id.tvMartes);
        TextView tvMiercoles = view.findViewById(R.id.tvMiercoles);
        TextView tvJueves = view.findViewById(R.id.tvJueves);
        TextView tvViernes = view.findViewById(R.id.tvViernes);
        TextView tvSabado = view.findViewById(R.id.tvSabado);
        TextView tvDomingo = view.findViewById(R.id.tvDomingo);

        int idCarrera = getArguments() != null ? getArguments().getInt("idCarrera") : 0;
        int idPeriodo = getArguments() != null ? getArguments().getInt("idPeriodo") : 0;
        int idMateria = getArguments() != null ? getArguments().getInt("idMateria") : 0;
        MateriasOfertadasDAO dao = FactoryDAO.getOrCreate().newMateriasOfertadasDAO();
        MateriasOfertadas materia = dao.seleccionar(idCarrera, idPeriodo, idMateria);

        tvPresencialOferta.setText(Util.primeraMayuscula(materia.getSCENTRO_DSC()));

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
                tvLunes.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLLunes.setBackground(ContextCompat.getDrawable(view.getContext(), getDrawableCarrera(materia.getLCARRERA_ID())));
            } else if (dia.equals("MA")) {
                tvHorarioMartes.setText(horario);
                tvHorarioMartes.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                tvMartes.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLMartes.setBackground(ContextCompat.getDrawable(view.getContext(), getDrawableCarrera(materia.getLCARRERA_ID())));
            } else if (dia.equals("MI")) {
                tvHorarioMiercoles.setText(horario);
                tvHorarioMiercoles.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                tvMiercoles.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLMiercoles.setBackground(ContextCompat.getDrawable(view.getContext(), getDrawableCarrera(materia.getLCARRERA_ID())));
            } else if (dia.equals("JU")) {
                tvHorarioJueves.setText(horario);
                tvHorarioJueves.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                tvJueves.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLJueves.setBackground(ContextCompat.getDrawable(view.getContext(), getDrawableCarrera(materia.getLCARRERA_ID())));
            } else if (dia.equals("VI")) {
                tvHorarioViernes.setText(horario);
                tvHorarioViernes.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                tvViernes.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLViernes.setBackground(ContextCompat.getDrawable(view.getContext(), getDrawableCarrera(materia.getLCARRERA_ID())));
            } else if (dia.equals("SA") || dia.equals("SÁ")) {
                tvHorarioSabado.setText(horario);
                tvHorarioSabado.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                tvSabado.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLSabado.setBackground(ContextCompat.getDrawable(view.getContext(), getDrawableCarrera(materia.getLCARRERA_ID())));
            } else if (dia.equals("DO")) {
                tvHorarioDomingo.setText(horario);
                tvHorarioDomingo.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                tvDomingo.setTextColor(ContextCompat.getColor(view.getContext(), R.color.overlay_light_90));
                lLDomingo.setBackground(ContextCompat.getDrawable(view.getContext(), getDrawableCarrera(materia.getLCARRERA_ID())));
            }
        }

        if (materia.getSCODGRUPO().isEmpty()) {
            tvGrupo.setVisibility(View.GONE);
            tvGrupoOferta.setVisibility(View.GONE);
        }

        tvCreditosOferta.setBackground(getActivity().getDrawable(getDrawableCarrera(materia.getLCARRERA_ID())));
        tvSemestreOferta.setBackground(getActivity().getDrawable(getDrawableCarrera(materia.getLCARRERA_ID())));

        llOferta.setBackground(getActivity().getDrawable(getDrawableCarrera(materia.getLCARRERA_ID())));

        builder.setView(view);

        return builder.create();
    }

    private int getDrawableCarrera(int carreraId) {
        switch (carreraId) {
            case Constantes.ADMINISTRACION:
                return R.drawable.bg_adm;

            case Constantes.COMERCIAL:
                return R.drawable.bg_comer;

            case Constantes.DERECHO:
                return R.drawable.bg_der;

            case Constantes.COMUNICACION_SOCIAL:
                return R.drawable.bg_comun;

//            case Constantes.IDIOMAS:
//                break;

            case Constantes.FINANCIERA:
                return R.drawable.bg_fin;

            case Constantes.MARKETING:
                return R.drawable.bg_mar;

            case Constantes.RELACIONES_INTERNACIONALES:
                return R.drawable.bg_int;

            case Constantes.SISTEMAS:
                return R.drawable.bg_sis;

            default:
                return R.drawable.backgroundcolorcardprofile;
        }
    }

}
