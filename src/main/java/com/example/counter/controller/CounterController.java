package com.example.counter.controller;

import com.example.counter.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/counter")
public class CounterController {

    private ConcurrentHashMap<String, Integer> counters = new ConcurrentHashMap<>();

    @PutMapping(value = "/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public void create(@PathVariable String name) {
        counters.putIfAbsent(name, 0);
    }

    @PostMapping(value = "increment/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public void increment(@PathVariable String name) {
        if (counters.computeIfPresent(name, (key, value) -> value+1) == null){
            throw new NotFoundException();
        }
    }

    @PostMapping(value = "decrement/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public void decrement(@PathVariable String name) {
        if (counters.computeIfPresent(name, (key, value) -> value-1) == null){
            throw new NotFoundException();
        }
    }

    @GetMapping(value = "/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public int get(@PathVariable String name) {
        if (!counters.containsKey(name)) {
            throw new NotFoundException();
        }
        return counters.get(name);
    }

    @DeleteMapping(value = "/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable String name) {
        if (counters.remove(name) == null) {
            throw new NotFoundException();
        }
    }

    @GetMapping(value = "/sum")
    @ResponseStatus(value = HttpStatus.OK)
    public int getSum() {
        return counters.values().stream().mapToInt(i -> i).sum();
    }

    @GetMapping(value = "/names", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Set<String> getNames() {
        return counters.keySet();
    }
}
