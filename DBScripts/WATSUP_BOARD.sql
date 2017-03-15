DROP DATABASE WHATSUP_SURVEY_DB;
CREATE DATABASE WHATSUP_SURVEY_DB;
USE WHATSUP_SURVEY_DB;


CREATE TABLE T_PROJECT(
PROJECT_ID INT NOT NULL AUTO_INCREMENT,
PROJECT_NAME  VARCHAR(100) UNIQUE NOT NULL,
ACTIVE_STATUS BOOLEAN NOT NULL DEFAULT 1,
PRIMARY KEY(PROJECT_ID));


CREATE TABLE T_QUALITY(
QUALITY_ID INT NOT NULL AUTO_INCREMENT,
QUALITY_NAME VARCHAR(100) NOT NULL,
QUALITY_DESCRIPTION VARCHAR(400) NOT NULL,
PRIMARY KEY(QUALITY_ID ));


CREATE TABLE T_ADMIN(
ACTIVE_STATUS BOOLEAN NOT NULL DEFAULT 1,
ADMIN_ID VARCHAR(100) NOT NULL ,
PASSWORD VARCHAR(20) NOT NULL,
ADMIN_EMAIL_ID VARCHAR(100) NOT NULL,
IS_SUPER_ADMIN BOOLEAN NOT NULL DEFAULT 0,
PROJECT_ID_ADMIN_FK INT NOT NULL,
PRIMARY KEY(ADMIN_ID), 
FOREIGN KEY (PROJECT_ID_ADMIN_FK) REFERENCES T_PROJECT(PROJECT_ID));

CREATE TABLE T_FEEDBACK(
FEEDBACK_ID INT NOT NULL AUTO_INCREMENT,
EMPLOYEE_ID VARCHAR(100) NOT NULL,
EMPLOYEE_EMAIL_ID VARCHAR(100) NOT NULL,
EMPLOYEE_NAME VARCHAR(100) NOT NULL,
FEEDBACK_MONTH VARCHAR(100) NOT NULL,
PROJECT_ID_FEEDBACK_FK INT NOT NULL,
PRIMARY KEY(FEEDBACK_ID),
FOREIGN KEY (PROJECT_ID_FEEDBACK_FK) REFERENCES T_PROJECT(PROJECT_ID));


CREATE TABLE T_FEEDBACK_QUALITY(
FEEDBACK_QUALITYID INT NOT NULL AUTO_INCREMENT,
SATISFY_INDICATOR BOOLEAN NOT NULL DEFAULT 1,
COMMENT VARCHAR(400),
FEEDBACK_ID_FK INT NOT NULL,
QUALITY_ID_FK INT NOT NULL,
PRIMARY KEY(FEEDBACK_QUALITYID),
FOREIGN KEY (FEEDBACK_ID_FK) REFERENCES T_FEEDBACK(FEEDBACK_ID),
FOREIGN KEY (QUALITY_ID_FK) REFERENCES T_QUALITY(QUALITY_ID));


CREATE TABLE T_EMPLOYEE(
EMPLOYEE_ID VARCHAR(100) NOT NULL,
EMPLOYEE_EMAIL_ID VARCHAR(100) NOT NULL,
EMPLOYEE_NAME VARCHAR(100) NOT NULL,
PROJECT_ID_EMPLOYEE_FK INT NOT NULL,
IS_ACTIVE BOOLEAN DEFAULT TRUE,
PRIMARY KEY(EMPLOYEE_ID),
FOREIGN KEY (PROJECT_ID_EMPLOYEE_FK) REFERENCES T_PROJECT(PROJECT_ID));


CREATE TABLE T_LINK_CODES(
CODE_KEY VARCHAR(100) NOT NULL,
CODE_VALUE VARCHAR(300) NOT NULL);


INSERT INTO T_PROJECT(PROJECT_NAME) VALUES ('PUMA');

INSERT INTO T_QUALITY(QUALITY_NAME,QUALITY_DESCRIPTION) VALUES('Leadership Touch','Is leadership able to connect with employees? Are they meeting employees on regular basis? Are employees finding their leadership approachable?');
INSERT INTO T_QUALITY(QUALITY_NAME,QUALITY_DESCRIPTION) VALUES('Communication','Are updates related to organisational growth & employee Initiatives communicated to employees regularly?');
INSERT INTO T_QUALITY(QUALITY_NAME,QUALITY_DESCRIPTION) VALUES('Timely Recognition','Are employees feeling that their work is being recognised timely?');
INSERT INTO T_QUALITY(QUALITY_NAME,QUALITY_DESCRIPTION) VALUES('Learning','Are employees aware of what is expected out of them? Does the employee know the skills and experience required to achieve his future goals?');
INSERT INTO T_QUALITY(QUALITY_NAME,QUALITY_DESCRIPTION) VALUES('Feed Forward','Are the employees learning on the job? Are they enough learning opportunities provided  within the scope of work and outside?');
INSERT INTO T_QUALITY(QUALITY_NAME,QUALITY_DESCRIPTION) VALUES('HR Responsiveness','Are employees receiving responses to their queries on time? Are the HRs accessible when required by the employees?');

insert into T_Employee(EMPLOYEE_ID ,EMPLOYEE_EMAIL_ID ,EMPLOYEE_NAME,PROJECT_ID_EMPLOYEE_FK )values('000000','Admin@brillio.com','Admin',1);

insert into t_admin(admin_ID,password,admin_email_id,is_super_admin,project_id_admin_fk)values('000000','1314170504','Admin@brillio.com',true,1) 