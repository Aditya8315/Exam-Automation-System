import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
      try{
//          Class.forName("com.mysql.jdbc.Driver");
          Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/examautomation","root",null);
          Statement stat=con.createStatement();
//          ResultSet res=stat.executeQuery("select * from student");
//          while(res.next()){
//              System.out.println(res.getString(1));
//          }
//          con.close();
          Scanner sc=new Scanner(System.in);
          Scanner sc2=new Scanner(System.in);
          int choice;
          System.out.println(":----Instruction----:");
          System.out.println("Click 1 for Set the Exam:");
          System.out.println("Click 2 for Inserting the Student:");
          System.out.println("Click 3 for Update the Students:");
          System.out.println("Click 4 for Deleting the Students:");
          System.out.println("Click 5 for View Students Data:");
          System.out.println("Click 6 for Inserting the Hall");
          System.out.println("Click 7 for Update the Hall");
          System.out.println("Click 8 for Deleting the Hall:");
          System.out.println("Click 9 for View Halls Data:");
          System.out.println("Click 10 for Inserting the Teacher");
          System.out.println("Click 11 for Update the Teacher");
          System.out.println("Click 12 for Deleting the Teacher:");
          System.out.println("Click 13 for View Teachers Data:");
          System.out.println("Click 14 for Inserting the Course");
          System.out.println("Click 15 for Update the Course");
          System.out.println("Click 16 for Deleting the Course:");
          System.out.println("Click 17 for View Courses Data:");
          System.out.println("Click 18 for Show the Exam Sheet According to Exam_ID:");
          System.out.println("Click 0 for Exit:");
          do{
              choice=sc.nextInt();
              switch(choice){
                  case 1:
                      System.out.println("------------------Set New Exam----------------------");
                      String Exam_date,Exam_time,Exam_branch,Exam_semester;
                      boolean flag1=false;
                      do {
                      System.out.println("-----------------------------------------");
                      System.out.println("Write the Date of the Exam: ");
                      Exam_date=sc2.nextLine();
                      System.out.println("Write the Time of the Exam: ");
                      Exam_time=sc2.nextLine();
                      System.out.println("Exam of Which Branch Student: ");
                      Exam_branch=sc2.nextLine();
                      System.out.println("Exam of Which Semester Student: ");
                      Exam_semester=sc2.nextLine();
                      ResultSet Exam_res=stat.executeQuery("select * from setexam");

                          while (Exam_res.next()) {
                              if (Exam_date.equals(Exam_res.getString(2)) && Exam_time.equals(Exam_res.getString(3)) && Exam_branch.equals(Exam_res.getString(4)) && Exam_semester.equals(Exam_res.getString(5))) {
                                  System.out.println("At this time these student have already Exam please refill the Entry with another time or date");
                                  flag1=true;
                              }
                          }
                      }while(flag1==true);
                      System.out.println("Exam of which Course: ");
                      String Exam_course=sc2.nextLine();
                      ResultSet Exam_res2=stat.executeQuery("select * from course");
                      boolean flag2=false;
                      boolean flag3=false;
                      while(Exam_res2.next()){
                          if(Exam_course.equals(Exam_res2.getString(1))){
                              flag2=true;
                              if(Exam_branch.equals(Exam_res2.getString(3))&& Exam_semester.equals(Exam_res2.getString(4))){
                                  flag3=true;
                              }
                              break;
                          }
                      }
                      if(flag2){
                          if(!flag3){
                              System.out.println("This course is not referred to this branch or this semester!!!");
                              break;
                          }
                      }else{
                          System.out.println("This course is not in Courses!!!");
                          break;
                      }
                      int Exam_total_student=0;
                      String Exam_q1="select * from student Where (Student_Branch = \""+Exam_branch+"\" and"+" Student_Semester = \""+Exam_semester+"\" )";
                      ResultSet Exam_res3=stat.executeQuery(Exam_q1);
                      while(Exam_res3.next()){
                          Exam_total_student++;
                      }
                      int Exam_total_seat=0;
                      String Exam_q2="select * from hall where Hall_Availability = \"true\"";
                      ResultSet Exam_res4=stat.executeQuery(Exam_q2);
                      while(Exam_res4.next()){
                          Exam_total_seat+=Integer.parseInt(Exam_res4.getString(2));
                      }
                      if(Exam_total_seat<Exam_total_student){
                          System.out.println("Total Student is more than left seat please add new seat!!!");
                          break;
                      }
                      int Exam_total_teacher=0;
                      String Exam_q3="select * from teacher where Teacher_Availability = \"true\"";
                      ResultSet Exam_res5=stat.executeQuery(Exam_q3);
                      while(Exam_res5.next()){
                          Exam_total_teacher++;
                      }
                      int Exam_total_teacher_required=0;
                      ResultSet Exam_res6=stat.executeQuery(Exam_q2);
                      while(Exam_res6.next()){
                          Exam_total_student-=Integer.parseInt(Exam_res6.getString(2));
                          Exam_total_teacher_required++;
                          if(Exam_total_student<=0){
                              break;
                          }
                      }
                      if(Exam_total_teacher_required>Exam_total_teacher){
                          System.out.println("Required Teacher is more than Available Teacher please add new Teacher!!!");
                          break;
                      }
                      String Exam_q4="insert into setexam(Exam_ID, Exam_Date, Exam_TimeInterval, Exam_Branch, Exam_Semester) values(?,?,?,?,?)";
                      int Exam_id=0;
                      String Exam_q5="Select * from setexam";
                      ResultSet Exam_res7=stat.executeQuery(Exam_q5);
                      while(Exam_res7.next()){
                          Exam_id++;
                      }
                      try{
                          PreparedStatement pst=con.prepareStatement(Exam_q4);
                          pst.setInt(1,Exam_id);
                          pst.setString(2,Exam_date);
                          pst.setString(3,Exam_time);
                          pst.setString(4,Exam_branch);
                          pst.setString(5,Exam_semester);
                          pst.executeUpdate();
                      }catch(Exception e){
                          System.out.println("Something wrong");
                      }
                      try{
                      for(int i=1;i<=Exam_total_teacher_required;i++) {
                          String Hall_ID="0";
                          int Hall_Capacity=0;
                          String Exam_q6="select * from hall Where Hall_Availability=\"true\"";
                          ResultSet Exam_res8=stat.executeQuery(Exam_q6);
                          while(Exam_res8.next()){
                              Hall_ID = Exam_res8.getString(1);
                              Hall_Capacity = Exam_res8.getInt(2);
                              break;
                          }
                          String Teacher_ID="0";
                          String Exam_q7="select * from teacher Where Teacher_Availability=\"true\"";
                          ResultSet Exam_res9=stat.executeQuery(Exam_q7);
                          while(Exam_res9.next()){
                              Teacher_ID = Exam_res9.getString(1);
                              break;
                          }
                          String Exam_q8="select * from student Where ( Student_Branch = \""+Exam_branch+"\" and"+" Student_Semester = \""+Exam_semester+"\" )";
                          ResultSet Exam_res10=stat.executeQuery(Exam_q8);
                          while (Exam_res10.next() && Hall_Capacity > 0) {
                              String Exam_q9 = "insert into examdata(Exam_Date, Student_ID, Course_ID, Hall_ID, Teacher_ID,Exam_ID) values(?,?,?,?,?,?)";
                              try {
                                  PreparedStatement pst = con.prepareStatement(Exam_q9);
                                  pst.setString(1, Exam_date);
                                  pst.setString(2, Exam_res10.getString(1));
                                  pst.setString(3, Exam_course);
                                  pst.setString(4, Hall_ID);
                                  pst.setString(5, Teacher_ID);
                                  pst.setInt(6, Exam_id);
                                  pst.executeUpdate();
                                  Hall_Capacity--;

                              } catch (Exception e) {
                                  System.out.println("Something wrong");
                              }
                          }
                          String q1="UPDATE Hall SET Hall_Availability=? WHERE Hall_ID= \""+Hall_ID+"\"";
                          PreparedStatement exam_res11=con.prepareStatement(q1);
                          exam_res11.setString(1,"false");
                          String q2="UPDATE Teacher SET Teacher_Availability=? WHERE Teacher_ID=\""+Teacher_ID+"\"";
                          PreparedStatement exam_res12=con.prepareStatement(q2);
                          exam_res12.setString(1,"false");
                      }
                      }catch(Exception e){
                          System.out.println("Something wrong...");
                      }
                      System.out.println("Exam Set----");
                      System.out.println("----------------------------------------------------------");
                      break;
                  case 2:
                      System.out.println("------------------INSERT NEW STUDENT----------------------");
                      System.out.println("How many new Student you want to insert: ");
                      int n=sc.nextInt();
                      for(int i=1;i<=n;i++){
                          String id;
                          boolean flag;
                          do {
                              flag=false;
                              System.out.println("Enter Student_ID: ");
                              id=sc2.nextLine();
                              ResultSet res=stat.executeQuery("select * from student");
                              while(res.next()) {
                                  if (id.equals(res.getString(1))) {
                                      System.out.println("This ID already given please give another ID");
                                      flag = true;
                                      break;
                                  }
                              }
                          }while(flag==true);
                          System.out.println("Enter First Name of the Student: ");
                          String firstName=sc2.nextLine();
                          System.out.println("Enter Last name of the Student: ");
                          String lastName=sc2.nextLine();
                          System.out.println("Enter Branch of the Student: ");
                          String branch=sc2.nextLine();
                          System.out.println("Enter Semester of the Student: ");
                          int sem=sc.nextInt();
                          String q="insert into student(Student_ID, Student_FirstName, Student_LastName, Student_Branch, Student_semester) values(?,?,?,?,?)";
                          try{
                              PreparedStatement pst=con.prepareStatement(q);
                              pst.setString(1,id);
                              pst.setString(2,firstName);
                              pst.setString(3,lastName);
                              pst.setString(4,branch);
                              pst.setInt(5,sem);
                              pst.executeUpdate();
                          }catch(Exception e){
                              System.out.println("Something wrong");
                          }
                      }
                      System.out.println("Inserted......");
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 3:
                      System.out.println("------------------UPDATE STUDENT----------------------");
                        String[] column=new String[4];
                        System.out.println("How many column do you want to update:");
                        int size=sc.nextInt();
                        System.out.println("Write the name of that columns: ");
                        for(int i=0;i<size;i++){
                            column[i]=sc2.nextLine();
                        }
                        String q="UPDATE STUDENT SET";
                        for(int i=0;i<size;i++){
                            if(column[i].length()!=0){
                                q=q+" "+column[i]+"=?";
                            }
                        }
                        System.out.println("Write the condition: ");
                        String cond=sc2.nextLine();
                        q=q+" "+"WHERE"+" "+cond;
                        try{
                            PreparedStatement pst=con.prepareStatement(q);
                            System.out.println("Write the value of that columns: ");
                            for(int i=1;i<=size;i++){
                                String value=sc2.nextLine();
                                pst.setString(i,value);
                            }
                            pst.executeUpdate();
                            System.out.println("Updated Successfully----");
                        }catch(Exception e){
                            System.out.println("Something wrong------");
                        }
                      System.out.println("---------------------------------------------------------------------------");
                  break;
                  case 4:
                      System.out.println("-------------------DELETE FROM STUDENT---------------------");
                        System.out.println("Write the condition: ");
                        String delete_cond=sc2.nextLine();
                        String delete_q="DELETE FROM STUDENT WHERE";
                        delete_q=delete_q+" "+delete_cond;
                        try{
                            PreparedStatement pst=con.prepareStatement(delete_q);
                            pst.executeUpdate();
                            System.out.println("Deleted Successfully----");
                        }catch(Exception e){
                            System.out.println("Something wrong------");
                        }
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 5:
                      System.out.println("-------------------DISPLAY STUDENT DATA---------------------");
                      System.out.println("Write the condition: ");
                      String display_cond=sc2.nextLine();
                      String display_q= "select * from student"+" "+"Where"+" "+display_cond;
                      Statement stm=con.createStatement();
                      try{
                          ResultSet res=stm.executeQuery(display_q);
                          System.out.println("-----------------------------------------------------------------------------------------------------------------");
                          System.out.println("      Student_ID      Student_firstName     Student_lastName      Student_Branch         Student_semester");
                          while(res.next()){
                              System.out.println("    "+res.getString(1)+"   "+res.getString(2)+"   "+res.getString(3)+"   "+res.getString(4)+"   "+res.getString(5));
                          }
                          System.out.println("-------------------------------------------------------------------------------------------------------------------");
                      }catch(Exception e){
                          System.out.println("Something wrong");
                      }
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 6:
                      System.out.println("------------------INSERT NEW Hall----------------------");
                      System.out.println("How many new Hall you want to insert: ");
                      int n_Hall=sc.nextInt();
                      for(int i=1;i<=n_Hall;i++){
                          String Hall_id;
                          boolean flag;
                          do {
                              flag=false;
                              System.out.println("Enter Hall_ID: ");
                              Hall_id=sc2.nextLine();
                              ResultSet res=stat.executeQuery("select * from hall");
                              while(res.next()) {
                                  if (Hall_id.equals(res.getString(1))) {
                                      System.out.println("This ID already given please give another ID");
                                      flag = true;
                                      break;
                                  }
                              }
                          }while(flag==true);
                          System.out.println("Enter Hall capacity: ");
                          String Hall_capacity=sc2.nextLine();
                          System.out.println("Enter Hall availability: ");
                          String Hall_availability=sc2.nextLine();
                          String Hall_q="insert into hall(Hall_ID, Hall_Capacity, Hall_Availability) values(?,?,?)";
                          try{
                              PreparedStatement pst=con.prepareStatement(Hall_q);
                              pst.setString(1,Hall_id);
                              pst.setString(2,Hall_capacity);
                              pst.setString(3,Hall_availability);
                              pst.executeUpdate();
                          }catch(Exception e){
                              System.out.println("Something wrong");
                          }
                      }
                      System.out.println("Inserted......");
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 7:
                      System.out.println("------------------Update Hall----------------------");
                      String[] Hall_column=new String[2];
                      System.out.println("How many column do you want to update:");
                      int Hall_column_size=sc.nextInt();
                      System.out.println("Write the name of that columns: ");
                      for(int i=0;i<Hall_column_size;i++){
                          Hall_column[i]=sc2.nextLine();
                      }
                      String Hall_q="UPDATE HALL SET";
                      for(int i=0;i<Hall_column_size;i++){
                          if(Hall_column[i].length()!=0){
                              Hall_q=Hall_q+" "+Hall_column[i]+"=?";
                          }
                      }
                      System.out.println("Write the condition: ");
                      String Hall_cond=sc2.nextLine();
                      Hall_q=Hall_q+" "+"WHERE"+" "+Hall_cond;
                      try{
                          PreparedStatement pst=con.prepareStatement(Hall_q);
                          System.out.println("Write the value of that columns: ");
                          for(int i=1;i<=Hall_column_size;i++){
                              String value=sc2.nextLine();
                              pst.setString(i,value);
                          }
                          pst.executeUpdate();
                          System.out.println("Updated Successfully----");
                      }catch(Exception e){
                          System.out.println("Something wrong------");
                      }
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 8:
                      System.out.println("-------------------DELETE FROM Hall---------------------");
                      System.out.println("Write the condition: ");
                      String Hall_delete_cond=sc2.nextLine();
                      String Hall_delete_q="DELETE FROM HALL WHERE";
                      Hall_delete_q=Hall_delete_q+" "+Hall_delete_cond;
                      try{
                          PreparedStatement pst=con.prepareStatement(Hall_delete_q);
                          pst.executeUpdate();
                          System.out.println("Deleted Successfully----");
                      }catch(Exception e){
                          System.out.println("Something wrong------");
                      }
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 9:
                      System.out.println("-------------------DISPLAY HALL DATA---------------------");
                      System.out.println("Write the condition: ");
                      String Hall_display_cond=sc2.nextLine();
                      String Hall_display_q= "select * from hall"+" "+"Where"+" "+Hall_display_cond;
                      Statement Hall_stm=con.createStatement();
                      try{
                          ResultSet res=Hall_stm.executeQuery(Hall_display_q);
                          System.out.println("-----------------------------------------------------------------------------------------------------------------");
                          System.out.println("      Hall_ID      Hall_Capacity     Hall_Availability      ");
                          while(res.next()){
                              System.out.println("    "+res.getString(1)+"   "+res.getString(2)+"   "+res.getString(3));
                          }
                          System.out.println("-------------------------------------------------------------------------------------------------------------------");
                      }catch(Exception e){
                          System.out.println("Something wrong");
                      }
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 10:
                      System.out.println("------------------INSERT NEW TEACHER----------------------");
                      System.out.println("How many new Teacher you want to insert: ");
                      int Teacher_n=sc.nextInt();
                      for(int i=1;i<=Teacher_n;i++){
                          String Teacher_id;
                          boolean flag;
                          do {
                              flag=false;
                              System.out.println("Enter Teacher_ID: ");
                              Teacher_id=sc2.nextLine();
                              ResultSet res=stat.executeQuery("select * from teacher");
                              while(res.next()) {
                                  if (Teacher_id.equals(res.getString(1))) {
                                      System.out.println("This ID already given please give another ID");
                                      flag = true;
                                      break;
                                  }
                              }
                          }while(flag==true);
                          System.out.println("Enter First Name of the Teacher: ");
                          String Teacher_firstName=sc2.nextLine();
                          System.out.println("Enter Last name of the Teacher: ");
                          String Teacher_lastName=sc2.nextLine();
                          System.out.println("Enter Teacher Availability: ");
                          String Teacher_availability=sc2.nextLine();
                          String Teacher_q="insert into teacher(Teacher_ID, Teacher_FirstName, Teacher_LastName, Teacher_Availability) values(?,?,?,?)";
                          try{
                              PreparedStatement pst=con.prepareStatement(Teacher_q);
                              pst.setString(1,Teacher_id);
                              pst.setString(2,Teacher_firstName);
                              pst.setString(3,Teacher_lastName);
                              pst.setString(4,Teacher_availability);
                              pst.executeUpdate();
                          }catch(Exception e){
                              System.out.println("Something wrong");
                          }
                      }
                      System.out.println("Inserted......");
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 11:
                      System.out.println("------------------UPDATE TEACHER----------------------");
                      String[] Teacher_column=new String[3];
                      System.out.println("How many column do you want to update:");
                      int Teacher_size=sc.nextInt();
                      System.out.println("Write the name of that columns: ");
                      for(int i=0;i<Teacher_size;i++){
                          Teacher_column[i]=sc2.nextLine();
                      }
                      String Teacher_q="UPDATE TEACHER SET";
                      for(int i=0;i<Teacher_size;i++){
                          if(Teacher_column[i].length()!=0){
                              Teacher_q=Teacher_q+" "+Teacher_column[i]+"=?";
                          }
                      }
                      System.out.println("Write the condition: ");
                      String Teacher_cond=sc2.nextLine();
                      Teacher_q=Teacher_q+" "+"WHERE"+" "+Teacher_cond;
                      try{
                          PreparedStatement pst=con.prepareStatement(Teacher_q);
                          System.out.println("Write the value of that columns: ");
                          for(int i=1;i<=Teacher_size;i++){
                              String value=sc2.nextLine();
                              pst.setString(i,value);
                          }
                          pst.executeUpdate();
                          System.out.println("Updated Successfully----");
                      }catch(Exception e){
                          System.out.println("Something wrong------");
                      }
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 12:
                      System.out.println("-------------------DELETE FROM Teacher---------------------");
                      System.out.println("Write the condition: ");
                      String Teacher_delete_cond=sc2.nextLine();
                      String Teacher_delete_q="DELETE FROM TEACHER WHERE";
                      Teacher_delete_q=Teacher_delete_q+" "+Teacher_delete_cond;
                      try{
                          PreparedStatement pst=con.prepareStatement(Teacher_delete_q);
                          pst.executeUpdate();
                          System.out.println("Deleted Successfully----");
                      }catch(Exception e){
                          System.out.println("Something wrong------");
                      }
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 13:
                      System.out.println("-------------------DISPLAY TEACHER DATA---------------------");
                      System.out.println("Write the condition: ");
                      String Teacher_display_cond=sc2.nextLine();
                      String Teacher_display_q= "select * from teacher"+" "+"Where"+" "+Teacher_display_cond;
                      Statement Teacher_stm=con.createStatement();
                      try{
                          ResultSet res=Teacher_stm.executeQuery(Teacher_display_q);
                          System.out.println("-----------------------------------------------------------------------------------------------------------------");
                          System.out.println("      Teacher_ID      Teacher_firstName     Teacher_lastName      Teacher_Availability         ");
                          while(res.next()){
                              System.out.println("    "+res.getString(1)+"   "+res.getString(2)+"   "+res.getString(3)+"   "+res.getString(4));
                          }
                          System.out.println("-------------------------------------------------------------------------------------------------------------------");
                      }catch(Exception e){
                          System.out.println("Something wrong");
                      }
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 14:
                      System.out.println("------------------INSERT NEW COURSE----------------------");
                      System.out.println("How many new Course you want to insert: ");
                      int Course_n=sc.nextInt();
                      for(int i=1;i<=Course_n;i++){
                          String Course_id;
                          boolean flag;
                          do {
                              flag=false;
                              System.out.println("Enter Course_ID: ");
                              Course_id=sc2.nextLine();
                              ResultSet res=stat.executeQuery("select * from course");
                              while(res.next()) {
                                  if (Course_id.equals(res.getString(1))) {
                                      System.out.println("This ID already given please give another ID");
                                      flag = true;
                                      break;
                                  }
                              }
                          }while(flag==true);
                          System.out.println("Enter Course Name: ");
                          String Course_name=sc2.nextLine();
                          System.out.println("Which Branch Student will study this Course: ");
                          String Course_branch=sc2.nextLine();
                          System.out.println("Which Semester Student will study this Course: ");
                          String Course_semester=sc2.nextLine();
                          String Course_q="insert into course(Course_ID, Course_Name, Course_Branch, Course_Semester) values(?,?,?,?)";
                          try{
                              PreparedStatement pst=con.prepareStatement(Course_q);
                              pst.setString(1,Course_id);
                              pst.setString(2,Course_name);
                              pst.setString(3,Course_branch);
                              pst.setString(4,Course_semester);
                              pst.executeUpdate();
                          }catch(Exception e){
                              System.out.println("Something wrong");
                          }
                      }
                      System.out.println("Inserted......");
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 15:
                      System.out.println("------------------UPDATE COURSE----------------------");
                      String[] Course_column=new String[3];
                      System.out.println("How many column do you want to update:");
                      int Course_size=sc.nextInt();
                      System.out.println("Write the name of that columns: ");
                      for(int i=0;i<Course_size;i++){
                          Course_column[i]=sc2.nextLine();
                      }
                      String Course_q="UPDATE COURSE SET";
                      for(int i=0;i<Course_size;i++){
                          if(Course_column[i].length()!=0){
                              Course_q=Course_q+" "+Course_column[i]+"=?";
                          }
                      }
                      System.out.println("Write the condition: ");
                      String Course_cond=sc2.nextLine();
                      Course_q=Course_q+" "+"WHERE"+" "+Course_cond;
                      try{
                          PreparedStatement pst=con.prepareStatement(Course_q);
                          System.out.println("Write the value of that columns: ");
                          for(int i=1;i<=Course_size;i++){
                              String value=sc2.nextLine();
                              pst.setString(i,value);
                          }
                          pst.executeUpdate();
                          System.out.println("Updated Successfully----");
                      }catch(Exception e){
                          System.out.println("Something wrong------");
                      }
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 16:
                      System.out.println("-------------------DELETE FROM Course---------------------");
                      System.out.println("Write the condition: ");
                      String Course_delete_cond=sc2.nextLine();
                      String Course_delete_q="DELETE FROM COURSE WHERE";
                      Course_delete_q=Course_delete_q+" "+Course_delete_cond;
                      try{
                          PreparedStatement pst=con.prepareStatement(Course_delete_q);
                          pst.executeUpdate();
                          System.out.println("Deleted Successfully----");
                      }catch(Exception e){
                          System.out.println("Something wrong------");
                      }
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 17:
                      System.out.println("-------------------DISPLAY COURSE DATA---------------------");
                      System.out.println("Write the condition: ");
                      String Course_display_cond=sc2.nextLine();
                      String Course_display_q= "select * from course"+" "+"Where"+" "+Course_display_cond;
                      Statement Course_stm=con.createStatement();
                      try{
                          ResultSet res=Course_stm.executeQuery(Course_display_q);
                          System.out.println("-----------------------------------------------------------------------------------------------------------------");
                          System.out.println("      Course_ID      Course_Name     Course_Branch      Course_Semester         ");
                          while(res.next()){
                              System.out.println("    "+res.getString(1)+"   "+res.getString(2)+"   "+res.getString(3)+"   "+res.getString(4));
                          }
                          System.out.println("-------------------------------------------------------------------------------------------------------------------");
                      }catch(Exception e){
                          System.out.println("Something wrong");
                      }
                      System.out.println("---------------------------------------------------------------------------");
                      break;
                  case 18:
                      System.out.println("-------------------DISPLAY EXAM---------------------");
                      System.out.println("Write the Exam_ID: ");
                      int Exam_Id=sc.nextInt();
                      String Exam_q ="Select * from examdata where Exam_Id = "+Exam_Id;
                      Statement Exam_stm=con.createStatement();
                      ResultSet Exam_res=Exam_stm.executeQuery(Exam_q);
                      if(!Exam_res.next()){
                          System.out.println("This ID may be wrong!!!");
                          break;
                      }
                      System.out.println("---------------------------------------------------------------------");
                      while(Exam_res.next()){

                          System.out.println("Exam_Date        Student_ID         Course_ID       Hall_ID       Teacher_ID        ");
                          System.out.println(Exam_res.getString(1)+"      "+Exam_res.getString(2)+"        "+Exam_res.getString(3)+"        "+Exam_res.getString(4)+"        "+Exam_res.getString(5)+"        ");

                      }
                      System.out.println("---------------------------------------------------------------------");
                      break;
              }
          }while(choice!=0);
      }catch(Exception e){
          System.out.println("Connection failed!!!!");
      }
    }
}