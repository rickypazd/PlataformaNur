package com.example.ricardopazdemiquel.plataformanur;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.ricardopazdemiquel.plataformanur.dao.FactoryDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.NotasDAO;
import com.example.ricardopazdemiquel.plataformanur.dto.Notas;

public class DetalleMateriaActivity extends AppCompatActivity {

    private TextView notaPrimerParcial, notaSegundoParcial, notaExamenFinal, notaFinal;
    private TextView tvNotaPrimerParcial, tvNotaSegundoParcial, tvNotaExamenFinal, tvNotaFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_materia);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });

        notaPrimerParcial = findViewById(R.id.notaPrimerParcial);
        notaSegundoParcial = findViewById(R.id.notaSegundoParcial);
        notaExamenFinal = findViewById(R.id.notaExamenFinal);
        notaFinal = findViewById(R.id.notaFinal);

        Intent intent = getIntent();
        int carrera = intent.getIntExtra("LCARRERA_ID", 0);
        int periodo = intent.getIntExtra("LPERIODO_ID", 0);
        String materia = intent.getStringExtra("SCODMATERIA");

        NotasDAO dao = FactoryDAO.getOrCreate().newNotasDAO();
        Notas nota = dao.seleccionar(carrera, periodo, materia);

        // PRIMER PARCIAL
        String notaPrimerParcialStr = nota.getPAR1(); /* "27/30" */
        notaPrimerParcialStr = notaPrimerParcialStr.replace(",", ".");
        String[] notaPrimerParcialArr = notaPrimerParcialStr.split("/"); /* ["27", "30"] */
        int notaPrimerParcial = (int) Double.parseDouble(notaPrimerParcialArr[0]);
        int residuoPrimerParcial = (int) (Double.parseDouble(notaPrimerParcialArr[0]) % 1);
        int primerParcialSobre = (int) Double.parseDouble(notaPrimerParcialArr[1]);
        animateTextViewV2(Double.parseDouble(notaPrimerParcialArr[0]), primerParcialSobre, this.notaPrimerParcial, residuoPrimerParcial);

        // SEGUNDO PARCIAL
        String notaSegundoParcialStr = nota.getPAR2(); /* "27/30" */
        notaSegundoParcialStr = notaSegundoParcialStr.replace(",", ".");
        String[] notaSegundoParcialArr = notaSegundoParcialStr.split("/"); /* ["27", "30"] */
        int notaSegundoParcial = (int) Double.parseDouble(notaSegundoParcialArr[0]);
        int residuoSegundoParcial = (int) (Double.parseDouble(notaSegundoParcialArr[0]) % 1);
        int segundoParcialSobre = (int) Double.parseDouble(notaSegundoParcialArr[1]);
        animateTextViewV2(Double.parseDouble(notaSegundoParcialArr[0]), segundoParcialSobre, this.notaSegundoParcial, residuoSegundoParcial);

        // EXAMEN FINAL
        String notaExamenFinalStr = nota.getEXFINAL(); /* "27/30" */
        notaExamenFinalStr = notaExamenFinalStr.replace(",", ".");
        String[] notaExamenFinalArr = notaExamenFinalStr.split("/"); /* ["27", "30"] */
        int notaExamenFinal = (int) Double.parseDouble(notaExamenFinalArr[0]);
        int residuoExamenFinal = (int) (Double.parseDouble(notaExamenFinalArr[0]) % 1);
        int examenFinalSobre = (int) Double.parseDouble(notaExamenFinalArr[1]);
        animateTextViewV2(Double.parseDouble(notaExamenFinalArr[0]), examenFinalSobre, this.notaExamenFinal, residuoExamenFinal);

        // NOTA FINAL
        String notaFinalStr = nota.getFINAL(); /* "27/30" */
        notaFinalStr = notaFinalStr.replace(",", ".");
        String[] notaFinalArr = notaFinalStr.split("/"); /* ["27", "30"] */
        int notaFinal = (int) Double.parseDouble(notaFinalArr[0]);
        int residuoNotaFinal = (int) (Double.parseDouble(notaFinalArr[0]) % 1);
        int notaFinalSobre = (int) Double.parseDouble(notaFinalArr[1]);
        animateTextViewV2(Double.parseDouble(notaFinalArr[0]), notaFinalSobre, this.notaFinal, residuoNotaFinal);


        tvNotaPrimerParcial = findViewById(R.id.tvNotaPrimerParcial);
        tvNotaSegundoParcial = findViewById(R.id.tvNotaSegundoParcial);
        tvNotaExamenFinal = findViewById(R.id.tvNotaExamenFinal);
        tvNotaFinal = findViewById(R.id.tvNotaFinal);

        tvNotaPrimerParcial.setText(nota.getPAR1());
        tvNotaSegundoParcial.setText(nota.getPAR2());
        tvNotaExamenFinal.setText(nota.getEXFINAL());
        tvNotaFinal.setText(nota.getFINAL());
    }

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.8f);

        int start = Math.min(initialValue, finalValue);
        int end = Math.max(initialValue, finalValue);
        int difference = Math.abs(finalValue - initialValue);

        Handler handler = new Handler();

        for (int count = start; count <= end; count++) {
            float input = ((float) count) / difference;
            int time = Math.round(decelerateInterpolator.getInterpolation(input) * 100) * count;
            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textview.setText(String.valueOf(finalCount));
                }
            }, time);
        }
    }

    public void animateTextViewV2(int nota, final int sobre, final TextView textview) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.2f);

        Handler handler = new Handler();

        for (int count = 0; count <= nota; count++) {
            float input = ((float) count) / nota;
            // int time = Math.round(decelerateInterpolator.getInterpolation(input) * 100) * count;
            int time = Math.round(input * 100) * 10;
            final int finalCount = count;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textview.setText(finalCount + "/" + sobre);
                }
            }, time);
        }
    }

    public void animateTextViewV2(final double nota, final int sobre, final TextView textview, final int puntoDecimal) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.2f);

        Handler handler = new Handler();

        for (int count = 0; count <= (int) nota; count++) {
            float input = ((float) count) / (int) nota;
            // int time = Math.round(decelerateInterpolator.getInterpolation(input) * 100) * count;
            int time = Math.round(input * 100) * 10;
            final int finalCount = count;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (finalCount == (int) nota && nota % 1 != 0) { /* ultima iteracion */
                        textview.setText(nota + "/" + sobre);
                        return;
                    }

                    textview.setText(finalCount + "/" + sobre);
                }
            }, time);
        }
    }

}
