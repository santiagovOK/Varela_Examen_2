package org.p3_segundo_parcial.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.p3_segundo_parcial.model.Producto;
import org.p3_segundo_parcial.util.JPAUtil;

import java.util.List;

// Santiago Octavio Varela / @santiagovOK (GitHub) <santiago.varela@tupad.utn.edu.ar>

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    // buscarPorCategoria es el método propio del repositorio ProductoRepository, que no forma parte de la clase base. Este método se encarga de buscar productos activos (no eliminados - baja lógica) que pertenecen a una categoría específica, identificada por su ID.

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        // Se pide un EntityManager para esta operación específica
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            /*
             * EXPLICACIÓN DE LA CONSULTA JPQL:
             * Se consultan las entidades "Producto" asignándoles el alias "p".
             * Se filtra mediante la cláusula WHERE para que coincidan dos condiciones:
             * 1. Que el id de la relación "categoria" sea igual al parámetro ingresado (:categoriaId).
             * 2. Que el atributo "eliminado" sea falso (baja lógica = false), para traer solo productos activos.
             */
            String jpql = "SELECT p " +
                    "FROM Producto p " +
                    "WHERE p.categoria.id = :categoriaId AND p.eliminado = false";

            // Usamos TypedQuery para asegurar el tipado de retorno sin casteos manuales. Justamente, TypedQuery tipea la consulta para que retorne objetos de tipo Producto, lo que mejora la seguridad de tipos y evita errores en tiempo de ejecución.

            TypedQuery<Producto> query = em.createQuery(jpql, Producto.class);
            // Vinculación del parámetro nombrado en la consulta (en el jpql anterior)
            query.setParameter("categoriaId", categoriaId);

            return query.getResultList();

        } finally {
            em.close(); // Se cierra el EntityManager, en el bloque finally
        }
    }
}