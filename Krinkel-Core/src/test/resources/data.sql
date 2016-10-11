DROP TABLE IF EXISTS registration_communication;
CREATE TABLE registration_communication (
`id` BIGINT,
`ad_number` VARCHAR(15),
`status` VARCHAR(20),
`communication_attempt` INT,
PRIMARY KEY(`id`)
);

INSERT INTO registration_communication VALUES ('1','12345','WAITING', '0');
INSERT INTO registration_communication VALUES ('2','22345','WAITING', '0');
INSERT INTO registration_communication VALUES ('3','32345','WAITING', '0');
INSERT INTO registration_communication VALUES ('4','42345','WAITING', '0');
INSERT INTO registration_communication VALUES ('5','52345','SENT', '0');
INSERT INTO registration_communication VALUES ('6','62345','FAILED', '0');
INSERT INTO registration_communication VALUES ('7','72345','SENT', '0');
INSERT INTO registration_communication VALUES ('8','82345','FAILED', '0');

