package com.yiibai;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.yiibai.entities.Department;
import com.yiibai.entities.Employee;

public class PersistentDemo {

   public static void main(String[] args) {

       SessionFactory factory = HibernateUtils.getSessionFactory();

       Session session = factory.getCurrentSession();
       Department department = null;

       try {
           session.getTransaction().begin();

           System.out.println("- Finding Department deptNo = D10...");

           // Persistent object.
           department = DataUtils.findDepartment(session, "D10");

           System.out.println("- First change Location");


           // Changing something on Persistent object.
           department.setLocation("Chicago " + System.currentTimeMillis());

           System.out.println("- Location = " + department.getLocation());

           System.out.println("- Calling flush...");

           // Use session.flush () to actively push the changes to the DB.
           // It works for all changed Persistent objects.
           session.flush();

           System.out.println("- Flush OK");

           System.out.println("- Second change Location");

           // Change something on Persistent object
           department.setLocation("Chicago " + System.currentTimeMillis());

           // Print out location
           System.out.println("- Location = " + department.getLocation());

           System.out.println("- Calling commit...");

           // Commit
           session.getTransaction().commit();

           System.out.println("- Commit OK");
       } catch (Exception e) {
           e.printStackTrace();
           session.getTransaction().rollback();
       }

       // Create the session after it had been closed earlier
       // (Cause by commit or update)
       session = factory.getCurrentSession();
       try {
           session.getTransaction().begin();

           System.out.println("- Finding Department deptNo = D10...");

           // Query lại Department D10.

           department = DataUtils.findDepartment(session, "D10");

           // Print out location
           System.out.println("- D10 Location = " + department.getLocation());

           session.getTransaction().commit();
       } catch (Exception e) {
           e.printStackTrace();
           session.getTransaction().rollback();
       }
   }

}
