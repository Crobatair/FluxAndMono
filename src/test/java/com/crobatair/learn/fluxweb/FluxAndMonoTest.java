package com.crobatair.learn.fluxweb;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void flux_expect_ordered_list_string() {
        Flux<String> flux = Flux.just("One", "Two", "Three", "Four").log();
        StepVerifier.create(flux)
                .expectNext("One")
                .expectNext("Two")
                .expectNext("Three")
                .expectNext("Four")
                .verifyComplete();
    }


    @Test
    public void flux_expect_runtime_exception() {
        Flux<String> flux = Flux.just("One", "Two", "Three", "Four").log()
            .concatWith(Flux.error(new RuntimeException("Something went wrong")));
        StepVerifier.create(flux)
                .expectNext("One")
                .expectNext("Two")
                .expectNext("Three")
                .expectNext("Four")
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void flux_return_four_elements() {
        Flux<String> flux = Flux.just("One", "Two", "Three", "Four").log()
                .concatWith(Flux.error(new RuntimeException("Something went wrong")));
        StepVerifier.create(flux)
                .expectNextCount(4);
    }


    @Test
    public void mono_test_retrieve_one_value() {
        Mono<String> mono = Mono.just("One").log();
        StepVerifier.create(mono)
                .expectNext("One")
                .verifyComplete();
    }

    @Test
    public void mono_test_error() {
        Mono<String> mono = Mono.error(new RuntimeException("Another one exception more"));
        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();
    }
}
