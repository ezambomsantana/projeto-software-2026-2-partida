package br.edu.insper.partida.partida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @GetMapping
    public List<Partida> listarTodas() {
        return partidaService.listarTodas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Partida criar(@RequestBody Partida partida,
                         @AuthenticationPrincipal Jwt jwt) {
        System.out.println(jwt.getClaimAsStringList("https://social-insper.com/roles"));
        return partidaService.criar(partida);
    }

    @GetMapping("/{id}")
    public Partida buscarPorId(@PathVariable Integer id) {
        return partidaService.buscarPorId(id);
    }
}
