-- Indexes for primary keys have been explicitly created.

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS LanguageStrings;
DROP TABLE IF EXISTS LanguageTexts;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Adress;
DROP TABLE IF EXISTS Email;
DROP TABLE IF EXISTS EmailTemplate;
DROP TABLE IF EXISTS NewsItem;
DROP TABLE IF EXISTS Sponsor;
DROP TABLE IF EXISTS Event;
SET foreign_key_checks = 1;


-- ------------------------------ LanguageStrings ------------------------------

CREATE TABLE LanguageStrings (
    LanguageStrings_id          bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	LanguageStrings_code        bigint UNSIGNED NOT NULL,
	LanguageStrings_language    varchar(5)  NOT NULL,
	LanguageStrings_string      varchar(255),
	CONSTRAINT LanguageStrings_PK PRIMARY KEY ( LanguageStrings_id ),
	CONSTRAINT LanguageStrings_CK  UNIQUE (LanguageStrings_code,LanguageStrings_language)
) engine=InnoDB;

CREATE INDEX LanguageStringsByLanguageStrings_code ON LanguageStrings (LanguageStrings_code);

-- ------------------------------ LanguageTexts ------------------------------

CREATE TABLE LanguageTexts (
    LanguageTexts_id          bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	LanguageTexts_code        bigint UNSIGNED NOT NULL,
	LanguageTexts_language    varchar(5)  NOT NULL,
	LanguageTexts_string      TEXT NOT NULL,
	CONSTRAINT LanguageTexts_PK PRIMARY KEY ( LanguageTexts_id ),
	CONSTRAINT LanguageTexts_CK  UNIQUE (LanguageTexts_id,LanguageTexts_language)
) engine=InnoDB;

CREATE INDEX LanguageTextsByLanguageTextss_code ON LanguageTexts (LanguageTexts_code);
	
-- ---------- Table for validation queries from the connection pool. ----------

DROP TABLE IF EXISTS PingTable;
CREATE TABLE PingTable (foo CHAR(1));


-- ------------------------------ User -------------------------------------

CREATE TABLE User ( 
	User_id                      bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	User_permissions             varchar(16) NOT NULL DEFAULT "",
	User_name                    varchar(250)  NOT NULL  ,
	User_login                   varchar(200) NOT NULL  ,
	User_password                varchar(200) NOT NULL  ,
	User_secondPassword          varchar(200) NOT NULL  ,
	User_secondPasswordExpDate   datetime ,
	User_dni                     varchar(11)  NOT NULL ,
	User_email                   varchar(200)  NOT NULL  ,
	User_telf                    varchar(15) , 
	User_shirtSize               varchar(3) ,
	User_borndate                datetime ,
	User_language                varchar(5)  NOT NULL,
	CONSTRAINT User_PK PRIMARY KEY ( User_id ) ,
	CONSTRAINT User_login_UNIQUE UNIQUE ( User_login )  ,
	CONSTRAINT User_dni_UNIQUE UNIQUE ( User_dni )  ,
	CONSTRAINT User_email_UNIQUE UNIQUE ( User_email )  
 ) engine=InnoDB;

 CREATE INDEX UserIndexByUser_login ON User (User_login);
 CREATE INDEX UserIndexByUser_dni ON User (User_dni); 
 CREATE INDEX UserIndexByUser_email ON User (User_email);

-- ------------------------------ Adress -------------------------------------

CREATE TABLE Adress (
    Adress_id 				BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    Adress_user 			VARCHAR(64) NOT NULL,
	Adress_password 		VARCHAR(64) NOT NULL,
    CONSTRAINT Adress_PK PRIMARY KEY(Adress_id),
    CONSTRAINT CategoryUniqueKey UNIQUE (Adress_user)
) engine=InnoDB;

-- ------------------------------ Email -------------------------------------

CREATE TABLE Email (
    Email_id 				BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	Email_confirmation		bool DEFAULT 0,
    Email_adress_id 		BIGINT UNSIGNED ,
	Email_file		 		VARCHAR(128), 
	Email_fileName	 		VARCHAR(128), 
	Email_user_id           BIGINT UNSIGNED NOT NULL,
	Email_case 				VARCHAR(128) NOT NULL,
	Email_body		 		MEDIUMTEXT,
	Email_senddate			datetime,
	email_Date				datetime,
    CONSTRAINT Email_PK PRIMARY KEY(Email_id )
) engine=InnoDB;

 CREATE INDEX EmailByAdressId ON Email (Email_adress_id);
 CREATE INDEX EmailByUserId ON Email (Email_user_id);
 CREATE INDEX EmailIndexConfirmation ON Email (Email_confirmation) USING BTREE;

-- ------------------------------ EmailTemplate -------------------------------------

CREATE TABLE EmailTemplate (
	EmailTemplate_id             bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	EmailTemplate_name           varchar(128) NOT NULL UNIQUE, 
	EmailTemplate_adress_id      bigint UNSIGNED ,
	EmailTemplate_file           varchar(128), 
	EmailTemplate_fileName       varchar(128), 
	EmailTemplate_case           varchar(128) NOT NULL,
	EmailTemplate_body           TEXT,
    CONSTRAINT EmailTemplate_PK PRIMARY KEY(EmailTemplate_id)
) engine=InnoDB;

CREATE INDEX EmailTemplateByAdressId ON EmailTemplate (EmailTemplate_adress_id);
CREATE INDEX EmailTemplateByname ON EmailTemplate (EmailTemplate_name);

-- ------------------------------ NewsItem -----------------------------------------

