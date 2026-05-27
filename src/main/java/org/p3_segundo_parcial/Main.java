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

    // De momento el ingreso masivo de datos fue refactorizado en `poblarBaseDeDatosBase()`, pero seguramente será eliminado

    private static void poblarBaseDeDatosBase() {
        // 1) Instanciar 2 Usuarios usando Builder
        Usuario usuarioUno = Usuario.builder()
                .nombre("Santiago")
                .apellido("Varela")
                .mail("santiago@email.com")
                .celular("123456789")
                .password("password123")
                .rol(Rol.ADMIN)
                .build();

        Usuario usuarioDos = Usuario.builder()
                .nombre("Pedro")
                .apellido("González")
                .mail("pedro@email.com")
                .celular("32456782")
                .password("password321")
                .rol(Rol.USUARIO)
                .build();

        // 3) Instanciar 3 Categorías usando Builder
        Categoria categoriaUno = Categoria.builder()
                .nombre("Bebidas")
                .descripcion("Bebidas frías y calientes")
                .build();

        Categoria categoriaDos = Categoria.builder()
                .nombre("Hamburguesas")
                .descripcion("Hamburguesas clásicas, vegetarianas y veganas")
                .build();

        Categoria categoriaTres = Categoria.builder()
                .nombre("Pizzas")
                .descripcion("Pizzas clásicas, vegetarianas y veganas")
                .build();

        // 4) Instanciar 10 productos usando Builder
        Producto prod1 = Producto.builder().nombre("Coca Cola 500ml").precio(1800.0).descripcion("Gaseosa cola").stock(4).imagen("coca500.png").disponible(true).categoria(categoriaUno).build();
        Producto prod2 = Producto.builder().nombre("Agua Mineral 500ml").precio(1200.0).descripcion("Agua sin gas").stock(60).imagen("agua500.png").disponible(true).categoria(categoriaUno).build();
        Producto prod3 = Producto.builder().nombre("Jugo Naranja").precio(1600.0).descripcion("Jugo natural").stock(40).imagen("jugo_naranja.png").disponible(true).categoria(categoriaUno).build();

        Producto prod4 = Producto.builder().nombre("Hamburguesa Clasica").precio(6500.0).descripcion("Carne, queso y huevo").stock(30).imagen("hamb_clasica.png").disponible(true).categoria(categoriaDos).build();
        Producto prod5 = Producto.builder().nombre("Hamburguesa Doble").precio(8200.0).descripcion("Doble carne y doble queso").stock(25).imagen("hamb_doble.png").disponible(true).categoria(categoriaDos).build();
        Producto prod6 = Producto.builder().nombre("Hamburguesa Veggie").precio(7000.0).descripcion("Medallon vegetal con queso").stock(20).imagen("hamb_veggie.png").disponible(true).categoria(categoriaDos).build();
        Producto prod7 = Producto.builder().nombre("Hamburguesa Vegana").precio(7300.0).descripcion("Sin ingredientes de origen animal").stock(18).imagen("hamb_vegana.png").disponible(true).categoria(categoriaDos).build();

        Producto prod8 = Producto.builder().nombre("Pizza Muzzarella").precio(13000.0).descripcion("Pizza clasica de muzzarella").stock(15).imagen("pizza_muzza.png").disponible(true).categoria(categoriaTres).build();
        Producto prod9 = Producto.builder().nombre("Pizza Napolitana").precio(15000.0).descripcion("Tomate, ajo y oregano").stock(12).imagen("pizza_napo.png").disponible(true).categoria(categoriaTres).build();
        Producto prod10 = Producto.builder().nombre("Pizza Especial").precio(15500.0).descripcion("Jamon, morron y huevo").stock(10).imagen("pizza_especial.png").disponible(true).categoria(categoriaTres).build();

        // Relacion categoria -> productos
        categoriaUno.getProductos().add(prod1);
        categoriaUno.getProductos().add(prod2);
        categoriaUno.getProductos().add(prod3);

        categoriaDos.getProductos().add(prod4);
        categoriaDos.getProductos().add(prod5);
        categoriaDos.getProductos().add(prod6);
        categoriaDos.getProductos().add(prod7);

        categoriaTres.getProductos().add(prod8);
        categoriaTres.getProductos().add(prod9);
        categoriaTres.getProductos().add(prod10);

        // 2) Instanciar 3 Pedidos usando Builder
        Pedido pedido1 = Pedido.builder().fecha(LocalDate.now()).estado(Estado.PENDIENTE).formaPago(FormaPago.EFECTIVO).build();
        pedido1.addDetallePedido(2, prod1);
        pedido1.addDetallePedido(1, prod2);

        Pedido pedido2 = Pedido.builder().fecha(LocalDate.now()).estado(Estado.PENDIENTE).formaPago(FormaPago.TARJETA).build();
        pedido2.addDetallePedido(3, prod3);
        pedido2.addDetallePedido(2, prod4);

        Pedido pedido3 = Pedido.builder().fecha(LocalDate.now()).estado(Estado.TERMINADO).formaPago(FormaPago.TRANSFERENCIA).build();
        pedido3.addDetallePedido(1, prod5);
        pedido3.addDetallePedido(4, prod6);

        // Coleccion general de productos cargados (esto queda igual que antes)
        Set<Producto> productos = new HashSet<>();
        productos.add(prod1);
        productos.add(prod2);
        productos.add(prod3);
        productos.add(prod4);
        productos.add(prod5);
        productos.add(prod6);
        productos.add(prod7);
        productos.add(prod8);
        productos.add(prod9);
        productos.add(prod10);

        // Asignación de pedidos por usuario (esto queda igual que antes)
        Map<Usuario, Set<Pedido>> pedidosPorUsuario = new HashMap<>();
        pedidosPorUsuario.put(usuarioUno, new HashSet<>());
        pedidosPorUsuario.put(usuarioDos, new HashSet<>());

        pedidosPorUsuario.get(usuarioUno).add(pedido1);
        pedidosPorUsuario.get(usuarioUno).add(pedido2);
        pedidosPorUsuario.get(usuarioDos).add(pedido3);

        // --- TP Nº8 - CONFIGURAR Y EJECUTAR EL ENTITY MANAGER ---
        /*
         * Aclaración ( Devolución TP N°8): Ver comentario JPAUtil.java , que esta estrucura no usa por ser heredada de TP Nº8
         */

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("p3_segundo_parcial");
        EntityManager em = emf.createEntityManager();

        // --- TP Nº8 - Instanciamiento y persistencia de lo solicitado en la consigna Nª3 (envuelto en try-catch para evitar errores) ---

        try {
            // Se abre la transacción
            em.getTransaction().begin();

            // Se guardan las Categorías
            em.persist(categoriaUno);
            em.persist(categoriaDos);
            em.persist(categoriaTres);

            // Se guardan los Usuarios
            em.persist(usuarioUno);
            em.persist(usuarioDos);

            // Se guardan los Productos (No es necesario hacer persist explícito si usé cascade = CascadeType.ALL en Categoria, pero es buena práctica)
            em.persist(prod1);
            em.persist(prod2);
            em.persist(prod3);
            em.persist(prod4);
            em.persist(prod5);
            em.persist(prod6);
            em.persist(prod7);
            em.persist(prod8);
            em.persist(prod9);
            em.persist(prod10);

            // Se guardan los Pedidos (Al tener cascade, va a guardar los DetallePedido automáticamente)
            em.persist(pedido1);
            em.persist(pedido2);
            em.persist(pedido3);

            // Se efectua el guardado en la base de datos
            em.getTransaction().commit();
            System.out.println("Datos persistidos en H2 con éxito.");

        } catch (jakarta.persistence.PersistenceException e) {
            System.err.println("Error de persistencia en la base de datos (Ej: Constraint, Conexión): " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            System.err.println("Ha ocurrido un error inesperado general.");
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
}

