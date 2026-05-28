# Resolucion del segundo parcial (partiendo de docs/resolucion_tp8.md)

## Preparación del entorno y refactorización

- **Refactorización de `Main.java` y creación del menú interactivo**: Se limpió el `main` de la lógica de trabajos anteriores para dar lugar parcialmente al menú de consola requerido (Categorías, Productos, Reportes).
- **Eliminación del viejo CRUD en `Main`**: Se borraron las consultas de la base de datos que se hacían de manera suelta (`find`, `remove` físicos). Esto se hizo porque la nueva arquitectura exige el uso del **Patrón Repository**, donde las operaciones estarán encapsuladas y aisladas. Además, la consigna demanda que las eliminaciones ahora sean lógicas (`eliminado = true`) y no físicas.
- **Creación de `JPAUtil`**: Se movió la lógica de instanciación del `EntityManagerFactory` a la clase utilitaria `JPAUtil` para garantizar que la conexión se recicle correctamente de forma centralizada al ser utilizada por los repositorios. No había sido creado para el TP Nº8, pero claramente es una buena práctica y facilita el mantenimiento de la conexión a la base de datos.

## Implementación de funcionalidades (Siguiendo las consignas del parcial (`consigna.md`))

- **Creación de `BaseRepository` (HU-01)**: Se desarrolló un repositorio genérico `BaseRepository<T>` abstracto para aislar las operaciones CRUD comunes usando JPA y `JPAUtil`. Incluye transacciones asiladas en los métodos `guardar()`, uso de `Optional` en la búsqueda, y provee listado de `activos` mediante JPQL e `eliminarLogico()` seteando el campo de la base `eliminado = true`, garantizando siempre el cierre del `EntityManager`.
- **Implementación de `CategoriaRepository` (HU-02)**: Se creó la clase extendiendo `BaseRepository<Categoria>` y llamando a `super(Categoria.class)`, heredando así todo el CRUD general de manera limpia y sin código adicional.
- **Implementación de `ProductoRepository` (HU-02 y HU-09)**: Extiende de `BaseRepository<Producto>`. Se agregó el método `buscarPorCategoria(Long categoriaId)` implementado con JPQL y parámetros nombrados, devolviendo un `TypedQuery<Producto>` para filtrar productos activos de una categoría, incluyendo el comentario explicativo exigido en la consigna.

## Modificaciones del sistema base e Interfaz

- **Implementación parcial del Menú (HU-03, HU-04, HU-05)**: Se integró de manera funcional el Submenú de **Categorías** dentro del ciclo interactivo en `Main.java` utilizando el `CategoriaRepository`. Consta de los cuatro requerimientos principales: Alta confirmando ID y nombre verificados, Baja lógica validando existencias, un procedimiento de Modificación que preserva en blanco los atributos sin cambio alguno, y finalmente el Listado completo de únicamente elementos cuyo `eliminado = false`.

- **Ajustes de terminal en `build.gradle.kts`**: Se agregó configuración para asignar el `standardInput` a la tarea run. Esto soluciona una limitación por defecto de Gradle que impedía la interacción manual a través del objeto `java.util.Scanner(System.in)`, arrojando un error de `NoSuchElementException`.
- **Higiene visual en Logs de Consola**: Se configuró vía código en el arranque mismo del proyecto (`Main.java`) el nivel de logging de `org.hibernate` hacia `SEVERE`. La inicialización del `EntityManagerFactory` ensuciaba con mensajes "INFO" y "WARN" el menú estándar, entorpeciendo la usabilidad requerida para la entrega interactiva.

- **Implementación parcial del Menú (HU-06, HU-07, HU-08)**: Se integró el Submenú de **Productos** utilizando el `ProductoRepository`. Se implementó el Alta de Producto forzando la selección previa de una Categoría listada, validando tipo y cantidad para `precio` (> 0) y `stock` (>= 0). La Baja Lógica opera verificando la existencia activa del registro. Durante la Modificación, se pueden dejar campos en blanco para mantener sus valores previos; y en el Listado, se incorporó la lectura relacional (`@ManyToOne`) mostrando el nombre de la Categoría propia de cada producto.