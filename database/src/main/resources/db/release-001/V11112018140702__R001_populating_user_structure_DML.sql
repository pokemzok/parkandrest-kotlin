INSERT INTO security.app_user(is_active, password, registration_date_time, username) VALUES (true, '$2a$10$qzEKGj8Gnu4Xh.sV1xnTWuCooI6HAQ.iwbiySrpErXWgMAohCWpBy', LOCALTIMESTAMP, 'fulladmin');
INSERT INTO security.app_user(is_active, password, registration_date_time, username) VALUES (true, '$2a$10$qzEKGj8Gnu4Xh.sV1xnTWuCooI6HAQ.iwbiySrpErXWgMAohCWpBy', LOCALTIMESTAMP, 'admin');
INSERT INTO security.app_user(is_active, password, registration_date_time, username) VALUES (true, '$2a$10$qzEKGj8Gnu4Xh.sV1xnTWuCooI6HAQ.iwbiySrpErXWgMAohCWpBy', LOCALTIMESTAMP, 'owner');
INSERT INTO security.app_user(is_active, password, registration_date_time, username) VALUES (true, '$2a$10$qzEKGj8Gnu4Xh.sV1xnTWuCooI6HAQ.iwbiySrpErXWgMAohCWpBy', LOCALTIMESTAMP, 'driver');
INSERT INTO security.app_user(is_active, password, registration_date_time, username) VALUES (true, '$2a$10$qzEKGj8Gnu4Xh.sV1xnTWuCooI6HAQ.iwbiySrpErXWgMAohCWpBy', LOCALTIMESTAMP, 'operator');

INSERT INTO security.user_authority(role, app_user_id) VALUES ('ADMIN', 1);
INSERT INTO security.user_authority(role, app_user_id) VALUES ('OWNER', 1);
INSERT INTO security.user_authority(role, app_user_id) VALUES ('DRIVER', 1);
INSERT INTO security.user_authority(role, app_user_id) VALUES ('OPERATOR', 1);

INSERT INTO security.user_authority(role, app_user_id) VALUES ('ADMIN', 2);
INSERT INTO security.user_authority(role, app_user_id) VALUES ('OWNER', 3);
INSERT INTO security.user_authority(role, app_user_id) VALUES ('DRIVER', 4);
INSERT INTO security.user_authority(role, app_user_id) VALUES ('OPERATOR', 5);