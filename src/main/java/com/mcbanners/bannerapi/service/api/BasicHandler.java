package com.mcbanners.bannerapi.service.api;

/**
 * Represents a handler which can take in a String or integer representation of some identifier and
 * produce some T.
 *
 * @param <T> the type that is produced by providing an identifier
 */
public abstract class BasicHandler<T> {
    /**
     * A handler that takes in a String identifier and produces a T.
     * By default this method forwards to {@link #handle(int)}.
     *
     * @param id the String identifier
     * @return the T
     */
    public T handle(String id) {
        return handle(Integer.parseInt(id));
    }

    /**
     * A handler that takes in an integer identifier and produces a T.
     * By default there is no implementation.
     *
     * @param id the integer identifier
     * @return the T
     */
    public T handle(int id) {
        throw new RuntimeException("No implementation defined for #handle(int)");
    }
}
