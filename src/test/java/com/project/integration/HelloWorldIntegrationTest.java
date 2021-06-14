package com.project.integration;

import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class HelloWorldIntegrationTest {

    @Inject
    EmbeddedApplication application;

    @Inject
    @Client("/home")
    RxHttpClient rxHttpClient; //NOPMD


    @Test
    void testHelloWorldBlockingWithHttpClient() {
        String helloString = rxHttpClient.toBlocking().retrieve("/hello");
        assertEquals("Hello world", helloString);
    }

}
