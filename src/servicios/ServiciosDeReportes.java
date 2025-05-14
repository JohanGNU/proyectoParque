package servicios;
// se inportan los paquetes y las clases
import modelos.Parque;
import modelos.Reporte;
import modelos.TipoDaño;
import modelos.Usuario;
import validaciones.ValidadorEntradas;
import java.time.LocalDate; // esta libreria es para manejar fechas
import java.util.ArrayList;
import java.util.List;


public class ServiciosDeReportes {
    //se crea la lista que va a guardar los reportes
    private List<Reporte> reportes=new ArrayList<>();

    /*en esta funcion pido los datos al usuario lo que es
    * nombre
    * edad
    * id (numero de cc)
    * correo electronico
    * nombre del parque
    * direccion
    * no se pide la fecha por que el programa crea la fecha automaticamente del dia en el que se hace el reporte
    * y como el reporte se crea hasta ahora el estado del reporte se envia automaticamente en false
    * cada peticion lleva su validacion para que el usuario no ingrese datos inccorectos en los campos
    * como numeros en el campo de nombre*/
    public void agregarReportes() {
        System.out.println("Ingrese los datos del reporte");
        System.out.println("Datos del usuario ");
        String nombreUsuario= ValidadorEntradas.validarNombre("Nombre: ");
        int edad= ValidadorEntradas.validarEnteroPositivo("Edad: ");
        int id=ValidadorEntradas.validarEnteroPositivo("ID: ");
        String email=ValidadorEntradas.validarEmail("Email: ");
        System.out.println("Datos del parque");
        String direccion=ValidadorEntradas.validarFormatoDireccion("Direccion: ");
        String nombreParque=ValidadorEntradas.validarNombre("Nombre del parque: ");

        /*se crean los objetos de cada reporte */
        Usuario usurio=new Usuario(nombreUsuario,edad,id,email);
        Parque parque=new Parque(direccion,nombreParque);
        Reporte reporte=new Reporte(LocalDate.now(),usurio,parque,seleccionarTipoDaño(),false);
        reportes.add(reporte);

        mostrarReportes();
    }

    /*en esta funcion pido el tipo del daño al usuario decidi pedirlo por serparado para mejro manejo de esta logica*/
    private TipoDaño seleccionarTipoDaño() {
        TipoDaño tipoDaño = new TipoDaño();
        System.out.println("\nSeleccione el tipo de daño:");
        TipoDaño.mostrarDaños();

        /*se pide la opcion al usuario y se valida que sea un numero y que ese nuemero este dentro del rango de opciones*/
        int opcion = ValidadorEntradas.validarEnteroRango("Opción: ", 1, TipoDaño.tipos.size());

        /*aca se verifica si el usuario elige la ultima opcion puesto que la ultima opcion es (otro tipo de daño) diferente a los daños
        * predefinidos*/
        if (opcion == TipoDaño.tipos.size()) { // Última opción = "Otro"
            String otroDaño = ValidadorEntradas.validarNombre("Describa el tipo de daño: ");
            tipoDaño.setTipoDaño(opcion, otroDaño);
          //si el usuario elije un daño de la lista de daños predefinidos se ejecuta el  else
        } else {
            tipoDaño.setTipoDaño(opcion, null);
        }
        return tipoDaño;
    }

    /*esta funcion muestra la lista de los reportes hechos*/
    public void mostrarReportes(){
        //este if verifica que la lista no este vacia
        if(reportes.isEmpty()){
            System.out.println("No hay reportes aún");
        }
        System.out.println("----LISTA DE REPORTES-----");
        for(int i=0;i<reportes.size();i++){
            System.out.println("Reporte "+(i+1));
            mostrarDetallesReportes(reportes.get(i));
        }

    }

    //esta funcion accede a cada campo del reporte y lo retorna a la funcion que los muestra
    public void mostrarDetallesReportes(Reporte reportes) {
        System.out.println("Fecha: "+reportes.getFecha());
        System.out.println("Nombre de la persona: "+reportes.getUsuario().getNombre());
        System.out.println("Nombre del parque: "+reportes.getParque().getNombreParque());
        System.out.println("Daño: "+reportes.getTipoDaño().getTipoSeleccionado());
        /*este if lo que hace es que valida el estado del daño como false osea es como si dijere if(reportes.isEstado == false )
        * si es false el daño esta pendiente por resolver*/
        if(!reportes.isEstado()){
            System.out.println("Estado del reporte: pendiende");
        }else{
            System.out.println("Estado del reporte: resuelto");
        }
    }

    //el programa se va a ejecutar desde el paquete el main pero por ahora ejecutelo aqui
    public static void main(String[] args) {
        ServiciosDeReportes serviciosDeReportes=new ServiciosDeReportes();
        serviciosDeReportes.agregarReportes();
        //5int edad= ValidadorEntradas.validarEnteroPositivo("Edad: ");
    }
}
