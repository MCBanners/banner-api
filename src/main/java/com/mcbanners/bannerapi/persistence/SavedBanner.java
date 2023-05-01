package com.mcbanners.bannerapi.persistence;

import com.mcbanners.bannerapi.banner.BannerType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "saved_banner", uniqueConstraints = {@UniqueConstraint(columnNames = {"mnemonic"})})
public class SavedBanner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private BannerType bannerType;

    @Column
    private UUID owner;

    @Column(nullable = false)
    private String mnemonic;

    @Column(nullable = false)
    private String metadata;

    @Column(nullable = false)
    private String settings;

    public long getId() {
        return id;
    }

    public BannerType getBannerType() {
        return bannerType;
    }

    public void setBannerType(BannerType bannerType) {
        this.bannerType = bannerType;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }
}