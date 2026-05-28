package org.p3_segundo_parcial.repository;

// Santiago Octavio Varela / @santiagovOK (GitHub) <santiago.varela@tupad.utn.edu.ar>

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.p3_segundo_parcial.entities.Base;
import org.p3_segundo_parcial.util.JPAUtil;

import java.util.List;
import java.util.Optional;

// 3.1- T extiende de Base para asegurar que todas las entidades manejadas por este repositorio tengan el campo eliminado y los métodos correspondientes

public abstract class BaseRepository<T extends Base> {

    private final Class<T> entityClass;
    private final EntityManagerFactory emf;

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
        // Obtenemos el EntityManagerFactory a través de JPAUtil
        this.emf = JPAUtil.getEntityManagerFactory();
    }

    // 3.1.2 - Método para guardar o actualizar una entidad, con manejo robusto de transacciones
    public void guardar(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity); // Guardamos o actualizamos
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback ante cualquier error según consigna
            }
            throw e;
            // Bloque finally para cerrar el EntityManager
        } finally {
            em.close();
        }
    }
    // 3.1.3 - Creación de buscarPorId(Long id)
    public Optional<T> buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity); // ofNullable devuelve Optional.empty() si entity es null
        } finally {
            em.close();
        }
    }


    public List<T> listarActivos() {
        EntityManager em = emf.createEntityManager();
        try {
            // JPQL dinámico usando el nombre de la clase. Utiliza `eliminado = false` para filtrar solo los registros activos, que no tienen baja lógica (true).
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e.eliminado = false";
            return em.createQuery(jpql, entityClass).getResultList();
        } finally {
            em.close();
        }
    }

    public boolean eliminarLogico(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);

            if (entity != null && !entity.isEliminado()) {
                // Requiere que T extienda de Base para acceder a setEliminado
                entity.setEliminado(true);
                em.merge(entity);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                // Retorna false si no encuentra el registro o si ya está marcado como eliminado.
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}