package com.mcbanners.bannerapi.persistence;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "saved_banner", uniqueConstraints = {@UniqueConstraint(columnNames = {"mnemonic"})})
public class SavedBanner {
    @Id
    @GeneratedValue
    private long id;

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