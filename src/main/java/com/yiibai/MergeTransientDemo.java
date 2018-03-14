package com.yiibai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.yiibai.DataUtils;
import com.yiibai.HibernateUtils;
import com.yiibai.entities.Employee;
import com.yiibai.entities.Timekeeper;

public class MergeTransientDemo {

   private static DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

   private static Timekeeper saveOrUpdate_Transient(Session session,
           Employee emp) {

       // Note:
       // Configuring of timekeeperId
       // @GeneratedValue(generator = "uuid")
       // @GenericGenerator(name = "uuid", strategy = "uuid2")        
       Timekeeper tk4 = new Timekeeper();

       tk4.setEmployee(emp);
       tk4.setInOut(Timekeeper.IN);
       tk4.setDateTime(new Date());

       // Now 'tk4' Transient status.
       System.out.println("- tk4 Persistent? " + session.contains(tk4));

       System.out.println("====== CALL merge(tk).... ===========");


       // Hibernate2 has method saveOrUpdateCopy
       // Hibernate3 change saveOrUpdateCopy to merge
       // So there will be similarities between the two methods merge and copyOrUpdate
       // Here Hibernate check tk4 has ID or not
       // If not, Hibernate assign value to ID of tk4
       // Return copy of tk4.
       Timekeeper tk4Copy = (Timekeeper) session.merge(tk4);

       System.out
               .println("- tk4.getTimekeeperId() = " + tk4.getTimekeeperId());


       // Now 'tk4' still Transient state.
       // and 'tk4Copy' has Persistent status
       // No action with DB (insert or update).
       System.out.println("- tk4 Persistent? " + session.contains(tk4));

       // 'tk4Copy' has Persistent status
       // ==> true
       System.out
               .println("- tk4Copy Persistent? " + session.contains(tk4Copy));

       System.out.println("- Call flush..");


       // This time have Insert or Update to DB. (!!!)
       session.flush();

       // 'tk4' still Transitent, after flush().
       // merge(..) safer than saveOrUpdate().
       System.out.println("- tk4 Persistent? " + session.contains(tk4));

       //
       String timekeeperId = tk4.getTimekeeperId();
       System.out.println("- timekeeperId = " + timekeeperId);
       System.out.println("- inOut = " + tk4.getInOut());
       System.out.println("- dateTime = " + df.format(tk4.getDateTime()));
       System.out.println();
       return tk4;
   }

   public static void main(String[] args) {
       SessionFactory factory = HibernateUtils.getSessionFactory();

       Session session = factory.getCurrentSession();
       Employee emp = null;
       try {
           session.getTransaction().begin();

           emp = DataUtils.findEmployee(session, "E7499");

           saveOrUpdate_Transient(session, emp);

           session.getTransaction().commit();
       } catch (Exception e) {
           e.printStackTrace();
           session.getTransaction().rollback();
       }
   }
}
