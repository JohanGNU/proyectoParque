package validaciones;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidadorEntradas {
    private static Scanner scanner = new Scanner(System.in);


    // Validación de nombres
    public static String validarNombre(String mensaje) {
        while (true) {
            System.out.print(mensaje);  // 1. Mensaje inicial
            String nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("Error: Este campo no puede estar vacío");  // 2. Error
                continue;
            }
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                System.out.println("Error: Solo se pueden ingresar letras");  // 2. Error
                continue;
            }

            return nombre;
        }
    }

    // Validación de enteros positivos
    public static int validarEnteroPositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valorEntero = scanner.nextLine().trim();
            try {
                if (valorEntero.isEmpty()) throw new IllegalArgumentException("Este campo no puede estar vacio");
                int valor = Integer.parseInt(valorEntero);
                if (valor < 0) throw new IllegalArgumentException("Debe ser positivo");
                return valor;
            } catch (NumberFormatException e) {
                System.err.println("Error: Ingrese un número válido");
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    /*expresion regular para la comparacion con el email ingresado por el usuario*/
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    /*Email: esta funcion valida que el usuario ingrese un email valido para hacer el registro
    * valida que el mail tenga letras antes del arroba, que tenga un arroba, que tenga un dominio y una extension
    * ejemplo: .com .co .org etc*/
    public static String validarEmail(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String email = scanner.nextLine().trim();

            String error = validarErrorEmail(email);
            if (error == null) {
                return email; // email valido
            }

            System.err.println("Error: " + error);
        }
    }

    /*esta funcion retorna null si no hay ningun error o retorna algun fallo como: campo vacio o
    * formato invalido */
    private static String validarErrorEmail(String email) {
        if (email.isEmpty()) return "Este campo no puede estar vacio";
        if (!EMAIL_PATTERN.matcher(email).matches()) return "Formato invalido. Ejemplo: usuario@dominio.com";
        return null; // retorna null si no hubo ningun error
    }

    //expresion regular para la comparacion con la direccion ingresada por el usuario
    private static final Pattern CALLE_PATTERN = Pattern.compile(
            "^(?i)" +  // Ignorar mayúsculas/minúsculas
                    "(?:" +
                    "(?:calle|call|cll|c|k|kalle|krra?|cra|cr|kra|carrera|carr|carretera|diagonal|dgn|dg|transversal|trv|tv|t|avenida|av|ave)" +
                    "\\.?\\s*" +
                    ")?" +  // Tipo de vía opcional
                    "\\s*" +
                    "\\d+[A-Za-z]?(?:\\s+[A-Za-z]+)?" +  // Número principal
                    "\\s*" +
                    "(?:" +
                    "(?:[#-]?\\s*\\d+[A-Za-z]?)" +  // # o - opcional antes del número adicional
                    "|" +
                    "(?:\\s*(?:apto|apartamento|int|interior|oficina|of|local|lc|manzana|mz|bloque|bl|torre|tr|edificio|ed|sector|sc|barrio|br)\\s*\\w+)" +
                    ")*" +
                    "$"
    );

    /*este metodo recibe la direccion ingresada por el usuario, esta se envia por parametro a otra funcion que valida
    el formato de la direccion"*/
    public static String validarFormatoDireccion(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        String direccion;

        while (true) {
            System.out.print(mensaje);  // Mostrar el mensaje de entrada
            direccion = scanner.nextLine().trim();

            if (direccion.isEmpty()) {
                System.err.println("Error: Este campo no puede estar vacío");
            } else if (!CALLE_PATTERN.matcher(direccion).matches()) {
                System.err.println("Error: Formato de dirección inválido. Ejemplo válido: 'Calle 123 #45-67'");
            } else {
                return direccion;  // Dirección válida
            }

            System.out.println();
        }
    }



    public static int validarEnteroRango(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String valorEntero = scanner.nextLine().trim();
            try {
                if (valorEntero.isEmpty()) {
                    throw new IllegalArgumentException("Este campo no puede estar vacío");
                }
                int valor = Integer.parseInt(valorEntero);
                if (valor < min || valor > max) {
                    throw new IllegalArgumentException("Debe estar entre " + min + " y " + max);
                }
                return valor;
            } catch (NumberFormatException e) {
                System.err.println("Error: Ingrese un número válido");
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    public static void cerrarScanner() {
        scanner.close();
    }
}