package modelos;

import java.util.Arrays;
import java.util.List;

public class TipoDaño {
    private String otroDaño;
    private String tipoSeleccionado;

    //daños predefinidos por defecto
    public static final List<String> tipos= Arrays.asList("Juego roto",
            "Basura acumulada",
            "Iluminacion en mal estado","Cancha en mal estado","Otro daño"
    );


    //esta funcion muestra esos daños predefinidos
    public static void mostrarDaños(){
        for(int i=0;i<TipoDaño.tipos.size();i++){
            System.out.println((i+1)+" - "+TipoDaño.tipos.get(i));
        }
    }

    //esta funcion va validar que tipo de daño elijio el usuario
    public void setTipoDaño(int opcion, String otroDaño) {
        //el primer if es para valiar que halla elejido dentro del rango permitido
        if (opcion >= 1 && opcion <= tipos.size()) {

            //este if verifica si el usuario elijio la ultima opcion
            if (opcion == tipos.size()) {
                //este if verifica que lo que la variable otroDaño que se recibe por parametro no este vacia
                //aunque esta demas por que el las validaciones se hace pero sin embargo la voy a dejar ahí
                if (otroDaño != null && !otroDaño.trim().isEmpty()) {
                    // se guarda el daño que halla escrito el usuairo
                    this.tipoSeleccionado = otroDaño;
                    this.otroDaño = otroDaño;
                } else {
                    //esta excepcion es para que el usuario no deje espacios en blanco
                    throw new IllegalArgumentException("Debe especificar el tipo de daño");
                }
            } else {
                //este else se ejecuta si el usuario elijio un daño que estaba por defecto en la lista este daño es el que se guardara
                this.tipoSeleccionado = tipos.get(opcion - 1);
                this.otroDaño = null;
            }
        } else {
            throw new IllegalArgumentException("Opción no válida");
        }
    }


    public String getOtroDaño() {
        return otroDaño;
    }

    public String getTipoSeleccionado() {
        return tipoSeleccionado;
    }
}
