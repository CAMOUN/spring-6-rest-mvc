package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }


    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }



    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Get Beer by Id - in Service. Id = " + id);

        return beerMap.get(id);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {

        Beer savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(savedBeer.getId(), savedBeer);

        return savedBeer;
    }

    @Override
    public void updateById(UUID id, Beer beer) {
        Beer exists = beerMap.get(id);
        exists.setBeerName(beer.getBeerName());
        exists.setBeerStyle(beer.getBeerStyle());
        exists.setUpc(beer.getUpc());
        exists.setPrice(beer.getPrice());
        exists.setQuantityOnHand(beer.getQuantityOnHand());
        exists.setUpdatedDate(LocalDateTime.now());
        exists.setVersion(exists.getVersion() + 1);

        beerMap.put(exists.getId(), exists);

    }

    @Override
    public void deleteById(UUID id) {
        beerMap.remove(id);
    }

    @Override
    public void patchById(UUID id, Beer beer) {
        Beer exists = beerMap.get(id);

        if(StringUtils.hasText(beer.getBeerName())) {
            exists.setBeerName(beer.getBeerName());
        }

        if(beer.getBeerStyle() != null) {
            exists.setBeerStyle(beer.getBeerStyle());
        }

        if(StringUtils.hasText(beer.getUpc())) {
            exists.setUpc(beer.getUpc());
        }

        if(beer.getPrice() != null) {
            exists.setPrice(beer.getPrice());
        }

        if(beer.getQuantityOnHand() != null) {
            exists.setQuantityOnHand(beer.getQuantityOnHand());
        }

        exists.setUpdatedDate(LocalDateTime.now());
        exists.setVersion(exists.getVersion() + 1);

        beerMap.put(exists.getId(), exists);
    }
}
