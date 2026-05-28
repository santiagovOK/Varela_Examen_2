package org.p3_segundo_parcial;

// Santiago Octavio Varela / @santiagovOK (GitHub) <santiago.varela@tupad.utn.edu.ar>

import org.p3_segundo_parcial.dtos.UsuarioDTO;
import org.p3_segundo_parcial.entities.Categoria;
import org.p3_segundo_parcial.entities.DetallePedido;
import org.p3_segundo_parcial.entities.Pedido;
import org.p3_segundo_parcial.entities.Producto;
import org.p3_segundo_parcial.entities.Usuario;
import org.p3_segundo_parcial.enums.Estado;
import org.p3_segundo_parcial.enums.FormaPago;
import org.p3_segundo_parcial.enums.Rol;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        // Descomentar la siguiente linea si desea cargar los datos de prueba
        // poblarBaseDeDatosBase();

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Submenú Categorías");
            System.out.println("2. Submenú Productos");
            System.out.println("3. Reportes (Productos por categoría)");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    System.out.println("Menú Categorías en desarrollo...");
                    break;
                case 2:
                    System.out.println("Menú Productos en desarrollo...");
                    break;
                case 3:
                    System.out.println("Reportes en desarrollo...");
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        scanner.close();
        org.p3_segundo_parcial.util.JPAUtil.cerrar();
    }
}

