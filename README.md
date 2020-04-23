# -JAVA-Course-Scheduling-System-Based-on-Genetic-Algorithm

使用前先把lib下的jar包导入到library里，然后直接运行EntryWindow.java即可

p2 遗传是演示视频 用屏幕录像工具就能打开

p2.lxe is the example video which shows how it works.

### 排课结果
<div align=center>
<img src="https://github.com/zzzyyyxxxmmm/-JAVA-Course-Scheduling-System-Based-on-Genetic-Algorithm/blob/master/image/1.png" width="900" height="600">
</div>

### 训练中:
<div align=center>
<img src="https://github.com/zzzyyyxxxmmm/-JAVA-Course-Scheduling-System-Based-on-Genetic-Algorithm/blob/master/image/2.png" width="500" height="700">
</div>

### 查询课表:
<div align=center>
<img src="https://github.com/zzzyyyxxxmmm/-JAVA-Course-Scheduling-System-Based-on-Genetic-Algorithm/blob/master/image/3.png" width="1000" height="600">
</div>

### 数据格式

p2目录下的1.xls

###s 编码

总共长25，表示某个**教师**在某个**时间**，某个**教室**教某个**班级**，取的都是map映射的值

0~5: 教师

5~10: 班级

10~15: 日期 (星期几 * 4+ 第几节课)

15~25: 地点
