/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2014/12/5 20:44:35                           */
/*==============================================================*/


drop table JBPM4_DEPLOYMENT cascade constraints;

drop table JBPM4_DEPLOYPROP cascade constraints;

drop table JBPM4_EXECUTION cascade constraints;

drop table JBPM4_EXT_BUSINESSROL_MAPPING cascade constraints;

drop table JBPM4_EXT_BUSINESS_ENTRUST cascade constraints;

drop table JBPM4_EXT_PROCESSROLE_DEF cascade constraints;

drop table JBPM4_EXT_PROCESS_DEF cascade constraints;

drop table JBPM4_HIST_ACTINST cascade constraints;

drop table JBPM4_HIST_DETAIL cascade constraints;

drop table JBPM4_HIST_PROCINST cascade constraints;

drop table JBPM4_HIST_TASK cascade constraints;

drop table JBPM4_HIST_VAR cascade constraints;

drop table JBPM4_ID_GROUP cascade constraints;

drop table JBPM4_ID_MEMBERSHIP cascade constraints;

drop table JBPM4_ID_USER cascade constraints;

drop table JBPM4_JOB cascade constraints;

drop table JBPM4_LOB cascade constraints;

drop table JBPM4_PARTICIPATION cascade constraints;

drop table JBPM4_PROPERTY cascade constraints;

drop table JBPM4_SWIMLANE cascade constraints;

drop table JBPM4_TASK cascade constraints;

drop table JBPM4_VARIABLE cascade constraints;

