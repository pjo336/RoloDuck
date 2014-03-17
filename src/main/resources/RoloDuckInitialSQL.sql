SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `ROLODUCK` ;
CREATE SCHEMA IF NOT EXISTS `ROLODUCK` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `ROLODUCK` ;

-- -----------------------------------------------------
-- Table `ROLODUCK`.`RD_COMPANY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ROLODUCK`.`RD_COMPANY` ;

CREATE TABLE IF NOT EXISTS `ROLODUCK`.`RD_COMPANY` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `company_name` VARCHAR(100) NOT NULL,
  `subscription_type` INT NOT NULL DEFAULT 0,
  `company_identifying_string` VARCHAR(45) NOT NULL,
  `date_created` TIMESTAMP NOT NULL,
  `date_modified` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `company_name_UNIQUE` (`company_name` ASC),
  UNIQUE INDEX `company_identifying_string_UNIQUE` (`company_identifying_string` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ROLODUCK`.`RD_USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ROLODUCK`.`RD_USER` ;

CREATE TABLE IF NOT EXISTS `ROLODUCK`.`RD_USER` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(100) NOT NULL,
  `user_email` VARCHAR(100) NOT NULL,
  `user_password` VARCHAR(75) NOT NULL,
  `company_id` BIGINT NOT NULL,
  `date_created` TIMESTAMP NOT NULL,
  `date_modified` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `user_email_UNIQUE` (`user_email` ASC),
  INDEX `FK_company_id_idx` (`company_id` ASC),
  CONSTRAINT `FK_user_company_id`
    FOREIGN KEY (`company_id`)
    REFERENCES `ROLODUCK`.`RD_COMPANY` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ROLODUCK`.`RD_USER_ROLES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ROLODUCK`.`RD_USER_ROLES` ;

CREATE TABLE IF NOT EXISTS `ROLODUCK`.`RD_USER_ROLES` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
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

-- -----------------------------------------------------
-- Table `ROLODUCK`.`RD_PROJECT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ROLODUCK`.`RD_PROJECT` ;

CREATE TABLE IF NOT EXISTS `ROLODUCK`.`RD_PROJECT` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `project_name` VARCHAR(45) NOT NULL,
  `project_description` VARCHAR(200) NULL,
  `created_by_user` BIGINT NOT NULL,
  `company_id` BIGINT NOT NULL,
  `date_created` TIMESTAMP NOT NULL,
  `date_modified` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `FK_company_id_idx` (`company_id` ASC),
  INDEX `FK_created_by_user_id_idx` (`created_by_user` ASC),
  CONSTRAINT `FK_project_company_id`
    FOREIGN KEY (`company_id`)
    REFERENCES `ROLODUCK`.`RD_COMPANY` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_project_created_by_user`
    FOREIGN KEY (`created_by_user`)
    REFERENCES `ROLODUCK`.`RD_USER` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ROLODUCK`.`RD_PARTNER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ROLODUCK`.`RD_PARTNER` ;

CREATE TABLE IF NOT EXISTS `ROLODUCK`.`RD_PARTNER` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `partner_name` VARCHAR(45) NOT NULL,
  `partner_description` VARCHAR(200) NULL,
  `date_created` TIMESTAMP NOT NULL,
  `date_modified` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ROLODUCK`.`RD_PARTNER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ROLODUCK`.`RD_PROJECT_PARTNER_ASSOC` ;

CREATE TABLE IF NOT EXISTS `ROLODUCK`.`RD_PROJECT_PARTNER_ASSOC` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `project_id` BIGINT NOT NULL,
  `partner_id` BIGINT NOT NULL,
  `date_created` TIMESTAMP NOT NULL,
  `date_modified` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `FK_project_id_project_idx` (`project_id` ASC),
  INDEX `FK_partner_id_partner_idx` (`partner_id` ASC),
  CONSTRAINT `FK_project_id_assoc`
    FOREIGN KEY (`project_id`)
    REFERENCES `ROLODUCK`.`RD_PROJECT` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_partner_id_assoc`
    FOREIGN KEY (`partner_id`)
    REFERENCES `ROLODUCK`.`RD_PARTNER` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ROLODUCK`.`RD_CONTACT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ROLODUCK`.`RD_CONTACT` ;

CREATE TABLE IF NOT EXISTS `ROLODUCK`.`RD_CONTACT` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `contact_first_name` VARCHAR(45) NOT NULL,
  `contact_last_name` VARCHAR(45) NOT NULL,
  `contact_title` VARCHAR(45) NULL,
  `contact_email` VARCHAR(45) NOT NULL,
  `contact_phone` VARCHAR(45) NULL,
  `partner_id` BIGINT NOT NULL,
  `date_added` TIMESTAMP NOT NULL,
  `date_modified` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `FK_partner_id_idx` (`partner_id` ASC),
  CONSTRAINT `FK_partner_id`
    FOREIGN KEY (`partner_id`)
    REFERENCES `ROLODUCK`.`RD_PARTNER` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
