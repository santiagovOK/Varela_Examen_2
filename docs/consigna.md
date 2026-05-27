# EVALUACION PARCIALJava Persistence API (JPA)

_Repositorios y ABM de Categorias y Productos_

# Objetivo General

Extender el proyecto Gradle del TP de la Unidad 8 creando los repositorios para las entidades Categoria y Producto, e implementando un menu de consola que permita realizar operaciones ABM sobre dichas entidades. Se incluye ademas una consulta JPQL personalizada para filtrar productos por categoria.

# Archivos a Crear

El alumno debe agregar al proyecto los siguientes archivos nuevos. No se debe modificar ninguna clase del TP base.

|     |     |
| --- | --- |
| **Archivo a crear** | **Responsabilidad** |
| BaseRepository.java | Repositorio generico &lt;T&gt; con operaciones CRUD comunes: guardar, buscarPorId, listarActivos, eliminarLogico. |
| CategoriaRepository.java | Extiende BaseRepository&lt;Categoria&gt;. Sin metodos adicionales. |
| ProductoRepository.java | Extiende BaseRepository&lt;Producto&gt;. Agrega buscarPorCategoria(Long id) con JPQL. |
| Main.java | Clase principal con menu de consola que expone el ABM de Categoria y Producto, y la consulta JPQL. |

### Esquema de paquetes

Respetar la siguiente estructura dentro de src/main/java/:

com.tp.jpa/

model/ model/enums/ util/ repository/

Main.java

<- ya existe (no modificar)

<- ya existe (no modificar)

<- ya existe, contiene JPAUtil.java

<- NUEVO: crear este paquete con los 3 repositorios

<- NUEVO: clase principal con el menu

# Consigna Tecnica

- 1.  **BaseRepository&lt;T&gt; (repositorio generico)**

Crear la clase abstracta BaseRepository&lt;T&gt; en el paquete repository. Debe recibir la Class&lt;T&gt; por constructor y obtener el EntityManagerFactory desde JPAUtil. Implementar los siguientes metodos:

1.  guardar(T entity): persiste o actualiza la entidad usando merge(). Abre y cierra su propio EntityManager. Maneja la transaccion con begin/commit y rollback en caso de error.
2.  buscarPorId(Long id): retorna Optional&lt;T&gt; usando find(). Retorna Optional.empty() si no existe.
3.  listarActivos(): retorna List&lt;T&gt; con los registros cuyo campo eliminado = false. Usa JPQL.
4.  eliminarLogico(Long id): busca la entidad por ID, establece eliminado = true y persiste el cambio. Retorna boolean indicando si encontro el registro.

**Cada metodo debe abrir su propio EntityManager al inicio y cerrarlo en un bloque finally.**

### CategoriaRepository

Crear la clase CategoriaRepository que extienda BaseRepository&lt;Categoria&gt;. El constructor debe llamar a super(Categoria.class). No requiere metodos adicionales: hereda todo el CRUD del repositorio base.

### ProductoRepository

Crear la clase ProductoRepository que extienda BaseRepository&lt;Producto&gt;. Ademas del CRUD heredado, implementar:

1.  buscarPorCategoria(Long categoriaId): retorna List&lt;Producto&gt; con los productos activos de esa categoria. La consulta debe escribirse en JPQL con un parametro nombrado, filtrar por eliminado = false, y retornar un TypedQuery&lt;Producto&gt; (sin casteos manuales).

### ABM de Categorias en Main

Implementar en la clase Main un submenu de Categorias con las siguientes opciones:

1.  Alta: solicitar nombre y descripcion, crear la instancia, persistir y mostrar el ID generado.
2.  Baja logica: solicitar el ID, marcar eliminado = true. Si no existe, mostrar mensaje de error.
3.  Modificacion: solicitar el ID, mostrar valores actuales, permitir editar nombre y/o descripcion, persistir.
4.  Listado: mostrar todas las categorias activas con ID, nombre y descripcion.

### ABM de Productos en Main

Implementar en Main un submenu de Productos con las siguientes opciones:

1.  Alta: listar categorias activas para seleccionar, solicitar nombre, precio, descripcion y stock, persistir.
2.  Baja logica: solicitar el ID del producto. Si no existe o ya esta dado de baja, mostrar error.
3.  Modificacion: solicitar el ID, mostrar valores actuales, permitir editar nombre, precio y stock.
4.  Listado: mostrar todos los productos activos con ID, nombre, precio, stock y nombre de su categoria.

### Consulta JPQL en el menu

Agregar en Main la opcion "Productos por categoria" dentro de un submenu de Reportes. Al seleccionarla:

