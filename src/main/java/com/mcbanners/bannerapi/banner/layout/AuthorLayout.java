package com.mcbanners.bannerapi.banner.layout;

import com.mcbanners.bannerapi.banner.Sprite;
import com.mcbanners.bannerapi.banner.component.BasicComponent;
import com.mcbanners.bannerapi.banner.component.LogoComponent;
import com.mcbanners.bannerapi.banner.parameter.AuthorParameters;
import com.mcbanners.bannerapi.obj.generic.Author;
import com.mcbanners.bannerapi.service.ServiceBackend;
import com.mcbanners.bannerapi.util.NumberUtil;

import java.util.List;
import java.util.Map;

public class AuthorLayout extends Layout<AuthorParameters> {
    private final Author author;
    private final ServiceBackend backend;

    public AuthorLayout(Author author, ServiceBackend backend, Map<String, String> rawParameters) {
        super(new AuthorParameters(rawParameters));

        this.author = author;
        this.backend = backend;
    }

    @Override
    public List<BasicComponent> build() {
        component(new LogoComponent(
                parameters().getLogo().readX(),
                parameters().getLogo().readSize(),
                author.icon(),
                Sprite.DEFAULT_AUTHOR_LOGO
        ));

        text(parameters().getAuthorName(), author.name());

        text(parameters().getResourceCount(), "%d resources", author.resources());

        if (author.likes() != -1) {
            String word;
            switch(backend) {
                case MODRINTH -> word = "followers";
                case HANGAR -> word = "stars";
                default -> word = "likes";
            }
            text(parameters().getLikes(), "%s %s", NumberUtil.abbreviate(author.likes()), word);
        }

        text(parameters().getDownloads(), "%s downloads", NumberUtil.abbreviate(author.downloads()));

        if (author.rating() != -1) {
            final String word = backend == ServiceBackend.HANGAR ? "watchers" : "reviews";
            text(parameters().getReviews(), "%s %s", author.rating(), word);
        }

        return components();
    }
}
