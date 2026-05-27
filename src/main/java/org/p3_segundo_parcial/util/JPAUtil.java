package org.p3_segundo_parcial.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "p3_segundo_parcial";
    private static EntityManagerFactory factory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory;
    }

    public static void cerrar() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}


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