- - - Listar las categorias activas para que el usuario elija una.
        - Llamar a ProductoRepository.buscarPorCategoria(id) con el ID seleccionado.
        - Mostrar los resultados con ID, nombre, precio y stock de cada producto.
        - Si no hay productos en esa categoria, informarlo explicitamente.

# Criterios de Evaluacion

|     |     |     |
| --- | --- | --- |
| **Item a evaluar** | **Descripcion** | **Puntaje** |
| HU-01: BaseRepository&lt;T&gt; | CRUD generico correcto, transacciones, Optional, cierre de EntityManager. | 18 pts |
| HU-02: CategoriaRepo / ProductoRepo | Extension correcta, super() con Class&lt;T&gt;, buscarPorCategoria con JPQL. | 12 pts |
| HU-03: Alta de categoria | Validacion de nombre, persistencia, ID visible. | 8 pts |
| HU-04: Modificacion de categoria | Listado previo, error en ID invalido, campo vacio conserva valor. | 10 pts |
| HU-05: Baja de categoria | Baja logica, error en ID invalido, no aparece en listados. | 7 pts |
| HU-06: Alta de producto | Listado de categorias, validacion precio/stock, @ManyToOne resuelto. | 12 pts |
| HU-07: Modificacion de producto | Valores actuales visibles, validaciones de precio y stock. | 10 pts |
| HU-08: Baja de producto | Baja logica, mensaje con nombre, error en ID invalido. | 8 pts |
| HU-09: Consulta JPQL | JPQL correcto, TypedQuery&lt;Producto&gt;, parametro nombrado, sin casteos. | 15 pts |

|     |     |
| --- | --- |
| **PUNTAJE TOTAL** | **100 puntos** |
| Minimo para aprobar | **60 puntos** |

# Condiciones de Entrega

1.  Entregar el proyecto Gradle completo comprimido en .zip con el nombre Apellido_Nombre_ParcialJPA.zip.
2.  El proyecto debe compilar y ejecutar sin errores. Se descuentan 10 puntos por cada error de compilacion.
3.  No modificar ninguna clase del TP base: entidades, enums, JPAUtil ni persistence.xml.
4.  Los 4 archivos nuevos (BaseRepository, CategoriaRepository, ProductoRepository y Main) deben estar en el paquete correcto.
5.  Las opciones del menu deben ser accesibles desde el menu principal de consola.
6.  El metodo buscarPorCategoria debe incluir un comentario explicando que hace la consulta JPQL.

◆uJ,´

## Entrega

La entrega deberá incluir:

³ 1. Repositorio

- - El código debe estar en un único archivo .zip con nombre “Nombre_Alumno_parcial_2.zip” y dejar en los comentarios de la entrega el link al video.
    - El proyecto debe ser funcional y ejecutable
    - Debe incluir un archivo README.md con:
        - Descripción breve del proyecto
        - Instrucciones para ejecutarlo

_2___. Video de presentación (obligatorio)_

Se deberá entregar un video con las siguientes características:

- - ·’ Duración: entre 10 y 15 minutos
    - .•çˆ。Cámara encendida durante toda la exposición
    - 3!• Audio claro y comprensible

z‘’**C ont e ni do del video**

En el video deberás:

1.  Presentarte brevemente
    - Motrar el funcionamiento de la aplicación:
    - Probar la generación de la Categoria.
    - Probar la creación de productos y asociarlos a categoría.
    - Mostrar los productos por id de categoría
    - Eliminar un producto de la categoría.
    - Mostrar los productos por id de categoría y comprobar que ya no se muestra.

1.  Explicar:
    - Qué funcionalidades lograste implementar
    - Cómo abordaste la resolución
    - Qué dificultades encontraste (si las hubo) y cómo las resolviste

# Historias de Usuario

### Repositorios

<div class="joplin-table-wrapper"><table><tbody><tr><td colspan="2"><p><strong>HU-01 | Repositorio generico con CRUD</strong></p></td></tr><tr><td colspan="2"><p><strong>Como </strong>desarrollador</p><p><strong>quiero </strong>contar con un BaseRepository&lt;T&gt; que implemente las operaciones CRUD comunes</p><p><strong>para </strong>no repetir codigo de persistencia en cada repositorio especifico</p></td></tr><tr><td colspan="2"><p><strong>Criterios de aceptacion</strong></p></td></tr><tr><td colspan="2"><ol><li>guardar() abre su propia transaccion, persiste con merge() y la cierra. Hace rollback ante error.</li><li>buscarPorId() retorna Optional&lt;T&gt;: Optional.of(entidad) si existe, Optional.empty() si no.</li><li>listarActivos() usa JPQL con WHERE e.eliminado = false y retorna List&lt;T&gt;.</li><li>eliminarLogico() busca por ID, establece eliminado = true, persiste y retorna true. Retorna false si no encuentra el registro.</li><li>Cada metodo cierra el EntityManager en un bloque finally.</li></ol></td></tr><tr><td><p><strong>Prioridad: </strong>Alta</p></td><td><p><strong>Story Points: </strong>18</p></td></tr></tbody></table></div>

