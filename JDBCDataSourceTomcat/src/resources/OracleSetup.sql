CREATE TABLE "EMPLOYEE"
  (
    "EMPID"   NUMBER NOT NULL ENABLE,
    "NAME"    VARCHAR2(10 BYTE) DEFAULT NULL,
    PRIMARY KEY ("EMPID")
  );
 
Insert into EMPLOYEE (EMPID,NAME) values (10,'Ashish');
Insert into EMPLOYEE (EMPID,NAME) values (5,'Tripathi');
Insert into EMPLOYEE (EMPID,NAME) values (1,'AT');
commit;