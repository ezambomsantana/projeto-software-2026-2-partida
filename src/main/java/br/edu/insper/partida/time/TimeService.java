package br.edu.insper.partida.time;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TimeService {


    public TimeResponseDTO getTime(String idTime) {
        RestClient restClient = RestClient.builder().build();

        return restClient.get()
                .uri("http://localhost:5001/times/" + idTime)
                .retrieve()
                .body(TimeResponseDTO.class);

    }
}
