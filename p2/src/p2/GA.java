package p2;

import javafx.util.Pair;

import javax.swing.*;
import java.util.*;

/**
 * @author jikangwang
 */
public class GA {

    public static final int IterationTimes = 1000;
    public static final String CLASSROOM_NUMBER_BINARY = "0000000111";
    public int opp = 0;
    private Best best;                           // 记录最佳答案的对象
    private final int POP_SIZE = 30;             // 种群大小（本题的种群由30个个体（即x）组成）
    private String[][] pop = new String[POP_SIZE + 5][1000];    // String型数组，存放种群中每个个体（即x）的编码
    private String[][] result = new String[POP_SIZE + 5][1000]; // double型数组，经过进化后的种群中的每个个体（即x）
    private int[] fitness = new int[POP_SIZE + 5];//double型，存放种群中每个个体（即x）的适应值
    private final int LENGTH = 22; // x的编码长度，因为要精确到小数点后六位，所以十进制的整数部分（1位）与小数部分（6位）共有7位。这需要用22位的二进制数表示。
    private final int conversionFactor = 4194303;//转换因子，22位二进制数所能表示最大的十进制数为2^22-1
    private Random random = new Random();     // 用于产生随机数的工具
    private static final double PM = 0.01;           // 变异率
    private double[] p = new double[POP_SIZE + 5];// 轮盘赌方法个体适应度概率
    private double[] q = new double[POP_SIZE + 5];// q[i]是前n项p之和
    private int[] unfi = new int[POP_SIZE + 5];
    private int k1, k2;    //被选出来杂交的两个个体

    public int getCoursesListSize() {
        return coursesList.size();
    }

    ArrayList<Course> coursesList = new ArrayList<>();    //原本的a
    private int misNum = 0;      //总共需要排的任务数，也就是coursesList.size()

    private Best best1;

    private Map mapt = new HashMap();
    private Map mapc = new HashMap();
    private Map rmapt = new HashMap();
    private Map rmapc = new HashMap();
    private Map mmap = new HashMap();

    JTable table = null;
    MyTable myTable = null;

    JLabel status;

    private int cishu = 0;
    Course aa[][][] = new Course[100][100][100];
    Course w[] = new Course[10000];
    ArrayList<Mission> missionList = new ArrayList<>();

    private static final int DAY_NUM = 5;
    private static final int COURSE_NUM = 4;
    private static final int CLASS_NUM = 7;

    private static final int OVERRIDE_NUM=5;    //最佳种群覆盖数