<div class="joplin-table-wrapper"><table><tbody><tr><td colspan="2"><p><strong>HU-02 | Repositorios especificos de Categoria y Producto</strong></p></td></tr><tr><td colspan="2"><p><strong>Como </strong>desarrollador</p><p><strong>quiero </strong>contar con CategoriaRepository y ProductoRepository que extiendan BaseRepository</p><p><strong>para </strong>operar sobre cada entidad sin reescribir el CRUD base</p></td></tr><tr><td colspan="2"><p><strong>Criterios de aceptacion</strong></p></td></tr><tr><td colspan="2"><ol><li>CategoriaRepository extiende BaseRepository&lt;Categoria&gt; y llama a super(Categoria.class).</li><li>ProductoRepository extiende BaseRepository&lt;Producto&gt; y llama a super(Producto.class).</li><li>ProductoRepository incluye buscarPorCategoria(Long categoriaId) con JPQL tipado.</li><li>La consulta JPQL filtra por categoria.id = :categoriaId y eliminado = false.</li><li>El metodo retorna TypedQuery&lt;Producto&gt;; no hay casteos con (Producto) en el codigo.</li></ol></td></tr><tr><td><p><strong>Prioridad: </strong>Alta</p></td><td><p><strong>Story Points: </strong>12</p></td></tr></tbody></table></div>

### Categorias

<div class="joplin-table-wrapper"><table><tbody><tr><td colspan="2"><p><strong>HU-03 | Dar de alta una categoria</strong></p></td></tr><tr><td colspan="2"><p><strong>Como </strong>operador del sistema</p><p><strong>quiero </strong>poder crear una nueva categoria ingresando nombre y descripcion</p><p><strong>para </strong>organizar los productos del catalogo en grupos tematicos</p></td></tr><tr><td colspan="2"><p><strong>Criterios de aceptacion</strong></p></td></tr><tr><td colspan="2"><ol><li>El sistema solicita nombre y descripcion por consola.</li><li>Si el nombre esta vacio, el sistema informa el error y no persiste.</li><li>Al guardar exitosamente, se muestra el ID generado por la base de datos.</li><li>La categoria queda con eliminado = false y createdAt con la fecha/hora actual.</li></ol></td></tr><tr><td><p><strong>Prioridad: </strong>Alta</p></td><td><p><strong>Story Points: </strong>8</p></td></tr></tbody></table></div>

<div class="joplin-table-wrapper"><table><tbody><tr><td colspan="2"><p><strong>HU-04 | Modificar una categoria existente</strong></p></td></tr><tr><td colspan="2"><p><strong>Como </strong>operador del sistema</p><p><strong>quiero </strong>poder editar el nombre o la descripcion de una categoria ya creada</p><p><strong>para </strong>corregir errores sin tener que borrar y recrear el registro</p></td></tr><tr><td colspan="2"><p><strong>Criterios de aceptacion</strong></p></td></tr><tr><td colspan="2"><ol><li>El sistema lista las categorias activas antes de pedir el ID.</li><li>Si el ID no corresponde a ninguna categoria activa, se muestra un mensaje de error.</li><li>Se muestran los valores actuales antes de pedir los nuevos.</li><li>Dejar un campo en blanco mantiene el valor anterior.</li><li>El cambio se persiste correctamente en la base de datos.</li></ol></td></tr><tr><td><p><strong>Prioridad: </strong>Alta</p></td><td><p><strong>Story Points: </strong>10</p></td></tr></tbody></table></div>

<div class="joplin-table-wrapper"><table><tbody><tr><td><p><strong>HU-05 | Dar de baja una categoria</strong></p></td></tr><tr><td><p><strong>Como </strong>operador del sistema</p><p><strong>quiero </strong>poder dar de baja una categoria que ya no se utiliza</p><p><strong>para </strong>ocultarla del sistema sin perder el historial de datos</p></td></tr><tr><td><p><strong>Criterios de aceptacion</strong></p></td></tr><tr><td><ol><li>La baja es logica: se establece eliminado = true, el registro permanece en la BD.</li><li>Si el ID no existe o ya esta dado de baja, el sistema informa el error.</li><li>La categoria dada de baja no aparece en ningun listado activo.</li><li>Se confirma la operacion mostrando el nombre de la categoria afectada.</li></ol></td></tr></tbody></table></div>

