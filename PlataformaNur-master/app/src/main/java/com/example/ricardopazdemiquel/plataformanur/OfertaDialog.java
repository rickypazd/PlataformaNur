package com.example.ricardopazdemiquel.plataformanur;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ricardopazdemiquel.plataformanur.Objs.Alumno;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;
import com.example.ricardopazdemiquel.plataformanur.dao.FactoryDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.HorariosOfertadosDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.MateriasOfertadasDAO;
import com.example.ricardopazdemiquel.plataformanur.dto.HorariosOfertados;
import com.example.ricardopazdemiquel.plataformanur.dto.MateriasOfertadas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfertaDialog extends AppCompatDialogFragment {

    private TextView tvPresencialOferta;
    private TextView tvSemestreOferta;
    private TextView tvCreditosOferta;
    private TextView tvDocenteOferta;
    private TextView tvCodigoOferta;
    private TextView tvGrupoOferta;
    private TextView tvHorarioOferta;
    private TextView tvDiasOferta;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_oferta, null);

        tvPresencialOferta = view.findViewById(R.id.tvPresencialOferta);
        tvSemestreOferta   = view.findViewById(R.id.tvSemestreOferta);
        tvCreditosOferta   = view.findViewById(R.id.tvCreditosOferta);
        tvDocenteOferta    = view.findViewById(R.id.tvDocenteOferta);
        tvCodigoOferta     = view.findViewById(R.id.tvCodigoOferta);
        tvGrupoOferta      = view.findViewById(R.id.tvGrupoOferta);
        tvHorarioOferta    = view.findViewById(R.id.tvHorarioOferta);
        tvDiasOferta       = view.findViewById(R.id.tvDiasOferta);

        int idCarrera = getArguments().getInt("idCarrera");
        int idPeriodo = getArguments().getInt("idPeriodo");
        String idMateria = getArguments().getString("idMateria");
        MateriasOfertadasDAO dao = FactoryDAO.getOrCreate().newMateriasOfertadasDAO();
        MateriasOfertadas materia = dao.seleccionar(idCarrera, idPeriodo, idMateria);

        tvPresencialOferta.setText(materia.getSCENTRO_DSC());

        tvSemestreOferta.setText(String.valueOf(materia.getLSEMESTRE()));
        tvCreditosOferta.setText(String.valueOf(materia.getLCREDITOS()));
        tvDocenteOferta.setText(materia.getDOCENTE());
        tvCodigoOferta.setText(materia.getSCODMATERIA());
        tvGrupoOferta.setText(materia.getSCODGRUPO());

        HorariosOfertadosDAO daoHorario = FactoryDAO.getOrCreate().newHorariosOfertadosDAO();
        List<HorariosOfertados> horariosOfertados = daoHorario.seleccionar(materia.getID());
        String dias = "";
        String horario = "";
        for (int i = 0; i < horariosOfertados.size(); i++) {
            horario = horariosOfertados.get(i).getDTHRENTRADA() + " - " + horariosOfertados.get(i).getDTHRSALIDA();
            String abr = String.valueOf(horariosOfertados.get(i).getSDIA_DSC().trim().charAt(0)) + String.valueOf(horariosOfertados.get(i).getSDIA_DSC().trim().charAt(1));
            abr = abr.toUpperCase();
            dias +=  abr + "  ";
        }
        tvHorarioOferta.setText(horario);
        tvDiasOferta.setText(dias);

        builder.setView(view)
                .setTitle(materia.getSMATERIA_DSC().trim())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
}
