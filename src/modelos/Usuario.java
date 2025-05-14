package modelos;

public class Usuario {
    private String nombre;
    private int edad;
    private int id;
    private String correo;

    public Usuario(String nombre, int edad, int id, String correo) {
        this.nombre = nombre;
        this.edad = edad;
        this.id = id;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public int getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }
}
