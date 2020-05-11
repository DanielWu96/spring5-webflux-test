daniel初探webflux

springboot版本：2.2.6.RELEASE
springcloud版本：Hoxton.SR3

写此项目的初衷是公司一个内部基础服务需要改造，从vertx改到webflux（我也不知道为啥要改，可能是vertx社区不活跃）。
所以此项目包括一个基础微服务所需要的基本功能：
* 1、过滤器（webflux没有拦截器interceptor的概念）。
* 2、全局异常处理
* 3、接口级别的限流和项目级别的限流
* 4、reactor模式的redis调用以及redis多数据源的配置
* 5、restful接口（当时为了使用起来熟练方便，本项目接口使用是基于springmvc的注解。也可以使用HandlerFunction和RouterFuntion实现接口功能和路由）

项目重构完成大概花了2个星期。正如Spring官网所言，webflux学习成本确实不低（本人略愚），首先要对JavaNIO(多路复用I
/O)有所了解；接着结合Netty深入了解reactor模式；理解reactor模式了，再学习Reactor框架（框架名称就叫Reactor。。。）；
最后再看spring对reactor框架的整合。

本人对webflux框架也是在探索阶段，欢迎大家对这个项目提出疑问和错误，我也会对这个项目持续更新，尽量对平时使用到的其他中间件和框架进行整合。