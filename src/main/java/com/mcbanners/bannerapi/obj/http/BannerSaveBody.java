package com.mcbanners.bannerapi.obj.http;

import com.mcbanners.bannerapi.banner.BannerType;

import java.util.Map;

public record BannerSaveBody(BannerType type, Map<String, String> metadata, Map<String, String> settings) {
}
