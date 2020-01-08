package com.mcbanners.bannerapi.persistence;

import com.mcbanners.bannerapi.banner.BannerType;

import javax.persistence.*;
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
}