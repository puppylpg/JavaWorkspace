FinalDemo工程说明：
【温馨提示：请最大化窗口后再开始ReadMe，要不看起来相当*疼啊！（虽然即使全屏了看起来还是相当*疼……文字描述水平有限，请见谅(∩_∩)）】
（从工程名“FinalDemo”就可以看出，这次作业我尝试着写了好几个工程啊，最后终于成功了……泪奔了┭┮﹏┭┮，夜没白刷）

关于本次多线程电梯的说明————

关于线程：
本次多线程电梯调度，工程一共实现了6个线程，分别是Timer, Simulator, Manager, Elevator1, Elevator2, Elevator3。

关于调度原则：
1.Manager线程给3个电梯分别分配任务，这一过程是根据“均衡原则”为最高原则进行的，即对于那些三个电梯都可以完成的指令，以运动量最少的优先；
2.在各个电梯内部，分别维护了一个由Manager分配而来的指令组成的队列，各个电梯在执行自己队列里的指令的时候，实现了之前要求的“顺路捎带”原则。

关于指令：
对于leave指令，一旦对某个电梯发出，则电梯停止运动，并随时间输出自己的状态为“Missing”，期间收到的关于此电梯的命令均忽略，直到出现了相应的join指令，才恢复正常状态。除了新加的这两条指令，其他命令几乎照旧。



下面通过两个样例来解释一下多线程电梯调度过程，可能会更清晰————

输入样例1，关于剥离和恢复电梯的测试：
(F_R,5,UP,0)
(leave,2,5)
(E_R,4,2,6)
(join,2,8)
(F_R,4,DOWN,9)
$

输出结果如下：
(Elevator_1,1,UP,P,0.5)         //输出格式为（电梯名，所在楼层，运动状态，P/S，时间），第一条楼层指令给了电梯1
(Elevator_2,0,STAY,S,0.5)
(Elevator_3,0,STAY,S,0.5)       //每半秒为一组，每组将三个电梯当前状态依次输出
(Elevator_1,2,UP,P,1.0)
(Elevator_2,0,STAY,S,1.0)
(Elevator_3,0,STAY,S,1.0)
(Elevator_1,3,UP,P,1.5)
(Elevator_2,0,STAY,S,1.5)
(Elevator_3,0,STAY,S,1.5)
(Elevator_1,4,UP,P,2.0)
(Elevator_2,0,STAY,S,2.0)
(Elevator_3,0,STAY,S,2.0)
(Elevator_1,5,UP,P,2.5)         //电梯1抵达五层
(Elevator_2,0,STAY,S,2.5)
(Elevator_3,0,STAY,S,2.5)
(Elevator_1,5,UP,P,3.0)
(Elevator_2,0,STAY,S,3.0)
(Elevator_3,0,STAY,S,3.0)
(Elevator_1,5,UP,P,3.5)
(Elevator_2,0,STAY,S,3.5)
(Elevator_3,0,STAY,S,3.5)
(Elevator_1,5,UP,P,4.0)
(Elevator_2,0,STAY,S,4.0)
(Elevator_3,0,STAY,S,4.0)
(Elevator_1,5,UP,P,4.5)
(Elevator_2,0,STAY,S,4.5)
(Elevator_3,0,STAY,S,4.5)
(Elevator_1,5,UP,P,5.0)
(Elevator_2,Missing,5.0)        //第五秒的时候，电梯2被剥离，故其状态为Missing
(Elevator_3,0,STAY,S,5.0)
(Elevator_1,5,UP,P,5.5)
(Elevator_2,Missing,5.5)
(Elevator_3,0,STAY,S,5.5)
(Elevator_1,5,UP,P,6.0)
(Elevator_2,Missing,6.0)        //第六秒的(E_R,4,2,6)指令被电梯2忽略，因为此时电梯2已经被剥离
(Elevator_3,0,STAY,S,6.0)
(Elevator_1,5,UP,P,6.5)
(Elevator_2,Missing,6.5)
(Elevator_3,0,STAY,S,6.5)
(Elevator_1,5,UP,P,7.0)
(Elevator_2,Missing,7.0)
(Elevator_3,0,STAY,S,7.0)
(Elevator_1,5,UP,P,7.5)
(Elevator_2,Missing,7.5)
(Elevator_3,0,STAY,S,7.5)
(Elevator_1,5,UP,P,8.0)         
(Elevator_2,0,STAY,S,8.0)       //第八秒电梯2回归
(Elevator_3,0,STAY,S,8.0)
(Elevator_1,5,UP,P,8.5)
(Elevator_2,0,STAY,S,8.5)
(Elevator_3,0,STAY,S,8.5)
(Elevator_1,5,UP,P,9.0)
(Elevator_2,1,UP,P,9.0)         //根据“均衡调度”原则，第二次的楼层指令由电梯2来执行
(Elevator_3,0,STAY,S,9.0)
(Elevator_1,5,UP,P,9.5)         //此后继续输出状态约10秒，程序结束运行
(Elevator_2,2,UP,P,9.5)
……
……                              //此处为了省略篇幅使用省略号代替
……
(Elevator_3,0,STAY,S,18.5)




