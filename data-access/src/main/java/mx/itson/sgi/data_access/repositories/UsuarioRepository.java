package mx.itson.sgi.data_access.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.itson.sgi.data_access.entities.Usuario;
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query("select new Usuario(u.nombre, u.rol, u.correo) from Usuario u where u.nombre = ?1")
    Optional<Usuario> findUserByNombre(String nombre);
    Optional<Usuario> findByNombre(String nombre);

}
