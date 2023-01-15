package Model;

import javax.swing.*;
import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

public class DatabaseIntegration {

    private final Scanner input;
    //SQL Variables
    private ResultSet resultSet;
    private Connection connectObj;
    private Statement state;
    private PreparedStatement pState;

    //Question variables.
    private int questionID;
    private int answerID;

    public DatabaseIntegration() throws SQLException, ClassNotFoundException {
        input = new Scanner(System.in);
        resultSet = null;
        connectObj = null;
        connectToSql();
    }

    public void connectToSql() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbUrl = "jdbc:mysql://localhost:3306/questions";
            connectObj = DriverManager.getConnection(dbUrl, "root", "1234");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\nSQL State: " + e.getSQLState() + "\nVendor Error: " + e.getErrorCode());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error connecting to the SQL server.\nClass not found.");
        }
    }

    public void createNewTables() throws SQLException {
        //Use schema/DB named 'questions'
        pState = connectObj.prepareStatement("use questions;");
        pState.executeUpdate();
        //Create tables one by one
        pState = connectObj.prepareStatement("CREATE TABLE OpenQuestions ( questionID INT NOT NULL, question VARCHAR(255), answer VARCHAR(255), PRIMARY KEY(questionID) ) ENGINE = InnoDB;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("CREATE TABLE AmericanQuestions ( questionID INT NOT NULL, question VARCHAR(255), PRIMARY KEY(questionID) ) ENGINE = InnoDB;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("CREATE TABLE AmericanAnswers ( answerID INT NOT NULL AUTO_INCREMENT, answer VARCHAR(255), isTrue VARCHAR(255), PRIMARY KEY(answerID) ) ENGINE = InnoDB;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("CREATE TABLE AmericanQuestions_AmericanAnswers ( questionID INT NOT NULL, answerID INT NOT NULL AUTO_INCREMENT, PRIMARY KEY (questionID, answerID), FOREIGN KEY (questionID) " +
                "REFERENCES AmericanQuestions(questionID), FOREIGN KEY (answerID) REFERENCES AmericanAnswers(answerID) ) ENGINE = InnoDB;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("CREATE TABLE exams ( examID INT NOT NULL AUTO_INCREMENT, americanQuestionsID INT NOT NULL, openQuestionsID INT NOT NULL, FOREIGN KEY (americanQuestionsID) REFERENCES AmericanQuestions(questionID)," +
                "FOREIGN KEY (openQuestionsID) REFERENCES OpenQuestions(questionID), PRIMARY KEY (examID) ) ENGINE = InnoDB;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("CREATE TABLE exams_americanquestions ( examID INT NOT NULL AUTO_INCREMENT, americanQuestionsID INT NOT NULL, PRIMARY KEY (examID, americanQuestionsID)," +
                "FOREIGN KEY (examID) REFERENCES exams(examID), FOREIGN KEY (americanQuestionsID) REFERENCES americanquestions(questionID) ) ENGINE = InnoDB;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("CREATE TABLE exams_openquestions ( examID INT NOT NULL AUTO_INCREMENT, openQuestionsID INT NOT NULL, PRIMARY KEY (examID, openQuestionsID)," +
                "FOREIGN KEY (examID) REFERENCES exams(examID), FOREIGN KEY (openQuestionsID) REFERENCES openquestions(questionID) ) ENGINE = InnoDB;");
        pState.executeUpdate();
        JOptionPane.showMessageDialog(null, "Tables Created");
    }

    public void dropOldTables() throws SQLException {
        //Use schema/DB named 'questions'
        pState = connectObj.prepareStatement("use questions;");
        pState.executeUpdate();
        //Drop all tables.
        pState = connectObj.prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("drop table if exists openquestions;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("drop table if exists americanquestions;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("drop table if exists americananswers;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("drop table if exists americanQuestions_americanAnswers;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("drop table if exists exams;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("drop table if exists exams_americanquestions;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("drop table if exists exams_openquestions;");
        pState.executeUpdate();
        pState = connectObj.prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
        pState.executeUpdate();
        JOptionPane.showMessageDialog(null, "Tables Dropped");
    }

    public boolean checkIfConnectionIsUp() throws SQLException {
        return connectObj.isValid(2);
    }

    public void closeConnectionInstant() throws SQLException {
        connectObj.close();
    }
    public void closeConnectionChoice() throws SQLException {
        System.out.println("Do you want to close the connection?\nPress 1 for yes.\n");
        int choice = input.nextInt();
        if (choice == 1) {
            connectObj.close();
        }
        else {
            System.out.println("Did not close connection.\nConnection still open.");
        }
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public Connection getConnection() {
        return connectObj;
    }

    public void addAmericanQuestionToDatabase(AmericanQ aQuest) throws SQLException {
                String query = "INSERT INTO AmericanQuestions (questionID, question)" + "VALUES (?, ?)";
                pState = connectObj.prepareStatement(query);
                pState.setInt(1, aQuest.getQuestionNumber());
                pState.setString(2, aQuest.getQuestion());
                pState.executeUpdate();
    }

    public void addAmericanAnswersToDatabase(AmericanQ aQuest, AmericanAnswers aN) throws SQLException {
        connectObj.setAutoCommit(false);
        String query = "INSERT INTO AmericanAnswers (answer, isTrue) VALUES (?, ?)";
        PreparedStatement pState = connectObj.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pState.setString(1, aN.getAnswer());
        pState.setString(2, aN.getBooleanAnswer());
        pState.executeUpdate();

        ResultSet generatedKeys = pState.getGeneratedKeys();
        int answerID = -1;
        if (generatedKeys.next()) {
            answerID = generatedKeys.getInt(1);
        }
        String addAnswerQuery = "INSERT INTO americanquestions_americananswers (questionID, answerID) VALUES (?, ?)";
        pState = connectObj.prepareStatement(addAnswerQuery);
        pState.setInt(1, aQuest.getQuestionNumber());
        pState.setInt(2, answerID);
        pState.executeUpdate();
        connectObj.commit();
    }

    public void addOpenQuestionToDatabase(OpenQ qQuest) throws SQLException {
        String query = "INSERT INTO OpenQuestions (questionID, question, answer) " + "VALUES (?, ?, ?)";
        pState = connectObj.prepareStatement(query);
        pState.setInt(1, qQuest.getQuestionNumber());
        pState.setString(2, qQuest.getQuestion());
        pState.setString(3, qQuest.getAnswer());
        pState.executeUpdate();
    }

    public void deleteAmericanQuestionFromDatabase(AmericanQ aQuest) throws SQLException {
        String query = "SELECT * FROM americanquestions WHERE question=?";
        pState = connectObj.prepareStatement(query);
        pState.setString(1, aQuest.getQuestion());
        resultSet = pState.executeQuery();
        int id = 0;
        while (resultSet.next()) {
            id = resultSet.getInt("questionID");
            removeAmericanAnswerFromQuestion(id);
            removeAmericanQuestion(id);
        }
    }

    public void removeAmericanQuestion(int questionId) throws SQLException {
        pState = null;
        resultSet.getInt(1);
        try {
            pState = connectObj.prepareStatement("DELETE FROM americanquestions WHERE questionID = ?");
            pState.setInt(1, questionId);
            pState.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\nSQL State: " + e.getSQLState() + "\nVendor Error: " + e.getErrorCode());
        } finally {
            if (pState != null) {
                pState.close();
            }
        }
    }

    public void removeAmericanAnswerFromQuestion(int questionId) throws SQLException {
        pState = null;
        resultSet.getInt(1);
        try {
            pState = connectObj.prepareStatement("DELETE FROM AmericanQuestions_AmericanAnswers WHERE questionID = ?");
            pState.setInt(1, questionId);
            pState.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\nSQL State: " + e.getSQLState() + "\nVendor Error: " + e.getErrorCode());
        } finally {
            if (pState != null) {
                pState.close();
            }
        }
    }

    public void deleteAmericanAnswerFromAQuestion(AmericanQ aQuest, AmericanAnswers aW) throws SQLException {
        String init = "use questions;";
        pState = connectObj.prepareStatement(init);
        String getAnswerId = "SELECT americananswers.answerID " +
                "FROM americananswers WHERE answer='"+aW.getAnswer()+"';";
        try (ResultSet resultSet = pState.executeQuery(getAnswerId)) {
            while (resultSet.next()) {
                pState = connectObj.prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
                pState.executeUpdate();
                int test = resultSet.getInt(1);
                System.out.println(test);
                String query = "DELETE FROM AmericanQuestions_AmericanAnswers WHERE (answerID='" + test + "' and questionID='" + aQuest.getQuestionNumber() + "');";
                //String query = "DELETE FROM AmericanQuestions_AmericanAnswers WHERE (answerID='" + test + "');";
                pState = connectObj.prepareStatement(query);
                pState.executeUpdate();

                String secondQuery = "DELETE FROM AmericanAnswers WHERE (answerID='" + test + "');";
                pState = connectObj.prepareStatement(secondQuery);
                pState.executeUpdate();
                pState = connectObj.prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
                pState.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\nSQL State: " + e.getSQLState() + "\nVendor Error: " + e.getErrorCode());
        }
    }

    public void deleteOpenQuestionFromDatabase(OpenQ qQuest) throws SQLException {
        String query = "SELECT * FROM openquestions WHERE question=? and answer=?";
        pState = connectObj.prepareStatement(query);
        pState.setString(1, qQuest.getQuestion());
        pState.setString(2, qQuest.getAnswer());
        resultSet = pState.executeQuery();
        int id = 0;
        while (resultSet.next()) {
            id = resultSet.getInt("questionID");
            removeOpenQuestion(id);
        }
    }

    public void removeOpenQuestion(int questionId) throws SQLException {
        pState = null;
        resultSet.getInt(1);
        try {
            pState = connectObj.prepareStatement("DELETE FROM openquestions WHERE questionID = ?");
            pState.setInt(1, questionId);
            pState.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\nSQL State: " + e.getSQLState() + "\nVendor Error: " + e.getErrorCode());
        } finally {
            if (pState != null) {
                pState.close();
            }
        }
    }

    public void updateAmericanQuestionInDatabase(String theQuestion, int questNum) throws SQLException {
        String query = "UPDATE AmericanQuestions SET question= '" + theQuestion + "' WHERE questionID='" + questNum + "';";
        pState = connectObj.prepareStatement(query);
        pState.execute();
    }

    public void updateOpenQuestionInDatabase(String theQuestion, int questNum) throws SQLException {
        String query = "UPDATE OpenQuestions SET question= '" + theQuestion + "' WHERE questionID='" + questNum + "';";
        pState = connectObj.prepareStatement(query);
        pState.execute();
    }

    public void updateOpenAnswerInDatabase(String theAns, int questNum) throws SQLException {
        String query = "UPDATE OpenQuestions SET answer= '" + theAns + "' WHERE questionID='" + questNum + "';";
        pState = connectObj.prepareStatement(query);
        pState.execute();
    }

    public void updateAmericanAnswerInDatabase(String theAnswer, int ansNum) throws SQLException {
        String query = "UPDATE AmericanAnswers SET answer= '" + theAnswer + "' WHERE answerID='" + ansNum + "';";
        pState = connectObj.prepareStatement(query);
        pState.execute();
    }

    public void saveManualExamArrayToDatabase(ArrayList<Question> manualExamArray) throws SQLException {
        pState = connectObj.prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
        pState.executeUpdate();
        connectObj.setAutoCommit(false);
        String insertExam = "INSERT INTO exams (openQuestionsID, americanQuestionsID) VALUES (?,?)";
        pState = connectObj.prepareStatement(insertExam, Statement.RETURN_GENERATED_KEYS);
        for (Question q : manualExamArray) {
            if (q instanceof OpenQ) {
                pState.setInt(1, q.getQuestionNumber());
                pState.setInt(2, 0);
            } else if (q instanceof AmericanQ) {
                pState.setInt(1, 0);
                pState.setInt(2, q.getQuestionNumber());
            }
        }
        pState.executeUpdate();
        ResultSet generatedKeys = pState.getGeneratedKeys();
        if (generatedKeys.next()) {
            int examID = generatedKeys.getInt(1);
            for (Question q : manualExamArray) {
                if (q instanceof OpenQ) {
                    String insertOpenQuestions = "INSERT INTO Exams_OpenQuestions (examID, openQuestionsID) VALUES (?, ?)";
                    pState = connectObj.prepareStatement(insertOpenQuestions);
                    pState.setInt(1, examID);
                    pState.setInt(2, q.getQuestionNumber());
                    pState.executeUpdate();
                } else if (q instanceof AmericanQ) {
                    String insertAmericanQuestions = "INSERT INTO Exams_AmericanQuestions (examID, americanQuestionsID) VALUES (?, ?)";
                    pState = connectObj.prepareStatement(insertAmericanQuestions);
                    pState.setInt(1, examID);
                    pState.setInt(2, q.getQuestionNumber());
                    pState.executeUpdate();
                }
            }
        }
        pState = connectObj.prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
        pState.executeUpdate();
        connectObj.commit();
    }
}
