/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/12/2 13:13:44                           */
/*==============================================================*/


drop table if exists JBPM4_DEPLOYMENT;

drop table if exists JBPM4_DEPLOYPROP;

drop table if exists JBPM4_EXECUTION;

drop table if exists JBPM4_EXT_BUSINESSROL_MAPPING;

drop table if exists JBPM4_EXT_BUSINESS_ENTRUST;

drop table if exists JBPM4_EXT_PROCESSROLE_DEF;

drop table if exists JBPM4_EXT_PROCESS_DEF;

drop table if exists JBPM4_HIST_ACTINST;

drop table if exists JBPM4_HIST_DETAIL;

drop table if exists JBPM4_HIST_PROCINST;

drop table if exists JBPM4_HIST_TASK;

drop table if exists JBPM4_HIST_VAR;

drop table if exists JBPM4_ID_GROUP;

drop table if exists JBPM4_ID_MEMBERSHIP;

drop table if exists JBPM4_ID_USER;

drop table if exists JBPM4_JOB;

drop table if exists JBPM4_LOB;

drop table if exists JBPM4_PARTICIPATION;

drop table if exists JBPM4_PROPERTY;

drop table if exists JBPM4_SWIMLANE;

drop table if exists JBPM4_TASK;

drop table if exists JBPM4_VARIABLE;

