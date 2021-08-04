package pl.kolbuszewski.BeerApiClient.client;

import org.springframework.http.ResponseEntity;
import pl.kolbuszewski.BeerApiClient.model.BeerDto;
import pl.kolbuszewski.BeerApiClient.model.BeerPagedList;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BeerClient {

    Mono<BeerDto> getBeerById(UUID id, Boolean showInventoryOnHand);

    Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pageSize, String beerName, String beerStyle, Boolean showInventoryOnHand);

    Mono<ResponseEntity> addBeer(BeerDto beerDto);

    Mono<ResponseEntity> updateBeer(BeerDto beerDto);

    Mono<ResponseEntity> deleteBeer(UUID id);

    Mono<BeerDto> getBeerByUPC(String upc);
}
