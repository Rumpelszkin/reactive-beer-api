package pl.kolbuszewski.BeerApiClient.client;

import org.springframework.http.ResponseEntity;
import pl.kolbuszewski.BeerApiClient.model.BeerDto;
import pl.kolbuszewski.BeerApiClient.model.BeerPagedList;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class BeerClientImpl implements BeerClient{
    @Override
    public Mono<BeerDto> getBeerById(UUID id, Boolean showInventoryOnHand) {
        return null;
    }

    @Override
    public Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pageSize, String beerName, String beerStyle, Boolean showInventoryOnHand) {
        return null;
    }

    @Override
    public Mono<ResponseEntity> addBeer(BeerDto beerDto) {
        return null;
    }

    @Override
    public Mono<ResponseEntity> updateBeer(BeerDto beerDto) {
        return null;
    }

    @Override
    public Mono<ResponseEntity> deleteBeer(UUID id) {
        return null;
    }

    @Override
    public Mono<BeerDto> getBeerByUPC(String upc) {
        return null;
    }
}
