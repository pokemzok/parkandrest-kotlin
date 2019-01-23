-- CLIENT_SECRET=password
INSERT INTO security.oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('uiClient', '$2a$10$qzEKGj8Gnu4Xh.sV1xnTWuCooI6HAQ.iwbiySrpErXWgMAohCWpBy', 'read,write',
	'password,authorization_code,refresh_token', null, null, 36000, 36000, null, true);

INSERT INTO security.oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('parkingmanagementClient', '$2a$10$qzEKGj8Gnu4Xh.sV1xnTWuCooI6HAQ.iwbiySrpErXWgMAohCWpBy', 'read,write',
	'password,authorization_code,refresh_token', null, null, 36000, 36000, null, true);