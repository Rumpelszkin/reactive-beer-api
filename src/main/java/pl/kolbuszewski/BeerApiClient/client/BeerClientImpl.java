package pl.kolbuszewski.BeerApiClient.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pl.kolbuszewski.BeerApiClient.config.WebclientProperties;
import pl.kolbuszewski.BeerApiClient.model.BeerDto;
import pl.kolbuszewski.BeerApiClient.model.BeerPagedList;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    private final WebClient webClient;

    @Override
    public Mono<BeerDto> getBeerById(UUID id, Boolean showInventoryOnHand) {
        return webClient.get()
                        .uri(uriBuilder -> uriBuilder.path(WebclientProperties.BEER_ID_PATH)
                                                     .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
                                                     .build(id))
                        .retrieve()
                        .bodyToMono(BeerDto.class);
    }

    @Override
    public Mono<BeerPagedList> listBeers(Integer pageNumber, Integer pageSize, String beerName, String beerStyle, Boolean showInventoryOnHand) {
        return webClient.get()
                        .uri(uriBuilder -> uriBuilder.path(WebclientProperties.BEER_V1_PATH)
                                                     .queryParamIfPresent("pageNumber", Optional.ofNullable(pageNumber))
                                                     .queryParamIfPresent("pageSize", Optional.ofNullable(pageSize))
                                                     .queryParamIfPresent("beerName", Optional.ofNullable(beerName))
                                                     .queryParamIfPresent("beerStyle", Optional.ofNullable(beerStyle))
                                                     .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
                                                     .build())
                        .retrieve()
                        .bodyToMono(BeerPagedList.class);
    }


    @Override
    public Mono<ResponseEntity<Void>> createBeer(BeerDto beerDto) {
        return webClient.post()
                        .uri(uriBuilder -> uriBuilder.path(WebclientProperties.BEER_V1_PATH)
                                                     .build())
                        .body(BodyInserters.fromValue(beerDto))
                        .retrieve()
                        .toBodilessEntity();
    }

    @Override
    public Mono<ResponseEntity<Void>> updateBeer(UUID id, BeerDto beerDto) {
        return webClient.put()
                        .uri(uriBuilder -> uriBuilder.path(WebclientProperties.BEER_ID_PATH)
                                                     .build(id))
                        .body(BodyInserters.fromValue(beerDto))
                        .retrieve()
                        .toBodilessEntity();
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteBeer(UUID id) {
        return webClient.delete()
                        .uri(uriBuilder -> uriBuilder.path(WebclientProperties.BEER_ID_PATH)
                                                     .build(id))
                        .retrieve()
                        .toBodilessEntity();
    }

    @Override
    public Mono<BeerDto> getBeerByUPC(String upc) {
        return webClient.get()
                        .uri(uriBuilder -> uriBuilder.path(WebclientProperties.BEER_UPC_PATH)
                                                     .build(upc))
                        .retrieve()
                        .bodyToMono(BeerDto.class);
    }
}
