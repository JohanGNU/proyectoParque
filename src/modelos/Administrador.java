package modelos;

public class Administrador extends Usuario{
    private String contraseña;

    public Administrador(String nombre, int id, String correo, String contraseña) {
        super(nombre, id, correo);
        this.contraseña = contraseña;
    }

    public String getContraseña() {
        return contraseña;
    }
}
