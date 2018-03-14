package com.yiibai;

import org.hibernate.Session;
import org.hibernate.query.Query;
import com.yiibai.entities.Department;
import com.yiibai.entities.Employee;

public class DataUtils {

   public static Department findDepartment(Session session, String deptNo) {
       String sql = "Select d from " + Department.class.getName() + " d "//
               + " Where d.deptNo = :deptNo";
       Query<Department> query = session.createQuery(sql);
       query.setParameter("deptNo", deptNo);
       return query.getSingleResult();
   }

   public static Long getMaxEmpId(Session session) {
       String sql = "Select max(e.empId) from " + Employee.class.getName() + " e ";
       Query<Number> query = session.createQuery(sql);
       Number value = query.getSingleResult();
       if (value == null) {
           return 0L;
       }
       return value.longValue();
   }

   public static Employee findEmployee(Session session, String empNo) {
       String sql = "Select e from " + Employee.class.getName() + " e "//
               + " Where e.empNo = :empNo";
       Query<Employee> query = session.createQuery(sql);
       query.setParameter("empNo", empNo);
       return query.getSingleResult();
   }

}
