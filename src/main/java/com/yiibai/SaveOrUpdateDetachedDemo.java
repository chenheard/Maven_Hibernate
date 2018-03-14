package com.yiibai;

import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.yiibai.DataUtils;
import com.yiibai.HibernateUtils;
import com.yiibai.entities.Employee;

public class SaveOrUpdateDetachedDemo {

   public static void main(String[] args) {

       // An object Detached state.
       Employee emp = getEmployee_Detached();

       System.out.println(" - GET EMP " + emp.getEmpId());

       // Random delete or not delete Employee
       boolean delete = deleteOrNotDelete(emp.getEmpId());

       System.out.println(" - DELETE? " + delete);

       // Call saveOrUpdate for detached object.
       saveOrUpdate_test(emp);

       // After call saveOrUpdate()
       System.out.println(" - EMP ID " + emp.getEmpId());
   }


   // Return Employee object has Detached state
   private static Employee getEmployee_Detached() {
       SessionFactory factory = HibernateUtils.getSessionFactory();

       Session session1 = factory.getCurrentSession();
       Employee emp = null;
       try {
           session1.getTransaction().begin();

           Long maxEmpId = DataUtils.getMaxEmpId(session1);
           System.out.println(" - Max Emp ID " + maxEmpId);

           Employee emp2 = DataUtils.findEmployee(session1, "E7839");

           Long empId = maxEmpId + 1;
           emp = new Employee();
           emp.setEmpId(empId);
           emp.setEmpNo("E" + empId);

           emp.setDepartment(emp2.getDepartment());
           emp.setEmpName(emp2.getEmpName());

           emp.setHideDate(emp2.getHideDate());
           emp.setJob("Test");
           emp.setSalary(1000F);

           // emp has been managed by Hibernate
           session1.persist(emp);

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

   // Random: delete or not delete.
   private static boolean deleteOrNotDelete(Long empId) {
       // A random number 0-9
       int random = new Random().nextInt(10);
       if (random < 5) {
           return false;
       }
       SessionFactory factory = HibernateUtils.getSessionFactory();

       Session session2 = factory.getCurrentSession();
       try {
           session2.getTransaction().begin();
           String sql = "Delete " + Employee.class.getName() + " e "
                   + " where e.empId =:empId ";
           Query query = session2.createQuery(sql);
           query.setParameter("empId", empId);

           query.executeUpdate();

           session2.getTransaction().commit();
           return true;
       } catch (Exception e) {
           e.printStackTrace();
           session2.getTransaction().rollback();
           return false;
       }
   }

   private static void saveOrUpdate_test(Employee emp) {
       SessionFactory factory = HibernateUtils.getSessionFactory();

       // Open other session
       Session session3 = factory.getCurrentSession();

       try {
           session3.getTransaction().begin();

           // Check state of emp
           // ==> false
           System.out.println(" - emp Persistent? " + session3.contains(emp));

           System.out.println(" - Emp salary before update: "
                   + emp.getSalary());

           // Set new salary for Detached emp object.
           emp.setSalary(emp.getSalary() + 100);


           // Using saveOrUpdate(emp) to switch emp to Persistent state
           // Note: If exists object same ID in session, this method raise Exception
           //
           // Now, no action with DB.            
           session3.saveOrUpdate(emp);

           // By pushing data into the DB.
           // It will call a Insert or update statement.
           // If the record is deleted before ==> insert
           // Else ==> update.    
           session3.flush();

           System.out
                   .println(" - Emp salary after update: " + emp.getSalary());

           // session3 was closed after a commit is called.
           session3.getTransaction().commit();
       } catch (Exception e) {
           e.printStackTrace();
           session3.getTransaction().rollback();
       }

   }

}
