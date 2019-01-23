INSERT INTO parking.parking_account(iban) VALUES ('93619559255759918039977563');
INSERT INTO parking.parking_account(iban) VALUES ('73061951068743308046822682');

INSERT INTO parking.parking( address, name, parking_account_id )VALUES ('Warszawska 3', 'Warsaw Parking', 1);
INSERT INTO parking.parking( address, name, parking_account_id )VALUES ('Krakowska 3', 'Cracovian Parking', 2);

INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 1);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 1);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 1);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 1);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 1);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 1);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 1);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 1);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 1);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 1);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 1);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 1);

INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 2);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 2);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 2);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 2);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 2);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 2);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 2);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 2);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 2);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 2);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('FREE', 2);
INSERT INTO parking.parking_space(status, parking_id) VALUES ('MAINTENANCE', 2);

INSERT INTO parking.tariff(period, precalculations_quantity, amount, currency, type, parking_id) VALUES ('HOUR', 4, 2, 'PLN', 'REGULAR', 1);
INSERT INTO parking.tariff(period, precalculations_quantity, amount, currency, type, parking_id) VALUES ('HOUR', 5, 0, 'PLN', 'VIP', 1);

INSERT INTO parking.tariff(period, precalculations_quantity, amount, currency, type, parking_id) VALUES ('HOUR', 4, 2, 'PLN', 'REGULAR', 2);
INSERT INTO parking.tariff(period, precalculations_quantity, amount, currency, type, parking_id) VALUES ('HOUR', 5, 0, 'PLN', 'VIP', 2);

INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('x', 'FIRST', 1);
INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('x*2', 'SECOND', 1);
INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('x*2*Math.pow(2,i)', 'RECURSIVE_NEXT', 1);

INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('x', 'FIRST', 2);
INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('x+2', 'SECOND', 2);
INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('(x+2)*Math.pow(1.5,i)', 'RECURSIVE_NEXT', 2);

INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('x', 'FIRST', 3);
INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('x*2', 'SECOND', 3);
INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('x*2*Math.pow(2,i)', 'RECURSIVE_NEXT', 3);

INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('x', 'FIRST', 4);
INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('x+2', 'SECOND', 4);
INSERT INTO parking.tariffication_rule(raw_formula, rule_order, tariff_id) VALUES ('(x+2)*Math.pow(1.5,i)', 'RECURSIVE_NEXT', 4);