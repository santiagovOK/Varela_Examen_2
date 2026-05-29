package org.p3_segundo_parcial.repository;

import org.p3_segundo_parcial.model.Categoria;

// Santiago Octavio Varela / @santiagovOK (GitHub) <santiago.varela@tupad.utn.edu.ar>

// 3.2- Crea la clase CategoriaRepository que extienda de BaseRepository<Categoria>. Esta clase no necesita métodos adicionales, ya que hereda todos los métodos CRUD y de listado de activos de BaseRepository. El constructor de CategoriaRepository debe llamar al constructor de la clase base pasando la clase Categoria.class para configurar el tipo de entidad que manejará este repositorio.

public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository() {
        super(Categoria.class);
    }
}