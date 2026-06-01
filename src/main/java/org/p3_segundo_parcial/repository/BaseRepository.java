package org.p3_segundo_parcial.repository;

// Santiago Octavio Varela / @santiagovOK (GitHub) <santiago.varela@tupad.utn.edu.ar>

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.p3_segundo_parcial.model.Base;
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
    /**
     * Guarda o actualiza una entidad en la base de datos de forma transaccional.
     * Si la entidad no tiene ID (o el ID no existe), se inserta.
     * Si ya tiene un ID existente, se actualiza (merge).
     * @param entity La entidad a guardar o actualizar.
     * @return La entidad gestionada devuelta por el EntityManager (con su ID generado si fue una inserción).
     * @throws Exception Si ocurre algún error durante la transacción, se hace rollback y se relanza la excepción.
     */
    public T guardar(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T mergedEntity = em.merge(entity); // Guardamos o actualizamos
            em.getTransaction().commit();
            return mergedEntity;
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
    /**
     * Busca una entidad por su clave primaria (ID).
     * Abre un EntityManager temporalmente para realizar la consulta de manera eficiente.
     * @param id El identificador único de la entidad que se quiere buscar.
     * @return Un Optional que contiene la entidad si se encontró, o Optional.empty() si no existe.
     */
    public Optional<T> buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity); // ofNullable devuelve Optional.empty() si entity es null
        } finally {
            em.close();
        }
    }


    /**
     * Obtiene una lista de todas las entidades que no han sido eliminadas lógicamente.
     * Construye dinámicamente una consulta JPQL basada en el nombre de la clase de la entidad.
     * Filtra los resultados usando la condición 'eliminado = false'.
     * @return Una lista (List) con todas las entidades activas encontradas en la base de datos.
     */
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

    /**
     * Realiza una baja lógica de la entidad identificada por el ID proporcionado.
     * En lugar de borrar el registro físicamente de la base de datos (DELETE),
     * actualiza el campo 'eliminado' a true (UPDATE) usando una transacción.
     * @param id El identificador de la entidad a eliminar de forma lógica.
     * @return true si la entidad fue encontrada y marcada como eliminada exitosamente;
     *         false si no se encontró la entidad o si ya estaba eliminada previamente.
     * @throws Exception Si ocurre un problema durante el proceso de transacción,
     *                   realiza un rollback y propaga la excepción.
     */
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