输入样例2，关于调度器将命令按照“均衡调度”的原则分配给各个电梯，各个电梯按照“顺路捎带”原则执行属于自己的命令的样例：
(E_R,8,2,0)
(E_R,5,2,1)
(F_R,4,UP,6)
(F_R,10,DOWN,8)
(F_R,1,UP,13)
$

输出结果如下：
(Elevator_1,0,STAY,S,0.5)
(Elevator_2,1,UP,P,0.5)         //第一条指令为E_R指令，执行者为电梯2，所以交由电梯2执行
(Elevator_3,0,STAY,S,0.5)
(Elevator_1,0,STAY,S,1.0)
(Elevator_2,2,UP,P,1.0)         //第二条指令也是交由电梯2的
(Elevator_3,0,STAY,S,1.0)
(Elevator_1,0,STAY,S,1.5)
(Elevator_2,3,UP,P,1.5)
(Elevator_3,0,STAY,S,1.5)
(Elevator_1,0,STAY,S,2.0)
(Elevator_2,4,UP,P,2.0)
(Elevator_3,0,STAY,S,2.0)
(Elevator_1,0,STAY,S,2.5)
(Elevator_2,5,UP,P,2.5)         //电梯2到达5楼，此时去8楼的指令要对去5楼的指令实现捎带
(Elevator_3,0,STAY,S,2.5)
(Elevator_1,0,STAY,S,3.0)       
(Elevator_2,5,STAY,S,3.0)       //电梯2停止中
(Elevator_3,0,STAY,S,3.0)
(Elevator_1,0,STAY,S,3.5)
(Elevator_2,6,UP,P,3.5)         //捎带完毕，继续走，去8楼
(Elevator_3,0,STAY,S,3.5)
(Elevator_1,0,STAY,S,4.0)
(Elevator_2,7,UP,P,4.0)
(Elevator_3,0,STAY,S,4.0)
(Elevator_1,0,STAY,S,4.5)
(Elevator_2,8,UP,P,4.5)
(Elevator_3,0,STAY,S,4.5)
(Elevator_1,0,STAY,S,5.0)
(Elevator_2,8,UP,P,5.0)
(Elevator_3,0,STAY,S,5.0)
(Elevator_1,0,STAY,S,5.5)
(Elevator_2,8,UP,P,5.5)
(Elevator_3,0,STAY,S,5.5)
(Elevator_1,1,UP,P,6.0)         //根据“均衡调度”原则，电梯1运行第三条指令（电梯2已经有运动距离了）
(Elevator_2,8,UP,P,6.0)
(Elevator_3,0,STAY,S,6.0)
(Elevator_1,2,UP,P,6.5)
(Elevator_2,8,UP,P,6.5)
(Elevator_3,0,STAY,S,6.5)
(Elevator_1,3,UP,P,7.0)
(Elevator_2,8,UP,P,7.0)
(Elevator_3,0,STAY,S,7.0)
(Elevator_1,4,UP,P,7.5)
(Elevator_2,8,UP,P,7.5)
(Elevator_3,0,STAY,S,7.5)
(Elevator_1,4,UP,P,8.0)
(Elevator_2,8,UP,P,8.0)
(Elevator_3,1,UP,P,8.0)         //根据“均衡调度”原则，电梯3运行第四条指令
(Elevator_1,4,UP,P,8.5)
(Elevator_2,8,UP,P,8.5)
(Elevator_3,2,UP,P,8.5)
(Elevator_1,4,UP,P,9.0)
(Elevator_2,8,UP,P,9.0)
(Elevator_3,3,UP,P,9.0)
(Elevator_1,4,UP,P,9.5)
(Elevator_2,8,UP,P,9.5)
(Elevator_3,4,UP,P,9.5)
(Elevator_1,4,UP,P,10.0)
(Elevator_2,8,UP,P,10.0)
(Elevator_3,5,UP,P,10.0)
(Elevator_1,4,UP,P,10.5)
(Elevator_2,8,UP,P,10.5)
(Elevator_3,6,UP,P,10.5)
(Elevator_1,4,UP,P,11.0)
(Elevator_2,8,UP,P,11.0)
(Elevator_3,7,UP,P,11.0)
(Elevator_1,4,UP,P,11.5)
(Elevator_2,8,UP,P,11.5)
(Elevator_3,8,UP,P,11.5)
(Elevator_1,4,UP,P,12.0)
(Elevator_2,8,UP,P,12.0)
(Elevator_3,9,UP,P,12.0)
(Elevator_1,4,UP,P,12.5)
(Elevator_2,8,UP,P,12.5)
(Elevator_3,10,UP,P,12.5)
(Elevator_1,4,STAY,S,13.0)      //由于电梯1的运动里程最近，所以根据“均衡调度”原则，电梯1运行第五条指令
(Elevator_2,8,UP,P,13.0)
(Elevator_3,10,UP,P,13.0)
(Elevator_1,3,DOWN,P,13.5)
(Elevator_2,8,UP,P,13.5)
(Elevator_3,10,UP,P,13.5)
(Elevator_1,2,DOWN,P,14.0)
(Elevator_2,8,UP,P,14.0)
(Elevator_3,10,UP,P,14.0)
(Elevator_1,1,DOWN,P,14.5)      //最后一条指令执行完毕，继续运行10秒左右停止
(Elevator_2,8,UP,P,14.5)
(Elevator_3,10,UP,P,14.5)
(Elevator_1,1,DOWN,P,15.0)
(Elevator_2,8,UP,P,15.0)
(Elevator_3,10,UP,P,15.0)
……
……
……
(Elevator_3,10,UP,P,22.5)


