package modelos;

public class Parque {
    private String direccion;
    private String nombreParque;

    public Parque(String direccion, String nombreParque) {
        this.direccion = direccion;
        this.nombreParque = nombreParque;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNombreParque() {
        return nombreParque;
    }
}
