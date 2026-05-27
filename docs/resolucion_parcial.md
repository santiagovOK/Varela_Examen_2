# Resolucion del segundo parcial (partiendo de docs/resolucion_tp8.md)

## Preparación del entorno y refactorización

- **Refactorización de `Main.java` y creación del menú interactivo**: Se limpió el `main` de la lógica de trabajos anteriores para dar lugar al menú de consola requerido (Categorías, Productos, Reportes).
- **Preservación de datos de prueba**: Se extrajo la creación de datos semilla (usuarios, categorías, productos, pedidos) al método `poblarBaseDeDatosBase()`. Esto permite disponer (por ahora) de datos iniciales para probar los reportes sin necesidad de cargar todo a mano, sin interferir con el menú.
- **Eliminación del viejo CRUD en `Main`**: Se borraron las consultas de la base de datos que se hacían de manera suelta (`find`, `remove` físicos). Esto se hizo porque la nueva arquitectura exige el uso del **Patrón Repository**, donde las operaciones estarán encapsuladas y aisladas. Además, la consigna demanda que las eliminaciones ahora sean lógicas (`eliminado = true`) y no físicas.
- **Creación de `JPAUtil`**: Se movió la lógica de instanciación del `EntityManagerFactory` a la clase utilitaria `JPAUtil` para garantizar que la conexión se recicle correctamente de forma centralizada al ser utilizada por los repositorios.
