-- Add Multiple Projects assigned to the admin user and RoloDuck Company
INSERT INTO RD_PROJECT (project_name, project_description, created_by_user, company_id) VALUES ('Project Number 1',
'This is Project Number 1', (SELECT id FROM RD_USER WHERE user_name = 'Admin'), (SELECT id FROM RD_COMPANY WHERE
company_name = 'RoloDuck') );
INSERT INTO RD_PROJECT (project_name, project_description, created_by_user, company_id) VALUES ('Project Number 2',
'This is Project Number 2', (SELECT id FROM RD_USER WHERE user_name = 'Admin'), (SELECT id FROM RD_COMPANY WHERE
company_name = 'RoloDuck') );
INSERT INTO RD_PROJECT (project_name, project_description, created_by_user, company_id) VALUES ('Project Number 3',
'This is Project Number 3', (SELECT id FROM RD_USER WHERE user_name = 'Admin'), (SELECT id FROM RD_COMPANY WHERE
company_name = 'RoloDuck') );
INSERT INTO RD_PROJECT (project_name, project_description, created_by_user, company_id) VALUES ('Project Number 4',
'This is Project Number 4', (SELECT id FROM RD_USER WHERE user_name = 'Admin'), (SELECT id FROM RD_COMPANY WHERE
company_name = 'RoloDuck') );
INSERT INTO RD_PROJECT (project_name, project_description, created_by_user, company_id) VALUES ('Project Number 5',
'This is Project Number 5', (SELECT id FROM RD_USER WHERE user_name = 'Admin'), (SELECT id FROM RD_COMPANY WHERE
company_name = 'RoloDuck') );
INSERT INTO RD_PROJECT (project_name, project_description, created_by_user, company_id) VALUES ('Project Number 6',
'This is Project Number 6', (SELECT id FROM RD_USER WHERE user_name = 'Admin'), (SELECT id FROM RD_COMPANY WHERE
company_name = 'RoloDuck') );
INSERT INTO RD_PROJECT (project_name, project_description, created_by_user, company_id) VALUES ('Project Number 7',
'This is Project Number 7', (SELECT id FROM RD_USER WHERE user_name = 'Admin'), (SELECT id FROM RD_COMPANY WHERE
company_name = 'RoloDuck') );
INSERT INTO RD_PROJECT (project_name, project_description, created_by_user, company_id) VALUES ('Project Number 8',
'This is Project Number 8', (SELECT id FROM RD_USER WHERE user_name = 'Admin'), (SELECT id FROM RD_COMPANY WHERE
company_name = 'RoloDuck') );
INSERT INTO RD_PROJECT (project_name, project_description, created_by_user, company_id) VALUES ('Project Number 9',
'This is Project Number 9', (SELECT id FROM RD_USER WHERE user_name = 'Admin'), (SELECT id FROM RD_COMPANY WHERE
company_name = 'RoloDuck') );
INSERT INTO RD_PROJECT (project_name, project_description, created_by_user, company_id) VALUES ('Project Number 10',
'This is Project Number 10', (SELECT id FROM RD_USER WHERE user_name = 'Admin'), (SELECT id FROM RD_COMPANY WHERE
company_name = 'RoloDuck') );

-- Add multiple Partners and project associations to them
-- Partner and association with projects for Fandango
INSERT INTO RD_PARTNER (partner_name, partner_description, company_id) VALUES ('Fandango', 'This is Fandango',
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 1'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'Fandango'));
 INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 2'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'Fandango'));
 INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 3'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'Fandango'));

-- Partner and association with projects for Disney
INSERT INTO RD_PARTNER (partner_name, partner_description, company_id) VALUES ('Disney', 'This is Disney',
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 1'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'Disney'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 4'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'Disney'));

