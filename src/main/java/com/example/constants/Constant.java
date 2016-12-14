package com.example.constants;

public interface Constant {


     String STRING_VALIDATOR="The name must have 2-15 characters length";

     String LEADERSHIP_TOUCH="LeaderShip Touch";
     String COMMUNICATION="Communication";
     String TIMELY_RECOGNITION ="Timely Recognition";
     String LEARNING="Learning";
     String FEEDFORWARD="Feed Forward";
     String HR_RESPONSIVENESS="HR Responsiveness";

     String INSERT_PROJECT_QUERY="insert into t_project(project_name) values(?)";

     String INSERT_FEEDBACK_QUERY="insert into t_feedback(EMPLOYEE_ID,EMPLOYEE_EMAIL_ID,EMPLOYEE_NAME,FEEDBACK_MONTH,PROJECT_ID_FEEDBACK_FK)values(?,?,?,?,?)";

     String GET_FEEDBACK_ID_QUERY="select FEEDBACK_ID from T_FEEDBACK where EMPLOYEE_ID=? and FEEDBACK_MONTH=? ";

     String GET_FEEDBACK_PROJECT_QUERY="select PROJECT_ID_FEEDBACK_FK from T_FEEDBACK where EMPLOYEE_ID=? ";

     String EMPLOYEEID_VALIDATOR_QUERY="select FEEDBACK_ID from T_FEEDBACK where EMPLOYEE_ID=? ";

     String GET_QUALITY_QUERY="select * from t_quality";

    String INSERT_QUALITY_FEEDBACK_QUERY="insert into t_feedback_quality(SATISFY_INDICATOR,COMMENT,FEEDBACK_ID_FK,QUALITY_ID_FK)values(?,?,?,?)";

    String DOES_QUALITY_FEEDBACK_EXIST_QUERY="Select feedback_qualityid from t_feedback_quality where feedback_id_fk=?";

    String GET_PROJECT_DETAILS_QUERY="select * from t_project";

    String GET_PROJECT_NAMES_QUERY="select project_name from t_project";

    String GET_PROJECT_QUERY="select * from t_project where project_name=?";


}
