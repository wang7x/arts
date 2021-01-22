---
layout: post
title:  "Open source load testing tool review 2020"
---

## Open source load testing tool review 2020

> Ragnar Lönn, 04 MARCH 2020
>
> 原文地址:[https://k6.io/blog/comparing-best-open-source-load-testing-tools](https://k6.io/blog/comparing-best-open-source-load-testing-tools)

### 摘要

今年想在DevOps平台上集成一些性能测试的工具，方便开发团队自助进行性能测试，调研了一些工具，分享其中一篇文章。

这篇文章的作者分别在17年和20年对市面上的性能测试工具进行了详细的比较和评测，内容非常翔实，他自己也是其中k6工具的作者。

文中参与评测的12款工具的清单如下：

```bash
Apachebench 2.3
Artillery 1.6.0
Drill 0.5.0 (new)
Gatling 3.3.1
Hey 0.1.2
Jmeter 5.2.1
k6 0.26.0
Locust 0.13.5
Siege 4.0.4
Tsung 1.7.0
Vegeta 12.7.0
Wrk 4.1.0
```

文章主要关注众多测试工具的两方面内容：一是性能表现如何，而是易用性如何。

### 主要内容

- 介绍了各个工具的基本信息，包括License、实现的语言、脚本化/多线程/分布式支持等，
今年Drill作为唯一的Rust实现的工具被加入比较，简单数了一下其他语言实现的数量：
C语言 3，NodeJS 1， Scala 1，Go 3，Java 1，Python 1，Erlang 1。

- 比较各个工具的开发活跃度，Jmeter、k6、Gatling、Locust最活跃，wrk改动较少，Apachebench没怎么维护了

- 介绍各个工具的历史和背景，Wrk看起来非常稳定和性能强大，缺点就是不支持HTTP/2和一些新功能，
Hey的输出很简洁清晰，在支持脚本化的工具中Locust被认为很棒，当然从作者的角度k6是最好的。

- 介绍了性能测试的一些观察指标，例如CPU负载、内存消耗、网络延迟、网络带宽，以及这些指标对理论性能的约束。

- 文章测试了工具的最大流量生成能力、每个虚拟用户（VU）的平均内存消耗、每笔请求的平均内存消耗、响应时间的测量精准度。

- 最大流量生成能力，Wrk独树一帜（5w RPS），Hey紧随其后（1.8w RPS），k6 1.1w，Jmeter 7000，Locust 2900， Drill拉胯。

- 内存占用方面，Java和Python的工具内存消耗最大，C和Go编写的表现更好。
通过观察每个虚拟用户的平均内存消耗和每笔请求的平均内存消耗，作者发现除了Jmeter，
大部分工具的内存占用并不会随着VU的增长而增长。

- 在响应时间的测量精准度方面，Wrk产生了4.5w RPS，但是响应时间中位数为1.79ms（Amazing），而Artillery只有266 RPS，
响应时间中位数高达158ms。

- 测试总结，Gatling更像现代版的Jmeter，Hey很适合简单的命令行测试，Vegeta适合更复杂一些的命令行测试场景，
Jmeter太笨重也许适合非开发者用户，作者推荐在面向开发者的自动化测试场景使用k6，
推荐喜欢用Python写测试脚本的用户使用Locust，推荐在希望产生海量请求的场景使用Wrk。

最后，文章的评论中有人提到了一些不错的工具，例如Grinder（Java）、Yandex Tank（Python），
也有人提到Gatling的测试成绩有偏差，作者重新经过warm up之后测试能达到4000 RPS，比Jmeter要快

### 后记

这些工具里面之前用得多的就是Jmeter，看完文章后去了解了下Gatling，可惜只有企业版有很完善的可视化报告界面，
相比之下k6的做法更好，没有配套的界面，但是度量数据可以输出到Kafka、InfluxDB + Grafana等工具中，
方便与企业已有的可视化工具集成，k6的文档也很规范完善，本身是完全开源的，适合与DevOps工具集成。
