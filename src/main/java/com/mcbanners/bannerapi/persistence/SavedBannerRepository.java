package com.mcbanners.bannerapi.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface SavedBannerRepository extends CrudRepository<SavedBanner, Long> {
    SavedBanner findByMnemonic(String mnemonic);

    List<SavedBanner> findAllByOwner(UUID owner);

    boolean existsByIdAndOwner(Long id, UUID owner);

    void deleteByOwner(UUID owner);
}
