package com.project;

import io.micronaut.runtime.Micronaut;

@SuppressWarnings("PMD.UseUtilityClass")
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
