CREATE TABLE IF NOT EXISTS `saved_banner` (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    type INT(5) NOT NULL,
    owner BINARY(16) NULL DEFAULT NULL,
    mnemonic VARCHAR(14) NOT NULL,
    PRIMARY KEY (id),
    INDEX (owner),
    CONSTRAINT mnemonic_unique UNIQUE (mnemonic)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `banner_settings` (
    id BIGINT(20) NOT NULL,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    INDEX (id),
    INDEX (name),
    FOREIGN KEY (id)
        REFERENCES `saved_banner`(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE = InnoDB;