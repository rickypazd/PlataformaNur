package com.nur.notas.notasnur.objetos;

public class Alumno {

    //Atributos del servicio "GetAlumnoInfo"
    private int id;
    private String registro;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String fechaNacimiento;
    private String sexo;
    private String celular;
    private String colegio;
    private String tipoColegio;
    private String telefono;
    private String email;
    private String estadoCivil;
    private String tipoSangre;
    private Boolean activoPasivo;
    private int horasServicio;

    public Alumno() {
    }

    public Alumno(int id, String registro, String nombre, String apellidoPaterno, String apellidoMaterno, String fechaNacimiento, String sexo, String celular, String telefono, String email, String estadoCivil, String tipoSangre, Boolean activoPasivo, int horasServicio) {
        this.id = id;
        this.registro = registro;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.celular = celular;
        this.telefono = telefono;
        this.email = email;
        this.estadoCivil = estadoCivil;
        this.tipoSangre = tipoSangre;
        this.activoPasivo = activoPasivo;
        this.horasServicio = horasServicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public Boolean getActivoPasivo() {
        return activoPasivo;
    }

    public void setActivoPasivo(Boolean activoPasivo) {
        this.activoPasivo = activoPasivo;
    }

    public int getHorasServicio() {
        return horasServicio;
    }

    public void setHorasServicio(int horasServicio) {
        this.horasServicio = horasServicio;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public String getTipoColegio() {
        return tipoColegio;
    }

    public void setTipoColegio(String tipoColegio) {
        this.tipoColegio = tipoColegio;
    }
}
