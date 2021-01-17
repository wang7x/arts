---
layout: post
title:  "Maximizing Developer Effectiveness Review"
---

## Maximizing Developer Effectiveness Review

> Tim Cochran, 06 January 2021
>
> 原文地址:[https://martinfowler.com/articles/developer-effectiveness.html](https://martinfowler.com/articles/developer-effectiveness.html)

### 背景

这篇文章是一系列文章的开篇，作者观察到许多企业在引入新技术后，开发者的效能不增反降，
作者认为这是由于新技术的复杂性和认知负担导致的。

通过研究，作者识别了开发者的关键反馈回路（key developer feedback loops），
包括开发者每天重复200次的微反馈回路（micro-feedback loops），
本系列文章试图引入一个最大化开发者效能的框架，通过优化这些回路提高开发者效能。

作者经常帮助转型中的组织，这些组织通常同时面临技术转型和文化转型，例如将宏服务拆分
为微服务架构，建立独立迭代的团队和DevOps流水线，同时采取敏捷的产品开发以更快响应市场。

但是这样的转型经常失败，经理们不满意交付延迟和预算超支，技术人员忙于解决各种绊脚石，
生产效率低下，团队同时开展许多依赖项繁多的工作、认知超载、对新工具新流程缺乏掌握。
最终向领导层承诺的成果无法兑现。

作者认为主要的原因在于组织忽视了给开发人员提供高效的工作环境。在转型中引入了太多的
新流程和新工具，给每日的工作带来了更大的复杂性和阻力。

### 高效工作环境中的开发人员

作者假想了高效工作环境中的开发人员应该是怎么样的

- 通过项目管理工具**了解工作进展和当天工作内容**
- 研发**环境自动升级**到最新版本，**CI/CD**通过
- **拉取最新代码**，增量代码可以通过本地环境部署和单元测试**快速验证**
- 对于依赖其他团队提供的功能，能够通过开发者中心方便找到**文档说明和API接口规格**
- 几小时**不受打扰**的专注于自己的工作
- 中途**放松休息**
- 提交代码，通过一系列**自动化检查**校验后部署到生产环境，**灰度发布**给用户，同时**监控业务和运营指标变化**。

作者也列举了低效开发人员的工作是什么样的，大致就是陷入无尽的生产告警、问题排错、低效的沟通、无意义的会议等等，
我就不详细列出了，大部分人看看自己每天的工作就有同感。

对比我工作的体会，我将重点内容以黑体标识了，这些目标可以用来评估我们建立DevOps工作流程是否高效，
至于用什么工具和方式达到是次要的，经常回顾一下可以是我们不至于陷入技术细节忘了初心。

作者提到低效的组织工作起来摩擦更大，久而久之这种工作方式就被工程师习以为常了，大家会认为软件开发本身就是这样，
这种现象叫习得性无助（learned helpless）。
为了改变现状，低效的组织更倾向于寻求度量开发者开发产出的方法，常见的反模式是度量代码行数、
需求产出数量和花费大量精力找出表现不好的人（Damn right，怀疑作者在监控我们^_^）。

作者提出更好的办法是关注于如何创造更好的高效工作环境，更顺畅的工作环境会给员工更多创新的机会。
我赞同作者的观点，这是一种基于对员工信任的管理思路，
但是国内大部分公司依然是一种不信任员工的思路在管理，依然把人视为流水线上的工具，
我们为“鞭策”那一小部分员工牺牲了大多数人的创造性。

作者接下来列举了Spotify的例子，Spotify为了解决内部工具碎片化和开发信息不透明的问题，
开发并开源了一套开发者门户项目[Backstage](https://backstage.io/docs/getting-started/)，
去Backstage的官网看了下，提供服务目录、项目模板、技术文档等功能，同时有快速增长的插件生态，
这跟我们去年在建设的架构资产管理平台很多诉求是相通的，肯定有许多理念和能力可以借鉴，
要是早半年看到就更好了，虽然现在看到也不晚。

接下来是最重要的部分，作者阐述了应该从哪些方面入手提高组织效能。

常见的是建立DevOps文化等等，四个关键度量指标（前置时间、部署频率、平均修复时间、变更失败率）。

作者研究了一系列反馈回路，推荐专注于优化这些反馈回路来提高效率

| 反馈回路                       | 低效组织        | 高效组织                     |
| ------------------------------ | --------------- | ---------------------------- |
| 验证本地代码修改的正确性       | 2分钟           | 5-15秒（根据技术栈）         |
| 找到故障的根因                 | 4-7天           | 1天                          |
| 验证组件集成的正确性           | 3天-2周         | 2小时                        |
| 验证变更是否满足非功能要求     | 3个月           | 1天到1周（根据变更范围大小） |
| 进入新团队后能够有效产出的时间 | 2个月           | 4周                          |
| 内部技术咨询得到回答的时间     | 1-2周           | 30分钟                       |
| 在生产发布一个新的服务         | 2-4个月         | 3天                          |
| 验证一项变更对客户有用         | 6个月或者没办法 | 1-4周 （根据变更范围大小）   |

就我的体会而言，这些选项并不是都特别典型，标准各个企业也不尽相同，例如验证本地代码正确性这一点，
有的团队可能只要编译通过、代码检查通过就达标，有的团队可能要求主流程测试通过，
因此需要根据企业的情况调整，这里的关键是借鉴这样贴合实际并且量化的思路，发掘适合自己的关键反馈回路。

最后，作者介绍了微反馈回路，就是开发者每天会做几十上百次的操作，例如修复bug时反复运行单元测试、
提交代码修改到本地环境、刷新环境数据等等，这些微反馈回路太细小通常容易被企业忽略。

这让我深有体会，2020年我们的DevOps团队花了一个多月的时间，将每次代码提交增量检查的耗时从几分钟优化到几秒，
耗费的精力不少，但是在企业层面很难看到这样工作的成绩和效果具体是什么。作者后续也提到了这一点，
大致就是说每次优化的时间乘以频率就会有很大不同，从逻辑上我是认同的，但是实际工作中，高层管理人员尤其是非技术的管理人员，
对此通常毫无理解。

好了，这篇文章的信息含量其实很大，作者在文章中附带了许多概念的介绍链接
例如习惯性无助、DevOps文化等等，也推荐了Accelerate等书籍和文章，值得慢慢消化，
今天的文章评析就到这里，期待作者后续的文章。