/*==============================================================*/
/* Table: JBPM4_DEPLOYMENT                                      */
/*==============================================================*/
create table JBPM4_DEPLOYMENT  (
   DBID_                NUMBER(19)                      not null,
   NAME_                CLOB,
   TIMESTAMP_           NUMBER(19),
   STATE_               VARCHAR2(255),
   constraint PK_JBPM4_DEPLOYMENT primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_DEPLOYPROP                                      */
/*==============================================================*/
create table JBPM4_DEPLOYPROP  (
   DBID_                NUMBER(19)                      not null,
   DEPLOYMENT_          NUMBER(19),
   OBJNAME_             VARCHAR2(255),
   KEY_                 VARCHAR2(255),
   STRINGVAL_           VARCHAR2(255),
   LONGVAL_             NUMBER(19),
   constraint PK_JBPM4_DEPLOYPROP primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_EXECUTION                                       */
/*==============================================================*/
create table JBPM4_EXECUTION  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               VARCHAR2(255),
   DBVERSION_           INTEGER,
   ACTIVITYNAME_        VARCHAR2(255),
   PROCDEFID_           VARCHAR2(255),
   HASVARS_             SMALLINT,
   NAME_                VARCHAR2(255),
   KEY_                 VARCHAR2(255),
   ID_                  VARCHAR2(255),
   STATE_               VARCHAR2(255),
   SUSPHISTSTATE_       VARCHAR2(255),
   PRIORITY_            INTEGER,
   HISACTINST_          NUMBER(19),
   PARENT_              NUMBER(19),
   INSTANCE_            NUMBER(19),
   SUPEREXEC_           NUMBER(19),
   SUBPROCINST_         NUMBER(19),
   PARENT_IDX_          INTEGER,
   constraint PK_JBPM4_EXECUTION primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_EXT_BUSINESSROL_MAPPING                         */
/*==============================================================*/
create table JBPM4_EXT_BUSINESSROL_MAPPING  (
   "id"                 NUMBER                          not null,
   "business_role"      VARCHAR2(50),
   "isdefault"          NUMBER(4),
   PROCESS_ROLE_DEF_ID  NUMBER(20),
   BUSINESS_ROLE_ID     VARCHAR2(50),
   constraint PK_JBPM4_EXT_BUSINESSROL_MAPPI primary key ("id")
);

comment on table JBPM4_EXT_BUSINESSROL_MAPPING is
'JBPM4_EXT_BUSINESSROL_MAPPING';

/*==============================================================*/
/* Table: JBPM4_EXT_BUSINESS_ENTRUST                            */
/*==============================================================*/
create table JBPM4_EXT_BUSINESS_ENTRUST  (
   "id"                 NUMBER                          not null,
   "consignor_role"     VARCHAR2(50),
   "consignee_role"     VARCHAR2(50),
   "process_key"        VARCHAR2(50),
   "begin_time"         DATE,
   "end_time"           DATE,
   constraint PK_JBPM4_EXT_BUSINESS_ENTRUST primary key ("id")
);

comment on table JBPM4_EXT_BUSINESS_ENTRUST is
'JBPM4_EXT_BUSINESS_ENTRUST';

/*==============================================================*/
/* Table: JBPM4_EXT_PROCESSROLE_DEF                             */
/*==============================================================*/
create table JBPM4_EXT_PROCESSROLE_DEF  (
   "id"                 NUMBER                          not null,
   "process_key"        VARCHAR2(50),
   "process_task"       VARCHAR2(50),
   "remark"             VARCHAR2(500),
   constraint PK_JBPM4_EXT_PROCESSROLE_DEF primary key ("id")
);

comment on table JBPM4_EXT_PROCESSROLE_DEF is
'JBPM4_EXT_PROCESSROLE_DEF';

comment on column JBPM4_EXT_PROCESSROLE_DEF."process_key" is
'发布流程时写入结点处理的角色';

/*==============================================================*/
/* Table: JBPM4_EXT_PROCESS_DEF                                 */
/*==============================================================*/
create table JBPM4_EXT_PROCESS_DEF  (
   "id"                 NUMBER                          not null,
   "process_zip"        VARCHAR2(50),
   "process_jpdl"       VARCHAR2(50),
   "process_png"        VARCHAR2(50),
   "process_key"        VARCHAR2(50),
   "process_name"       VARCHAR2(50),
   "process_state"      NUMBER(1),
   "process_deployid"   VARCHAR2(50),
   "process_createtime" DATE,
   constraint PK_JBPM4_EXT_PROCESS_DEF primary key ("id")
);

/*==============================================================*/
/* Table: JBPM4_HIST_ACTINST                                    */
/*==============================================================*/
create table JBPM4_HIST_ACTINST  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               VARCHAR2(255),
   DBVERSION_           INTEGER,
   HPROCI_              NUMBER(19),
   TYPE_                VARCHAR2(255),
   EXECUTION_           VARCHAR2(255),
   ACTIVITY_NAME_       VARCHAR2(255),
   START_               DATE,
   END_                 DATE,
   DURATION_            NUMBER(19),
   TRANSITION_          VARCHAR2(255),
   NEXTIDX_             INTEGER,
   HTASK_               NUMBER(19),
   constraint PK_JBPM4_HIST_ACTINST primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_HIST_DETAIL                                     */
/*==============================================================*/
create table JBPM4_HIST_DETAIL  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               VARCHAR2(255),
   DBVERSION_           INTEGER,
   USERID_              VARCHAR2(255),
   TIME_                DATE,
   HPROCI_              NUMBER(19),
   HPROCIIDX_           INTEGER,
   HACTI_               NUMBER(19),
   HACTIIDX_            INTEGER,
   HTASK_               NUMBER(19),
   HTASKIDX_            INTEGER,
   HVAR_                NUMBER(19),
   HVARIDX_             INTEGER,
   MESSAGE_             CLOB,
   OLD_STR_             VARCHAR2(255),
   NEW_STR_             VARCHAR2(255),
   OLD_INT_             INTEGER,
   NEW_INT_             INTEGER,
   OLD_TIME_            DATE,
   NEW_TIME_            DATE,
   PARENT_              NUMBER(19),
   PARENT_IDX_          INTEGER,
   constraint PK_JBPM4_HIST_DETAIL primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_HIST_PROCINST                                   */
/*==============================================================*/
create table JBPM4_HIST_PROCINST  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           INTEGER,
   ID_                  VARCHAR2(255),
   PROCDEFID_           VARCHAR2(255),
   KEY_                 VARCHAR2(255),
   START_               DATE,
   END_                 DATE,
   DURATION_            NUMBER(19),
   STATE_               VARCHAR2(255),
   ENDACTIVITY_         VARCHAR2(255),
   NEXTIDX_             INTEGER,
   constraint PK_JBPM4_HIST_PROCINST primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_HIST_TASK                                       */
/*==============================================================*/
create table JBPM4_HIST_TASK  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           INTEGER,
   EXECUTION_           VARCHAR2(255),
   OUTCOME_             VARCHAR2(255),
   ASSIGNEE_            VARCHAR2(255),
   PRIORITY_            INTEGER,
   STATE_               VARCHAR2(255),
   CREATE_              DATE,
   END_                 DATE,
   DURATION_            NUMBER(19),
   NEXTIDX_             INTEGER,
   SUPERTASK_           NUMBER(19),
   constraint PK_JBPM4_HIST_TASK primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_HIST_VAR                                        */
/*==============================================================*/
create table JBPM4_HIST_VAR  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           INTEGER,
   PROCINSTID_          VARCHAR2(255),
   EXECUTIONID_         VARCHAR2(255),
   VARNAME_             VARCHAR2(255),
   VALUE_               VARCHAR2(255),
   HPROCI_              NUMBER(19),
   HTASK_               NUMBER(19),
   constraint PK_JBPM4_HIST_VAR primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_ID_GROUP                                        */
/*==============================================================*/
create table JBPM4_ID_GROUP  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           INTEGER,
   ID_                  VARCHAR2(255),
   NAME_                VARCHAR2(255),
   TYPE_                VARCHAR2(255),
   PARENT_              NUMBER(19),
   constraint PK_JBPM4_ID_GROUP primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_ID_MEMBERSHIP                                   */
/*==============================================================*/
create table JBPM4_ID_MEMBERSHIP  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           INTEGER,
   USER_                NUMBER(19),
   GROUP_               NUMBER(19),
   NAME_                VARCHAR2(255),
   constraint PK_JBPM4_ID_MEMBERSHIP primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_ID_USER                                         */
/*==============================================================*/
create table JBPM4_ID_USER  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           INTEGER,
   ID_                  VARCHAR2(255),
   PASSWORD_            VARCHAR2(255),
   GIVENNAME_           VARCHAR2(255),
   FAMILYNAME_          VARCHAR2(255),
   BUSINESSEMAIL_       VARCHAR2(255),
   constraint PK_JBPM4_ID_USER primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_JOB                                             */
/*==============================================================*/
create table JBPM4_JOB  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               VARCHAR2(255),
   DBVERSION_           INTEGER,
   DUEDATE_             DATE,
   STATE_               VARCHAR2(255),
   ISEXCLUSIVE_         SMALLINT,
   LOCKOWNER_           VARCHAR2(255),
   LOCKEXPTIME_         DATE,
   EXCEPTION_           CLOB,
   RETRIES_             INTEGER,
   PROCESSINSTANCE_     NUMBER(19),
   EXECUTION_           NUMBER(19),
   CFG_                 NUMBER(19),
   SIGNAL_              VARCHAR2(255),
   EVENT_               VARCHAR2(255),
   REPEAT_              VARCHAR2(255),
   constraint PK_JBPM4_JOB primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_LOB                                             */
/*==============================================================*/
create table JBPM4_LOB  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           INTEGER,
   BLOB_VALUE_          BLOB,
   DEPLOYMENT_          NUMBER(19),
   NAME_                CLOB,
   constraint PK_JBPM4_LOB primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_PARTICIPATION                                   */
/*==============================================================*/
create table JBPM4_PARTICIPATION  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           INTEGER,
   GROUPID_             VARCHAR2(255),
   USERID_              VARCHAR2(255),
   TYPE_                VARCHAR2(255),
   TASK_                NUMBER(19),
   SWIMLANE_            NUMBER(19),
   constraint PK_JBPM4_PARTICIPATION primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_PROPERTY                                        */
/*==============================================================*/
create table JBPM4_PROPERTY  (
   KEY_                 VARCHAR2(255)                   not null,
   VERSION_             INTEGER,
   VALUE_               VARCHAR2(255),
   constraint PK_JBPM4_PROPERTY primary key (KEY_)
);

/*==============================================================*/
/* Table: JBPM4_SWIMLANE                                        */
/*==============================================================*/
create table JBPM4_SWIMLANE  (
   DBID_                NUMBER(19)                      not null,
   DBVERSION_           INTEGER,
   NAME_                VARCHAR2(255),
   ASSIGNEE_            VARCHAR2(255),
   EXECUTION_           NUMBER(19),
   constraint PK_JBPM4_SWIMLANE primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_TASK                                            */
/*==============================================================*/
create table JBPM4_TASK  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               CHAR(1),
   DBVERSION_           INTEGER,
   NAME_                VARCHAR2(255),
   DESCR_               CLOB,
   STATE_               VARCHAR2(255),
   SUSPHISTSTATE_       VARCHAR2(255),
   ASSIGNEE_            VARCHAR2(255),
   FORM_                VARCHAR2(255),
   PRIORITY_            INTEGER,
   CREATE_              DATE,
   DUEDATE_             DATE,
   PROGRESS_            INTEGER,
   SIGNALLING_          SMALLINT,
   EXECUTION_ID_        VARCHAR2(255),
   ACTIVITY_NAME_       VARCHAR2(255),
   HASVARS_             SMALLINT,
   SUPERTASK_           NUMBER(19),
   EXECUTION_           NUMBER(19),
   PROCINST_            NUMBER(19),
   SWIMLANE_            NUMBER(19),
   TASKDEFNAME_         VARCHAR2(255),
   constraint PK_JBPM4_TASK primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_VARIABLE                                        */
/*==============================================================*/
create table JBPM4_VARIABLE  (
   DBID_                NUMBER(19)                      not null,
   CLASS_               VARCHAR2(255),
   DBVERSION_           INTEGER,
   KEY_                 VARCHAR2(255),
   CONVERTER_           VARCHAR2(255),
   HIST_                SMALLINT,
   EXECUTION_           NUMBER(19),
   TASK_                NUMBER(19),
   LOB_                 NUMBER(19),
   DATE_VALUE_          DATE,
   DOUBLE_VALUE_        FLOAT,
   CLASSNAME_           VARCHAR2(255),
   LONG_VALUE__         NUMBER(19),
   STRING_VALUE_        VARCHAR2(2000),
   TEXT_VALUE_          CLOB,
   EXESYS_              NUMBER(19),
   constraint PK_JBPM4_VARIABLE primary key (DBID_)
);

