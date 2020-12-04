/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package schoolmanagementsystem.Database;

import java.awt.HeadlessException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import schoolmanagementsystem.JConnection;

/**
 *
 * @author fahim
 */
public class TeacherDatabase {
    private String teacherName;
    private String teacherSubject;
    private String designation;
    private String id;
    final JPanel panel = new JPanel();
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Statement st;
    
    //Constructor
    public TeacherDatabase() {
    }
    //Constructor (Polymorphism - Method Overloading)
    public TeacherDatabase(String id) {
        this.id = id;
        conn=JConnection.connectdb();
        getTeacherData(id);
    }
    private void getTeacherData(String id) {
        
        String sql = "select * from teacher_accounts where id=\'" +id+ "\'";
        try{
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                setTeacherName(rs.getString("name"));
                setTeacherSubject(rs.getString("subject"));
                setDesignation(rs.getString("designation"));
            }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(panel, "Database error","Warning",JOptionPane.WARNING_MESSAGE);
        }
    }
    public void postHomework(String studentClass,String section,int totalMarks,String dueDate,String homeworkText){
        Date datetime = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String postDateString=dateFormat.format(datetime);
        String sql = "INSERT INTO homework(class, sec,teacher_name, subject, total_marks,post_date, due_date,homework_text) VALUES(?,?,?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, studentClass);
            pst.setString(2, section);
            pst.setString(3, getTeacherName());
            pst.setString(4, getTeacherSubject());
            pst.setInt(5, totalMarks);
            pst.setString(6,postDateString);
            pst.setString(7, dueDate);
            pst.setString(8,homeworkText);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Homework have been posted Successfully");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(panel, e,"Warning",JOptionPane.WARNING_MESSAGE);
        }
        
    }
    public void postNotice(String studentClass,String notice){
        Date datetime = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        String dateString=dateFormat.format(datetime);
        String timeString=timeFormat.format(datetime);
        
        String sql = "INSERT INTO notice_board(date, time, teacher_name, class, subject, notice) VALUES(?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, dateString);
            pst.setString(2, timeString);
            pst.setString(3, getTeacherName());
            pst.setString(4, studentClass);
            pst.setString(5, getTeacherSubject());
            pst.setString(6,notice);
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Notice have been posted Successfully");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(panel, "Database error","Warning",JOptionPane.WARNING_MESSAGE);
        }
    }
    public void logout(){
        try {
            InetAddress myIP=InetAddress.getLocalHost();
            PreparedStatement  preparedStatement = conn.prepareStatement("update login_info set state =?  where id = \'"+getId()+"\' and ip_address=\'"+myIP.getHostAddress()+"\'");
            preparedStatement.setInt(1, 0);
            int update_done = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(panel, "Database error","Warning",JOptionPane.WARNING_MESSAGE);
            
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(panel, "Sorry, Can't get your IP Address.","Warning",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    
    
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
    public String getTeacherName() {
        return teacherName;
    }
    
    public String getTeacherSubject() {
        return teacherSubject;
    }
    
    public void setTeacherSubject(String teacherSubject) {
        this.teacherSubject = teacherSubject;
    }
    
    
    public String getDesignation() {
        return designation;
    }
    
    public String getId() {
        return id;
    }
    
    
    
    
    
}
