package com.mcbanners.backend.net;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BasicHttpClient {
    private static final String USER_AGENT = "MCBanners";
    private final String baseURL;
    private final HttpHeaders headers;
    private final RestTemplate template;

    public BasicHttpClient(String baseURL) {
        this.baseURL = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", USER_AGENT);
        this.headers = headers;

        this.template = new RestTemplate();
    }

    /**
     * Make a GET request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> get(String endpoint, Class<T> responseType) {
        return get(endpoint, responseType, null);
    }

    /**
     * Make a GET request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @param extraHeaders any extra headers that should be applied to this request only
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> get(String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) {
        return get(baseURL, endpoint, responseType, extraHeaders);
    }

    /**
     * Make a GET request to a manually specified base URL (override class level base URL) + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @param extraHeaders any extra headers that should be applied to this request only
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> get(String base, String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) {
        return makeRequest(base + endpoint, HttpMethod.GET, responseType, extraHeaders);
    }

    /**
     * Make a POST request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> post(String endpoint, Class<T> responseType) {
        return post(endpoint, responseType, null);
    }

    /**
     * Make a POST request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @param extraHeaders any extra headers that should be applied to this request only
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> post(String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) {
        return post(baseURL, endpoint, responseType, extraHeaders);
    }

    /**
     * Make a POST request to a manually specified base URL (override class level base URL) + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @param extraHeaders any extra headers that should be applied to this request only
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> post(String base, String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) {
        return makeRequest(base + endpoint, HttpMethod.POST, responseType, extraHeaders);
    }

    /**
     * Make a PUT request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> put(String endpoint, Class<T> responseType) {
        return get(endpoint, responseType, null);
    }

    /**
     * Make a PUT request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @param extraHeaders any extra headers that should be applied to this request only
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> put(String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) {
        return put(baseURL, endpoint, responseType, extraHeaders);
    }

    /**
     * Make a PUT request to a manually specified base URL (override class level base URL) + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @param extraHeaders any extra headers that should be applied to this request only
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> put(String base, String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) {
        return makeRequest(base + endpoint, HttpMethod.PUT, responseType, extraHeaders);
    }

    /**
     * Make a PATCH request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> patch(String endpoint, Class<T> responseType) {
        return patch(endpoint, responseType, null);
    }

    /**
     * Make a PATCH request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @param extraHeaders any extra headers that should be applied to this request only
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> patch(String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) {
        return patch(baseURL, endpoint, responseType, extraHeaders);
    }

    /**
     * Make a PATCH request to a manually specified base URL (override class level base URL) + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @param extraHeaders any extra headers that should be applied to this request only
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> patch(String base, String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) {
        return makeRequest(base + endpoint, HttpMethod.PATCH, responseType, extraHeaders);
    }

    /**
     * Make a DELETE request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> delete(String endpoint, Class<T> responseType) {
        return delete(endpoint, responseType, null);
    }

    /**
     * Make a DELETE request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @param extraHeaders any extra headers that should be applied to this request only
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> delete(String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) {
        return delete(baseURL, endpoint, responseType, extraHeaders);
    }

    /**
     * Make a DELETE request to a manually specified base URL (override class level base URL) + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @param extraHeaders any extra headers that should be applied to this request only
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> delete(String base, String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) {
        return makeRequest(base + endpoint, HttpMethod.DELETE, responseType, extraHeaders);
    }

    private <T> ResponseEntity<T> makeRequest(String url, HttpMethod method, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) {
        HttpHeaders headers = this.headers;

        if (extraHeaders != null) {
            headers = extraHeaders.apply(headers);
        }

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        try {
            return this.template.exchange(
                    url,
                    method,
                    entity,
                    responseType
            );
        } catch (Exception ignored) {
            return null;
        }
    }
}
