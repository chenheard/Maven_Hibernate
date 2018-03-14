package com.yiibai;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.yiibai.DataUtils;
import com.yiibai.HibernateUtils;
import com.yiibai.entities.Employee;

public class RefreshDetachedDemo {

   public static void main(String[] args) {

       // an Object with Detached status
       Employee emp = getEmployee_Detached();

       System.out.println(" - GET EMP " + emp.getEmpId());

       // Refresh Object  
       refresh_test(emp);
   }


   // Return Employee object has Detached state
   private static Employee getEmployee_Detached() {
       SessionFactory factory = HibernateUtils.getSessionFactory();

       Session session1 = factory.getCurrentSession();
       Employee emp = null;
       try {
           session1.getTransaction().begin();

           emp = DataUtils.findEmployee(session1, "E7839");

           // session1 was closed after a commit is called.
           // An Employee record are insert into DB.
           session1.getTransaction().commit();
       } catch (Exception e) {
           e.printStackTrace();
           session1.getTransaction().rollback();
       }
       // Session1 closed 'emp' switch to Detached state.
       return emp;
   }

   private static void refresh_test(Employee emp) {
       SessionFactory factory = HibernateUtils.getSessionFactory();

       // Open other session
       Session session2 = factory.getCurrentSession();

       try {
           session2.getTransaction().begin();


           // Check the status of 'emp' (Detached)
           // ==> false
           System.out.println(" - emp Persistent? " + session2.contains(emp));

           System.out.println(" - Emp salary before update: "
                   + emp.getSalary());

            // Set new salary for 'emp'.
           emp.setSalary(emp.getSalary() + 100);


           // refresh: make a query statement
           // and switch 'emp' to Persistent state
           // The changes are ignored
           session2.refresh(emp);

           // ==> true
           System.out.println(" - emp Persistent? " + session2.contains(emp));

           System.out.println(" - Emp salary after refresh: "
                   + emp.getSalary());

           session2.getTransaction().commit();
       } catch (Exception e) {
           e.printStackTrace();
           session2.getTransaction().rollback();
       }

   }

}
