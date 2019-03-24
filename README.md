# -JAVA-Course-Scheduling-System-Based-on-Genetic-Algorithm

使用前需要调整读取课表要求文档1.xls的目录

You must set the catalog to the catalog of 1.xls which is the request of Course scheduling.

p2 遗传是演示视频 用屏幕录像工具就能打开

p2.lxe is the example video which shows how it works.

##数据格式

p2目录下的1.xls

## 编码

总共长25，表示某个**教师**在某个**时间**，某个**教室**教某个**班级**，取的都是map映射的值

0~5: 教师

5~10: 班级

10~15: 日期 (星期几 * 4+ 第几节课)

15~25: 地点
