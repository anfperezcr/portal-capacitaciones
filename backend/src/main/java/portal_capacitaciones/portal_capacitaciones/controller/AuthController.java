package portal_capacitaciones.portal_capacitaciones.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portal_capacitaciones.portal_capacitaciones.dto.LoginRequestDTO;
import portal_capacitaciones.portal_capacitaciones.dto.LoginResponseDTO;
import portal_capacitaciones.portal_capacitaciones.service.AuthService;


@Tag(
        name = "Autenticación",
        description = "Autenticación de usuarios y generación de sesión/token"
)
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin // para Angular
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO dto
    ) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
