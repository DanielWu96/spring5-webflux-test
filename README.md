daniel初探webflux

springboot版本：2.2.6.RELEASE
springcloud版本：Hoxton.SR3

写此项目的初衷是公司一个内部基础服务需要改造，从vertx改到webflux（我也不知道为啥要改，可能是vertx社区不活跃？）。
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

2020-05-13
* 今天随便写了2.2.2.RELEASE版本的openFeign和zuul（顺便把源码看了一遍，嘻嘻），发现都不支持reactive模式，feign不支持可以自己包装一层实现伪多路复用，但是zuul
不是很好实现（如果有想法可以一起交流探讨）。最后看spring官网发现gateway支持reactive，底层使用servlet，但使用了webflux，多嵌套了一层框架，提供了抽象负载均衡，
提供了抽象流控，并默认实现了RedisRateLimiter。下次找时间试一下。（虽然openfeign和zuul不支持！但是代码还是要保留的。。。毕竟是结晶，
也可以让其他入坑的小伙伴少走弯路）