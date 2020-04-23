package p2;

/**
 * @author jikangwang
 */
public class Mission {
    private String teacherName;
    private String courseName;
    private String day;
    private String time;
    private String classroom;
    private String classId;

    public Mission(String teacherName, String courseName, String day, String time, String classroom, String classId) {
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.day = day;
        this.time = time;
        this.classroom = classroom;
        this.classId = classId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "teacherName='" + teacherName + '\'' +
                ", courseName='" + courseName + '\'' +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", classroom='" + classroom + '\'' +
                ", classId='" + classId + '\'' +
                '}';
    }

    public String out(){
        if(teacherName.length()==0) return "";
        return teacherName+"老师"+"("+courseName+")"+"在"+classroom+"教室"+"教"+classId+"班";
    }
}
