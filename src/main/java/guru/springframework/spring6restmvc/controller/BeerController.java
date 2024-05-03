package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;




    @GetMapping(BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID id) {
        log.debug("Get Beer by Id - in Controller");
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(BEER_PATH)
    public Page<BeerDTO> listBeers(@RequestParam(value = "beerName", required = false) String beerName,
                                   @RequestParam(value = "beerStyle", required = false) BeerStyle beerStyle,
                                   @RequestParam(value = "showInventory", required = false) Boolean showInventory,
                                   @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return beerService.listBeers(beerName, beerStyle, showInventory, pageNumber, pageSize);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> handlePost(@Validated @RequestBody BeerDTO beer) {
         BeerDTO savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> updateById(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer) {
        if(beerService.updateById(id, beer).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> deleteById(@PathVariable("beerId") UUID id) {
        if( !beerService.deleteById(id)) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<BeerDTO> updatePatchById(@PathVariable("beerId") UUID id, @RequestBody BeerDTO beer) {
        if(beerService.patchById(id, beer).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
