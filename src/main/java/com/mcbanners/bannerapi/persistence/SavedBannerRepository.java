package com.mcbanners.bannerapi.persistence;

import org.springframework.data.repository.CrudRepository;

public interface SavedBannerRepository extends CrudRepository<SavedBanner, Long> {
    SavedBanner findByMnemonic(String mnemonic);
}
