package com.mcbanners.bannerapi.controller;

import com.mcbanners.bannerapi.banner.BannerOutputType;
import com.mcbanners.bannerapi.image.BannerImageWriter;
import com.mcbanners.bannerapi.image.layout.MemberLayout;
import com.mcbanners.bannerapi.obj.generic.Member;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("member")
public class MemberController {
    private final MemberService members;

    @Autowired
    public MemberController(MemberService members) {
        this.members = members;
    }

    @GetMapping(value = "/builtbybit/{id}/isValid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Boolean>> getIsValid(@PathVariable int id) {
        Member member = this.members.getMember(id, ServiceBackend.BUILTBYBIT);
        return new ResponseEntity<>(Collections.singletonMap("valid", member != null), HttpStatus.OK);
    }

    @GetMapping(value = "/builtbybit/{id}/banner.{outputType}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBanner(@PathVariable int id, @PathVariable BannerOutputType outputType, @RequestParam Map<String, String> raw) {
        Member member = this.members.getMember(id, ServiceBackend.BUILTBYBIT);
        if (member == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return draw(member, raw, ServiceBackend.BUILTBYBIT, outputType);
    }

    private ResponseEntity<byte[]> draw(Member member, Map<String, String> raw, ServiceBackend backend, BannerOutputType outputType) {
        return BannerImageWriter.write(new MemberLayout(member, raw, backend).draw(outputType), outputType);
    }
}
