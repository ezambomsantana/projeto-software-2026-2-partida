package br.edu.insper.partida.partida;

import br.edu.insper.partida.time.TimeResponseDTO;
import br.edu.insper.partida.time.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;
import java.util.Optional;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private TimeService timeService;

    public List<Partida> listarTodas() {
        return partidaRepository.findAll();
    }

    public Partida criar(Partida partida) {
        try {
            TimeResponseDTO mandante = timeService.getTime(partida.getIdMandante());
            TimeResponseDTO visitante = timeService.getTime(partida.getIdVisitante());

            partida.setEstadio(mandante.getEstadio());
            partida.setNomeMandate(mandante.getNome());
            partida.setNomeVisitate(visitante.getNome());

            return partidaRepository.save(partida);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erro ao buscar dados do time no serviço externo", e);
        }
    }

    public Partida buscarPorId(Integer id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
    }
}
