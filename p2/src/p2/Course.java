package p2;

/**
 * @author jikangwang
 */
public class Course {
    private String courseId; //课程ID
    private String courseName; //课程名称
    private String courseMajor; //专业
    private String courseClass; //班级
    private String time1; //理论课时
    private String time2; //实验课时
    private int teacherID; //任课教师ID
    private String teacherName; //任课教师姓名

    public Course() {

    }

    public Course(String courseId, String courseName, String courseMajor, String courseClass, int teacherID, String teacherName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseMajor = courseMajor;
        this.courseClass = courseClass;
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.time1 = "";
        this.time2 = "";
    }

    public Course(String courseId, String courseName, String courseMajor, String courseClass, String time1, String time2, int teacherID, String teacherName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseMajor = courseMajor;
        this.courseClass = courseClass;
        this.time1 = time1;
        this.time2 = time2;
        this.teacherID = teacherID;
        this.teacherName = teacherName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public String getCourseClass() {
        return courseClass;
    }

    public String getTime1() {
        return time1;
    }

    public String getTime2() {
        return time2;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }
}