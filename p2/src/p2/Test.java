package p2;

/**
 * @author jikangwang
 */
public class Test {

    public static void main(String[] args) {
        GA ga=new GA();
        String Dir = "/Users/jikangwang/JavaProject/-JAVA-Course-Scheduling-System-Based-on-Genetic-Algorithm/p2/1.xls";
        FileProcesser fileProcesser = new FileProcesser();
        fileProcesser.read(Dir);
        ga.setCoursesList(fileProcesser.getCourseList());


        ga.start();


    }

}
