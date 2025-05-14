package modelos;

import java.time.LocalDate;

public class Reporte {
    private LocalDate fecha;
    private Usuario usuario;
    private Parque parque;
    private TipoDaño tipoDaño;
    private boolean estado;


    public Reporte(LocalDate fecha, Usuario usuario, Parque parque, TipoDaño tipoDaño, boolean estado) {
        this.fecha = fecha;
        this.usuario = usuario;
        this.parque = parque;
        this.tipoDaño = tipoDaño;
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Parque getParque() {
        return parque;
    }

    public TipoDaño getTipoDaño() {
        return tipoDaño;
    }

    public boolean isEstado() {
        return estado;
    }
}
