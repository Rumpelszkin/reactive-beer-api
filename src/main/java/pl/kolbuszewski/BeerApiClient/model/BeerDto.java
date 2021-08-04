package pl.kolbuszewski.BeerApiClient.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
public class BeerDto {

    @Null
    private UUID Id;

    @NotBlank
    private String beerName;

    @NotBlank
    private String beerStyle;

    private String upc;

    private Integer quantityOnHand;

    private BigDecimal price;

    private Integer version;

    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
}
