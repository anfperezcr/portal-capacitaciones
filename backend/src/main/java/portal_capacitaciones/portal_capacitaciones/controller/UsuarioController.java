package portal_capacitaciones.portal_capacitaciones.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal_capacitaciones.portal_capacitaciones.dto.UsuarioResponseDTO;
import portal_capacitaciones.portal_capacitaciones.model.Usuario;
import portal_capacitaciones.portal_capacitaciones.service.UsuarioService;

import java.util.List;

@Tag(
        name = "Usuarios",
        description = "Gestión de usuarios del sistema"
)
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody Usuario usuario) {

        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);

        UsuarioResponseDTO respuesta = new UsuarioResponseDTO(
                nuevoUsuario.getId(),
                nuevoUsuario.getUsername(),
                nuevoUsuario.getNombre(),
                nuevoUsuario.getRol().toString()
        );

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }



    //Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodosLosUsuarios() {

        List<Usuario> usuarios = usuarioService.obtenerTodos();

        // Convertimos cada Usuario a UsuarioResponseDTO
        List<UsuarioResponseDTO> respuesta = usuarios.stream()
                .map(u -> new UsuarioResponseDTO(
                        u.getId(),
                        u.getUsername(),
                        u.getNombre(),
                        u.getRol().toString()
                ))
                .toList();

        return ResponseEntity.ok(respuesta);
    }


    //Obtener un usuario por ID ---
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable Long id) {


        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);

        UsuarioResponseDTO respuesta = new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getRol().toString()
        );


        return ResponseEntity.ok(respuesta); // Devuelve el usuario y código 200 OK
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario detallesUsuario) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, detallesUsuario);

        UsuarioResponseDTO respuesta = new UsuarioResponseDTO(
                usuarioActualizado.getId(),
                usuarioActualizado.getUsername(),
                usuarioActualizado.getNombre(),
                usuarioActualizado.getRol().toString()
        );

        return ResponseEntity.ok(respuesta); // Devuelve el objeto actualizado y 200 OK
    }

    // Eliminar registro de un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        // Devuelve código 204 NO CONTENT, indicando que la operación fue exitosa
        return ResponseEntity.noContent().build();
    }

}
