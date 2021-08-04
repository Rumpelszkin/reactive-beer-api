package pl.kolbuszewski.BeerApiClient.client;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kolbuszewski.BeerApiClient.config.WebClientConfig;
import pl.kolbuszewski.BeerApiClient.model.BeerDto;
import pl.kolbuszewski.BeerApiClient.model.BeerPagedList;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class BeerClientImplTest {

    BeerClientImpl beerClient;

    @BeforeEach
    void setUp(){
        beerClient = new BeerClientImpl(new WebClientConfig().webClient());
    }
    @Test
    void listBeers(){
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(null,null,null,null,null);

        BeerPagedList pagedList = beerPagedListMono.block();

        Assertions.assertThat(pagedList).isNotNull();
        Assertions.assertThat(pagedList.getContent().size()).isGreaterThan(0);

    }
    @Test
    void listBeersPageSize10(){
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(1,10,null,null,null);

        BeerPagedList pagedList = beerPagedListMono.block();

        Assertions.assertThat(pagedList).isNotNull();
        Assertions.assertThat(pagedList.getContent().size()).isEqualTo(10);

    }

    @Test
    void listBeersNoRecords(){
        Mono<BeerPagedList> beerPagedListMono = beerClient.listBeers(10,20,null,null,null);

        BeerPagedList pagedList = beerPagedListMono.block();

        Assertions.assertThat(pagedList).isNotNull();
        Assertions.assertThat(pagedList.getContent().size()).isEqualTo(0);

    }

    @Test
    void getBeerById(){
        Mono<BeerDto> beerDtoMono = beerClient.getBeerById(UUID.fromString("d2053939-34dc-47a1-a55d-9bda489041ac"),null);
        BeerDto beerDto = beerDtoMono.block();

        Assertions.assertThat(beerDto).isNotNull();
        System.out.println(beerDto.toString());
    }

    @Test
    void createBeer(){

    }
    @Test
    void updateBeer(){

    }
    @Test
    void deleteBeerById(){

    }
    @Test
    void getBeerByUPC(){

    }

}
