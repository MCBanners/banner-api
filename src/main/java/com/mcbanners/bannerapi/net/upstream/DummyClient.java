package com.mcbanners.bannerapi.net.upstream;

import com.mcbanners.bannerapi.net.BasicHttpClient;
import org.springframework.stereotype.Component;

@Component
public class DummyClient extends BasicHttpClient {
    public DummyClient() {
        super("");
    }
}
