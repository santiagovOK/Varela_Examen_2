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
         * Aclaración ( Devolución TP N°8):
         * Actualmente, la conexión se establece fijando en código el nombre de la
         * unidad ("p3_segundo_parcial") y dependiendo completamente del archivo base persistence.xml.
         *
         * Sé que esto no sería viable en un entorno de producción, ya que si cambian las
         * credenciales o la URL de la base de datos (un cambio de credenciales o motor de DB obligaría a recompilar el proyecto),
         * tendríamos que recompilar la aplicación. La solución profesional y robusta sería pasar
         * variables de entorno o un archivo de configuración externo (.env / .properties / .yaml)
         * y crear el EntityManagerFactory de esta forma:
         *
         * Map<String, String> dbConfig = loadExternalConfig();
         * EntityManagerFactory emf = Persistence.createEntityManagerFactory("p3_segundo_parcial", dbConfig);
         *
         * Por facilidad y para cumplir con los requerimientos del segundo parcial, mantengo el
         * uso directo de la plantilla del persistence.xml ofrecida en el TP Nº8. Simplemente quiero dar cuenta de esta cuestión.
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

        // --- TP Nº8 - Consignas 3,4,5,6 - Operaciones CRUD solicitadas ---

        try {
            // Abrimos una nueva transacción para hacer las modificaciones
            em.getTransaction().begin();

            // Consigna 3 - Actualizar al menos 2 productos
            System.out.println("\n--- 1. Actualizar al menos 2 productos ---");
            Producto productoUpdate1 = em.find(Producto.class, 1L); // Asumiendo que obtendrá el de ID 1
            if (productoUpdate1 != null) {
                productoUpdate1.setPrecio(2000.0); // Cambiamos el precio
                System.out.println("Producto 1 actualizado: " + productoUpdate1.getNombre() + " a $" + productoUpdate1.getPrecio());
            }

            Producto productoUpdate2 = em.find(Producto.class, 2L); // Asumiendo que obtendrá el de ID 2
            if (productoUpdate2 != null) {
                productoUpdate2.setStock(150); // Cambiamos el stock
                System.out.println("Producto 2 actualizado: " + productoUpdate2.getNombre() + " a " + productoUpdate2.getStock() + " unidades");
            }

            // Consigna 4 - Buscar Usuario por id
            System.out.println("\n--- 2. Buscar Usuario por ID ---");
            Usuario usuarioBuscadoId = em.find(Usuario.class, 1L);
            if (usuarioBuscadoId != null) {
                System.out.println("Usuario encontrado por ID 1: " + usuarioBuscadoId.getNombre() + " " + usuarioBuscadoId.getApellido());
            }

            // Consigna 5 - Buscar Usuario por mail
            System.out.println("\n--- 3. Buscar Usuario por mail ---");
            String mailABuscar = "santiago@email.com";
            // Se realiza la consulta mediante JPQL (no vimos createQuery en los materiales si no me equivoco, pero es una función del EntityManager en JPA utilizada para crear y definir una instancia de consulta dinámica)
            Usuario usuarioBuscadoMail = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :mail", Usuario.class)
                    .setParameter("mail", mailABuscar)
                    .getSingleResult();
            System.out.println("Usuario encontrado por mail: " + usuarioBuscadoMail.getNombre() + " - Rol: " + usuarioBuscadoMail.getRol());

            // Consigna 6 - Borrar 1 producto
            System.out.println("\n--- 4. Borrar 1 producto ---");

            // Se busca el producto ID 10 ("Pizza Especial") para eliminarlo.

            Producto productoABorrar = em.find(Producto.class, 10L);
            // Consigna 6 - Borrar 1 producto (CORREGIDO C11: Manejo de integridad referencial)
            System.out.println("\n--- 4. Borrar 1 producto ---");
            Producto productoABorrar = em.find(Producto.class, 10L);

            if (productoABorrar != null) {

                // a. Validar que el producto no esté en ningún DetallePedido
                Long countDetalles = em.createQuery(
                                "SELECT COUNT(dp) FROM DetallePedido dp WHERE dp.producto = :prod", Long.class)
                        .setParameter("prod", productoABorrar)
                        .getSingleResult();

                if (countDetalles > 0) {
                    System.out.println("No se puede borrar el producto '" + productoABorrar.getNombre() +
                            "' porque está asociado a " + countDetalles + " pedidos.");
                } else {
                    // b. Desvincular de la Categoría para mantener la consistencia en memoria
                    if (productoABorrar.getCategoria() != null) {
                        productoABorrar.getCategoria().getProductos().remove(productoABorrar);
                    }

                    // c. Remover la entidad
                    em.remove(productoABorrar);
                    System.out.println("Producto borrado exitosamente: " + productoABorrar.getNombre());
                }
            } else {
                System.out.println("El producto con ID 10 no fue encontrado.");
            }

            // Se insertan las operaciones a la base de datos
            em.getTransaction().commit();

        } catch (jakarta.persistence.PersistenceException e) {
            System.err.println("Fallo durante las operaciones CRUD (Violación de integridad / sintaxis JPQL): " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            System.err.println("Fallo inesperado general en las operaciones CRUD.");
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            // Cerramos el EntityManager y su Factory porque ya terminamos todas las interacciones con JPA
            em.close();
            emf.close();
        }

        // Mostrar por consola un producto
        System.out.println("Muestro un producto:");
        System.out.println(prod1);

        // Mostrar el listado de productos cargados
        System.out.println("\n=== Listado de productos cargados ===");
        for (Producto producto : productos) {
            System.out.println(producto);
        }

        // Obtener y mostrar pedidos del usuario con mas pedidos
        Usuario usuarioConMasPedidos = null;
        int maxPedidos = -1;

        for (Map.Entry<Usuario, Set<Pedido>> entrada : pedidosPorUsuario.entrySet()) {
            int cantidadPedidos = entrada.getValue().size();
            if (cantidadPedidos > maxPedidos) {
                maxPedidos = cantidadPedidos;
                usuarioConMasPedidos = entrada.getKey();
            }
        }

        System.out.println("\n=== Usuario con mas pedidos ===");
        System.out.println(usuarioConMasPedidos);
        System.out.println("Cantidad de pedidos: " + maxPedidos);

        if (usuarioConMasPedidos != null && pedidosPorUsuario.containsKey(usuarioConMasPedidos)) {
            Set<Pedido> pedidosDelUsuario = pedidosPorUsuario.get(usuarioConMasPedidos);
            if (pedidosDelUsuario != null) {
                for (Pedido pedido : pedidosDelUsuario) {
                    System.out.println(pedido);
                    for (DetallePedido detalle : pedido.getDetalles()) {
                        System.out.println("  " + detalle);
                    }
                }
            }
        }

        // Instanciar un producto nuevo con los mismos campos comparados por equals
        Producto productoDuplicado = Producto.builder()
                .nombre("Coca Cola 500ml")
                .precio(1800.0)
                .descripcion("Gaseosa cola")
                .stock(50)
                .imagen("coca500.png")
                .disponible(true)
                .categoria(categoriaUno)
                .build();

        System.out.println("\n=== Comparacion de producto nuevo vs coleccion ===");
        for (Producto producto : productos) {
            System.out.println("Duplicado equals " + producto.getNombre() + " -> " + productoDuplicado.equals(producto));
        }
        System.out.println("Set contiene producto duplicado: " + productos.contains(productoDuplicado));

        // Prueba final: Mostrar el UsuarioDTO
        System.out.println("\n=== Demostración de Usuario DTO ===");
        UsuarioDTO dto = new UsuarioDTO(
                usuarioUno.getNombre(),
                usuarioUno.getApellido(),
                usuarioUno.getMail(),
                usuarioUno.getCelular()
        );
        System.out.println("Usuario DTO generado: " + dto);

        // --- SALIDAS POR CONSOLA SOLICITADAS PARA TP UNIDAD 7 ---


        // Consigna 2 - Mostrar por consola productos disponibles
        System.out.println("\n=== Productos disponibles ===");
        productos.stream()
                // equivaldría a p -> p.isDisponible()
                .filter(Producto::isDisponible)
                .forEach(p -> System.out.println("- " + p.getNombre()));

        // Consigna 3 - Mostrar por consola la cantidad de ítems que tiene un pedido

        System.out.println("\n=== Cantidad de ítems en Pedido 1 ===");
        int cantidadItems = pedido1.getDetalles().stream()
                .mapToInt(DetallePedido::getCantidad)
                .sum();
        System.out.println("El pedido 1 contiene " + cantidadItems + " ítems en total.");

        // Consigna 4 - Detectar productos que tengan menos de 5 como valor en stock (cambié "Coca Cola 500ml" (prod1) para que aparezca)

        System.out.println("\n=== Productos con stock menor a 5 ===");
        productos.stream()
                .filter(p -> p.getStock() < 5)
                .forEach(p -> System.out.println("ALERTA - " + p.getNombre() + " (Stock: " + p.getStock() + ")"));

    }


}