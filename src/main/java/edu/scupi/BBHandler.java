package edu.scupi;

import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.user.User;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.course.CourseMembershipDbPersister;
import blackboard.persist.user.UserDbLoader;

public class BBHandler {

    private static UserDbLoader userDbLoader = null;

    private static CourseDbLoader courseDbLoader = null;

    private static CourseMembershipDbLoader courseMembershipDbLoader = null;

    private static CourseMembershipDbPersister courseMembershipDbPersister = null;

    static {
        try {
            userDbLoader = UserDbLoader.Default.getInstance();
            courseDbLoader = CourseDbLoader.Default.getInstance();
            courseMembershipDbLoader = CourseMembershipDbLoader.Default.getInstance();
            courseMembershipDbPersister = CourseMembershipDbPersister.Default.getInstance();
        } catch (PersistenceException persistenceException) {
        }
    }

    public static User loadUserByUserName(String userName) {
        User user = new User();
        try {
            user = userDbLoader.loadByUserName(userName);
        } catch (PersistenceException e) {
            user = null;
        }
        return user;
    }

    public static Course loadCourseByCourseId(String courseId) {
        Course course = new Course();
        try {
            course = courseDbLoader.loadByCourseId(courseId);
        } catch (PersistenceException e) {
            course = null;
        }
        return course;
    }

    public static CourseMembership loadCourseMemberShipByCourseIdAndUserId(Id courseId, Id userId) {
        CourseMembership courseMembership = new CourseMembership();
        try {
            courseMembership = courseMembershipDbLoader.loadByCourseAndUserId(courseId, userId);
        } catch (KeyNotFoundException e) {
            courseMembership = null;
        } catch (PersistenceException e) {
            courseMembership = null;
        }
        return courseMembership;
    }

    public static void deleteCourseUser(CourseMembership courseMembership)
            throws PersistenceException {
        courseMembershipDbPersister.deleteById(courseMembership.getId());
    }

}
