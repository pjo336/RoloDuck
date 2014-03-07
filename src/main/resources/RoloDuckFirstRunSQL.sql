-- -----------------------------------------------------
-- Table `ROLODUCK`.`RD_USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ROLODUCK`.`RD_USER` ;

CREATE TABLE IF NOT EXISTS `ROLODUCK`.`RD_USER` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(100) NOT NULL,
  `user_email` VARCHAR(100) NOT NULL,
  `user_password` VARCHAR(45) NOT NULL,
  `date_created` TIMESTAMP NOT NULL,
  `date_modified` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `user_email_UNIQUE` (`user_email` ASC)
)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ROLODUCK`.`RD_USER_ROLES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ROLODUCK`.`RD_USER_ROLES` ;

CREATE TABLE IF NOT EXISTS `ROLODUCK`.`RD_USER_ROLES` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `authority` VARCHAR(45) NOT NULL,
  `date_created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_user_roles` (`user_id`),
  CONSTRAINT `FK_user_roles` FOREIGN KEY (`USER_ID`) REFERENCES `RD_USER` (`id`)
	ON DELETE CASCADE
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table `ROLODUCK`.`PERSISTENT_LOGINS`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `ROLODUCK`.`PERSISTENT_LOGINS`;

CREATE TABLE IF NOT EXISTS `ROLODUCK`.`PERSISTENT_LOGINS` (
  `username` VARCHAR(64) NOT NULL,
  `series` VARCHAR(64) NOT NULL,
  `token` VARCHAR(64) NOT NULL,
  `last_used` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB;