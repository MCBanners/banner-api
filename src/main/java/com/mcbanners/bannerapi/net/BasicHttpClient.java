package com.mcbanners.bannerapi.net;

import com.mcbanners.bannerapi.util.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.function.Function;

public abstract class BasicHttpClient {
    private static final String USER_AGENT = "MCBanners";
    private final String baseURL;
    private final RestTemplate template;

    public BasicHttpClient(String baseURL) {
        this.baseURL = baseURL;
        this.template = new RestTemplate();
    }

    /**
     * Make a GET request to the specified URL, receiving a PNG image in return.
     *
     * @param url the url to load the image from
     * @return the byte array of the image, or null if the url is invalid or if the image could not be loaded
     */
    public final ResponseEntity<byte[]> getImage(String url) {
        if (url == null || url.isEmpty() || url.isBlank()) {
            return null;
        }

        try {
            return get(url, "", byte[].class, headers -> {
                headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));
                return headers;
            });
        } catch (RestClientResponseException ex) {
            Log.error("Failed to load image by url %s: %s", url, ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Make a GET request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> get(String endpoint, Class<T> responseType) throws RestClientResponseException {
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
    public final <T> ResponseEntity<T> get(String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) throws RestClientResponseException {
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
    public final <T> ResponseEntity<T> get(String base, String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) throws RestClientResponseException {
        return makeRequestForResponseType(base + endpoint, HttpMethod.GET, responseType, extraHeaders);
    }

    /**
     * Make a POST request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> post(String endpoint, Class<T> responseType) throws RestClientResponseException {
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
    public final <T> ResponseEntity<T> post(String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) throws RestClientResponseException {
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
    public final <T> ResponseEntity<T> post(String base, String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) throws RestClientResponseException {
        return makeRequestForResponseType(base + endpoint, HttpMethod.POST, responseType, extraHeaders);
    }

    /**
     * Make a PUT request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> put(String endpoint, Class<T> responseType) throws RestClientResponseException {
        return put(endpoint, responseType, null);
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
    public final <T> ResponseEntity<T> put(String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) throws RestClientResponseException {
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
    public final <T> ResponseEntity<T> put(String base, String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) throws RestClientResponseException {
        return makeRequestForResponseType(base + endpoint, HttpMethod.PUT, responseType, extraHeaders);
    }

    /**
     * Make a PATCH request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> patch(String endpoint, Class<T> responseType) throws RestClientResponseException {
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
    public final <T> ResponseEntity<T> patch(String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) throws RestClientResponseException {
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
    public final <T> ResponseEntity<T> patch(String base, String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) throws RestClientResponseException {
        return makeRequestForResponseType(base + endpoint, HttpMethod.PATCH, responseType, extraHeaders);
    }

    /**
     * Make a DELETE request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> delete(String endpoint, Class<T> responseType) throws RestClientResponseException {
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
    public final <T> ResponseEntity<T> delete(String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) throws RestClientResponseException {
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
    public final <T> ResponseEntity<T> delete(String base, String endpoint, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) throws RestClientResponseException {
        return makeRequestForResponseType(base + endpoint, HttpMethod.DELETE, responseType, extraHeaders);
    }

    private <T> ResponseEntity<T> makeRequestForResponseType(String url, HttpMethod method, Class<T> responseType, Function<HttpHeaders, HttpHeaders> extraHeaders) throws RestClientResponseException {
        HttpHeaders headers = new HttpHeaders();

        if (extraHeaders != null) {
            headers = extraHeaders.apply(headers);
        }

        // Force adds the user agent. Idk why it isn't there from the other attempt
        headers.add("User-Agent", USER_AGENT);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return this.template.exchange(
                url,
                method,
                entity,
                responseType
        );
    }
}
