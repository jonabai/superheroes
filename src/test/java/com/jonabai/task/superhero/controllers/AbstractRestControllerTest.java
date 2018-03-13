package com.jonabai.task.superhero.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract Rest Controller class
 * It includes common methods
 */
public abstract class AbstractRestControllerTest {

    /**
     * Converts an object into a Json string
     * @param obj object to be parsed
     * @return the parsed Json string
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
