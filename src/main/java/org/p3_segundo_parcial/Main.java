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
import org.p3_segundo_parcial.repository.CategoriaRepository;

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

        // Deshabilitar logs de Hibernate (java.util.logging) ya que entorpecen la visión del menú
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

        // Inicializar JPA al principio del programa para evitar problemas de EntityManagerFactory no inicializado.
        org.p3_segundo_parcial.util.JPAUtil.getEntityManagerFactory();

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        CategoriaRepository categoriaRepo = new CategoriaRepository();
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
                    menuCategorias(scanner, categoriaRepo);
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

    // 3.4 - ABM de Categorias en Main (HU-03 / HU-04 / HU-05)
    private static void menuCategorias(java.util.Scanner scanner, CategoriaRepository categoriaRepo) {
        int opcion = -1;
        do {
            System.out.println("\n=== SUBMENÚ CATEGORÍAS ===");
            System.out.println("1. Alta de categoría");
            System.out.println("2. Baja lógica de categoría");
            System.out.println("3. Modificación de categoría");
            System.out.println("4. Listado de categorías activas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elija una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    altaCategoria(scanner, categoriaRepo);
                    break;
                case 2:
                    bajaCategoria(scanner, categoriaRepo);
                    break;
                case 3:
                    modificarCategoria(scanner, categoriaRepo);
                    break;
                case 4:
                    listarCategorias(categoriaRepo);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
    // HU-03 - Implementación de Alta en el Menú de Categorias
    private static void altaCategoria(java.util.Scanner scanner, CategoriaRepository categoriaRepo) {
        System.out.println("\n--- ALTA DE CATEGORÍA ---");
        System.out.print("Ingrese nombre de la categoría: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return;
        }
        System.out.print("Ingrese descripción de la categoría: ");
        String descripcion = scanner.nextLine().trim();

        Categoria cat = new Categoria();
        cat.setNombre(nombre);
        cat.setDescripcion(descripcion);

        categoriaRepo.guardar(cat);
        System.out.println("Categoría creada exitosamente con ID: " + cat.getId());
    }
    // HU-05 - Implementación de Baja en el Menú de Categorias
    private static void bajaCategoria(java.util.Scanner scanner, CategoriaRepository categoriaRepo) {
        System.out.println("\n--- BAJA LÓGICA DE CATEGORÍA ---");
        System.out.print("Ingrese el ID de la categoría a dar de baja: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            java.util.Optional<Categoria> optCat = categoriaRepo.buscarPorId(id);

            if (optCat.isPresent() && !optCat.get().isEliminado()) {
                String nombreCat = optCat.get().getNombre();
                boolean eliminada = categoriaRepo.eliminarLogico(id);
                if (eliminada) {
                    System.out.println("Categoría '" + nombreCat + "' dada de baja exitosamente.");
                } else {
                    System.out.println("Error al intentar dar de baja la categoría.");
                }
            } else {
                System.out.println("Error: El ID no existe o la categoría ya está dada de baja.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: ID inválido.");
        }
    }
    // HU-04 - Implementación de Baja en el Menú de Categorias
    private static void modificarCategoria(java.util.Scanner scanner, CategoriaRepository categoriaRepo) {
        System.out.println("\n--- MODIFICACIÓN DE CATEGORÍA ---");
        listarCategorias(categoriaRepo);
        System.out.print("\nIngrese el ID de la categoría a modificar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            java.util.Optional<Categoria> optCat = categoriaRepo.buscarPorId(id);

            if (optCat.isPresent() && !optCat.get().isEliminado()) {
                Categoria cat = optCat.get();
                System.out.println("Valores actuales:");
                System.out.println("Nombre: " + cat.getNombre());
                System.out.println("Descripción: " + cat.getDescripcion());

                System.out.print("Nuevo nombre (deje en blanco para no modificar): ");
                String nuevoNombre = scanner.nextLine().trim();
                System.out.print("Nueva descripción (deje en blanco para no modificar): ");
                String nuevaDesc = scanner.nextLine().trim();

                if (!nuevoNombre.isEmpty()) {
                    cat.setNombre(nuevoNombre);
                }
                if (!nuevaDesc.isEmpty()) {
                    cat.setDescripcion(nuevaDesc);
                }
                categoriaRepo.guardar(cat);
                System.out.println("Categoría actualizada exitosamente.");
            } else {
                System.out.println("Error: ID no encontrado o la categoría está inactiva.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: ID inválido.");
        }
    }

    private static void listarCategorias(CategoriaRepository categoriaRepo) {
        System.out.println("\n--- LISTADO DE CATEGORÍAS ACTIVAS ---");
        java.util.List<Categoria> activas = categoriaRepo.listarActivos();
        if (activas.isEmpty()) {
            System.out.println("No hay categorías activas.");
            return;
        }
        for (Categoria c : activas) {
            System.out.println("ID: " + c.getId() + " | Nombre: " + c.getNombre() + " | Descripción: " + c.getDescripcion());
        }
    }
}