//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////

正文（写了这么久才到正文。。。已醉。。。见谅。。。）——

输入格式：
1.只支持标准格式，即(F_R,n,UP/DOWN,t)或(E_R,n,e_ID,t)，数字不支持前导零，楼层1-10，时间最大范围为10位数（已包含int的范围），超过视为溢出；

2.命令与命令之间需输入回车；

3.程序以“$”结束，即在输入完所有的命令之后-->回车-->输入$-->回车，则程序完成输入；

4.命令与命令之间时间是递增的，即若出现后一条指令的时间小于等于前一条，视为非法输入；

5.根据要求，第一条命令的t必须为0，否则将报错；

6.根据要求，指令为F_R且在第10层将不允许输入UP，在第1层将不允许输入DOWN；

7.如果测试数据太大（比如几个G之类的。。。变态。。。），程序会自动报错终止。


输出格式：
1.（电梯号，楼层，运动方向，P/S，时间），如果电梯被leave了，输出（电梯号，Missing，时间）；

2.“运动方向”有UP/DOWN/STAY三种情况，主要是考虑到如果命令请求中的楼层和电梯现在的楼层一致，则运动方向UP，DOWN均不合适，STAY比较合适\(^o^)/~；

3.如果时间输入的太大，输出的结果console可能会装不下。。。
