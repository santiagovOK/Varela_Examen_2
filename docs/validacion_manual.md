# Validación Manual del Proyecto

Este documento describe los pasos que se deben seguir para corroborar manualmente que todas las funcionalidades descritas en la consigna (`consigna.md`) están correctamente resueltas y cumplen con cada Historia de Usuario (HU).

## Requisitos Previos
1. El proyecto debe ser construido con Gradle, ejecutado desde un IDE compatible.
2. Contar con una base de datos local H2 en la carpeta `/data` según la persistencia configurada por defecto.

---

# Validación básica (para video)

1. Probar la generación de la Categoria.
2. Probar la creación de productos y asociarlos a categoría.
3. Mostrar los productos por id de categoría
4. Eliminar un producto de la categoría.
5. Mostrar los productos por id de categoría y comprobar que ya no se muestra. )

---

# Validación detallada

## 1. Validación del ABM de Categorías (HU-03, HU-04, HU-05)
Al ejecutar el `Main.java`, ingrese al menú de Categorías.
 
### 1.1 Alta de Categoría (HU-03)
- Seleccione la opción para dar de Alta una categoría.
- **Caso de prueba exitoso**: Ingrese un nombre válido (ej. "Electrónica") y una descripción.
- **Resultado esperado**: La categoría se guarda, se muestra un mensaje de éxito con el ID generado, y la consola regresa al submenú.
- **Caso de prueba de validación**: Intente ingresar un nombre vacío.
- **Resultado esperado**: El sistema no persiste el registro y notifica el error.

### 1.2 Listado de Categorías
- Seleccione la opción de listar categorías.
- **Resultado esperado**: Se listan las categorías cargadas previamente con `eliminado = false`.

### 1.3 Modificación de Categoría (HU-04)
- Seleccione la opción para Modificar una categoría.
- **Resultado esperado preliminar**: El sistema lista las categorías actuales. 
- **Caso de prueba**: Elija un ID de categoría válido. Ingrese un nuevo nombre o simplemente presione `Enter` para dejar algunos en blanco y mantener el anterior.
- **Resultado esperado**: Si deja un campo en blanco, este mantiene su valor original. Se confirma la actualización. Si se ingresa un ID inválido, se informa el error.

### 1.4 Baja de Categoría (HU-05)
- Seleccione la opción de Baja de categoría.
- **Caso de prueba**: Ingrese el ID de la categoría a eliminar.
- **Resultado esperado**: La bajá se informa como exitosa de forma lógica (cambio a `eliminado = true`). Si vuelve a presionar "listar categorías", esta ya no deberá aparecer en el listado activo. Al consultar la base de datos de H2, el registro aún existe, sólo ha cambiado de estado.

---

## 2. Validación del ABM de Productos (HU-06, HU-07, HU-08)
En el menú principal, ingrese al menú de Productos. 

### 2.1 Alta de Producto (HU-06)
- Seleccione la opción para dar de Alta un Producto.
- **Resultado esperado preliminar**: El sistema lista primero las categorías activas.
- **Caso de prueba**: Seleccione el ID de una categoría válida. Ingrese un nombre, descripción, precio mayor a cero y stock válido (ej. Precio 1500, stock 10).
- **Resultado esperado**: El producto se crea con éxito, asociándose correctamente a la categoría informando el ID.
- **Caso de prueba de error**: Intente ingresar un precio <= 0 o stock < 0.
- **Resultado esperado**: Se muestran mensajes informando los errores de validación.

### 2.2 Listado de Productos
- Seleccione la opción de listar productos.
- **Resultado esperado**: Se lista el producto recién creado junto al nombre de su categoría asociada. No deben aparecer productos dados de baja lógicamente.

### 2.3 Modificar Producto (HU-07)
- Seleccione Modificar un Producto.
- **Caso de prueba**: Ingrese el ID del producto que desea actualizar. 
- **Resultado esperado**: Se visualizan los valores anteriores de cada campo. Intente presionar `Enter` en algún campo para evitar actualizarlo y actualice otro. Si los datos ingresados no son un precio o stock válido (<=0 en precio, por ejemplo), no se completará la operación. De lo contrario, se guarda la edición exitosamente.

### 2.4 Baja de Producto (HU-08)
- Seleccione Baja de Producto.
- **Caso de prueba**: Ingrese el ID del producto existente.
- **Resultado esperado**: El producto se "elimina". Al volver a listar los productos este ya no aparecerá en pantalla.

---

## 3. Consulta JPQL y Repositorios (HU-01, HU-02, HU-09)
En el menú principal, diríjase al Submenú de Reportes/Consultas de Productos por Categoría.

### 3.1 Productos por Categoría (HU-09)
- **Caso de prueba**: El sistema mostrará todas las categorías activas. Seleccione el ID de una categoría en la que previamente se cargó al menos un producto que siga estando ACTIVO.
- **Resultado esperado**: Se ejecuta el método `buscarPorCategoria(Long)` en `ProductoRepository` (el cual utiliza un `TypedQuery<Producto>` con JPQL y sin casteos para filtrar por ID y donde `eliminado = false`). Se deberá imprimir la lista de los productos bajo dicha categoría de forma correcta, mostrando ID, nombre, precio y stock.
- **Caso de prueba sin productos**: Si escoge una categoría que no tiene asociados productos activos, el sistema debe imprimir explícitamente "No hay productos en esta categoría".

### 3.2 Repositorios y Operaciones Genéricas (HU-01, HU-02)
- Revisa manualmente los archivos en `src/main/java/org/.../repository`:
  - `BaseRepository.java`: Para asegurar que se cumple con Genericidad con `Class<T>`, usando `merge()`, manejando el bloque `finally` para el cierre de `EntityManager` y devolviendo un `Optional<T>`.
  - `CategoriaRepository` y `ProductoRepository`: Que extiendan de `BaseRepository` y llamen directamente en el constructor a `super(Clase.class)` sin necesidad de reescribir lógica del CRUD.

