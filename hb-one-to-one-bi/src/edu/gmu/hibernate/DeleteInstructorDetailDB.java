package edu.gmu.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.gmu.hibernate.entity.Instructor;
import edu.gmu.hibernate.entity.InstructorDetail;

public class DeleteInstructorDetailDB {

	public static void main(String[] args) {

		// create session factory.
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {
			// start transaction.
			session.beginTransaction();
			
			// get an instructor detail object by primary key / id
			int id = 1;
			InstructorDetail anInstructorDetail = session.get(InstructorDetail.class, id);
			
			// print detail object.
			System.out.println("an Instructor Detail: " + anInstructorDetail);
			
			// print the associated instructor.
			System.out.println("the associated instructor: " + anInstructorDetail.getInstructor());
		
			// delete the instructor detail.
			/* 
			 * note: this deleting will be cascaded, 
			 * so that the associated instructor will ALSO be deleted.
			 */
			System.out.println("Deleting the instructor detail ... ");
			session.delete(anInstructorDetail);
			
			// commit the transaction.
			session.getTransaction().commit();
			System.out.println("Done!");
		} 
		catch (Exception exc) {
			exc.printStackTrace();
		}
		finally {
			
			// handle connection leak issue.
			session.close();
			
			factory.close();
		}
	}
}