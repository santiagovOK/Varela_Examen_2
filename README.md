# Segundo Examen Parcial - Java Persistence API (JPA) - Programación III - UTN TUPaD

✨ Estudiante

- Nombre: Varela, Santiago Octavio
- Comisión: M25 C3-13
- Email institucional: santiago.varela@tupad.utn.edu.ar

# Links de relevancia para la evaluación

**Link al repositorio en Github: https://github.com/santiagovOK/**

**- Video explicativo: **
**- Video explicativo (link B):**

**- Presentación utilizada en el video:**

Resumen breve de resolución de cada una de las consignas (principalmente para guiarme yo). Pueden verlo aquí: [docs/resolucion_parcial.md](docs/resolucion_parcial.md)

---

## Objetivo General

Extender el proyecto Gradle del TP de la Unidad 8 creando los repositorios para las entidades Categoria y Producto, e implementando un menu de consola que permita realizar operaciones ABM sobre dichas entidades. Se incluye ademas una consulta JPQL personalizada para filtrar productos por categoria. Mi resolución del TP de la Unidad 8 fue la [siguiente](https://github.com/santiagovOK/UTN-TUPaD-P3/tree/main/unidad8_jpa)

### Resumen de funcionalidades implementadas:

Se implementaron las siguientes funcionalidades requeridas en la consigna:
- **Repositorios Genéricos y Específicos:** Implementación de `BaseRepository<T>` conteniendo las operaciones CRUD base (alta, baja lógica, modificación, listar activos y buscar por ID). `CategoriaRepository` y `ProductoRepository` heredan de este repositorio base.
- **Consulta JPQL Personalizada:** Método `buscarPorCategoria(Long categoriaId)` en `ProductoRepository` para filtrar productos activos de una categoría en específico sin necesidad de casteos manuales (`TypedQuery`).
- **ABM de Categorías:** Submenú en `Main` para gestionar las categorías (Alta, Baja lógica, Modificación, Listado completo).
- **ABM de Productos:** Submenú en `Main` para gestionar los productos (Alta, relacionándolos con una categoría existente; Baja lógica, Modificación y Listado de productos con su categoría).
- **Reporte JPQL:** Opción dentro del menú para listar los productos activos filtrados por categoría.

### Estructura del proyecto

El proyecto sigue la siguiente estructura de directorios principal:
```
src/main/java/org/p3_segundo_parcial/
├── Main.java               # Punto de entrada de la aplicación y sistema de menús.
├── model/                  # Entidades JPA (Categoria, Producto, Base, etc.)
├── repository/             # Repositorios (BaseRepository, CategoriaRepository, ProductoRepository)
└── util/                   # Utilidades como JPAUtil para inicializar EntityManagerFactory
```



## Validaciones Manuales

Pueden ver el paso a paso de las validaciones manuales que seguí para el cumplimiento de las consignas y los criterios evaluativos en [docs/validacion_manual.md](docs/validacion_manual.md).

---

# Detalles sobre la persistencia de la base de datos y el uso de H2

**Nota importante sobre la persistencia:** El proyecto está configurado para guardar los datos físicamente en un archivo local (indicado por el prefijo `file:` en la URL JDBC), no es una base de datos en memoria (`mem:`). Esto significa que la información persiste de forma permanente en el directorio `data/` incluso al cerrar la aplicación, permitiendo consultarla posteriormente.

Para visualizar la base de datos H2 generada en el directorio `data/`, se puede utilizar la consola de H2 o cualquier IDE que soporte conexiones JDBC conectándose a la misma. 

**Configuración de la conexión:**
- **URL JDBC:** `jdbc:h2:./data/jpa_db` (usar la ruta absoluta a la carpeta `data/` del proyecto si es necesario)
- **Usuario:** `sa`
- **Contraseña:** *(vacío)*

**Aviso de modo de conexión:** Al utilizar la base de datos de H2 montada en archivo de manera convencional, esta no admite múltiples conexiones en diferentes programas al mismo tiempo por bloqueos en el sistema de archivos (`.lock`). Si el código en Java (`Main`) está ejecutándose, debe finalizar su ejecución antes de que sea visualizado mediante un gestor de base de datos. Una alternativa si se prefiere trabajar al mismo tiempo es configurar la variable `AUTO_SERVER=TRUE` de H2 en el `persistence.xml`.

## Licencia

Este proyecto se distribuye bajo la [Licencia MIT](LICENCE.TXT).