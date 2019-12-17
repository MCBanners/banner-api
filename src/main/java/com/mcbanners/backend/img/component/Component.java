package com.mcbanners.backend.img.component;

import com.mcbanners.backend.img.ImageBuilder;

public abstract class Component {
    protected final int x;
    protected final int y;

    public Component(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract ImageBuilder draw(ImageBuilder builder);
}
