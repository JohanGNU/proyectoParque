package servicios;
// se inportan los paquetes y las clases

import modelos.*;
import validaciones.ValidadorEntradas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ServiciosDeReportes {
    //se crea la lista que va a guardar los reportes
    private List<Reporte> reportes=new ArrayList<>();
    private List<Usuario> admins=new ArrayList<>();
    private Administrador adminEnSesion = null;

    /*esta funcion es como el panel principal donde van a interactuar los usuarios ya sean clientes o administradores*/
    public void servicios() {
        int continuar;
        do {
            System.out.println("Bienvenido a RPV \nIndique como desea hacer el registro");
            int opcion;
            //se hacen las validaciones de las opciones
            do {
                opcion = ValidadorEntradas.validarEnteroPositivo("1. Cliente \n2. Administrador\n");
                if (opcion < 1 || opcion > 2) {
                    System.out.print("Debe elegir una opcion disponible: ");
                }
            } while (opcion < 1 || opcion > 2);

            //este if es para validar si es un cliente la opcione elejida por el usuario
            if (opcion == 1) {
                generarReporte();
            }else if (opcion == 2) {
                /*este es un pequeño menu para los administradores esto con el fin de que puedan ejecutar varias acciones
                * sin cerrar la sesion automaticamente*/
                int opcionAdmin;
                do {
                    System.out.println("\n=== ACCESO ADMINISTRADOR ===");
                    System.out.println("1. Registrar nuevo administrador");
                    System.out.println("2. Iniciar sesion");
                    System.out.println("3. Volver al menu principal");

                    //se valida el rango de la opcion
                    opcionAdmin = ValidadorEntradas.validarEnteroRango("Elija una opcion: ", 1, 3);

                    switch (opcionAdmin) {
                        case 1 -> {
                            pedirDatosAdmin();
                        }
                        case 2 -> {
                            if (admins.isEmpty()) {
                                System.err.println("No hay administradores registrados. Registrese primero.");
                            } else if (autenticarAdmin()) {
                                menuAdmin(); // se inicia sesion si hay al menos 1 cuenta registrado
                            }
                        }
                    }
                } while (opcionAdmin != 3); //sale del menu si presiona la opcion 3
            }

            // Preguntar si desea continuar
            continuar = ValidadorEntradas.validarEnteroPositivo(
                    "\n¿Desea realizar otra operación?\n1. Sí\n2. No\n"
            );

        } while (continuar == 1); // se repite mientras el usuario elija 1 (Sí)

        System.out.println("Gracias por usar RPV. ¡Hasta pronto!");
    }


    /*esta funcion autentica las sesiones de los administradores*/
    public boolean autenticarAdmin() {
        System.out.println("Ingrese las credenciales para el inicio de sesion");
        //se validan los formatos
        String email = ValidadorEntradas.validarEmail("Email: ");
        String contraseña = ValidadorEntradas.validarContraseña("Contraseña: ");

        /*se usa un foreach para filtrar todos los objetos que sean de tipo administrador*/
        for (Usuario usuario : admins) {
            if (usuario instanceof Administrador) {
                Administrador admin = (Administrador) usuario; // Casting seguro
                if (admin.getCorreo().equals(email) && admin.getContraseña().equals(contraseña)) {
                    adminEnSesion = admin; // se usa esta variable para persistir el inicio de sesion
                    System.out.println("\nBienvenido, " + adminEnSesion.getNombre());
                    return true;
                }
            }
        }
        System.err.println("Credenciales incorrectas");

        return false;
    }
    /*si el usuario elije la opcion de registrarse como cliente lo dirige a esta funcion
    * para hacer el respectivo reporte */
    public void generarReporte(){
        String nombreUsuario = ValidadorEntradas.validarNombre("Nombre: ");
        int id = ValidadorEntradas.validarEnteroPositivo("ID: ");
        String email = ValidadorEntradas.validarEmail("Email: ");
        System.out.println("Datos del parque");
        String direccion = ValidadorEntradas.validarFormatoDireccion("Direccion: ");
        String nombreParque = ValidadorEntradas.validarNombre("Nombre del parque: ");
        Usuario cliente=new Cliente(nombreUsuario,id,email);
        Parque parque=new Parque(direccion,nombreParque);
        Reporte reporte=new Reporte(LocalDate.now(),cliente,parque,seleccionarTipoDaño(),false);
        reportes.add(reporte);
        System.out.println("Reporte exitoso");
    }

    //se piden los datos a los administradores
    public void pedirDatosAdmin() {
        String nombreUsuario = ValidadorEntradas.validarNombre("Nombre: ");
        int id = ValidadorEntradas.validarEnteroPositivo("ID: ");
        String email = ValidadorEntradas.validarEmail("Email: ");
        String contraseña=ValidadorEntradas.validarContraseña("Contraseña: ");
        Usuario admin=new Administrador(nombreUsuario,id,email,contraseña);
        admins.add(admin);
        System.out.println("Registro exitoso");
    }

    /*esta funcion permite al administrador hacer varias opciones antes de cerrar la sesion*/
    public void menuAdmin() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Mostrar reportes");
            System.out.println("2. Cambiar estado de reporte");
            System.out.println("3. Eliminar reporte");
            System.out.println("4. Cerrar sesión");
            opcion = ValidadorEntradas.validarEnteroRango("Opción: ", 1, 4);

            switch (opcion) {
                case 1 -> mostrarReportes();
                case 2 -> cambiarEstadoReporte();
                case 3 -> eliminarReporte();
                case 4 -> {
                    adminEnSesion = null; //se marca admin como como null cuando se cierra al sesion
                    System.out.println("Sesion cerrada");
                }
            }
        } while (opcion != 4 && adminEnSesion != null);
    }

    /*esta funcion cambia el estado de un reporte seleccionado por el administrador*/
    public void cambiarEstadoReporte(){
        int opcion;
        mostrarReportes();
        do {
            //validaciones para entradas correctas
            opcion = ValidadorEntradas.validarEnteroPositivo("\nIngrese el numero del reporte: ");
            if(opcion<1||opcion>reportes.size()){
                System.err.println("Debe ingresar una opcion disponible");
            }
        }while(opcion<1||opcion>reportes.size());

        Reporte reporte = reportes.get(opcion-1);
        reporte.setEstado(true);
        System.out.println("Cambio de estado exitoso");
    }

    /*esta funcion, elimina un reporte elejido por el administrador*/
    public void eliminarReporte(){
        mostrarReportes();
        int opcion;
        do{
            //validaciones para entradas correctas
            opcion=ValidadorEntradas.validarEnteroPositivo("Cual reporte desea eliminar: ");
            if(opcion<1||opcion>reportes.size()){
                System.err.println("Debe elegir una opcion disponible");
            }
        }while(opcion<1||opcion>reportes.size());
        System.out.println("Reporte eliminado exitosamente");
        reportes.remove(opcion-1);
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
        }else {
            System.out.println("----LISTA DE REPORTES-----");
            for (int i = 0; i < reportes.size(); i++) {
                System.out.println("Reporte " + (i + 1));
                mostrarDetallesReportes(reportes.get(i));
            }
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


}
