## 模块介绍
本模块用于模拟第三方组件，基于SpringFactories机制可将本模块的类注入到上级项目的spring容器中，也可通过@Import注解加载类到spring容器中

### 1、SpringFactories

spring.factories中配置了的类，项目中可直接注入使用

### 2、Import注解

注解方式需要在启动类配置注解，让上级项目注入我们需要的bean

@Import注解提供了三种用法

- @Import一个普通类 spring会将该类加载到spring容器中

- @Import一个类，该类实现了ImportBeanDefinitionRegistrar接口，在重写的registerBeanDefinitions方法里面，能拿到BeanDefinitionRegistry bd的注册器，能手工往beanDefinitionMap中注册 beanDefinition

- @Import一个类 该类实现了ImportSelector 重写selectImports方法该方法返回了String[]数组的对象，数组里面的类都会注入到spring容器当中

具体如下：
```
@Import(TestAutoConfiguration1.class)
```
但是一般在使用过程中我们会通过一层封装来间接加载这些bean

```
@EnableTestAutoConfig
```
