package com.mcbanners.bannerapi.net.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This is required to deal with the CurseForge API.
 * Per CF team: some projects don't yet have defined URLs because they have not yet been requested.
 * On first request, the system will queue this processing and return a 202 status code.
 * This is telling the user that they need to try again shortly as the content is not yet available.
 * When this happens to us, we will propagate the status on our end and let the frontend display a
 * message that the user needs to wait.
 */
@ResponseStatus(value = HttpStatus.ACCEPTED)
public class FurtherProcessingRequiredException extends RuntimeException {
    public FurtherProcessingRequiredException() {
        this("Further processing is required. Please try again in 30 seconds.");
    }

    public FurtherProcessingRequiredException(String message) {
        super(message);
    }
}
