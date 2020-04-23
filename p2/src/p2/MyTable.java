package p2;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author jikangwang
 */
class MyTable{//使用AbstractTableModel来创建表格模型


    JTable table;
    Object[][] rowData =
            {
                    {"1-2", null, null, null, null, null},
                    {"3-4", null, null, null, null, null},
                    {"5-6", null, null, null, null, null},
                    {"7-8", null, null, null, null, null},
                    {"9-10", null, null, null, null, null},
            };
    Object[][] cache =
            {
                    {"1-2", null, null, null, null, null},
                    {"3-4", null, null, null, null, null},
                    {"5-6", null, null, null, null, null},
                    {"7-8", null, null, null, null, null},
                    {"9-10", null, null, null, null, null},
            };
    String[] columnNames = {"", "\u661F\u671F\u4E00", "\u661F\u671F\u4E8C", "\u661F\u671F\u4E09", "\u661F\u671F\u56DB", "\u661F\u671F\u4E94"};

    public JTable getTable() {
        return table;
    }

    public MyTable() {
        table=new JTable(rowData, columnNames);
        table.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());
    }

    public void sava() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= 5; j++) {
                cache[i][j] = rowData[i][j];
            }
        }
    }

    public void restore() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= 5; j++) {
                rowData[i][j] = cache[i][j];
            }
        }
    }

    public Boolean sp(String s1, String s2, String s3, String ss1, String ss2)    //教师 班级 时间            时间 教室
    {
        String[] t1=s3.split("-");
        Unit u=(Unit)getValueAt(Integer.parseInt(t1[1])-1,Integer.parseInt(t1[0]));
        if(u==null) return false;
        Mission m=u.getByName(s1, s2);
        if(m==null) return false;

        Mission nm=new Mission(m.getTeacherName(),m.getCourseName(),m.getDay(),m.getTime(),m.getClassroom(),m.getClassId());
        u.Delele(m);

        String[] t2=ss1.split("-");
        Unit u2=(Unit)getValueAt(Integer.parseInt(t2[1])-1,Integer.parseInt(t2[0]));
        nm.setClassroom(ss2);
        u2.Add(nm);
        this.sava();

        return true;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return rowData.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return rowData[row][col];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {    //判断单元格是否可以编辑
        return true;
    }

    public void setValueAt(int row, int col ,Object value) {
        table.setValueAt(value, row, col);
    }

    public void appendValueAt(int row, int col,Mission value ) {
        Unit u=(Unit) table.getValueAt(row, col);
        u.Add(value);
    }

    public void clear() {
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j <= 5; j++) {
                rowData[i][j] = new Unit();
            }
        }
    }

    class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer {
        public TableCellTextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            // 计算当下行的最佳高度
            int maxPreferredHeight = 0;
            for (int i = 0; i < table.getColumnCount(); i++) {
                setText("" + table.getValueAt(row, i));
                setSize(table.getColumnModel().getColumn(column).getWidth(), 0);
                maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height);
            }

            if (table.getRowHeight(row) != maxPreferredHeight)  // 少了这行则处理器瞎忙
                table.setRowHeight(row, maxPreferredHeight);

            setText(value == null ? "" : value.toString());
            return this;
        }
    }
}
