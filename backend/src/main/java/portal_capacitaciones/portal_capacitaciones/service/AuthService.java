package portal_capacitaciones.portal_capacitaciones.service;

import org.springframework.stereotype.Service;
import portal_capacitaciones.portal_capacitaciones.dto.LoginRequestDTO;
import portal_capacitaciones.portal_capacitaciones.dto.LoginResponseDTO;
import portal_capacitaciones.portal_capacitaciones.exceptions.ResourceNotFoundException;
import portal_capacitaciones.portal_capacitaciones.model.Usuario;
import portal_capacitaciones.portal_capacitaciones.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // ⚠️ Validación simple (prueba técnica)
        if (!usuario.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return new LoginResponseDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getRol().toString()
        );
    }
}
