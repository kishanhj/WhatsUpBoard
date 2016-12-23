package com.example.constants;

public interface QueryConstant {

     String INSERT_PROJECT_QUERY="insert into t_project(project_name) values(?)";

     String INSERT_FEEDBACK_QUERY="insert into t_feedback(EMPLOYEE_ID,EMPLOYEE_EMAIL_ID,EMPLOYEE_NAME,FEEDBACK_MONTH,PROJECT_ID_FEEDBACK_FK)values(?,?,?,?,?)";

     String GET_FEEDBACK_ID_QUERY="select FEEDBACK_ID from T_FEEDBACK where EMPLOYEE_ID=? and FEEDBACK_MONTH=? ";

     String GET_FEEDBACK_ID_MONTH_QUERY="select FEEDBACK_ID from T_FEEDBACK where FEEDBACK_MONTH=? ";

     String GET_FEEDBACK_PROJECT_QUERY="select PROJECT_ID_FEEDBACK_FK from T_FEEDBACK where EMPLOYEE_ID=? ";

     String GET_EMPLOYEE_NAME_QUERY="select employee_name from T_FEEDBACK where feedback_ID=? ";

     String EMPLOYEEID_VALIDATOR_QUERY="select FEEDBACK_ID from T_FEEDBACK where EMPLOYEE_ID=? ";

     String GET_QUALITY_QUERY="select * from t_quality";

    String INSERT_QUALITY_FEEDBACK_QUERY="insert into t_feedback_quality(SATISFY_INDICATOR,COMMENT,FEEDBACK_ID_FK,QUALITY_ID_FK)values(?,?,?,?)";

    String DOES_QUALITY_FEEDBACK_EXIST_QUERY="Select feedback_qualityid from t_feedback_quality where feedback_id_fk=?";

    String GET_PROJECT_DETAILS_QUERY="select * from t_project";

    String GET_PROJECT_NAMES_QUERY="select project_name from t_project";

    String GET_PROJECT_QUERY="select * from t_project where project_name=?";

    String GET_PROJECT_NAME_QUERY="select project_name from t_project where project_id=?";

    String GET_ALL_EMPLOYEE_QUERY="select * from t_employee";

    String GET_EMPLOYEE_QUERY="select * from t_employee where employee_id=?";

    String INSERT_CODE_QUERY="insert into t_link_codes(code_key,code_value)values(?,?)";

    String GET_CODE_QUERY="Select * from t_link_codes";

    String GET_FEEDBACKS="select * from  t_feedback_quality where feedback_id_fk=?";

    String GET_QUALITY_FEEDBACKS="select * from  t_feedback_quality where feedback_id_fk=? and quality_id_fk=?";

    String GET_QUALITY_NAME="select quality_name from t_quality where quality_id=?";

    String GET_QUALITY_ID="select quality_id from t_quality where quality_name=?";
}
