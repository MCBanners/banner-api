CREATE TABLE IF NOT EXISTS `saved_banner` (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    mnemonic VARCHAR(24) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT mnemonic_unique UNIQUE (mnemonic)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `banner_settings` (
    id BIGINT(20) NOT NULL,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    INDEX (name)
) ENGINE = InnoDB;