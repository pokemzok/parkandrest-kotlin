CREATE TABLE security.app_user
(
    id BIGSERIAL NOT NULL  PRIMARY KEY ,
    is_active boolean,
    password character varying(255) COLLATE pg_catalog."default",
    registration_date_time timestamp without time zone,
    username character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT uk_3k4cplvh82srueuttfkwnylq0 UNIQUE (username)

)
TABLESPACE pg_default;

CREATE TABLE security.user_authority
(
    id BIGSERIAL NOT NULL  PRIMARY KEY,
    role character varying(50) COLLATE pg_catalog."default" NOT NULL,
    app_user_id bigint NOT NULL,
    CONSTRAINT fkliv5e4unib9asqlqf51udolrl FOREIGN KEY (app_user_id)
        REFERENCES security.app_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;

CREATE TABLE security.oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);

create table security.oauth_client_token (
  token_id VARCHAR(255),
  token BYTEA,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

CREATE TABLE security.oauth_access_token (
  token_id VARCHAR(256) DEFAULT NULL,
  token BYTEA,
  authentication_id VARCHAR(256) DEFAULT NULL,
  user_name VARCHAR(256) DEFAULT NULL,
  client_id VARCHAR(256) DEFAULT NULL,
  authentication BYTEA,
  refresh_token VARCHAR(256) DEFAULT NULL
);

CREATE TABLE security.oauth_refresh_token (
  token_id VARCHAR(256) DEFAULT NULL,
  token BYTEA,
  authentication BYTEA
);

create table security.oauth_code (
  code VARCHAR(255), authentication BYTEA
);

create table security.oauth_approvals (
    userId VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt timestamp,
    lastModifiedAt timestamp
);