# ACTIVE-IA

Devolución de Trabajo Práctico

**Materia:**

PROG3-M2026 - Programación 3

**Comisión:**

COMI-13 (2026)

**Trabajo:**

Trabajo Práctico - JPA

**Alumno:**

Santiago Octavio Varela

**Calificación Final: 95/100**

# EVALUACIÓN POR CRITERIOS

|     |     |     |     |
| --- | --- | --- | --- |
| **C1: Desarrollo de Clases y Relaciones (UML) 15/15** |     |     |     |
|     |     |     |
|     |     |     |     |
|     |     |     |     |
| Excelente implementación de las entidades utilizando Lombok y respetando las relaciones y colecciones Set. |     |     |

|     |     |     |     |
| --- | --- | --- | --- |
| **C2: Incorporación de librería Hibernate 5/5** |     |     |     |
|     |     |     |
|     |     |     |     |
|     |     |     |     |
| La configuración y uso de JPA/Hibernate es correcta. |     |     |

|     |     |     |     |
| --- | --- | --- | --- |
| **C3: Configuración de persistence.xml 10/10** |     |     |     |
|     |     |     |
|     |     |     |     |
|     |     |     |     |
| Configuración correcta para la unidad de persistencia. |     |     |

|     |     |     |     |
| --- | --- | --- | --- |
| **C4: Mapeo JPA de Entidades Básicas 10/10** |     |     |     |
|     |     |     |
|     |     |     |     |
|     |     |     |     |
| Uso correcto de anotaciones JPA para el mapeo de entidades y enums. |     |     |

|     |     |     |     |
| --- | --- | --- | --- |
| **C5: Mapeo JPA de Relaciones 10/10** |     |     |     |
|     |     |     |
|     |     |     |     |
|     |     |     |     |
| Las relaciones OneToMany y ManyToOne están correctamente configuradas con sus respectivos mapeos. |     |     |

|     |     |     |     |
| --- | --- | --- | --- |
| **C6: CRUD: Instanciar y Persistir Entidades Base 15/15** |     |     |     |
|     |     |     |
|     |     |     |     |
|     |     |     |     |
| Persistencia de entidades base realizada correctamente en el Main. |     |     |

|     |     |     |     |
| --- | --- | --- | --- |
| **C7: CRUD: Instanciar y Persistir Pedidos con Detalles 10/10** |     |     |     |
|     |     |     |
|     |     |     |     |
|     |     |     |     |
| La composición entre Pedido y DetallePedido está bien gestionada mediante CascadeType.ALL. |     |     |

|     |     |     |     |
| --- | --- | --- | --- |
| **C8: CRUD: Actualizar Productos 10/10** |     |     |     |
|     |     |     |
|     |     |     |     |
|     |     |     |     |
| Actualización de registros implementada correctamente mediante el EntityManager. |     |     |

|     |     |     |     |
| --- | --- | --- | --- |
| **C9: CRUD: Buscar Usuario por ID 5/5** |     |     |     |
|     |     |     |
|     |     |     |     |
|     |     |     |     |
| Búsqueda por ID implementada correctamente. |     |     |

|     |     |     |     |
| --- | --- | --- | --- |
| **C10: CRUD: Buscar Usuario por Mail 5/5** |     |     |     |
|     |     |     |
|     |     |     |     |
|     |     |     |

Consulta JPQL personalizada implementada correctamente.

|     |
| --- |
| **C11: CRUD: Borrar 1 Producto 0/5** |
|     |
| Aunque el código intenta borrar el producto, la lógica de negocio de la aplicación (relaciones) podría causar un error de integridad referencial si no se gestiona el borrado en cascada o la desvinculación previa. |

**FORTALEZAS**

- Excelente uso de Lombok para reducir el código repetitivo (boilerplate).
- Implementación limpia y organizada de los patrones Builder y DTO.
- Manejo correcto de las transacciones y el ciclo de vida del EntityManager.

# RECOMENDACIONES

1.  Revisar la integridad referencial al eliminar entidades que tienen relaciones activas.
2.  Considerar el uso de un archivo de configuración de base de datos externa para entornos de producción.
3.  Implementar un manejo de excepciones más robusto en las operaciones de base de datos.

# COMENTARIOS DEL EVALUADOR

El trabajo práctico demuestra un dominio sólido de JPA y Hibernate, con una estructura de clases muy bien definida y profesional. El código es legible, sigue buenas prácticas de diseño y cumple con la mayoría de los requerimientos funcionales solicitados.

Documento generado por ACTIVE-IA