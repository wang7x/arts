---
layout: post
title:  "Leaning Kafka 1"
---

## OutOfMemory的Kafka学习笔记(1)

### 背景

Kafka是Apache的顶级消息中间件项目，因为强大的处理能力，被广泛应用于大数据处理和日志采集等领域，发展的前景非常好。

我希望通过对Kafka的学习，深入了解流式数据处理的基本原理，为深入大数据领域的工作奠定基础。

同时也希望能够在金融业务处理中借鉴或引入相关思想和技术。

### 目标

- 理解Kafka的原理、架构和关键处理机制
- 掌握构建、部署和管理Kafka集群的技术
- 掌握Kafka的参数配置和应用编程
- 熟悉Kafka关键处理模块的源码
- 为Kafka开源项目贡献力量

### 计划

- 每周至少两个番茄投入
- 先学习了解Kafka官方文档，了解Kafka的基本概念
- 搭建Kafka集群
- 学习Kafka集群管理和基本功能
- 学习Kafka参数配置和应用编程
- 制定学习Kafka源码想要了解的问题，并不断完善
- 从Kafka的核心功能入手，理解源码的结构和主要逻辑
- 搭建Kafka编译环境，编译、构建Kafka程序
- 尝试重现和调试Kafka开源项目的issue

### 官方文档学习

#### 官方网站: [kafka.apache.org/](https://kafka.apache.org/)

#### Kafka的核心能力

- 高吞吐

    使用一组集群机器，可以以达到网络限制的吞吐处理消息，时延低至2ms

- 灵活扩展

    生产集群可以扩展至上千broker，每天处理万亿级消息、pb级数据、数十万分区，可弹性伸缩的存储和处理能力

- 永久性存储

    将数据流存储在分布式的、持久的、容错的集群中

- 高可用

    将集群伸展到多个可用的区域，或可连接多个地理分隔的集群

#### introduction video

跟随官网指引观看了一个Kafka的[introduction视频](https://kafka.apache.org/intro)，提到了几个核心要点：

- Kafka描述事件而不是描述对象，事件以log的方式被永久性存储
- Kafka将一系列事件log描述为一个topic，应用程序可以产生和消费topic中的事件记录
- Kafka可以应用于将宏服务拆分为相互独立的微服务架构
- Kafka connect工具可以连接已有系统的数据库，将数据导入Kafka，提供给其他应用程序消费
- Kafka提供join聚合、过滤topic数据的能力，应用程序通过steam api操作

Apache Kafka 定位为事件流式处理平台,流式处理就像是人类身体的神经系统，一个很形象的比喻

- To publish (write) and subscribe to (read) streams of events, including continuous import/export of your data from other systems.
- To store streams of events durably and reliably for as long as you want.
- To process streams of events as they occur or retrospectively.
