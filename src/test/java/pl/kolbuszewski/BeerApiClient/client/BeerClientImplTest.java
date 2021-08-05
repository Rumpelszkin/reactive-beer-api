package pl.kolbuszewski.BeerApiClient.client;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.kolbuszewski.BeerApiClient.config.WebClientConfig;
import pl.kolbuszewski.BeerApiClient.model.BeerDto;
import pl.kolbuszewski.BeerApiClient.model.BeerPagedList;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

public class BeerClientImplTest {

    BeerClientImpl beerClient;

    @BeforeEach
    void setUp() {
        beerClient = new BeerClientImpl(new WebClientConfig().webClient());
    }

    @Test
    void listBeers() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null, null, null, null);

        BeerPagedList pagedList = beerPagedListMono.block();

        assertThat(pagedList)
                .isNotNull();
        assertThat(pagedList.getContent()
                            .size())
                .isGreaterThan(0);

    }

    @Test
    void listBeersPageSize10() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1, 10, null, null, null);

        BeerPagedList pagedList = beerPagedListMono.block();

        assertThat(pagedList)
                .isNotNull();
        assertThat(pagedList.getContent()
                            .size())
                .isEqualTo(10);

    }

    @Test
    void listBeersNoRecords() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(10, 20, null, null, null);

        BeerPagedList pagedList = beerPagedListMono.block();

        assertThat(pagedList)
                .isNotNull();
        assertThat(pagedList.getContent()
                            .size())
                .isEqualTo(0);

    }

    @Test
    void getBeerById() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null, null, null, null);
        BeerPagedList pagedList = beerPagedListMono.block();
        UUID beerId = pagedList.getContent()
                               .get(0)
                               .getId();


        Mono<BeerDto> beerDtoMono = beerClient.getBeerById(beerId, null);
        BeerDto beerDto = beerDtoMono.block();

        assertThat(beerDto.getId())
                .isEqualTo(beerId);
        System.out.println(beerDto.toString());
    }

    @Test
    void createBeer() {
        BeerDto beerDto = BeerDto.builder()
                                 .beerName("pIPA")
                                 .beerStyle("IPA")
                                 .price(new BigDecimal("0.69"))
                                 .upc("923810420")
                                 .build();

        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.createBeer(beerDto);
        ResponseEntity responseEntity = responseEntityMono.block();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void updateBeer() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null, null, null, null);
        BeerPagedList pagedList = beerPagedListMono.block();

        BeerDto beerDto = pagedList.getContent()
                                   .get(0);

        BeerDto updatedBeer = BeerDto.builder()
                                     .beerName("Nice one!")
                                     .beerStyle(beerDto.getBeerStyle())
                                     .upc(beerDto.getUpc())
                                     .price(beerDto.getPrice())
                                     .build();

        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.updateBeer(beerDto.getId(), updatedBeer);
        ResponseEntity<Void> responseEntity = responseEntityMono.block();
        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    void deleteBeerById() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null, null, null, null);
        BeerPagedList pagedList = beerPagedListMono.block();

        UUID id = pagedList.getContent()
                           .get(0)
                           .getId();

        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeer(id);

        ResponseEntity<Void> responseEntity = responseEntityMono.block();
        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getBeerByUPC() {
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null, null, null, null, null);
        BeerPagedList pagedList = beerPagedListMono.block();
        String upc = pagedList.getContent()
                              .get(0)
                              .getUpc();

        Mono<BeerDto> beerDtoMono = beerClient.getBeerByUPC(upc);
        BeerDto beerDto = beerDtoMono.block();

        assertThat(beerDto.getUpc())
                .isEqualTo(upc);
        System.out.println(beerDto.toString());
    }

    @Test
        // way to handle WebClientResponseException passing Mono
    void testDeleteBeerHandleException() {
        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeer(UUID.randomUUID());

        ResponseEntity<Void> responseEntity = responseEntityMono.onErrorResume(throwable -> {
            if (throwable instanceof WebClientResponseException) {
                WebClientResponseException exception = (WebClientResponseException) throwable;
                return Mono.just(ResponseEntity.status(exception.getStatusCode())
                                               .build());
            } else {
                throw new RuntimeException(throwable);
            }
        })
                                                                .block();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test // way to handle WebClientResponseException in Test
    void testDeleteBeerByIdException() {
        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.deleteBeer(UUID.randomUUID());

        assertThrows(WebClientResponseException.class, () -> {
            ResponseEntity<Void> responseEntity = responseEntityMono.block();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        });
    }
}