    public GA() {
        best1 = new Best();
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void setMyTable(MyTable myTable) {
        this.myTable = myTable;
    }

    public void setCoursesList(ArrayList<Course> coursesList) {
        this.coursesList = coursesList;
        this.misNum = coursesList.size();
    }

    public void setStatus(JLabel status) {
        this.status = status;
    }

    public void bruteForce() {

        System.out.println("start bruteforce...");
        missionList.clear();
        table.repaint();
        boolean vis[] = new boolean[10000];
        //弱弱的暴力算法已淘汰
        for (int i = 0; i < 9999; i++) {
            w[i] = new Course();
        }
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    aa[i][j][k] = new Course();
                }
            }
        }

        for (int pp = 0; pp <= 5; pp++) {
            //周一至周五
            for (int i = 1; i <= DAY_NUM; i++) {
                //目前每天只排四节课
                for (int j = 1; j <= COURSE_NUM; j++) {

                    for (int k = 0; k < CLASS_NUM; k++) {

                        for (int ii = 0; ii < misNum; ii++) {        //尝试排ii号任务
                            if (vis[ii]) continue;
                            if (check(i, j, k, ii)) {    //检查是否和之前排过的课程发生冲突

                                aa[i][j][k] = coursesList.get(ii);

                                String teacherName = coursesList.get(ii).getTeacherName();
                                int day = j - 1;
                                int time = i;
                                String courseName = coursesList.get(ii).getCourseName();
                                int classRoom = k;
                                String classId = coursesList.get(ii).getCourseClass();
                                Mission mission = new Mission(teacherName, courseName, String.valueOf(day), String.valueOf(time), String.valueOf(classRoom), classId);
                                missionList.add(mission);

                                myTable.appendValueAt(j - 1, i, mission);
                                vis[ii] = true;
                                break;
                            } else {
                                continue;
                            }

                        }

                    }
                }
            }
            for (int ii = 0; ii < misNum; ii++) {        //尝试排ii号任务
                if (!vis[ii]) {
                    System.out.println(coursesList.get(ii).getCourseName());
                }
            }
            myTable.sava();
            table.repaint();//系统重新绘制表格
        }
    }


    int max = -999999999;

    /*
     * 取得结果
     */
    private double findBestOne() {

        for (int i = 0; i < POP_SIZE; i++) {

            if (fitness[i] > max) {
                max = fitness[i];
                best1.setFitness(max);
                for (int j = 0; j < misNum; j++) {
                    //System.out.println("----"+result[i][j]);
                    best1.x[j] = result[i][j];
                }
                best1.str = pop[i];

            }
        }
        return max;
    }

    public void fitness() {

        int Max = Integer.MIN_VALUE;
        int fit1 = 0;
        int sum = 0;
        int conflict = 0;        //种群所有个体总冲突
        int bestConflict = Integer.MAX_VALUE;   //最佳种群冲突数

        //最佳种群会覆盖掉
        //System.out.println(best1.x[0]);
        if (cishu >= 10) {
            for (int w = 0; w < OVERRIDE_NUM; w++) {
                for (int j = 0; j < misNum; j++) {
                    result[opp + w][j] = best1.x[j];
                }
            }
        }

        ArrayList<Pair<Integer,Integer>> fit=new ArrayList<>();

        for (int i = 0; i < POP_SIZE; i++) {
            Map map1 = new HashMap();
            Map map2 = new HashMap();
            Map map3 = new HashMap();
            fit1 = 0;    //不能在同一天一个班有相同的课
            int tot1 = 0;
            int tot2 = 0;
            int tot3 = 0;
            int t = 0;
            for (int j = 0; j < misNum; j++) {
                if (!map1.containsKey(result[i][j].substring(10, 25))) {    //时间  教室
                    map1.put(result[i][j].substring(10, 25), tot1);
                    tot1++;
                } else {
                    conflict++;
                    t++;
                }
                if (!map2.containsKey(result[i][j].substring(5, 15))) {    //班级 时间
                    map2.put(result[i][j].substring(5, 15), tot2);
                    tot2++;
                } else {
                    conflict++;
                    t++;
                }
                if (!map3.containsKey(result[i][j].substring(0, 5) + result[i][j].substring(10, 15))) {    //教师 时间
                    map3.put(result[i][j].substring(0, 5) + result[i][j].substring(10, 15), tot3);
                    tot3++;
                } else {
                    conflict++;
                    t++;
                }
                for (int k = j + 1; k < misNum; k++) {
                    /*
                     *
                     * 这里加上一个班级不能在同一天上相同的课
                     *
                     *
                     */
                    if (result[i][j].substring(5, 10).equals(result[i][k].substring(5, 10)) && result[i][j].substring(0, 5).equals(result[i][k].substring(0, 5))) {    //学生相同，并且课程相同
                        int t1 = 0;
                        for (int ii = 4; ii >= 0; ii--) {
                            if (result[i][j].substring(10, 15).charAt(ii) == '1') t1 += Math.pow(2, 4 - j);
                        }
                        int t2 = 0;
                        for (int ii = 4; ii <= 0; ii--) {
                            if (result[i][k].substring(10, 15).charAt(ii) == '1') t2 += Math.pow(2, 4 - j);
                        }
                        t1 /= 4;
                        t2 /= 4;
                        if (Math.abs(t1 - t2) >= 2) {
                            fit1++;
                        }
                    }
                }
            }

            bestConflict = Math.min(bestConflict, t);
            fitness[i] = 1000 + fit1 * 5 - t * 50;
            fit.add(new Pair<Integer,Integer>(i,fitness[i]));

        }

        Collections.sort(fit, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return o2.getValue()-o1.getValue();
            }
        });
        opp += OVERRIDE_NUM;
        opp %= POP_SIZE;


        double base=0.5;        //最大概率
        for (int i = 0; i < POP_SIZE; i++) {
            p[fit.get(i).getKey()] = base;
            base=base/2;
        }



        cishu++;
        System.out.println("繁殖次数:" + cishu + "  " + "适应度:" + fit.get(0).getValue() + "  冲突数量:" + conflict + "  最佳个体冲突数:" + bestConflict);
        for (int i = 0; i < POP_SIZE; i++) {
            for (int j = 0; j < misNum; j++) {
                pop[i][j] = result[i][j];
            }
        }

    }

    /*
     * 编码方法，将解值表示为二进制字节码形式
     */
    public void encoding() {


        ArrayList<String> encodedCourseString = new ArrayList<>();
        int tott = 0;
        int totc = 0;
        int mis = 0;
        int ii = 1;
        int jj = 0;
        for (int i = 0; i < misNum; i++) {
            if (!mapt.containsKey(coursesList.get(i).getTeacherName())) {
                mmap.put(coursesList.get(i).getTeacherName(), coursesList.get(i).getCourseName());
            }
            if (!mapt.containsKey(coursesList.get(i).getTeacherName())) {
                rmapt.put(tott, coursesList.get(i).getTeacherName());
                mapt.put(coursesList.get(i).getTeacherName(), tott);
                tott++;
            }
            int t1 = mapt.get(coursesList.get(i).getTeacherName()).hashCode();
            String s1 = "";
            int temp = t1;
            int w = 5;
            while (true) {
                s1 = String.valueOf((temp & 1)) + s1;
                temp >>= 1;
                w--;
                if (temp == 0) break;
            }
            while (w != 0) {
                s1 = "0" + s1;
                w--;
            }

            if (mapc.containsKey(coursesList.get(i).getCourseClass()) == false) {
                rmapc.put(totc, coursesList.get(i).getCourseClass());
                mapc.put(coursesList.get(i).getCourseClass(), totc);
                totc++;
            }
            int t2 = mapc.get(coursesList.get(i).getCourseClass()).hashCode();
            String s2 = "";
            temp = t2;
            w = 5;
            while (true) {
                s2 = String.valueOf((temp & 1)) + s2;
                temp >>= 1;
                w--;
                if (temp == 0) break;
            }
            while (w != 0) {
                s2 = "0" + s2;
                w--;
            }

            //System.out.println(s1+" "+s2);


            String ss1 = "";
            temp = ii;
            w = 5;
            while (true) {
                ss1 = String.valueOf((temp & 1)) + ss1;
                temp >>= 1;
                w--;
                if (temp == 0) break;
            }
            while (w != 0) {
                ss1 = "0" + ss1;
                w--;
            }
            //System.out.println("ss1="+ss1);
            String ss2 = "";
            temp = jj;
            w = 10;
            while (true) {
                ss2 = String.valueOf((temp & 1)) + ss2;
                temp >>= 1;
                w--;
                if (temp == 0) break;
            }
            while (w != 0) {
                ss2 = "0" + ss2;
                w--;
            }

            //System.out.println(s1+s2);
            encodedCourseString.add(s1 + s2 +/*ss1+ss2*/"000010000000001");


            if (ii == 19 && jj == 4) {
                continue;
            }
            ii++;
            if (ii == 19) {
                jj++;
                ii %= 19;
            }

        }

        for (int i = 0; i < POP_SIZE; i++) {
            for (int j = 0; j < misNum; j++)
                result[i][j] = encodedCourseString.get(j);
        }
    }

    /*
     * 选择操作,依据轮盘赌算法从种群中选出两个个体进行杂交。
     */
    public void selection() {
        do {
            k1 = roulettewheel();
            k2 = roulettewheel();
        } while (k1 != k2);
    }

    /*
     * 轮盘赌算法，适应值大的个体被选中的机率更大些。
     */

    int roulettewheel() {
        double m = 0;
        double r = random.nextDouble(); //r为0至1的随机数
        int i = 0;
        int flag = 0;
        int max = 9999999;
        for (i = 0; i < POP_SIZE; i++) {
            /* 产生的随机数在m~m+P[i]间则认为选中了i
             *  因此i被选中的概率是P[i]
             */
            m = m + p[i];
            if (r <= m)
                break;
        }

        //选择性淘汰

        return i;
    }

    /*
     * 解码方法，将二进制字节码还原
     */
    public void decoding() {
        int i, j;
        for (i = 0; i < POP_SIZE; i++) {
            for (j = 0; j < misNum; j++) {

                result[i][j] = pop[i][j];
            }
        }

    }

    /*
     * 交叉操作
     */
    public void crossover() {
        //随机产生是染色体发生交叉操作的位置
        int position1 = random.nextInt(29);
        int position2 = random.nextInt(29);
        //s1字符串被切成s11和s12，s2字符串被切成s21，s22
        String s11 = null, s12 = null, s21 = null, s22 = null;
        //System.out.println(pop[k1][position1]+" "+k1+" "+k2);
        s11 = pop[k1][position1].substring(0, 15);
        s12 = pop[k1][position1].substring(15, 25);
        s21 = pop[k2][position2].substring(0, 15);
        s22 = pop[k2][position2].substring(15, 25);
        //重新拼接字符串，并放入种群
        pop[k1][position1] = s11 + s22;
        pop[k2][position2] = s21 + s12;


    }

    /*
     * 变异操作，变异在染色体上的每个基因都可能发生
     */
    public void mutation() {
        //第i个个体（即染色体）
        for (int i = 0; i < POP_SIZE; i++) {
            //第i个个体第j号基因
            for (int j = 0; j < misNum; j++) {
                double k = random.nextDouble();
                //如果产生的随机数ｋ小于变异率，则进行变异操作。
                if (0.5 > k) {
                    mutation(i, j);
                }
            }
        }
    }

    //如果基因的位置是“1”则换为“0”。如果基因的位置是“0”则换为“1”。
    public void mutation(int i, int j) {

        //只更改教学时间和教学地点，不更改教师和课程
        String s = pop[i][j];
        //随机选择更改的基因

        //System.out.println("变异前"+s);
        StringBuffer sb = new StringBuffer(s);

        String w = s.substring(0, 10);
        String w1 = (String) s.subSequence(10, 15);    //教学时间  一定是小于20的,因此变异时注意控制
        StringBuffer sb1 = new StringBuffer(w1);
        while (true) {
            int p1 = random.nextInt(5);    //时间更改
            //System.out.println(sb1+" "+p1);
            if (sb1.charAt(p1) == '0')
                sb1.setCharAt(p1, '1');
            else
                sb1.setCharAt(p1, '0');
            w1 = sb1.toString();
            if (w1.compareTo("10011") < 1) {        //符合变异结果
                break;
            }
        }

        String w2 = (String) s.subSequence(15, 25);    //教学地点  一定是小于20的,因此变异时注意控制
        StringBuffer sb2 = new StringBuffer(w2);
        while (true) {
            int p2 = random.nextInt(10);    //时间更改
            //System.out.println(sb2+" "+p2);
            if (sb2.charAt(p2) == '0')
                sb2.setCharAt(p2, '1');
            else
                sb2.setCharAt(p2, '0');
            w2 = sb2.toString();
            if (w2.compareTo(CLASSROOM_NUMBER_BINARY) < 1) {        //符合变异结果
                break;
            }
        }
        s = w + w1 + w2;
        //System.out.println("变异后"+s);
        pop[i][j] = s;




    }

    /*
     * 一次进化
     */
    public void evolution() {

        fitness();          //计算适应度
        selection();        //选择
        crossover();        //交叉

        mutation();         //变异

        decoding();         //解码
    }

    /*
     * 整个进化过程，n 表示进化多少代
     */
    public void dispose(int n) {

        for (int i = 0; i <= n; i++) {


            if(i%100==0) status.setText(i+"/"+IterationTimes);
            status.paintImmediately(status.getVisibleRect());
            evolution();

            findBestOne();
        }
    }

    /*
     * 保存最佳个体的对象
     */


    private boolean check(int x, int y, int cl, int z) {    //检查是否和之前排过的课程发生冲突
        int i, j, k;

        Course ww = coursesList.get(z);
        boolean flag = true;

        //确定在这个班没有其他课,确定这个老师在其他教师也没课
        for (k = 0; k < cl; k++) {
            if (aa[x][y][k].getCourseClass() == null) continue;

            if (aa[x][y][k].getCourseClass().equals(ww.getCourseClass()) || aa[x][y][k].getCourseName().equals(ww.getCourseName())) {
                return false;
            }
        }


        int ch[] = new int[5];
        ch[0] = x - 1;
        ch[1] = x + 1;
        ch[2] = x;
        //System.out.println("****");
        for (int ii = 0; ii < 3; ii++) {
            int qq = ch[ii];
            if (qq < 1 || qq > 5) continue;

            for (j = 1; j <= 4; j++) {
                for (k = 0; k < 7; k++) {
                    if (aa[qq][j][k].getCourseClass() == null) continue;
                    //System.out.println(aa[i][j][k].cl+" "+aa[i][j][k].courseName+" "+i+" "+j+" "+k);
                    if (aa[qq][j][k].getCourseClass().equals(ww.getCourseClass()) && aa[qq][j][k].getCourseName().equals(ww.getCourseName())) {            //在间隔不到一天的时间内，存在给相同的班上相同的课
                        flag = false;
                    }
                }
            }
        }

        return flag;

    }

    private void init() {
        //String d[] = {"0000100001000010000000001" ,"0000100010000010000000001","0001000010000010000000001","0001000010000010000000001"};
        // 初始化初始种群及其他数据
        System.out.println("种群进化中....");
        encoding();         //编码
        // 进化，这里进化10000次
        dispose(IterationTimes);

        findBestOne();       //取得结果
        System.out.println("+++++++++++++++++++++++++++结果为：");
        missionList.clear();
        for (int i = 0; i < misNum; i++) {
            String a1 = best1.x[i].substring(0, 5);    //教师
            int t1 = 0;
            for (int j = a1.length() - 1; j >= 0; j--) {
                if (a1.charAt(j) == '1') {
                    t1 += Math.pow(2, a1.length() - 1 - j);
                }
            }
            String a2 = best1.x[i].substring(5, 10);    //班级
            int t2 = 0;
            for (int j = a2.length() - 1; j >= 0; j--) {
                if (a2.charAt(j) == '1') {
                    t2 += Math.pow(2, a2.length() - 1 - j);
                }
            }
            String a3 = best1.x[i].substring(10, 15);    //日期
            int t3 = 0;
            for (int j = a3.length() - 1; j >= 0; j--) {
                if (a3.charAt(j) == '1') {
                    t3 += Math.pow(2, a3.length() - 1 - j);
                }
            }
            String a4 = best1.x[i].substring(15, 25);    //教室
            int t4 = 0;
            for (int j = a4.length() - 1; j >= 0; j--) {
                if (a4.charAt(j) == '1') {
                    t4 += Math.pow(2, a4.length() - 1 - j);
                }
            }
            //System.out.println(t1+" "+t2+" "+t3+" "+t4);

            String teacherName = (String) rmapt.get(t1);
            int day = t3 % COURSE_NUM;
            int time = t3 / COURSE_NUM + 1;
            String courseName = (String) mmap.get((String) rmapt.get(t1));
            int classRoom = t4;
            String classId = (String) rmapc.get(t2);
            Mission mission = new Mission(teacherName, courseName, String.valueOf(day), String.valueOf(time), String.valueOf(classRoom), classId);
            missionList.add(mission);
            myTable.appendValueAt(day, time, mission);
            myTable.sava();
        }
        System.out.println("f=" + best1.getFitness());
    }

    public void start() {
        init();
    }


    void queryByClassId(String id) {
        for (Mission m : missionList) {
            if (id.equals(m.getClassId())) {
                myTable.appendValueAt(Integer.valueOf(m.getDay()), Integer.valueOf(m.getTime()), m);
            }
        }
    }

    void queryByTeacherName(String name) {
        for (Mission m : missionList) {
            if (name.equals(m.getTeacherName())) {
                myTable.appendValueAt(Integer.valueOf(m.getDay()), Integer.valueOf(m.getTime()), m);
            }
        }
    }

    void queryByClassRoom(String room) {
        for (Mission m : missionList) {
            if (room.equals(m.getClassroom())) {
                myTable.appendValueAt(Integer.valueOf(m.getDay()), Integer.valueOf(m.getTime()), m);
            }
        }
    }

}
