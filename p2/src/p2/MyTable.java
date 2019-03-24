package p2;

import javax.swing.table.AbstractTableModel;

/**
 * @author jikangwang
 */
class MyTable extends AbstractTableModel {//使用AbstractTableModel来创建表格模型
    Object[][] p =
            {
                    {"1-2", null, null, null, null, null},
                    {"3-4", null, null, null, null, null},
                    {"5-6", null, null, null, null, null},
                    {"7-8", null, null, null, null, null},
                    {"9-10", null, null, null, null, null},
            };
    Object[][] pp =
            {
                    {"1-2", null, null, null, null, null},
                    {"3-4", null, null, null, null, null},
                    {"5-6", null, null, null, null, null},
                    {"7-8", null, null, null, null, null},
                    {"9-10", null, null, null, null, null},
            };
    String[] n = {"", "\u661F\u671F\u4E00", "\u661F\u671F\u4E8C", "\u661F\u671F\u4E09", "\u661F\u671F\u56DB", "\u661F\u671F\u4E94"};

    public void sava() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= 5; j++) {
                pp[i][j] = p[i][j];
            }
        }
    }

    public void restore() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= 5; j++) {
                p[i][j] = pp[i][j];
            }
        }
    }

    public void check(int x, String cl) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= 5; j++) {
                p[i][j] = pp[i][j];
            }
        }
        if (x == 0) {    //按班级查询

            cl = cl + "班";
            for (int i = 0; i < 5; i++) {
                for (int j = 1; j <= 5; j++) {
                    String w1 = (String) p[i][j];
                    if (w1 == null) continue;
                    int index = w1.indexOf(cl);
                    int index1 = index;
                    int index2 = index;
                    //System.out.println(index1+" "+index2);
                    if (index != -1) {    //找到班级前缀

                        while (true) {
                            index1--;
                            if (index1 == -1 || w1.charAt(index1) == '*') {
                                index1++;
                                break;
                            }
                        }
                        while (true) {
                            index2++;
                            if (w1.charAt(index2) == '*') {
                                break;
                            }
                        }
                        p[i][j] = (Object) (w1.substring(index1, index2 + 1));
                    } else
                        p[i][j] = null;

                }
            }
        }
        if (x == 1) {    //按教师查询

            cl = cl + "老";
            for (int i = 0; i < 5; i++) {
                for (int j = 1; j <= 5; j++) {
                    String w1 = (String) p[i][j];
                    if (w1 == null) continue;
                    int index = w1.indexOf(cl);
                    int index1 = index;
                    int index2 = index;
                    if (index != -1) {    //找到班级前缀

                        while (true) {
                            index1--;
                            if (index1 == -1 || w1.charAt(index1) == '*') {
                                index1++;
                                break;
                            }
                        }
                        while (true) {
                            index2++;
                            if (w1.charAt(index2) == '*') {
                                break;
                            }
                        }
                        p[i][j] = (Object) (w1.substring(index1, index2 + 1));
                    } else
                        p[i][j] = null;
                }
            }
        }

        if (x == 2) {    //按教室查询

            cl = cl + "教";
            for (int i = 0; i < 5; i++) {
                for (int j = 1; j <= 5; j++) {
                    String w1 = (String) p[i][j];
                    if (w1 == null) continue;
                    int index = w1.indexOf(cl);
                    int index1 = index;
                    int index2 = index;
                    if (index != -1) {    //找到班级前缀

                        while (true) {
                            index1--;
                            if (index1 == -1 || w1.charAt(index1) == '*') {
                                index1++;
                                break;
                            }
                        }
                        while (true) {
                            index2++;
                            if (w1.charAt(index2) == '*') {
                                break;
                            }
                        }
                        p[i][j] = (Object) (w1.substring(index1, index2 + 1));
                    } else
                        p[i][j] = null;
                }
            }
        }


    }

    public void sp(String s1, String s2, String s3, String ss1, String ss2)    //教师 班级 时间            时间 教室
    {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= 5; j++) {
                p[i][j] = pp[i][j];
            }
        }

        s2 = s2 + "班";
        //System.out.println(s2+"*********"+s3);
        int jjj = s3.charAt(0) - '0';
        int iii = s3.charAt(2) - '0' - 1;
        String w1 = (String) p[iii][jjj];
        int index = w1.indexOf(s2);
        int index1 = index;
        int index2 = index;
        if (index != -1) {    //找到班级前缀

            while (true) {
                index1--;
                if (index1 == -1 || w1.charAt(index1) == '*') {
                    index1++;
                    break;
                }
            }
            while (true) {
                index2++;
                if (w1.charAt(index2) == '*') {
                    break;
                }
            }

            String neww1 = w1.substring(0, index1) + w1.substring(index2 + 1, w1.length());
            p[iii][jjj] = (Object) neww1;

        }


        this.sava();
    }

    public int getColumnCount() {
        return n.length;
    }

    public int getRowCount() {
        return p.length;
    }

    public String getColumnName(int col) {
        return n[col];
    }

    public Object getValueAt(int row, int col) {
        return p[row][col];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {    //判断单元格是否可以编辑
        return true;
    }

    public void setValueAt(int row, int col ,Object value) {
        p[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public void appendValueAt(int row, int col,Object value ) {
        String w1 = (String) value;
        String w2 = (String) p[row][col];
        if (w2 == null) {
            p[row][col] = (Object) (w1);
        } else {
            p[row][col] = (Object) (w1 + "\n"+w2);
        }

    }

    public void clear() {
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j <= 5; j++) {
                p[i][j] = null;
            }
        }
    }
}
