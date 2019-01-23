CREATE TABLE parking.parking_account
(
    id BIGSERIAL NOT NULL  PRIMARY KEY,
    iban character varying(34) COLLATE pg_catalog."default" NOT NULL
)
TABLESPACE pg_default;

CREATE TABLE parking.parking
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    address character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    parking_account_id bigint NOT NULL,
    CONSTRAINT fkj4cb690lw38fv6xd9e2j1scmh FOREIGN KEY (parking_account_id)
        REFERENCES parking.parking_account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;

CREATE TABLE parking.parking_space
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    status character varying(50) COLLATE pg_catalog."default" NOT NULL,
    parking_id bigint NOT NULL,
    CONSTRAINT fku4ns1toqpuqf2nohwnqd6xe0 FOREIGN KEY (parking_id)
        REFERENCES parking.parking (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;

CREATE TABLE parking.tariff
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    period character varying(50) COLLATE pg_catalog."default" NOT NULL,
    precalculations_quantity bigint NOT NULL,
    amount numeric(19,2) NOT NULL,
    currency character varying(50) COLLATE pg_catalog."default" NOT NULL,
    type character varying(50) COLLATE pg_catalog."default" NOT NULL,
    parking_id bigint NOT NULL,
    CONSTRAINT fkmcm4m5cxwvbo0hrtbjopr6jj2 FOREIGN KEY (parking_id)
        REFERENCES parking.parking (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;

CREATE TABLE parking.tariffication_rule
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    raw_formula character varying(255) COLLATE pg_catalog."default" NOT NULL,
    rule_order character varying(50) COLLATE pg_catalog."default" NOT NULL,
    tariff_id bigint NOT NULL,
    CONSTRAINT fk2rt1d8gp9aplpsrn1ayg872ik FOREIGN KEY (tariff_id)
        REFERENCES parking.tariff (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;

CREATE TABLE parking.vehicle_registry
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    end_date_time timestamp without time zone NOT NULL,
    is_parked boolean NOT NULL,
    registration character varying(15) COLLATE pg_catalog."default" NOT NULL,
    start_date_time timestamp without time zone NOT NULL,
    parking_space_id bigint NOT NULL,
    selected_tariff_id bigint,
    CONSTRAINT fk7yy9tdhjhxw85luxy9psppyes FOREIGN KEY (selected_tariff_id)
        REFERENCES parking.tariff (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkrmnb75p1p9kw8nceenodmrpob FOREIGN KEY (parking_space_id)
        REFERENCES parking.parking_space (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;

CREATE TABLE parking.calculated_charge
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    calculation_date_time timestamp without time zone NOT NULL,
    amount numeric(19,2) NOT NULL,
    currency character varying(50) COLLATE pg_catalog."default" NOT NULL,
    selected_tariff_id bigint NOT NULL,
    vehicle_registry_id bigint NOT NULL,
    CONSTRAINT fk178aii3x5her0t4kdq7in7uad FOREIGN KEY (selected_tariff_id)
        REFERENCES parking.tariff (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk68qpbrs5e78falui9helgbr79 FOREIGN KEY (vehicle_registry_id)
        REFERENCES parking.vehicle_registry (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;

CREATE TABLE parking.payment
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    amount numeric(19,2) NOT NULL,
    currency character varying(50) COLLATE pg_catalog."default" NOT NULL,
    payment_date_time timestamp without time zone NOT NULL,
    calculated_charge_id bigint NOT NULL,
    parking_account_id bigint NOT NULL,
    selected_tariff_id bigint NOT NULL,
    CONSTRAINT fk1lvqm4h6rc9tcsc8ij0gonlg7 FOREIGN KEY (parking_account_id)
        REFERENCES parking.parking_account (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhqu20mg2flncypi60qawqv13n FOREIGN KEY (calculated_charge_id)
        REFERENCES parking.calculated_charge (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkr2otaggv7j85q056d8batbdeb FOREIGN KEY (selected_tariff_id)
        REFERENCES parking.tariff (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
TABLESPACE pg_default;
