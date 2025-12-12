package portal_capacitaciones.portal_capacitaciones.service;

import org.springframework.stereotype.Service;
import portal_capacitaciones.portal_capacitaciones.exceptions.DuplicateResourceException;
import portal_capacitaciones.portal_capacitaciones.exceptions.ResourceNotFoundException;
import portal_capacitaciones.portal_capacitaciones.model.Usuario;
import portal_capacitaciones.portal_capacitaciones.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;

    // Inyección de dependencias a través del constructor (recomendado)
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // --- C R E A R ---

    //Guarda un nuevo usuario en la base de datos.
    @Transactional
    public Usuario guardarUsuario(Usuario usuario) {

        // 1. Validar campos obligatorios
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        // 2. Verificar si ya existe un usuario con el mismo username
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new DuplicateResourceException("El usuario ya existe.");
        }

        // 3. Guardar el usuario
        return usuarioRepository.save(usuario);
    }


    // --- L E E R ---

    //Obtiene todos los usuarios.
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }


    //Obtiene un usuario por su ID.
    @Transactional(readOnly = true)
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }


    //Obtiene un usuario por su nombre de usuario (útil para login).
    @Transactional(readOnly = true)
    public Usuario obtenerUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con nombre de usuario: " + username));
    }

    // --- A C T U A L I Z A R ---

    //Actualiza un usuario existente.
    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario detallesUsuario) {

        Usuario usuarioExistente = obtenerUsuarioPorId(id);

        // Validar username duplicado
        if (!usuarioExistente.getUsername().equals(detallesUsuario.getUsername()) &&
                usuarioRepository.existsByUsername(detallesUsuario.getUsername())) {
            throw new DuplicateResourceException("El username ya se encuentra usado.");
        }

        // Actualizar campos permitidos
        if (detallesUsuario.getUsername() != null) {
            usuarioExistente.setUsername(detallesUsuario.getUsername());
        }

        if (detallesUsuario.getNombre() != null) {
            usuarioExistente.setNombre(detallesUsuario.getNombre());
        }

        if (detallesUsuario.getRol() != null) {
            usuarioExistente.setRol(detallesUsuario.getRol());
        }

        // Manejo opcional de contraseña
    /*
    if (detallesUsuario.getPassword() != null) {
        usuarioExistente.setPassword(
            passwordEncoder.encode(detallesUsuario.getPassword())
        );
    }
    */

        return usuarioRepository.save(usuarioExistente);
    }

    // --- E L I M I N A R ---

    //Elimina un usuario por su ID.
    @Transactional
    public void eliminarUsuario(Long id) {
        Usuario usuario = obtenerUsuarioPorId(id); // Verifica existencia antes de eliminar
        usuarioRepository.delete(usuario);
    }


}
