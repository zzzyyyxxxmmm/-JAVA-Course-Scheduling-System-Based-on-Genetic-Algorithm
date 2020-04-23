package p2;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.*;

import java.awt.BorderLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class EntryWindow {


    public static final boolean DEBUG = false;


    private JFrame frame;
    private JPanel panel;
    private JButton startBruteButton;
    private JButton startGAButton;
    MyTable table = null;
    MyTable myTable = new MyTable();


    public JTextField textField;
    private JLabel status;
    public String Dir;
    private GA ga;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {


                    EntryWindow window = new EntryWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public EntryWindow() {
        initialize();
    }

    private void initialize() {

        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ga = new GA();
        createTable();


        ga.setTable(table.getTable());
        ga.setMyTable(myTable);
        ga.setStatus(status);

    }


    private void createTable() {
        myTable = new MyTable();
        table = myTable;
        // Data to be displayed in the JTable
        JScrollPane s = new JScrollPane(table.getTable());
        frame.getContentPane().add(s, BorderLayout.CENTER);
        frame.setTitle("JAVA Course Scheduling System Based on Genetic Algorithm");
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize(1200,700);

        panel = new JPanel();
        startBruteButton = new JButton("开始排课(暴力算法)");
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        status = new JLabel("排课");
        panel.add(status);
        status.setPreferredSize(new Dimension(150,15));

        status.setText("排课");

        startBruteButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if(ga.getCoursesListSize()==0){
                    JOptionPane.showMessageDialog(null, "请先导入p2目录下的excel文件", "注意", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                myTable.clear();
                table.getTable().repaint();//系统重新绘制表格
                ga.bruteForce();

            }
        });
        panel.add(startBruteButton);

        startGAButton = new JButton("开始排课(GA)");

        startGAButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if(ga.getCoursesListSize()==0){
                    JOptionPane.showMessageDialog(null, "请先导入p2目录下的excel文件", "注意", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                myTable.clear();
                table.getTable().repaint();//系统重新绘制表格
                ga.start();
            }
        });
        panel.add(startGAButton);
        table.getTable().repaint();//系统重新绘制表格


        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);


        JMenu fmenu = new JMenu("文件");
        menuBar.add(fmenu);
        JMenuItem fMenuItem1 = new JMenuItem("excel导入");
        fMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent Event) {
                FileChooser ab = new FileChooser();
                Dir = ab.ac();
                FileProcesser fileProcesser = new FileProcesser();
                fileProcesser.read(Dir);
                ga.setCoursesList(fileProcesser.getCourseList());

                //table.repaint();
            }
        });

        if (DEBUG) {
            Dir = "/Users/jikangwang/JavaProject/-JAVA-Course-Scheduling-System-Based-on-Genetic-Algorithm/p2/1.xls";
            FileProcesser fileProcesser = new FileProcesser();
            fileProcesser.read(Dir);
            ga.setCoursesList(fileProcesser.getCourseList());
        }

        fmenu.add(fMenuItem1);


        JMenu menu = new JMenu("操作");
        menuBar.add(menu);

        JMenuItem menuItem1_1 = new JMenuItem("手动排课");
        menuItem1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                final JFrame frame1 = new JFrame();
                frame1.setBounds(250, 250, 900, 200);
                frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame1.setVisible(true);

                JPanel panel1 = new JPanel();
                frame1.getContentPane().add(panel1, BorderLayout.CENTER);

                JLabel label = new JLabel("请输入需要修改的教师班级和时间");
                panel1.add(label);

                textField = new JTextField();
                panel1.add(textField);
                textField.setColumns(10);


                final JTextField textField_4 = new JTextField();
                panel1.add(textField_4);
                textField_4.setColumns(10);

                final JTextField textField_5 = new JTextField();
                panel1.add(textField_5);
                textField_5.setColumns(10);


                JPanel panel_2 = new JPanel();
                frame1.getContentPane().add(panel_2, BorderLayout.SOUTH);
                frame1.setSize(1200,100);

                JLabel lblNewLabel = new JLabel("请输入时间和地点");
                panel_2.add(lblNewLabel);

                final JTextField textField_1 = new JTextField();
                panel_2.add(textField_1);
                textField_1.setColumns(10);


                final JTextField textField_2 = new JTextField();
                panel_2.add(textField_2);
                textField_2.setColumns(10);

                JPanel panel_3 = new JPanel();
                frame1.getContentPane().add(panel_3, BorderLayout.EAST);

                JLabel lblNewLabel_1 = new JLabel("日期按照天-课次  例如星期三第三四节课，输入3-2");
                panel_3.add(lblNewLabel_1);

                JButton btnNewButton = new JButton("排课");
                textField.setText("沈明玉");
                textField_4.setText("计科-1");
                textField_5.setText("1-1");
                textField_1.setText("4-1");
                textField_2.setText("1");

                btnNewButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        String s1 = textField.getText();
                        String s2 = textField_4.getText();
                        String s3 = textField_5.getText();
                        String ss1 = textField_1.getText();
                        String ss2 = textField_2.getText();
                        boolean result=myTable.sp(s1, s2, s3, ss1, ss2);
                        if(!result){
                            lblNewLabel_1.setText("未找到对应班级");
                        } else {
                            lblNewLabel_1.setText("修改成功");
                        }
                        table.getTable().repaint();
                    }

                });
                panel1.add(btnNewButton);


            }
        });


        menu.add(menuItem1_1);


        JMenu menu_1 = new JMenu("查询");
        menuBar.add(menu_1);

        JMenuItem menuItem = new JMenuItem("按班级");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                queryDialog(0);

            }
        });
        menu_1.add(menuItem);

        JMenuItem menuItem_1 = new JMenuItem("按教师");
        menuItem_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                queryDialog(1);

            }
        });
        menu_1.add(menuItem_1);

        JMenuItem menuItem_2 = new JMenuItem("按教室");
        menuItem_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                queryDialog(2);

            }
        });
        menu_1.add(menuItem_2);

        JMenuItem menuItem_3 = new JMenuItem("恢复");
        menuItem_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                myTable.restore();
                table.getTable().repaint();
            }
        });
        menu_1.add(menuItem_3);

        JMenu menu_2 = new JMenu("帮助");
        menuBar.add(menu_2);


    }

    private void queryDialog(final int queryType) {
        final JFrame frame1 = new JFrame();
        frame1.setBounds(500, 500, 200, 100);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.setVisible(true);

        JPanel panel1 = new JPanel();
        frame1.getContentPane().add(panel1, BorderLayout.CENTER);
        JLabel label = new JLabel();


        if (queryType == 0)
            label.setText("请输入需要查询的班级");
        else if (queryType == 1)
            label.setText("请输入需要查询的教师");
        else if (queryType == 2)
            label.setText("请输入需要查询的教室");

        panel1.add(label);

        textField = new JTextField();
        panel1.add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("查询");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                myTable.clear();
                String xx = textField.getText();
                if (queryType == 0)
                    ga.queryByClassId(xx);
                else if (queryType == 1)
                    ga.queryByTeacherName(xx);
                else if (queryType == 2)
                    ga.queryByClassRoom(xx);
                table.getTable().repaint();
            }

        });
        panel1.add(btnNewButton);

        table.getTable().repaint();

    }

}