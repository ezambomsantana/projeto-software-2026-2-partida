package br.edu.insper.partida.partida;

import br.edu.insper.partida.time.TimeResponseDTO;
import br.edu.insper.partida.time.TimeService;
import br.edu.insper.partida.time.TimeCacheService;
import br.edu.insper.partida.time.RedisTimeCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private TimeService timeService;

    @Autowired
    private TimeCacheService timeCacheService;

    @Autowired
    private RedisTimeCacheService redisTimeCacheService;

    public List<Partida> listarTodas() {
        //return partidaRepository
        //        .findAll();


         return partidaRepository
                .findAll()
                .stream()
                .map(p -> defineTime(p))
                .toList();
    }

    private Partida defineTime(Partida p) {
        TimeResponseDTO mandante = obterTime(p.getIdMandante());
        TimeResponseDTO visitante = obterTime(p.getIdVisitante());

        p.setNomeMandate(mandante.getNome());
        p.setNomeVisitate(visitante.getNome());
        return p;

    }

    public Partida criar(Partida partida) {
        try {

            TimeResponseDTO mandante = obterTime(partida.getIdMandante());
            TimeResponseDTO visitante = obterTime(partida.getIdVisitante());

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


    private TimeResponseDTO obterTime(String idTime) {
        // Usando cache em memória (HashMap)
        if (redisTimeCacheService.contemNoCache(idTime)) {
            return timeCacheService.obterDoCache(idTime);
        }

        TimeResponseDTO timeData = timeService.getTime(idTime);
        redisTimeCacheService.armazenarNoCache(idTime, timeData);
        return timeData;
    }
}
