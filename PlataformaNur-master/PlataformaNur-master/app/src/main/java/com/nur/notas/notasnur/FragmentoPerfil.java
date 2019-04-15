package com.nur.notas.notasnur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nur.notas.notasnur.objetos.Alumno;
import com.nur.notas.notasnur.objetos.AlumnoCarrera;
import com.nur.notas.notasnur.utiles.Preferences;
import com.nur.notas.notasnur.dao.FactoryDAO;
import com.nur.notas.notasnur.dao.HorariosMateriasDAO;
import com.nur.notas.notasnur.dao.HorariosOfertadosDAO;
import com.nur.notas.notasnur.dao.MateriasDAO;
import com.nur.notas.notasnur.dao.MateriasOfertadasDAO;
import com.nur.notas.notasnur.dao.NotasDAO;
import com.nur.notas.notasnur.dao.RequisitosMateriasDAO;

import org.json.JSONArray;

import java.io.IOException;


public class FragmentoPerfil extends Fragment implements View.OnClickListener {

    private TextView textView_nombre;
    private TextView textView_registro;
    private TextView textView_horas;
    private TextView textView_celular;
    private TextView textView_telefono;
    private TextView textView_email;
    private TextView textView_fecha_nac;
    private Spinner spinnerCarrera;

    public FragmentoPerfil() {
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

        String alumnoImagenStr = Preferences.getAlumnoImagenStr(getContext());
        ImageView logo = view.findViewById(R.id.imgUser);
        byte[] imageByteArray = Base64.decode(alumnoImagenStr, Base64.DEFAULT);

        if (alumnoImagenStr.isEmpty()) {
            Glide.with(getContext()).load(R.drawable.ic_user).into(logo);
        } else {
            Glide.with(getContext()).load(imageByteArray).into(logo);
        }

        cargarPerfil();

        return view;
    }

    public void cargarPerfil(){
        Alumno alumno = Preferences.getAlumno(FragmentoPerfil.this.getContext());

        textView_nombre.setText(alumno.getNombre() + " " + alumno.getApellidoPaterno() + " " + alumno.getApellidoMaterno());
        textView_registro.setText(alumno.getRegistro());
        textView_horas.setText(String.valueOf(alumno.getHorasServicio()));
        if (!alumno.getCelular().equals("0")) textView_celular.setText(alumno.getCelular());
        if (!alumno.getTelefono().equals("0")) textView_telefono.setText(alumno.getTelefono());
        if (!alumno.getEmail().equals("0")) textView_email.setText(alumno.getEmail());
        textView_fecha_nac.setText(alumno.getFechaNacimiento());
    }

    @Override
    public void onClick(View view) {
    }

    public void verLinks(){
        LinksDialog linksDialog = new LinksDialog();
        linksDialog.setContext(getContext());
        linksDialog.show(FragmentoPerfil.this.getFragmentManager(), "Páginas informativas");
    }

    public void editarPerfil(){
        EditarPerfilDialog editarPerfilDialog = new EditarPerfilDialog();
        editarPerfilDialog.setContext(getContext());
        editarPerfilDialog.show(FragmentoPerfil.this.getFragmentManager(), "Editar perfil");
    }

    public void cambiarPin(){
        CambiarPinDialog cambiarPinDialog = new CambiarPinDialog();
        cambiarPinDialog.show(FragmentoPerfil.this.getFragmentManager(), "Cambiar pin");
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
        MateriasDAO materiasDao = FactoryDAO.getOrCreate().newMateriasDAO();
        RequisitosMateriasDAO requisitosDao = FactoryDAO.getOrCreate().newRequisitosMateriasDAO();
        notasDAO.truncate();
        materiasOfertadasDao.truncate();
        horariosOfertadosDao.truncate();
        horariosMateriasDao.truncate();
        materiasDao.truncate();
        requisitosDao.truncate();

        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ver_historial:
                verHistorial();
                break;

            case R.id.action_ver_links:
                verLinks();
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