|     |     |
| --- | --- |
| **Prioridad:** Media | **Story Points:** 7 |

### Productos

<div class="joplin-table-wrapper"><table><tbody><tr><td colspan="2"><p><strong>HU-06 | Dar de alta un producto</strong></p></td></tr><tr><td colspan="2"><p><strong>Como </strong>operador del sistema</p><p><strong>quiero </strong>poder registrar un nuevo producto asociandolo a una categoria existente</p><p><strong>para </strong>incorporar articulos al catalogo con toda su informacion basica</p></td></tr><tr><td colspan="2"><p><strong>Criterios de aceptacion</strong></p></td></tr><tr><td colspan="2"><ol><li>El sistema lista las categorias activas para que el operador seleccione una.</li><li>Si no hay categorias activas, se informa y se cancela la operacion.</li><li>Se solicitan: nombre, precio (mayor a 0) y stock (mayor o igual a 0).</li><li>Si precio o stock tienen valores invalidos, se informa el error y no se persiste.</li><li>Al guardar, se muestra el ID generado y la categoria asignada.</li></ol></td></tr><tr><td><p><strong>Prioridad: </strong>Alta</p></td><td><p><strong>Story Points: </strong>12</p></td></tr></tbody></table></div>

<div class="joplin-table-wrapper"><table><tbody><tr><td colspan="2"><p><strong>HU-07 | Modificar un producto</strong></p></td></tr><tr><td colspan="2"><p><strong>Como </strong>operador del sistema</p><p><strong>quiero </strong>poder actualizar el nombre, precio y stock de un producto existente</p><p><strong>para </strong>mantener el catalogo actualizado sin recrear el registro</p></td></tr><tr><td colspan="2"><p><strong>Criterios de aceptacion</strong></p></td></tr><tr><td colspan="2"><ol><li>El sistema lista los productos activos antes de pedir el ID.</li><li>Si el ID no existe o el producto esta dado de baja, se muestra error.</li><li>Se muestran los valores actuales antes de pedir los nuevos.</li><li>Dejar un campo en blanco conserva el valor anterior.</li><li>Precio no puede actualizarse a un valor menor o igual a 0.</li><li>Stock no puede actualizarse a un valor negativo.</li></ol></td></tr><tr><td><p><strong>Prioridad: </strong>Alta</p></td><td><p><strong>Story Points: </strong>10</p></td></tr></tbody></table></div>

<div class="joplin-table-wrapper"><table><tbody><tr><td colspan="2"><p><strong>HU-08 | Dar de baja un producto</strong></p></td></tr><tr><td colspan="2"><p><strong>Como </strong>operador del sistema</p><p><strong>quiero </strong>poder dar de baja un producto que ya no esta disponible</p><p><strong>para </strong>retirarlo del catalogo activo sin eliminar su historial</p></td></tr><tr><td colspan="2"><p><strong>Criterios de aceptacion</strong></p></td></tr><tr><td colspan="2"><ol><li>La baja es logica: eliminado = true, el registro permanece en la BD.</li><li>Si el ID no existe o ya esta dado de baja, se informa el error.</li><li>El producto dado de baja no aparece en el listado de productos activos.</li><li>Se muestra confirmacion con el nombre del producto afectado.</li></ol></td></tr><tr><td><p><strong>Prioridad: </strong>Media</p></td><td><p><strong>Story Points: </strong>8</p></td></tr></tbody></table></div>

### Consulta JPQL

<div class="joplin-table-wrapper"><table><tbody><tr><td colspan="2"><p><strong>HU-09 | Listar productos de una categoria</strong></p></td></tr><tr><td colspan="2"><p><strong>Como </strong>operador del sistema</p><p><strong>quiero </strong>poder ver todos los productos activos que pertenecen a una categoria especifica</p><p><strong>para </strong>consultar el catalogo filtrado sin tener que revisar todos los productos</p></td></tr><tr><td colspan="2"><p><strong>Criterios de aceptacion</strong></p></td></tr><tr><td colspan="2"><ol><li>El sistema lista las categorias activas para que el operador seleccione una.</li><li>La consulta esta implementada en ProductoRepository con JPQL y parametro nombrado</li></ol><p>:categoriaId.</p><ol><li>Se usa TypedQuery&lt;Producto&gt;; no hay casteos manuales en el codigo.</li><li>Solo se incluyen productos con eliminado = false.</li><li>El resultado muestra: ID, nombre, precio y stock de cada producto.</li><li>Si la categoria no tiene productos activos, se informa explicitamente.</li></ol></td></tr><tr><td><p><strong>Prioridad: </strong>Alta</p></td><td><p><strong>Story Points: </strong>15</p></td></tr></tbody></table></div>