CREATE TABLE NewsItem ( 
	NewsItem_id                   bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
    NewsItem_title                varchar(255)  NOT NULL  ,
    NewsItem_image                varchar(255) ,
	NewsItem_date_created         datetime  ,
	NewsItem_date_publish         datetime NOT NULL  ,
	NewsItem_content              MEDIUMTEXT   ,
	NewsItem_user_id              bigint UNSIGNED   ,
	NewsItem_event_id             bigint UNSIGNED NOT NULL  ,
	CONSTRAINT NewsItem_PK PRIMARY KEY ( NewsItem_id )
 ) engine=InnoDB;
 
 CREATE INDEX NewsItemIndexByNewsItemUser_id ON NewsItem (NewsItem_user_id);
 CREATE INDEX NewsItemIndexByNewsItemEvent_id ON NewsItem (NewsItem_event_id);

-- ------------------------------ Sponsor -----------------------------------------

CREATE TABLE Sponsor (
	Sponsor_id             bigint UNSIGNED NOT NULL  AUTO_INCREMENT,
	Sponsor_name           varchar(128),
	Sponsor_url            varchar(255),
	Sponsor_imageurl       varchar(256),
	Sponsor_event_id       bigint UNSIGNED NOT NULL  ,
    CONSTRAINT pk_sponsor PRIMARY KEY(Sponsor_id)
) engine=InnoDB;

 CREATE INDEX SponsorIndexByNewsItemEvent_id ON Sponsor (Sponsor_event_id);
-- ------------------------------ EVENT -------------------------------------

CREATE TABLE Event ( 
	Event_id                           bigint UNSIGNED NOT NULL AUTO_INCREMENT,
	Event_name                         varchar(150) NOT NULL  ,
	Event_description                  TEXT    ,
	Event_num_participants             int DEFAULT 1 ,
	Event_minimunAge                   int DEFAULT 0 ,
	Event_price                        int DEFAULT 0 ,
	Event_date_start                   date NOT NULL  ,
	Event_date_end                     date NOT NULL  ,
	Event_reg_date_open                datetime NOT NULL  ,
	Event_reg_date_close               datetime NOT NULL  ,
	Event_setPaidTemplate_id           bigint UNSIGNED ,
	Event_onQueueTemplate_id           bigint UNSIGNED ,
	Event_outstandingTemplate_id       bigint UNSIGNED ,
	Event_outOfDateTemplate_id         bigint UNSIGNED ,
	Event_fromQueueToOutstanding_id    bigint UNSIGNED ,
	Event_rules                        TEXT,
	CONSTRAINT pk_event PRIMARY KEY ( Event_id ) ,
	CONSTRAINT Event_name_UNIQUE UNIQUE ( Event_name )
 ) engine=InnoDB;
 
 CREATE INDEX EventIndexByEvent_Name ON Event (Event_name);

-- FOREING KEY

 ALTER TABLE Email ADD CONSTRAINT fk_email_adress FOREIGN KEY ( Email_adress_id ) REFERENCES Adress (Adress_id) ON DELETE SET NULL ON UPDATE CASCADE;
 ALTER TABLE Email ADD CONSTRAINT fk_email_user FOREIGN KEY ( Email_user_id ) REFERENCES User (User_id) ON DELETE CASCADE ON UPDATE CASCADE;

 ALTER TABLE EmailTemplate ADD CONSTRAINT fk_emailtemplate_adress FOREIGN KEY( EmailTemplate_adress_id ) REFERENCES Adress (Adress_id) ON UPDATE CASCADE ON DELETE SET NULL;

 ALTER TABLE NewsItem ADD CONSTRAINT fk_newsitem_user FOREIGN KEY( NewsItem_user_id ) REFERENCES User (User_id) ON UPDATE CASCADE ON DELETE SET NULL;
 ALTER TABLE NewsItem ADD CONSTRAINT fk_newsitem_event FOREIGN KEY( NewsItem_event_id ) REFERENCES Event (Event_id) ON UPDATE CASCADE ON DELETE CASCADE;
 
 ALTER TABLE Sponsor ADD CONSTRAINT fk_sponsor_event FOREIGN KEY( Sponsor_event_id ) REFERENCES Event (Event_id) ON UPDATE CASCADE ON DELETE CASCADE;
 
 ALTER TABLE Event ADD CONSTRAINT fk_event_setPaidTemplate FOREIGN KEY ( Event_setPaidTemplate_id ) REFERENCES EmailTemplate (EmailTemplate_id) ON DELETE SET NULL ON UPDATE CASCADE;
 ALTER TABLE Event ADD CONSTRAINT fk_event_onQueueTemplate FOREIGN KEY ( Event_onQueueTemplate_id ) REFERENCES EmailTemplate (EmailTemplate_id) ON DELETE SET NULL ON UPDATE CASCADE;
 ALTER TABLE Event ADD CONSTRAINT fk_event_outstandingTemplate FOREIGN KEY (Event_outstandingTemplate_id ) REFERENCES EmailTemplate (EmailTemplate_id) ON DELETE SET NULL ON UPDATE CASCADE;
 ALTER TABLE Event ADD CONSTRAINT fk_event_outOfDateTemplate FOREIGN KEY ( Event_outOfDateTemplate_id ) REFERENCES EmailTemplate (EmailTemplate_id) ON DELETE SET NULL ON UPDATE CASCADE;
 ALTER TABLE Event ADD CONSTRAINT fk_event_fromQueueToOutstanding FOREIGN KEY ( Event_fromQueueToOutstanding_id ) REFERENCES EmailTemplate (EmailTemplate_id) ON DELETE SET NULL ON UPDATE CASCADE;
 
