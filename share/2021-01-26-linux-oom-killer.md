---
layout: post
title:  "Linux OOM Killer"
---

## Linux OOM Killer

今天介绍一个跟公众号名字很相关的Linux内存管理机制。

### 概念

在软件故障中，[Out-Of-Memory](https://en.wikipedia.org/wiki/Out_of_memory)(OOM,系统可用内存不足)是最古老的故障之一，并且一直延续至今。

在早期的计算机系统中，内存小到以kb为单位，那时的程序员要精细的计算每一条指令的内存使用量，
，犹如在钢丝绳上表演杂技，稍有不慎就会出现内存不足导致软件或操作系统崩溃，但是即使限制那么苛刻，
依然产生了许多伟大的软件，这也是这个公众号的名字由来。

即使今天的家用计算机内存已经至少8G起步，操作系统上运行的软件也会遇见OOM错误，Linux为了避免
系统内存不足导致整个系统不可用，建立了一套OOM管理机制。

Linux的[OOM管理机制](https://www.kernel.org/doc/gorman/html/understand/understand016.html)会检测可用内存，在内存不足故障发生时选择性的杀死一个或者几个进程以释放内存，
而执行这一任务的进程就被称作Linux Out-Of-Memory(OOM) Killer。

### 为什么要了解OOM Killer

那么Linux的这一机制对我们有什么影响呢？

因为OOM Killer选择杀死哪个进程有不确定性。

在服务器上运行着我们为客户提供服务的进程、监控系统情况的进程、数据库进程等等，
通常这些进程的内存占用都相对较大，容易被OOM Killer选中。

如果杀死的是客户服务进程或者数据库进程，轻则服务失败降低用户体验，重则丢失数据引起严重的生产事故。

如果杀死的是监控进程，可能造成服务器异常下线，或者无法及时重启等问题。

所以我们需要了解OOM Killer的运行机制，然后根据生产服务的情况进行合适的配置，以避免关键进程被杀死带来的损失。

### OOM Killer的机制

Linux的OOM管理机制可以大致分为“检查可用内存-判定OOM状态-选择牺牲者进程-杀死牺牲者”四个步骤。

#### 检查可用内存

当程序申请操作系统分配内存时，需要的内存数量会被传递给`vm_enough_memory()`，
除非系统管理员设置了内存过载使用，否则Linux会检查可用内存是否充足。

Linux检查可用内存时，除了当前可以直接使用的`free pages`内存，
还会检查`page cache`等容易被回收的内存和swap交换区的可用空间。

如果这些内存空间加起来能够满足申请分配的数量，Linux会执行回收并分配内存的动作，不会触发OOM处理。

#### 判定OOM状态

当系统内存不足，并且向磁盘交换并回收旧页帧（page frames）也无法释放足够的内存时，Linux会调用
`out_of_memory()`来决策是否要杀死进程。

因为有可能系统只是极短时间的内存不足，等某个IO操作完成或者内存换页后就缓解了，所以为了避免误杀进程，
在真正杀死进程之前，需要根据一些条件判定是否进入OOM状态：

- 是否有足够的交换空间，如果有，不进入OOM状态

- 是否距离上一次内存分配失败已经超过5秒，如果是，不进入OOM状态

- 是否OOM检查一秒内未通过次数大于一次，如果否，不进入OOM状态

- 是否在最近5秒内至少10次内存分配错误，如果否，不进入OOM状态

- 是否5秒内已经杀死过一个进程，如果是，不进入OOM状态

当前Linux内核的规则不一定跟上面完全一样，但是我们可以看出Linux会尽力避免频繁的进入OOM状态。

所有这些检查都通过之后，就会进入下一步，选择牺牲者进程。

#### 选择牺牲者进程

到了这一阶段，OOM Killer会从一众程序中按照一定规则计算和选取一个进程杀死并释放内存，
以保证系统能够继续运行。选择进程时会遵循如下[原则](https://rakeshjain-devops.medium.com/linux-out-of-memory-killer-31e477a45759)：

- 尽量选择内存占用较多的进程（通常容易命中我们的服务进程）

- 尽量通过杀死最少的进程来达到目标

- 尽量不杀死系统和内核运行需要的进程

- 如果有进程处于`SIGKILL`或者退出动作中，优先选择以加快内存释放

OOM Killer通过一套尽心设计的算法计算每个进程的`oom_score`，按照分数高低选择应该杀死哪个进程。

具体进程的`oom_score`可以通过命令`cat /proc/[pid]/oom_score`来查看。

在我的ubuntu上，系统进程的`oom_score`通常是0，chrome中内存占用较大页面进程在300左右。

#### 杀死牺牲者

选出牺牲者进程后，OOM Killer会发送``信号杀死牺牲者，即使这个程序本身没有任何问题。

如果内存依然不足，OOM Killer会重复上面的步骤，继续选择并杀死新的进程，直到释放足够的内存位置，
颇有为了拯救世界宁愿杀尽天下人的冷血范。

### [查看OOM Killer的运行情况](https://docs.memset.com/other/linux-s-oom-process-killer)

可以通过`dmesg`命令查看OOM Killer的日志记录

```bash
sudo dmesg | grep -i “killed process”
```

在不同的发行版也可以查看以下日志

```bash
#CentOS
grep -i "out of memory" /var/log/messages

#Debian / Ubuntu
grep -i "out of memory" /var/log/kern.log
```

### [如何控制OOM Killer](https://www.oracle.com/technical-resources/articles/it-infrastructure/dev-oom-killer.html)

我们可以通过以下方法避免OOM Killer杀死指定进程。

- 调整进程的`oom_socre`

    可以向`/proc/[pid]/oom_score_adj`文件中写入一个负数（例如-1000）来避免进程被选中（`oom_adj`已经废弃），

    为了使系统重启后调整仍然生效，可以在`systemd`的`service unit`中写入如下配置

    ```inimingling
    [Service]
    OOMScoreAdjust=-1000
    ```

- 关闭OOM Killer

    通过命令`sudo -s sysctl -w vm.oom-kill = 0`可以关闭OOM Killer.

    通过命令`echo vm.oom-kill = 0 >>/etc/sysctl.conf`写入配置文件可以使重启后也生效.

    但是强烈建议**不要**关闭OOM Killer，可能导致系统出现不可预知的异常

- 配置`overcommit_memory`内核参数

    ```bash
    sudo sysctl vm.overcommit_memory=2
    echo "vm.overcommit_memory=2" >> /etc/sysctl.conf
    ```

    `overcommit_memory`有三个取值:

    0 - 操作系统自行决定进程是否过载使用内存，通常这是默认值

    1 - 强制过载使用内存，这样配置会有导致内核进程内存不足的风险
    ，适用于某些内存必须分配成功的科学计算场景

    2 - 不允许过载使用内存，意味着系统分配的内存不会超过总内存乘以`overcmmit_ratio`
    (`/proc/sys/vm/overcommit_ratio`，默认50%)，这是最安全的配置。

- 配置`panic_on_oom`内核参数

    `echo 1 > /proc/sys/vm/panic_on_oom`

    将`panic_on_oom`内核参数设置为1可以让系统在内存不足时抛出内核错误(kernel panic)，这通常也不是我们希望的。

- 增加内存😀
  
    可能有点废话，不过如果经常内存不足，物理手段有时比软件优化来得快速有效。

- 降低软件的内存消耗，修复内存泄漏问题。

    大部分场景下，软件系统自身错误的内存使用方式和Bug是内存不足的主要元凶，
    例如在数据库查询中不限制查询出来的记录数量等等
