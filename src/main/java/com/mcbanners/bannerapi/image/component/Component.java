package com.mcbanners.backend.image.component;

import com.mcbanners.backend.image.ImageBuilder;

public abstract class Component {
    protected final int x;
    protected final int y;

    public Component(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract ImageBuilder draw(ImageBuilder builder);
}
