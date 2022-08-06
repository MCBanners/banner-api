package com.mcbanners.bannerapi.service.impl;

import com.mcbanners.bannerapi.net.BuiltByBitClient;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitMember;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitMemberData;
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
    private final BuiltByBitClient builtByBitClient;

    @Autowired
    public DefaultMemberService(BuiltByBitClient builtByBitClient) {
        this.builtByBitClient = builtByBitClient;
    }

    @Override
    @Cacheable(unless = "#result == null")
    public Member getMember(int memberId, ServiceBackend backend) {
        if (backend == ServiceBackend.BUILTBYBIT) {
            return handleBuiltByBit(memberId);
        }
        return null;
    }

    private Member handleBuiltByBit(int memberId) {
        BuiltByBitMember member = loadBuiltByBitClientMember(memberId);

        if (member == null || member.getResult().equals("error")) {
            return null;
        }

        BuiltByBitMemberData data = member.getData();

        if (data == null) {
            return null;
        }

        String avatarUrl = data.getAvatarUrl();
        avatarUrl = avatarUrl.replace("https://static.mc-market.org", "https://cdn.builtbybit.com");

        String memberIcon = loadBuiltByBitClientMemberIcon(avatarUrl);
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

    private BuiltByBitMember loadBuiltByBitClientMember(int memberId) {
        ResponseEntity<BuiltByBitMember> resp = builtByBitClient.getMember(memberId);
        if (resp == null) {
            return null;
        }

        return resp.getBody();
    }

    private String loadBuiltByBitClientMemberIcon(String url) {
        ResponseEntity<byte[]> resp = builtByBitClient.getIcon(url);
        if (resp == null) {
            return null;
        }

        byte[] body = resp.getBody();
        return Base64.getEncoder().encodeToString(body);
    }
}
