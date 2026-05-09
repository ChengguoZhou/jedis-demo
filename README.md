# jedis-demo

`jedis-demo` 是一个基于 Java 17、Maven 和 Jedis 的 Redis 入门示例项目。项目通过 Jedis 连接池封装 Redis 连接，并使用 JUnit 测试用例演示 Redis Hash 类型数据的写入与读取。

## 资料来源
【黑马程序员Redis入门到实战教程，深度透析redis底层原理+redis分布式锁+企业解决方案+黑马点评实战项目】 https://www.bilibili.com/video/BV1cr4y1671t/?share_source=copy_web&vd_source=e7679fc1e3d878557754a0d970bef26b

## 项目功能

- 使用 Jedis 连接 Redis 服务
- 封装 `JedisPool` 连接池，统一获取 Redis 连接
- 演示 Redis Hash 数据结构的基础操作
- 提供 Redis 基础命令练习资料，便于配合代码学习

## 技术栈

| 技术 | 版本 / 说明 |
| --- | --- |
| Java | 17 |
| Maven | 本地 Maven 环境，项目未包含 Maven Wrapper |
| Jedis | 7.1.0 |
| JUnit Jupiter | 6.1.0-RC1 |
| Redis | 默认连接端口 `6379` |

## 项目结构

```text
jedis-demo
├── pom.xml
├── README.md
├── src
│   ├── Appendix
│   │   ├── 01-Redis#U5feb#U901f#U5165#U95e8.pptx
│   │   └── redis#U57fa#U672c#U6307#U4ee4#U7ec3#U4e60.md
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── util
│   │               ├── Constants.java
│   │               └── JedisConnectionFactory.java
│   └── test
│       └── java
│           └── com
│               └── zcg
│                   └── test
│                       └── JedisTest.java
└── target
```

> 说明：`target/` 为 Maven 编译输出目录，通常不需要提交到代码仓库。

## 核心代码说明

### `Constants.java`

用于维护 Redis 连接相关常量：

```java
public static final String VIRTUAL_MACHINE_IP = "192.168.42.130";
public static final int JEDIS_PORT = 6379;
```

当前项目默认连接的 Redis 地址为：

```text
192.168.42.130:6379
```

### `JedisConnectionFactory.java`

用于创建 Jedis 连接池，并对外提供统一的 Redis 连接获取方法。

当前连接池配置如下：

| 配置项 | 当前值 | 说明 |
| --- | --- | --- |
| `maxTotal` | 8 | 最大连接数 |
| `maxIdle` | 8 | 最大空闲连接数 |
| `minIdle` | 0 | 最小空闲连接数 |
| `maxWaitMillis` | 1000 | 获取连接的最长等待时间，单位毫秒 |
| `timeout` | 1000 | Redis 连接超时时间，单位毫秒 |
| `password` | `root` | Redis 连接密码 |

获取 Jedis 连接的方法：

```java
public static Jedis getJedis(){
    return jedisPool.getResource();
}
```

### `JedisTest.java`

测试类通过 `JedisConnectionFactory` 获取连接，并演示 Hash 类型操作。

测试流程：

1. 获取 Redis 连接
2. 进行密码认证
3. 选择 Redis 的 `0` 号数据库
4. 写入 Hash 数据
5. 查询 Hash 数据并打印结果
6. 关闭 Jedis 连接，将连接归还给连接池

示例测试数据：

```java
jedis.hset("user:1", "name", "Jack");
jedis.hset("user:1", "age", "21");
Map<String, String> userMap = jedis.hgetAll("user:1");
System.out.println(userMap);
```

预期控制台输出类似：

```text
{name=Jack, age=21}
```

> `Map` 的输出顺序不固定，也可能显示为 `{age=21, name=Jack}`。

## 环境准备

### 1. 安装 Java 17

确认本机已安装 JDK 17：

```bash
java -version
```

### 2. 安装 Maven

确认本机已安装 Maven：

```bash
mvn -version
```

### 3. 准备 Redis 服务

项目默认连接虚拟机中的 Redis：

```text
host: 192.168.42.130
port: 6379
password: root
```

如果 Redis 服务地址、端口或密码不同，请修改以下文件：

```text
src/main/java/com/util/Constants.java
src/main/java/com/util/JedisConnectionFactory.java
```

如果使用 Docker 快速启动一个带密码的 Redis，可参考：

```bash
docker run -d \
  --name redis \
  -p 6379:6379 \
  redis:7 \
  redis-server --requirepass root
```

如果使用本机 Redis，需要将 `Constants.java` 中的 IP 修改为：

```java
public static final String VIRTUAL_MACHINE_IP = "127.0.0.1";
```

## 快速开始

### 1. 克隆或进入项目目录

```bash
cd jedis-demo
```

### 2. 编译项目

```bash
mvn clean compile
```

### 3. 运行测试

运行全部测试：

```bash
mvn test
```

只运行 `JedisTest`：

```bash
mvn -Dtest=JedisTest test
```

只运行 `testHash` 方法：

```bash
mvn -Dtest=JedisTest#testHash test
```

## Redis Hash 示例说明

本项目中的 `testHash()` 方法使用了 Redis Hash 类型。

Hash 适合存储对象结构，例如用户信息、商品信息等。

示例数据结构：

```text
key: user:1
fields:
  name: Jack
  age: 21
```

对应 Redis 命令为：

```bash
HSET user:1 name Jack
HSET user:1 age 21
HGETALL user:1
```

## 附录资料

`src/Appendix` 目录中包含 Redis 学习资料：

| 文件 | 说明 |
| --- | --- |
| `01-Redis#U5feb#U901f#U5165#U95e8.pptx` | Redis 快速入门课件 |
| `redis#U57fa#U672c#U6307#U4ee4#U7ec3#U4e60.md` | Redis 基本命令练习 |

> 如果文件名显示为 `#Uxxxx` 形式，可能是压缩包解压时的中文文件名编码问题，可根据实际含义重命名为中文文件名。

## 常见问题

### 1. 连接 Redis 失败

可能原因：

- Redis 服务未启动
- Redis IP 或端口配置错误
- 虚拟机网络不可达
- Redis 未开放远程访问
- 防火墙未放行 `6379` 端口

排查方式：

```bash
redis-cli -h 192.168.42.130 -p 6379 -a root ping
```

正常情况下应返回：

```text
PONG
```

### 2. Redis 认证失败

如果出现认证失败，请确认 Redis 的密码是否为 `root`。

当前项目中密码配置位置：

```java
jedisPool = new JedisPool(poolConfig, VIRTUAL_MACHINE_IP, JEDIS_PORT, 1000, "root");
```

测试类中也调用了：

```java
jedis.auth("root");
```

如果连接池已经完成认证，可根据实际 Redis 版本和配置决定是否保留测试类中的重复认证逻辑。

### 3. Maven 无法识别 JUnit 5 测试

如果执行 `mvn test` 后未正确运行 JUnit 5 测试，可以在 `pom.xml` 中补充 Maven Surefire 插件，例如：

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>3.2.5</version>
    </plugin>
  </plugins>
</build>
```

## 后续优化建议

- 将 Redis IP、端口、密码抽取到配置文件或环境变量中
- 避免在代码中明文硬编码 Redis 密码
- 增加 String、List、Set、Sorted Set 等更多 Redis 数据结构示例
- 增加连接池关闭方法，便于应用退出时释放资源
- 移除无用或重复的测试依赖，保持 `pom.xml` 简洁
- 将 `target/`、`.idea/` 等本地生成目录从提交内容中排除

## 许可证

本项目当前未声明许可证。如需开源，请根据实际用途补充 `LICENSE` 文件。
