CREATE TABLE person
(
  person_id           INT AUTO_INCREMENT
    PRIMARY KEY,
  gender              VARCHAR(50) DEFAULT '' NULL,
  birthdate           DATE                   NULL,
  birthdate_estimated TINYINT(1) DEFAULT '0' NOT NULL,
  dead                TINYINT(1) DEFAULT '0' NOT NULL,
  death_date          DATETIME               NULL,
  cause_of_death      INT                    NULL,
  creator             INT                    NULL,
  changed_by          INT                    NULL,
  date_changed        DATETIME               NULL,
  voided              TINYINT(1) DEFAULT '0' NOT NULL,
  voided_by           INT                    NULL,
  date_voided         DATETIME               NULL,
  void_reason         VARCHAR(255)           NULL,
  deathdate_estimated TINYINT(1) DEFAULT '0' NOT NULL,
  birthtime           TIME                   NULL
);