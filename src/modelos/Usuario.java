package modelos;

public abstract class Usuario {
    private String nombre;
    private int id;
    private String correo;

    public Usuario(String nombre, int id, String correo) {
        this.nombre = nombre;
        this.id = id;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }


}
