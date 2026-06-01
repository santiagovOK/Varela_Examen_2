package org.p3_segundo_parcial;

// Santiago Octavio Varela / @santiagovOK (GitHub) <santiago.varela@tupad.utn.edu.ar>

import org.p3_segundo_parcial.dtos.UsuarioDTO;
import org.p3_segundo_parcial.model.Categoria;
import org.p3_segundo_parcial.model.DetallePedido;
import org.p3_segundo_parcial.model.Pedido;
import org.p3_segundo_parcial.model.Producto;
import org.p3_segundo_parcial.model.Usuario;
import org.p3_segundo_parcial.model.enums.Estado;
import org.p3_segundo_parcial.model.enums.FormaPago;
import org.p3_segundo_parcial.model.enums.Rol;
import org.p3_segundo_parcial.repository.CategoriaRepository;
import org.p3_segundo_parcial.repository.ProductoRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        // Deshabilitar algunos logs de Hibernate (java.util.logging , aparecen en rojo y son invasivos) ya que entorpecen la visión del menú
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

        // Inicializar JPA al principio del programa para evitar problemas de EntityManagerFactory no inicializado.
        org.p3_segundo_parcial.util.JPAUtil.getEntityManagerFactory();

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        CategoriaRepository categoriaRepo = new CategoriaRepository();
        ProductoRepository productoRepo = new ProductoRepository();
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
                    menuProductos(scanner, productoRepo, categoriaRepo);
                    break;
                case 3:
                    menuReportes(scanner, productoRepo, categoriaRepo);
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
        cat.setCreatedAt(java.time.LocalDateTime.now());
        cat.setEliminado(false);

        cat = categoriaRepo.guardar(cat);
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

    // 3.5 - ABM de Productos en Main (HU-06 / HU-07 / HU-08)

    private static void menuProductos(java.util.Scanner scanner, ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {
        int opcion = -1;
        do {
            System.out.println("\n=== SUBMENÚ PRODUCTOS ===");
            System.out.println("1. Alta de producto");
            System.out.println("2. Baja lógica de producto");
            System.out.println("3. Modificación de producto");
            System.out.println("4. Listado de productos activos");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elija una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    altaProducto(scanner, productoRepo, categoriaRepo);
                    break;
                case 2:
                    bajaProducto(scanner, productoRepo);
                    break;
                case 3:
                    modificarProducto(scanner, productoRepo);
                    break;
                case 4:
                    listarProductos(productoRepo);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    // HU-06 - Implementación de Alta en el Menú de Productos
    private static void altaProducto(java.util.Scanner scanner, ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {
        System.out.println("\n--- ALTA DE PRODUCTO ---");
        
        java.util.List<Categoria> categoriasActivas = categoriaRepo.listarActivos();
        if (categoriasActivas.isEmpty()) {
            System.out.println("Error: No hay categorías activas. Debe dar de alta al menos una categoría primero.");
            return;
        }

        System.out.println("Seleccione una categoría activa por su ID:");
        listarCategorias(categoriaRepo);
        System.out.print("ID Categoría: ");
        
        Long idCategoria;
        try {
            idCategoria = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: ID inválido. Operación cancelada.");
            return;
        }

        java.util.Optional<Categoria> optCat = categoriaRepo.buscarPorId(idCategoria);
        if (optCat.isEmpty() || optCat.get().isEliminado()) {
            System.out.println("Error: Categoría no encontrada o inactiva.");
            return;
        }

        System.out.print("Ingrese nombre del producto: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacío.");
            return;
        }

        double precio;
        try {
            System.out.print("Ingrese precio del producto: ");
            precio = Double.parseDouble(scanner.nextLine());
            if (precio <= 0) {
                System.out.println("Error: El precio debe ser mayor a 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Formato de precio inválido.");
            return;
        }

        int stock;
        try {
            System.out.print("Ingrese stock del producto: ");
            stock = Integer.parseInt(scanner.nextLine());
            if (stock < 0) {
                System.out.println("Error: El stock no puede ser negativo.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Formato de stock inválido.");
            return;
        }

        System.out.print("Ingrese descripción del producto (opcional): ");
        String descripcion = scanner.nextLine().trim();

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setDescripcion(descripcion);
        producto.setCategoria(optCat.get());
        producto.setDisponible(true); // Opcional, marcar por defecto
        producto.setCreatedAt(java.time.LocalDateTime.now());
        producto.setEliminado(false);

        producto = productoRepo.guardar(producto);
        System.out.println("Producto creado exitosamente con ID " + producto.getId() + " en la categoría '" + optCat.get().getNombre() + "'.");
    }

    // HU-08 - Implementación de Baja en el Menú de Productos
    private static void bajaProducto(java.util.Scanner scanner, ProductoRepository productoRepo) {
        System.out.println("\n--- BAJA LÓGICA DE PRODUCTO ---");
        System.out.print("Ingrese el ID del producto a dar de baja: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            java.util.Optional<Producto> optProd = productoRepo.buscarPorId(id);

            if (optProd.isPresent() && !optProd.get().isEliminado()) {
                String nombreProd = optProd.get().getNombre();
                boolean eliminado = productoRepo.eliminarLogico(id);
                if (eliminado) {
                    System.out.println("Producto '" + nombreProd + "' dado de baja exitosamente.");
                } else {
                    System.out.println("Error al intentar dar de baja el producto.");
                }
            } else {
                System.out.println("Error: El ID no existe o el producto ya está dado de baja.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: ID inválido.");
        }
    }

    // HU-07 - Implementación de Modificación en el Menú de Productos
    private static void modificarProducto(java.util.Scanner scanner, ProductoRepository productoRepo) {
        System.out.println("\n--- MODIFICACIÓN DE PRODUCTO ---");
        listarProductos(productoRepo);
        System.out.print("\nIngrese el ID del producto a modificar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            java.util.Optional<Producto> optProd = productoRepo.buscarPorId(id);

            if (optProd.isPresent() && !optProd.get().isEliminado()) {
                Producto prod = optProd.get();
                System.out.println("Valores actuales:");
                System.out.println("Nombre: " + prod.getNombre());
                System.out.println("Precio: " + prod.getPrecio());
                System.out.println("Stock: " + prod.getStock());

                System.out.print("Nuevo nombre (deje en blanco para no modificar): ");
                String nuevoNombre = scanner.nextLine().trim();
                
                System.out.print("Nuevo precio (deje en blanco para no modificar): ");
                String precioStr = scanner.nextLine().trim();
                
                System.out.print("Nuevo stock (deje en blanco para no modificar): ");
                String stockStr = scanner.nextLine().trim();

                if (!nuevoNombre.isEmpty()) {
                    prod.setNombre(nuevoNombre);
                }

                if (!precioStr.isEmpty()) {
                    try {
                        double nuevoPrecio = Double.parseDouble(precioStr);
                        if (nuevoPrecio > 0) {
                            prod.setPrecio(nuevoPrecio);
                        } else {
                            System.out.println("Error: El precio no puede ser menor o igual a 0. Se conservará el precio anterior.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Formato de precio inválido. Se conservará el precio anterior.");
                    }
                }

                if (!stockStr.isEmpty()) {
                    try {
                        int nuevoStock = Integer.parseInt(stockStr);
                        if (nuevoStock >= 0) {
                            prod.setStock(nuevoStock);
                        } else {
                            System.out.println("Error: El stock no puede ser negativo. Se conservará el stock anterior.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Formato de stock inválido. Se conservará el stock anterior.");
                    }
                }

                productoRepo.guardar(prod);
                System.out.println("Producto actualizado exitosamente.");
            } else {
                System.out.println("Error: ID no encontrado o el producto está inactivo.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: ID inválido.");
        }
    }

    private static void listarProductos(ProductoRepository productoRepo) {
        System.out.println("\n--- LISTADO DE PRODUCTOS ACTIVOS ---");
        java.util.List<Producto> activos = productoRepo.listarActivos();
        if (activos.isEmpty()) {
            System.out.println("No hay productos activos.");
            return;
        }
        for (Producto p : activos) {
            String catNombre = (p.getCategoria() != null) ? p.getCategoria().getNombre() : "Sin categoría";
            System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + 
                               " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock() + 
                               " | Categoría: " + catNombre);
        }
    }

    // HU-09 - Consulta JPQL / Reporte de Productos por Categoría

    private static void menuReportes(Scanner scanner, ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {
        System.out.println("\n--- REPORTES ---");
        System.out.println("Productos por categoría");

        // Mostrar categorías activas
        // Almaceno categorías activas en una variable (var) para evitar hacer múltiples consultas al repositorio durante la validación y selección de categoría. Interpreto que es una buena práctica en este caso
        var categorias = categoriaRepo.listarActivos();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías activas.");
            return;
        }

        System.out.println("Categorías disponibles:");
        for (var cat : categorias) {
            System.out.println("ID: " + cat.getId() + " - " + cat.getNombre());
        }

        // Pedir ID de categoría al usuario
        System.out.print("Ingrese ID de la categoría para ver sus productos: ");
        Long idCat;
        try {
            idCat = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: ID inválido.");
            return;
        }

        // Validar que la categoría elegida exista y esté activa
        // Main llama a ProductoRepository y espera la respuesta para buscar por buscarPorId. Así se cumplen los criterios 2,3 y 4 de la HU-09
        var categoriaOpt = categoriaRepo.buscarPorId(idCat);
        if (categoriaOpt.isEmpty() || categoriaOpt.get().isEliminado()) {
            System.out.println("Error: La categoría no existe o está inactiva.");
            return;
        }

        // Buscar productos usando el método JPQL (mismo caso que `categorias` respecto al uso de var)
        var productos = productoRepo.buscarPorCategoria(idCat);

        // Mostrar informe explícito si está vacío o los productos encontrados
        if (productos.isEmpty()) {
            System.out.println("No hay productos vinculados a la categoría " + categoriaOpt.get().getNombre() + ".");
        } else {
            System.out.println("\nProductos de la categoría '" + categoriaOpt.get().getNombre() + "':");
            for (var prod : productos) {
                System.out.println(String.format("ID: %d | Nombre: %s | Precio: %.2f | Stock: %d",
                        prod.getId(), prod.getNombre(), prod.getPrecio(), prod.getStock()));
            }
        }
    }
}
