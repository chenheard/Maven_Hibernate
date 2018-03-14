package com.yiibai;

import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.yiibai.DataUtils;
import com.yiibai.HibernateUtils;
import com.yiibai.entities.Employee;

public class MergeDetachedDemo {

   public static void main(String[] args) {

       // An object has Detached status
       Employee emp = getEmployee_Detached();

       System.out.println(" - GET EMP " + emp.getEmpId());

       // Random: delete or not delete the Employee by ID.
       boolean delete = deleteOrNotDelete(emp.getEmpId());

       System.out.println(" - DELETE? " + delete);

       // Call saveOrUpdate Detached object
       saveOrUpdate_test(emp);

       // After call saveOrUpdate
       // ...
       System.out.println(" - EMP ID " + emp.getEmpId());
   }


   // Method return Employee object
   // and has Detached status.
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

           // 'emp' has Persistant state
           session1.persist(emp);


           // session1 was closed after a commit is called.
           // An Employee record are insert into DB.
           session1.getTransaction().commit();
       } catch (Exception e) {
           e.printStackTrace();
           session1.getTransaction().rollback();
       }
       // session1 closed, 'emp' switched Detached state.
       return emp;
   }


   // Delete Employee by ID
   // Random: delete or not delete
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


           // The fact, 'emp' has Detached state
           // It is not managed by Hibernate.
           // Check the status of emp:
           // ==> false
           System.out.println(" - emp Persistent? " + session3.contains(emp));

           System.out.println(" - Emp salary before update: "
                   + emp.getSalary());

           // Set new salary for Detached object 'emp'
           emp.setSalary(emp.getSalary() + 100);


           // merge(emp) return empMerge, a copy of 'emp',
           // empMerge managed by Hibernate
           // 'emp' still in Detached state
           //
           // At this time there is no action regarding DB.
           Employee empMerge = (Employee) session3.merge(emp);

           // ==> false
           System.out.println(" - emp Persistent? " + session3.contains(emp));
           // ==> true
           System.out.println(" - empMerge Persistent? "
                   + session3.contains(empMerge));


           // Push data into the DB.
           // Here it is possible to create the Insert or Update on DB.
           // If the corresponding record has been deleted by someone, it insert
           // else it update
           session3.flush();

           System.out
                   .println(" - Emp salary after update: " + emp.getSalary());

           // session3 closed after a commit is called.
           session3.getTransaction().commit();
       } catch (Exception e) {
           e.printStackTrace();
           session3.getTransaction().rollback();
       }

   }

}