/*==============================================================*/
/* Table: JBPM4_DEPLOYMENT                                      */
/*==============================================================*/
create table JBPM4_DEPLOYMENT
(
   DBID_                numeric(19) not null,
   NAME_                longtext,
   TIMESTAMP_           numeric(19),
   STATE_               varchar(255),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_DEPLOYPROP                                      */
/*==============================================================*/
create table JBPM4_DEPLOYPROP
(
   DBID_                numeric(19) not null,
   DEPLOYMENT_          numeric(19),
   OBJNAME_             varchar(255),
   KEY_                 varchar(255),
   STRINGVAL_           varchar(255),
   LONGVAL_             numeric(19),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_EXECUTION                                       */
/*==============================================================*/
create table JBPM4_EXECUTION
(
   DBID_                numeric(19) not null,
   CLASS_               varchar(255),
   DBVERSION_           int,
   ACTIVITYNAME_        varchar(255),
   PROCDEFID_           varchar(255),
   HASVARS_             tinyint,
   NAME_                varchar(255),
   KEY_                 varchar(255),
   ID_                  varchar(255),
   STATE_               varchar(255),
   SUSPHISTSTATE_       varchar(255),
   PRIORITY_            int,
   HISACTINST_          numeric(19),
   PARENT_              numeric(19),
   INSTANCE_            numeric(19),
   SUPEREXEC_           numeric(19),
   SUBPROCINST_         numeric(19),
   PARENT_IDX_          int,
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_EXT_BUSINESSROL_MAPPING                         */
/*==============================================================*/
create table JBPM4_EXT_BUSINESSROL_MAPPING
(
   id                   numeric not null,
   process_key          varchar(50) comment '��������ʱд���㴦��Ľ�ɫ',
   process_task         varchar(50),
   business_role        varchar(50),
   isdefault            numeric,
   primary key (id)
);

alter table JBPM4_EXT_BUSINESSROL_MAPPING comment 'JBPM4_EXT_BUSINESSROL_MAPPING';

/*==============================================================*/
/* Table: JBPM4_EXT_BUSINESS_ENTRUST                            */
/*==============================================================*/
create table JBPM4_EXT_BUSINESS_ENTRUST
(
   id                   numeric not null,
   consignor_role       varchar(50),
   consignee_role       varchar(50),
   process_key          varchar(50),
   begin_time           datetime,
   end_time             datetime,
   primary key (id)
);

alter table JBPM4_EXT_BUSINESS_ENTRUST comment 'JBPM4_EXT_BUSINESS_ENTRUST';

/*==============================================================*/
/* Table: JBPM4_EXT_PROCESSROLE_DEF                             */
/*==============================================================*/
create table JBPM4_EXT_PROCESSROLE_DEF
(
   id                   numeric not null,
   process_key          varchar(50) comment '��������ʱд���㴦��Ľ�ɫ',
   process_task         varchar(50),
   remark               varchar(500),
   primary key (id)
);

alter table JBPM4_EXT_PROCESSROLE_DEF comment 'JBPM4_EXT_PROCESSROLE_DEF';

/*==============================================================*/
/* Table: JBPM4_EXT_PROCESS_DEF                                 */
/*==============================================================*/
create table JBPM4_EXT_PROCESS_DEF
(
   id                   numeric not null,
   process_zip          varchar(50),
   process_jpdl         varchar(50),
   process_png          varchar(50),
   process_key          varchar(50),
   process_name         varchar(50),
   process_state        numeric(1),
   process_deployid     varchar(50),
   process_createtime   datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: JBPM4_HIST_ACTINST                                    */
/*==============================================================*/
create table JBPM4_HIST_ACTINST
(
   DBID_                numeric(19) not null,
   CLASS_               varchar(255),
   DBVERSION_           int,
   HPROCI_              numeric(19),
   TYPE_                varchar(255),
   EXECUTION_           varchar(255),
   ACTIVITY_NAME_       varchar(255),
   START_               datetime,
   END_                 datetime,
   DURATION_            numeric(19),
   TRANSITION_          varchar(255),
   NEXTIDX_             int,
   HTASK_               numeric(19),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_HIST_DETAIL                                     */
/*==============================================================*/
create table JBPM4_HIST_DETAIL
(
   DBID_                numeric(19) not null,
   CLASS_               varchar(255),
   DBVERSION_           int,
   USERID_              varchar(255),
   TIME_                datetime,
   HPROCI_              numeric(19),
   HPROCIIDX_           int,
   HACTI_               numeric(19),
   HACTIIDX_            int,
   HTASK_               numeric(19),
   HTASKIDX_            int,
   HVAR_                numeric(19),
   HVARIDX_             int,
   MESSAGE_             longtext,
   OLD_STR_             varchar(255),
   NEW_STR_             varchar(255),
   OLD_INT_             int,
   NEW_INT_             int,
   OLD_TIME_            datetime,
   NEW_TIME_            datetime,
   PARENT_              numeric(19),
   PARENT_IDX_          int,
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_HIST_PROCINST                                   */
/*==============================================================*/
create table JBPM4_HIST_PROCINST
(
   DBID_                numeric(19) not null,
   DBVERSION_           int,
   ID_                  varchar(255),
   PROCDEFID_           varchar(255),
   KEY_                 varchar(255),
   START_               datetime,
   END_                 datetime,
   DURATION_            numeric(19),
   STATE_               varchar(255),
   ENDACTIVITY_         varchar(255),
   NEXTIDX_             int,
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_HIST_TASK                                       */
/*==============================================================*/
create table JBPM4_HIST_TASK
(
   DBID_                numeric(19) not null,
   DBVERSION_           int,
   EXECUTION_           varchar(255),
   OUTCOME_             varchar(255),
   ASSIGNEE_            varchar(255),
   PRIORITY_            int,
   STATE_               varchar(255),
   CREATE_              datetime,
   END_                 datetime,
   DURATION_            numeric(19),
   NEXTIDX_             int,
   SUPERTASK_           numeric(19),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_HIST_VAR                                        */
/*==============================================================*/
create table JBPM4_HIST_VAR
(
   DBID_                numeric(19) not null,
   DBVERSION_           int,
   PROCINSTID_          varchar(255),
   EXECUTIONID_         varchar(255),
   VARNAME_             varchar(255),
   VALUE_               varchar(255),
   HPROCI_              numeric(19),
   HTASK_               numeric(19),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_ID_GROUP                                        */
/*==============================================================*/
create table JBPM4_ID_GROUP
(
   DBID_                numeric(19) not null,
   DBVERSION_           int,
   ID_                  varchar(255),
   NAME_                varchar(255),
   TYPE_                varchar(255),
   PARENT_              numeric(19),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_ID_MEMBERSHIP                                   */
/*==============================================================*/
create table JBPM4_ID_MEMBERSHIP
(
   DBID_                numeric(19) not null,
   DBVERSION_           int,
   USER_                numeric(19),
   GROUP_               numeric(19),
   NAME_                varchar(255),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_ID_USER                                         */
/*==============================================================*/
create table JBPM4_ID_USER
(
   DBID_                numeric(19) not null,
   DBVERSION_           int,
   ID_                  varchar(255),
   PASSWORD_            varchar(255),
   GIVENNAME_           varchar(255),
   FAMILYNAME_          varchar(255),
   BUSINESSEMAIL_       varchar(255),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_JOB                                             */
/*==============================================================*/
create table JBPM4_JOB
(
   DBID_                numeric(19) not null,
   CLASS_               varchar(255),
   DBVERSION_           int,
   DUEDATE_             datetime,
   STATE_               varchar(255),
   ISEXCLUSIVE_         tinyint,
   LOCKOWNER_           varchar(255),
   LOCKEXPTIME_         datetime,
   EXCEPTION_           longtext,
   RETRIES_             int,
   PROCESSINSTANCE_     numeric(19),
   EXECUTION_           numeric(19),
   CFG_                 numeric(19),
   SIGNAL_              varchar(255),
   EVENT_               varchar(255),
   REPEAT_              varchar(255),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_LOB                                             */
/*==============================================================*/
create table JBPM4_LOB
(
   DBID_                numeric(19) not null,
   DBVERSION_           int,
   BLOB_VALUE_          longblob,
   DEPLOYMENT_          numeric(19),
   NAME_                longtext,
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_PARTICIPATION                                   */
/*==============================================================*/
create table JBPM4_PARTICIPATION
(
   DBID_                numeric(19) not null,
   DBVERSION_           int,
   GROUPID_             varchar(255),
   USERID_              varchar(255),
   TYPE_                varchar(255),
   TASK_                numeric(19),
   SWIMLANE_            numeric(19),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_PROPERTY                                        */
/*==============================================================*/
create table JBPM4_PROPERTY
(
   KEY_                 varchar(255) not null,
   VERSION_             int,
   VALUE_               varchar(255),
   primary key (KEY_)
);

/*==============================================================*/
/* Table: JBPM4_SWIMLANE                                        */
/*==============================================================*/
create table JBPM4_SWIMLANE
(
   DBID_                numeric(19) not null,
   DBVERSION_           int,
   NAME_                varchar(255),
   ASSIGNEE_            varchar(255),
   EXECUTION_           numeric(19),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_TASK                                            */
/*==============================================================*/
create table JBPM4_TASK
(
   DBID_                numeric(19) not null,
   CLASS_               char(1),
   DBVERSION_           int,
   NAME_                varchar(255),
   DESCR_               longtext,
   STATE_               varchar(255),
   SUSPHISTSTATE_       varchar(255),
   ASSIGNEE_            varchar(255),
   FORM_                varchar(255),
   PRIORITY_            int,
   CREATE_              datetime,
   DUEDATE_             datetime,
   PROGRESS_            int,
   SIGNALLING_          tinyint,
   EXECUTION_ID_        varchar(255),
   ACTIVITY_NAME_       varchar(255),
   HASVARS_             tinyint,
   SUPERTASK_           numeric(19),
   EXECUTION_           numeric(19),
   PROCINST_            numeric(19),
   SWIMLANE_            numeric(19),
   TASKDEFNAME_         varchar(255),
   primary key (DBID_)
);

/*==============================================================*/
/* Table: JBPM4_VARIABLE                                        */
/*==============================================================*/
create table JBPM4_VARIABLE
(
   DBID_                numeric(19) not null,
   CLASS_               varchar(255),
   DBVERSION_           int,
   KEY_                 varchar(255),
   CONVERTER_           varchar(255),
   HIST_                tinyint,
   EXECUTION_           numeric(19),
   TASK_                numeric(19),
   LOB_                 numeric(19),
   DATE_VALUE_          datetime,
   DOUBLE_VALUE_        float,
   CLASSNAME_           varchar(255),
   ColumnLONG_VALUE__13 numeric(19),
   STRING_VALUE_        varchar(2000),
   TEXT_VALUE_          longtext,
   EXESYS_              numeric(19),
   primary key (DBID_)
);

