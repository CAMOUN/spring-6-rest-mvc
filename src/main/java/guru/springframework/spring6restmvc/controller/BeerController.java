package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public Beer getBeerById(@PathVariable("beerId") UUID id) {
        log.debug("Get Beer by Id - in Controller");
        return beerService.getBeerById(id);
    }

    @GetMapping
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @PostMapping
    public ResponseEntity<Beer> handlePost(@RequestBody Beer beer) {
         Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<Beer> updateById(@PathVariable("beerId") UUID id, @RequestBody Beer beer) {
        beerService.updateById(id, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    public ResponseEntity<Beer> deleteById(@PathVariable("beerId") UUID id) {
        beerService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{beerId}")
    public ResponseEntity<Beer> updatePatchById(@PathVariable("beerId") UUID id, @RequestBody Beer beer) {
        beerService.patchById(id, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
