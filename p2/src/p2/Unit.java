package p2;

import java.util.ArrayList;

/**
 * @author jikangwang
 * 用来存储每个时间点的所有教学任务
 */
public class Unit {

    private ArrayList<Mission> list=new ArrayList<>();

    Unit(){
    }
    public void Add(Mission m){
        list.add(m);
    }

    public Mission getByName(String teacher, String cls){
        for(Mission m:list){
            if(m.getTeacherName().equals(teacher)&&m.getClassId().equals(cls)){
                return m;
            }
        }
        return null;
    }

    public void Delele(Mission m){
        list.remove(m);
    }

    public void Show(){
        for(Mission m:list){
            m.out();
        }
    }

    @Override
    public String toString() {
        if(list.size()==0)  return "*空*";
        StringBuffer s=new StringBuffer();
        for(Mission m:list){
            if(m.out().length()==0) continue;
            s.append(m.out()+"\n");
        }
        return s.toString();
    }
}
