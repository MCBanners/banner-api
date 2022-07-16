package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.MCMarketClient;
import com.mcbanners.bannerapi.obj.backend.mcmarket.MCMarketMember;
import com.mcbanners.bannerapi.obj.backend.mcmarket.MCMarketMemberData;
import com.mcbanners.bannerapi.obj.generic.Member;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Service
@CacheConfig(cacheNames = {"member"})
public class DefaultMemberService implements MemberService {
    private final MCMarketClient mcMarketClient;

    @Autowired
    public DefaultMemberService(MCMarketClient mcMarketClient) {
        this.mcMarketClient = mcMarketClient;
    }

    @Override
    @Cacheable(unless = "#result == null")
    public Member getMember(int memberId, ServiceBackend backend) {
        if (backend == ServiceBackend.MCMARKET) {
            return handleMcMarket(memberId);
        }
        return null;
    }

    private Member handleMcMarket(int memberId) {
        MCMarketMember member = loadMCMarketMember(memberId);

        if (member == null || member.getResult().equals("error")) {
            return null;
        }

        MCMarketMemberData data = member.getData();

        if (data == null) {
            return null;
        }

        String memberIcon = loadMCMarketMemberIcon(data.getAvatarUrl());
        if (memberIcon == null) {
            memberIcon = "";
        }


        Instant instant = Instant.ofEpochSecond(data.getJoinDate());
        Date date = Date.from(instant);
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        String rank;
        if (data.isUltimate()) {
            rank = "Ultimate";
        } else if (!data.isUltimate() && data.isSupreme()) {
            rank = "Supreme";
        } else if (!data.isUltimate() && !data.isSupreme() && data.isPremium()) {
            rank = "Premium";
        } else {
            rank = "";
        }

        return new Member(
                data.getUsername(),
                rank,
                sdf.format(date),
                memberIcon,
                data.getPostCount(),
                data.getFeedbackPositive(),
                data.getFeedbackNegative());
    }

    private MCMarketMember loadMCMarketMember(int memberId) {
        ResponseEntity<MCMarketMember> resp = mcMarketClient.getMember(memberId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadMCMarketMemberIcon(String url) {
        ResponseEntity<byte[]> resp = mcMarketClient.getIcon(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }
}
