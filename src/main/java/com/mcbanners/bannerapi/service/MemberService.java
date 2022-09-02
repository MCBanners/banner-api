package com.mcbanners.bannerapi.service;

import com.mcbanners.bannerapi.net.BuiltByBitClient;
import com.mcbanners.bannerapi.obj.backend.builtbybit.BuiltByBitMember;
import com.mcbanners.bannerapi.obj.generic.Member;
import com.mcbanners.bannerapi.service.ServiceBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Service
@CacheConfig(cacheNames = {"member"})
public class MemberService {
    private final BuiltByBitClient builtByBitClient;

    @Autowired
    public MemberService(BuiltByBitClient builtByBitClient) {
        this.builtByBitClient = builtByBitClient;
    }

    @Cacheable(unless = "#result == null")
    public Member getMember(int memberId, ServiceBackend backend) {
        return backend == ServiceBackend.BUILTBYBIT ? handleBuiltByBit(memberId) : null;
    }

    private Member handleBuiltByBit(int memberId) {
        BuiltByBitMember member = fetchMember(memberId);

        if (member == null) {
            return null;
        }

        String avatarUrl = builtByBitClient.getBase64Image(member.avatarUrl());

        if (avatarUrl == null) {
            avatarUrl = "";
        }


        Instant instant = Instant.ofEpochSecond(member.joinDate());
        Date date = Date.from(instant);
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        String rank;
        if (member.ultimate()) {
            rank = "Ultimate";
        } else if (member.supreme()) {
            rank = "Supreme";
        } else if (member.premium()) {
            rank = "Premium";
        } else {
            rank = "";
        }

        return new Member(
                member.username(),
                rank,
                sdf.format(date),
                avatarUrl,
                member.postCount(),
                member.feedbackPositive(),
                member.feedbackNegative()
        );
    }

    private BuiltByBitMember fetchMember(int memberId) {
        final ResponseEntity<BuiltByBitMember> resp = builtByBitClient.getMember(memberId);
        return resp == null ? null : resp.getBody();
    }
}