-- Partner and association with projects for Dick Nuts
INSERT INTO RD_PARTNER (partner_name, partner_description, company_id) VALUES ('Dick Nuts', 'This is Dick Nuts',
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 5'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'Dick Nuts'));

-- Partner and association with projects for HBO
INSERT INTO RD_PARTNER (partner_name, partner_description, company_id) VALUES ('HBO', 'This is HBO',
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 6'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'HBO'));
 INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 7'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'HBO'));

-- Partner and association with projects for ShowTime
INSERT INTO RD_PARTNER (partner_name, partner_description, company_id) VALUES ('ShowTime', 'This is ShowTime',
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 6'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'ShowTime'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 8'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'ShowTime'));

-- Partner and association with projects for Chicago White Sox
INSERT INTO RD_PARTNER (partner_name, partner_description, company_id) VALUES ('ChiWhiSox',
'This is the Chicago White Sox', (SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 9'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'ChiWhiSox'));

-- Partner and association with projects for Lions Gate
INSERT INTO RD_PARTNER (partner_name, partner_description, company_id) VALUES ('Lions Gate',
'This is Lions Gate',(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 10'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'Lions Gate'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 7'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'Lions Gate'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 4'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'Lions Gate'));
INSERT INTO RD_PROJECT_PARTNER_ASSOC (project_id, partner_id) VALUES ((SELECT id FROM RD_PROJECT WHERE project_name =
 'Project Number 1'), (SELECT id FROM RD_PARTNER WHERE partner_name = 'Lions Gate'));

-- Partner and no one wants to associate with projects for Michael Bay
INSERT INTO RD_PARTNER (partner_name, partner_description, company_id) VALUES ('Michael Bay',
'This is Michael bay', (SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));

-- Insert many test contacts
INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Carlos', 'Whatever', 'Guy', 'Carlos@Fandango.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Fandango'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Sheila', 'Harry', 'Gal', 'Sheila@Fandango.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Fandango'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Trent', 'Berry', 'Boss', 'Trent@Fandango.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Fandango'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));

INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Jenkins', 'Kenkins', 'Guy', 'Jenkins@Disney.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Disney'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Lisa', 'Ford', 'Gal', 'Lisa@Disney.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Disney'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Heath', 'Bell', 'Boss', 'Heath@Disney.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Disney'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));

INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Dick', 'Nuts', 'Founder', 'Dick@DickNuts.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Dick Nuts'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Sloan', 'Nuts', 'Gold Digger', 'Sloan@DickNuts.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Dick Nuts'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Eric', 'Jazz', 'CEO', 'Eric@DickNuts.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Dick Nuts'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));

INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Jerry', 'Seinfeld', 'A Funny Guy', 'Jerry@HBO.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'HBO'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Julie', 'Dryfus', 'Get Out!', 'Julie@HBO.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'HBO'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Larry', 'David', 'Awesome', 'Larry@HBO.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'HBO'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));

INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Jose', 'Abreu', 'Guy', 'Jose@ShowTime.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'ShowTime'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Steven', 'Harry', 'Steveo', 'Steven@ShowTime.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'ShowTime'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));
INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Alyssa', 'Fards', 'Boss', 'Alyssa@ShowTime.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'ShowTime'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));


INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Horatio', 'Mooremont', 'Janitor', 'janitor@janitor.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'ChiWhiSox'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));

INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Jackson', 'Furguson', 'Man Boy', 'furguson@chisox.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'ChiWhiSox'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));

INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Dayan', 'Viciedo', 'Batter', 'A@A.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'ChiWhiSox'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));

INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('Andy', 'Ertell', 'Lower Than a Janitor', 'Aertell@janitor.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Lions Gate'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));

INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('H', 'B', 'A', 'A@A.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Lions Gate'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));

INSERT INTO RD_CONTACT (contact_first_name, contact_last_name, contact_title, contact_email, contact_phone,
partner_id, company_id) VALUES ('A', 'SANS', 'A2', 'A2@A2.com', '555-555-5555',
(SELECT id FROM RD_PARTNER WHERE partner_name = 'Lions Gate'),
(SELECT id FROM RD_COMPANY WHERE company_name = 'RoloDuck'));

