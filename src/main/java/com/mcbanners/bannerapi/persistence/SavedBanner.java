package com.mcbanners.bannerapi.persistence;

import com.mcbanners.bannerapi.banner.BannerType;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "saved_banner", uniqueConstraints = {@UniqueConstraint(columnNames = {"mnemonic"})})
public class SavedBanner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
    private BannerType bannerType;

    @Column
    private UUID owner;

    @Column
    private String mnemonic;

    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @CollectionTable(name = "banner_settings", joinColumns = @JoinColumn(name = "id"))
    private Map<String, String> settings;

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

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }

    public final boolean isAuthorBanner() {
        return switch (bannerType) {
            case SPIGOT_AUTHOR, SPONGE_AUTHOR, CURSEFORGE_AUTHOR, MODRINTH_AUTHOR, BUILTBYBIT_AUTHOR, POLYMART_AUTHOR -> true;
            default -> false;
        };
    }
}