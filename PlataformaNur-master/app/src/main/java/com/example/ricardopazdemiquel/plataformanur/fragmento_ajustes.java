package com.example.ricardopazdemiquel.plataformanur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ricardopazdemiquel.plataformanur.Objs.Alumno;
import com.example.ricardopazdemiquel.plataformanur.Objs.AlumnoCarrera;
import com.example.ricardopazdemiquel.plataformanur.Utiles.Preferences;
import com.example.ricardopazdemiquel.plataformanur.dao.FactoryDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.HorariosMateriasDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.HorariosOfertadosDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.MateriasOfertadasDAO;
import com.example.ricardopazdemiquel.plataformanur.dao.NotasDAO;

import org.json.JSONArray;

import java.util.ArrayList;


public class fragmento_ajustes extends Fragment implements View.OnClickListener {

    private TextView textView_nombre;
    private TextView textView_registro;
    private TextView textView_horas;
    private TextView textView_celular;
    private TextView textView_telefono;
    private TextView textView_email;
    private TextView textView_fecha_nac;
    private Spinner spinnerCarrera;

    public fragmento_ajustes() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((TabBarActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_ajustes, container, false);

        textView_nombre = (TextView) view.findViewById(R.id.textView_nombre);
        textView_registro = (TextView) view.findViewById(R.id.textView_registro);
        textView_horas = (TextView) view.findViewById(R.id.textView_horas);
        textView_celular = (TextView) view.findViewById(R.id.textView_celular);
        textView_telefono = (TextView) view.findViewById(R.id.textView_telefono);
        textView_email = (TextView) view.findViewById(R.id.textView_email);
        textView_fecha_nac = (TextView) view.findViewById(R.id.textView_fecha_nac);

        ImageView logo = view.findViewById(R.id.imgUser);
        String alumnoImagenStr = Preferences.getAlumnoImagenStr(getContext());
        byte[] imageByteArray = Base64.decode(alumnoImagenStr, Base64.DEFAULT);

        Glide.with(this).load(imageByteArray).into(logo);

        cargarPerfil();

        return view;
    }

    public void cargarPerfil(){
        Alumno alumno = Preferences.getAlumno(fragmento_ajustes.this.getContext());

        textView_nombre.setText(alumno.getNombre() + " " + alumno.getApellidoPaterno() + " " + alumno.getApellidoMaterno());
        textView_registro.setText(alumno.getRegistro());
        textView_horas.setText(String.valueOf(alumno.getHorasServicio()));
        textView_celular.setText(alumno.getCelular());
        textView_telefono.setText(alumno.getTelefono());
        textView_email.setText(alumno.getEmail());
        textView_fecha_nac.setText(alumno.getFechaNacimiento());
    }

    @Override
    public void onClick(View view) {
    }

    public void editarPerfil(){
        EditarPerfilDialog editarPerfilDialog = new EditarPerfilDialog();
        editarPerfilDialog.setContext(getContext());
        editarPerfilDialog.show(fragmento_ajustes.this.getFragmentManager(), "Editar perfil");
    }

    public void cambiarPin(){
        CambiarPinDialog cambiarPinDialog = new CambiarPinDialog();
        cambiarPinDialog.show(fragmento_ajustes.this.getFragmentManager(), "Cambiar pin");
    }

    private void confirmarCerrarSesion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("¿Cerrar sesión?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cerrarSesion();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    public void cerrarSesion() {
        Preferences.setAlumno(getContext(), new Alumno());
        Preferences.setPeriodos(getContext(), new JSONArray());
        Preferences.setAlumnoCarreras(getContext(), new JSONArray());
        Preferences.setTokenAcceso(getContext(), "");
        Preferences.setRegistro(getContext(), "");
        Preferences.setPin(getContext(), "");
        Preferences.setPeriodosOferta(getContext(), new JSONArray());
        Preferences.setCarreraSeleccionada(getContext(), new AlumnoCarrera());
        Preferences.setIsFirstTime(getContext(), false);
        Preferences.setAlumnoImagenStr(getContext(), "");

        NotasDAO notasDAO = FactoryDAO.getOrCreate().newNotasDAO();
        MateriasOfertadasDAO materiasOfertadasDao = FactoryDAO.getOrCreate().newMateriasOfertadasDAO();
        HorariosOfertadosDAO horariosOfertadosDao = FactoryDAO.getOrCreate().newHorariosOfertadosDAO();
        HorariosMateriasDAO horariosMateriasDao = FactoryDAO.getOrCreate().newHorariosMateriasDAO();
        notasDAO.truncate();
        materiasOfertadasDao.truncate();
        horariosOfertadosDao.truncate();
        horariosMateriasDao.truncate();

        Intent intent = new Intent(getContext(), Login2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ver_historial:
                verHistorial();
                break;

            case R.id.action_editar_perfil:
                editarPerfil();
                break;

            case R.id.action_cambiar_pin:
                cambiarPin();
                break;

            case R.id.action_cerrar_sesion:
                confirmarCerrarSesion();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void verHistorial() {
        Intent intent = new Intent(getContext(), HistorialAcademico.class);
        startActivity(intent);
    }

}
