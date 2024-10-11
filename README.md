介绍：由本人开发并维护的一套基于Spring Security的权限管理脚手架,适合后端快速进行开发

技术栈：Spring Booot、Spring Security + JWT、MapStruct、Validation、Mybatis-Plus、Redis

主要模块：
基于RBAC权限控制模型来实现对用户-角色-权限之间的关系协调
自定义过滤器链逐步处理认证请求，使用spel机制解析鉴权字段，自定义鉴权逻辑
使用Validation的全局传参异常处理，基于MapStruct的Bean属性转换

项目结构

![image](https://github.com/user-attachments/assets/62ed021e-54d3-4f9e-8d6b-dd5be324a0da)

