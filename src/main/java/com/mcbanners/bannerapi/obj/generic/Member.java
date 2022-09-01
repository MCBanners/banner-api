package com.mcbanners.bannerapi.obj.generic;

public record Member(String name, String rank, String joinDate, String icon, int posts, int positiveFeedback,
                     int negativeFeedback) {
}
