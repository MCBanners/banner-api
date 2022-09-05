ALTER TABLE `saved_banner`
    ADD metadata LONGTEXT NOT NULL
        AFTER mnemonic;

ALTER TABLE `saved_banner`
    ADD settings LONGTEXT NOT NULL
        AFTER metadata;
