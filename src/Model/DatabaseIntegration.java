package Model;

import javax.swing.*;
import java.util.Scanner;
import java.sql.*;

public class DatabaseIntegration {

    private Scanner input;
    //SQL Variables
    private ResultSet resultSet;
    private Connection connectObj;
    private Statement state;
    private PreparedStatement pState;

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
        state = connectObj.createStatement();
        dropOldTables();
        createNewTables();
    }

    private void createNewTables() throws SQLException {
        //Use schema/DB named 'questions'
        pState = connectObj.prepareStatement("use questions;");
        pState.execute();
        //Create tables one by one
        pState = connectObj.prepareStatement("CREATE TABLE OpenQuestions ( questionID INT NOT NULL, question VARCHAR(100), answer VARCHAR(100), PRIMARY KEY(questionID) ) ENGINE = InnoDB;");
        pState.execute();
        pState = connectObj.prepareStatement("CREATE TABLE AmericanQuestions ( questionID INT NOT NULL, answerAmount INT NOT NULL, question VARCHAR(100), PRIMARY KEY(questionID) ) ENGINE = InnoDB;");
        pState.execute();
        pState = connectObj.prepareStatement("CREATE TABLE AmericanAnswers ( questionID INT NOT NULL, answerID INT NOT NULL, answer VARCHAR(100), FOREIGN KEY (questionID) REFERENCES AmericanQuestions(questionID), PRIMARY KEY(answerID) ) ENGINE = InnoDB;");
        pState.execute();
        pState = connectObj.prepareStatement("CREATE TABLE exams ( examID INT NOT NULL, americanQuestionsID INT NOT NULL, openQuestionsID INT NOT NULL, FOREIGN KEY (americanQuestionsID) REFERENCES AmericanQuestions(questionID)," +
                "FOREIGN KEY (openQuestionsID) REFERENCES OpenQuestions(questionID), PRIMARY KEY (examID) ) ENGINE = InnoDB;");
        pState.execute();
    }

    private void dropOldTables() throws SQLException {
        pState = connectObj.prepareStatement("SET FOREIGN_KEY_CHECKS = 0;");
        pState.execute();
        pState = connectObj.prepareStatement("drop table if exists openquestions;");
        pState.execute();
        pState = connectObj.prepareStatement("drop table if exists americanquestions;");
        pState.execute();
        pState = connectObj.prepareStatement("drop table if exists exams;");
        pState.execute();
        pState = connectObj.prepareStatement("drop table if exists americananswers;");
        pState.execute();
        pState = connectObj.prepareStatement("SET FOREIGN_KEY_CHECKS = 1;");
        pState.execute();
    }

    public boolean checkIfConnectionIsUp() throws SQLException {
        if (connectObj.isValid(2)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void closeConnection(Connection connectObj) throws SQLException {
        System.out.println("Do you want to close the connection?\nPress 1 for yes.\n");
        int choice = input.nextInt();
        if (choice == 1) {
            connectObj.close();
        }
        else {
            System.out.println("Did not close connection.\nConnection still open.");
        }
    }

    public void addAmericanQuestionToDatabase(AmericanQ aQuest) throws SQLException {
        String question = aQuest.getQuestion();
        int questionID = aQuest.getQuestionNumber();
        int answerAmount = aQuest.getAnswersNum();
        String query = "INSERT INTO AmericanQuestions (questionID, answerAmount, question)" + "VALUES (?, ?, ?)";
        pState = connectObj.prepareStatement(query);
        pState.setInt(1, questionID);
        pState.setInt(2, answerAmount);
        pState.setString(3, question);
        pState.executeUpdate();
    }

    public void addOpenQuestionToDatabase(OpenQ qQuest) throws SQLException {
        String question = qQuest.getQuestion();
        String answer = qQuest.getAnswer();
        int questionID = qQuest.getQuestionNumber();
        String query = "INSERT INTO OpenQuestions (questionID, question, answer) " + "VALUES (?, ?, ?)";
        pState = connectObj.prepareStatement(query);
        pState.setInt(1, questionID);
        pState.setString(2, question);
        pState.setString(3, answer);
        pState.executeUpdate();
    }

    public void deleteAmericanQuestionFromDatabase(AmericanQ aQuest) throws SQLException {
        int questNum = aQuest.getQuestionNumber();
        String query = "DELETE FROM AmericanQuestions WHERE questionID=questNum";
        pState = connectObj.prepareStatement(query);
        pState.executeUpdate();
    }

    public void deleteOpenQuestionFromDatabase(OpenQ qQuest) throws SQLException {
        int questNum = qQuest.getQuestionNumber();
        String query = "DELETE FROM OpenQuestions WHERE questionID=questNum";
        pState = connectObj.prepareStatement(query);
        pState.execute();
    }

    public void editAmericanQuestionInDatabase(AmericanQ aQuest) {

    }
    public void editOpenQuestionInDatabase(OpenQ qQuest) {

    }
}
