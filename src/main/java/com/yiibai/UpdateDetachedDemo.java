package com.yiibai;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.yiibai.DataUtils;
import com.yiibai.HibernateUtils;
import com.yiibai.entities.Department;
import com.yiibai.entities.Employee;

public class UpdateDetachedDemo {

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtils.getSessionFactory();

        Session session1 = factory.getCurrentSession();
        Employee emp = null;
        try {
            session1.getTransaction().begin();

            // This is a Persistent object.
            emp = DataUtils.findEmployee(session1, "E7499");

            // session1 was closed after a commit is called.
            session1.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session1.getTransaction().rollback();
        }

        // Open other session
        Session session2 = factory.getCurrentSession();

        try {
            session2.getTransaction().begin();

            // Check state of 'emp'
            // ==> false
            System.out.println("- emp Persistent? " + session2.contains(emp));

            System.out.println("Emp salary: " + emp.getSalary());

            emp.setSalary(emp.getSalary() + 100);

            // update (..) is only used for Detached object.
            // (Not for Transient object).
            // Use the update (emp) to bring back emp Persistent state.
            session2.update(emp);

            // Call flush
            // Update statement will be called.
            session2.flush();

            System.out.println("Emp salary after update: " + emp.getSalary());

            // session2 was closed after a commit is called.
            session2.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session2.getTransaction().rollback();
        }

    }
}
