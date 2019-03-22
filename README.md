# 售票系统 ticketSale

## 1、开发背景

&#160; &#160; &#160; &#160;随着科学技术的发展，计算机领域不断取得日新月异的研究成果，在日常生活中随处都离不开网络。尤其是在交通发达的今天，新时代的人们越来越依赖于方便、快捷的网络购票，计算机优势更加体现出来。在数字化的今天，为了使旅客更方便地购票、使售票系统更加利于管理，开发售票系统更加显得重要。

&#160; &#160; &#160; &#160;本系统主要为了更好地实现火车票的自动化销售，给旅客提供一个方便快捷的购票平台。同时也为系统管理员提供一个井然有序的管理平台, 防止手工管理混乱，避免一些人为的错误。

&#160; &#160; &#160; &#160;说明：本系统是根据我的已有项目[火车票售票系统](https://github.com/Linxfeng/TrainTicketSale)进行改造升级的，所以主要偏向于技术层面，业务方面沿用之前的项目。

## 2、需求分析
- 前台售票网站需求：
	- 1.用户可以以游客身份浏览网站页面、查询车票相关信息（车次、车程、起点终点、票价、余票等）；
	- 2.用户可以登陆/注册，登陆后的用户在填写了个人信息后可以进行选票购票；
    - 3.一个用户支持添加多个乘客，为乘客购票；
	- 4.用户购票时下订单，还可以取消订单，查看订单等；
	- 5.用户查询车票时，可以根据输入的起始点和终点智能查询不同出行路线，并可以根据不同条件进行排序
    - 6.用户可以根据起点和终点查询路线，支持直达和换乘。
		
- 后台管理系统需求：
	- 1.车辆/车次的管理；
	- 2.起始点和终点的路线管理；
	- 3.车票票价和余票管理；
	- 4.根据用户的出行推送广告。

## 3、系统设计

设计流程图：

![系统交互图](https://raw.githubusercontent.com/Linxfeng/images/master/image043.png)
![设计流程图](https://raw.githubusercontent.com/Linxfeng/images/master/image045.png)

## 4、技术栈&开发环境

**技术栈：**
本项目采用spring cloud微服务架构，使售票系统实现服务间相互解耦，实现高可用，使用spring cloud的优点非常多，其组件也非常多。在本项目中，我们需要使用到spring cloud的5大组件：
- 服务发现——Netflix Eureka
- 客服端负载均衡——Netflix Ribbon
- 分布式配置——Spring Cloud Config
- 服务网关——Netflix Zuul
- 断路器——Netflix Hystrix

**本地开发环境（windos 10）**
- jdk 1.8
- MySQL 5.7
- maven 3.5
- redis 3.2
- git 2.21
- rabbitmq 

**应用部署环境（linux）**
- jdk 1.8



## 5、功能设计&服务拆分

**功能设计：**

- 1.登陆/注册模块
   - ⑴ 用户注册，前后端校验，保证用户名的唯一性
   - ⑵ 用户登录，前后端校验
- 2.车票查询模块
	- ⑴ 根据出发地和目的地的站点查询火车票，第一次查询从数据库中读取，存入缓存，当该车次的相关区段售出车票时，更新缓存中的数据
	- ⑵ 查询结果可以根据车程、票价、出发时间进行排序
	- ⑶ 根据出发地和目的地的站点自动匹配出行路线（包括中途上车，中途转车，中途下车）
	- ⑷ 可以根据不同车辆种类、座位种类和余票、区段票价进行选择
- 3.订单模块
	- ⑴ 可以更换座位类型，用户类型（成人票/学生票），修改乘客
	- ⑵ 查看订单（历史订单/未出行订单/未付款订单）
	- ⑶ 取消订单（退款）
	- ⑷ 用户出票
- 4.个人信息模块
	- ⑴ 用户信息（唯一性）修改
	- ⑵ 乘客信息（有多个）修改/添加
- 5.广告模块
	- ⑴ 后台可以发布公告/通知
	- ⑵ 用户登陆后可以收到公告通知，实时显示在首页上
- 6.车辆/车次管理模块
	- ⑴ 根据车次编号查询、修改车辆信息，每个车次都有不定数量的站点
	- ⑵ 根据不同站点规定不同到达时间，根据不同区段设定不同票价
	- ⑶ 区段余票/可售票数的管理
	- ⑷ 管理员添加/修改车次信息时，将途经每个站点的信息都录入数据库。
- 7.后台管理员登录模块


**服务拆分：**

由于本项目是采用spring cloud微服务架构，所以我们可以根据项目的功能模块来拆分服务。

- 用户服务——user： 
主要是用户登录/注册、管理员登陆和个人/乘客信息管理等功能。

- 订单服务——order：
为用户提供订单的创建/查询/取消等操作。

- 广告服务——advert：
根据用户的出行，推送广告/消息；推送系统公告。

- 车票服务——ticket：
主要是对车票/票价/余票等车票信息进行增删改查；提供列车信息，进行增删改查，包含车站信息的查询及管理。

这里我本来想把车票、车站、列车拆分成3个微服务的，但是由于这3个模块直接解耦非常难，依赖性太强。对于这种售票系统来讲，业务比较简单，拆分太散反而有弊端，所以干脆放在同一个服务中。

## 6、设计数据库

开发中...