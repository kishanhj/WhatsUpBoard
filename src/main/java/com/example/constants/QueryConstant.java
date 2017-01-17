package com.example.constants;

public interface QueryConstant {

     String INSERT_PROJECT_QUERY="insert into t_project(project_name) values(?)";

     String INSERT_FEEDBACK_QUERY="insert into t_feedback(EMPLOYEE_ID,EMPLOYEE_EMAIL_ID,EMPLOYEE_NAME,FEEDBACK_MONTH,PROJECT_ID_FEEDBACK_FK)values(?,?,?,?,?)";

     String GET_FEEDBACK_ID_QUERY="select FEEDBACK_ID from T_FEEDBACK where EMPLOYEE_ID=? and FEEDBACK_MONTH=? ";

     String GET_FEEDBACK_ID_MONTH_AND_PROJECT_QUERY="select FEEDBACK_ID from T_FEEDBACK where FEEDBACK_MONTH=?  and project_id_feedback_fk=?";

     String GET_FEEDBACK_ID_MONTH_QUERY="select FEEDBACK_ID from T_FEEDBACK where FEEDBACK_MONTH=? and project_id_feedback_fk=?";

     String GET_MONTH_LIST_QUERY="select distinct FEEDBACK_MONTH from T_FEEDBACK where project_id_feedback_fk=? ";

     String GET_FEEDBACK_PROJECT_QUERY="select PROJECT_ID_FEEDBACK_FK from T_FEEDBACK where EMPLOYEE_ID=? ";

     String GET_FEEDBACK_PROJECT_ID_QUERY="select PROJECT_ID_FEEDBACK_FK from T_FEEDBACK where feedback_ID=? ";

     String GET_EMPLOYEE_NAME_QUERY="select employee_name from T_FEEDBACK where feedback_ID=? ";

     String EMPLOYEEID_VALIDATOR_QUERY="select FEEDBACK_ID from T_FEEDBACK where EMPLOYEE_ID=? ";

     String GET_QUALITY_QUERY="select * from t_quality";

    String INSERT_QUALITY_FEEDBACK_QUERY="insert into t_feedback_quality(SATISFY_INDICATOR,COMMENT,FEEDBACK_ID_FK,QUALITY_ID_FK)values(?,?,?,?)";

    String DOES_QUALITY_FEEDBACK_EXIST_QUERY="Select feedback_qualityid from t_feedback_quality where feedback_id_fk=?";

    String GET_PROJECT_DETAILS_QUERY="select * from t_project";

    String GET_PROJECT_NAMES_QUERY="select project_name from t_project";

    String GET_PROJECT_QUERY="select * from t_project where project_name=?";

    String GET_PROJECT_NAME_QUERY="select project_name from t_project where project_id=?";

    String ADD_PROJECT_QUERY="insert into t_project(project_id,project_name) values(?,?)";

    String MAX_PROJECT_ID_QUERY="select max(project_id) from t_project";

    String GET_ALL_EMPLOYEE_QUERY="select * from t_employee";

    String GET_ADMIN_DETAILS_QUERY="select * from t_admin where admin_id=?";

    String ADD_ADMIN_QUERY="insert into t_admin(ADMIN_ID,PASSWORD,ADMIN_EMAIL_ID,IS_SUPER_ADMIN,PROJECT_ID_ADMIN_FK) values(?,?,?,?,?); ";

    String GET_ALL_ADMIN_DETAILS_QUERY="select * from t_admin";

    String ADMIN_AUTHENTICATION_QUERY="select * from t_admin where admin_id=? and password=?";

    String EMAIL_EXIST_QUERY="select * from t_admin where admin_email_id=? ";

    String PASSWORD_RESET_QUERY="update t_admin set password =? where admin_email_id=? ";

    String PASSWORD_CHANGE_QUERY="update t_admin set password =? where password=? and admin_id=? ";

    String GET_EMPLOYEE_QUERY="select * from t_employee where employee_id=?";

    String INSERT_EMPLOYEE_QUERY="insert into T_Employee(EMPLOYEE_ID ,EMPLOYEE_EMAIL_ID ,EMPLOYEE_NAME,PROJECT_ID_EMPLOYEE_FK )values(?,?,?,?);";

    String GET_EMPLOYEE_COUNT_QUERY="select count(*) from T_employee where project_id_employee_fk=?";

    String EMPLOYEE_EXISTS_QUERY="select * from T_employee where employee_id=?";

    String INSERT_CODE_QUERY="insert into t_link_codes(code_key,code_value)values(?,?)";

    String GET_CODE_QUERY="Select * from t_link_codes";

    String DELETE_CODE_QUERY="delete from t_link_codes where code_key=?";

    String GET_FEEDBACKS="select * from  t_feedback_quality where feedback_id_fk=?";

    String GET_QUALITY_FEEDBACKS="select * from  t_feedback_quality where feedback_id_fk=? and quality_id_fk=?";

    String GET_QUALITY_NAME="select quality_name from t_quality where quality_id=?";

    String GET_QUALITY_ID="select quality_id from t_quality where quality_name=?";
}
