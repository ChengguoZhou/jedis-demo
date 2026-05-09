# Redis 常见命令练习题（业务场景版）

## 1. 通用命令

1. 创建 key "name"="Jack" 和 key "age"=30。
```bash
# 批量创建name='Jack' age=30
MSET name Jack age 30
```
2. 查看当前数据库中所有 key。
```bash
# 查看数据库中所有key
KEYS *
```
3. 删除 key "age"，并验证是否删除成功。
```bash
# 删除key='age'
DEL age
# 判断age是否存在
EXISTS age
```
4. 判断 key "name" 是否存在。
```bash
# 判断key='name'是否存在
EXISTS name
```
5. 给 key "session:123" 设置过期时间 15 秒。
```bash
# 先创建key='session:123'记录
SET session:123 root
# 设置过期时间15s
EXPIRE session:123 15
```
6. 查看 key "session:123" 的剩余有效期。
```bash
# 查看剩余有效期
TTL session:123
```
7. 使用 help 查看 KEYS 命令的详细用法。
```bash
# 查看keys命令详细用法
HELP KEYS
```

## 2. String 类型命令

1. 设置 key "product:1" 的值为 "Laptop"。
```bash
SET product:1 Laptop
```
2. 获取 key "product:1" 的值。
```bash
GET product:1
```
3. 批量设置 key "k1"="v1", "k2"="v2", "k3"="v3"。
```bash
MSET k1 v1 k2 v2 k3 v3
```
4. 批量获取 key "k1", "k2", "k3" 的值。
```bash
MGET k1 k2 k3
```
5. 对 key "inventory" 执行自增操作。
```bash
# 先创建inventory、price、rating用于5-7题
MSET inventory 0 price 100 rating 0.7
# 对key'inventory'执行自增操作
INCR inventory
```
6. 对 key "price" 执行自增 50 操作。
```bash
INCRBY price 50
```
7. 对 key "rating" 执行浮点自增 0.5。
```bash
INCRBYFLOAT rating 0.5
```
8. 使用 SETNX 设置 key "category" 为 "Electronics"，保证只有当 key 不存在时才生效。
```bash
SETNX category Electronics 
```
9. 使用 SETEX 设置 key "token:xyz"，值为 "abcd"，有效期 60 秒。
```bash
SETEX token:xyz 60 abcd 
```

## 3. Hash 类型命令

1. 创建 hash "user:1"，字段 "name"="Alice"， "age"="25"。
```bash
HSET user:1 name Alice age 25
```
2. 获取 "user:1" 中字段 "name" 的值。
```bash
HGET user:1 name
```
3. 批量设置 "user:2" 的字段 "name"="Bob", "age"="28", "city"="Shanghai"。
```bash
HMSET user:2 name Bob age 28 city Shanghai
```
4. 批量获取 "user:2" 的字段 "name" 和 "age"。
```bash
HMGET user:2 name age
```
5. 获取 "user:1" 中所有字段名。
```bash
HKEYS user:1
```
6. 获取 "user:2" 中所有字段值。
```bash
HVALS user:2
```
7. 对 "user:1" 的字段 "age" 自增 3。
```bash
HINCRBY user:1 age 3
```
8. 使用 HSETNX 为 "user:1" 添加字段 "vip"="yes"，保证字段不存在时才添加。
```bash
HSETNX user:1 vip yes
```

## 4. List 类型命令

1. 向列表 "tasks" 左侧插入 "task1", "task2"。
```bash
LPUSH tasks task1 task2
```
2. 向列表 "tasks" 右侧插入 "task3", "task4"。
```bash
RPUSH tasks task3 task4
```
3. 从左侧弹出列表 "tasks" 的元素。
```bash
LPOP tasks
# 此时弹出的是task2
```
4. 从右侧弹出列表 "tasks" 的元素。
```bash
RPOP tasks
# 此时弹出的应是task4
```
5. 获取列表 "tasks" 的索引 0 到 2 的元素。
```bash
LRANGE tasks 0 2
# 1) "task1" 2) "task3"
```
6. 使用 BLPOP 对列表 "queue" 阻塞弹出元素。(假设等待60s)
```bash
BLPOP queue 60
```

## 5. Set 类型命令

1. 向集合 "friends:alice" 添加 "bob", "carol", "dave"。
```bash
SADD friends:alice bob carol dave
```
2. 向集合 "friends:bob" 添加 "carol", "erin", "frank"。
```bash
SADD friends:bob carol erin frank
```
3. 获取集合 "friends:alice" 的元素个数。
```bash
SCARD friends:alice
```
4. 获取集合 "friends:alice" 和 "friends:bob" 的交集。
```bash
SINTER friends:alice friends:bob
```
5. 获取集合 "friends:alice" 和 "friends:bob" 的差集。
```bash
SDIFF friends:alice friends:bob
```
6. 获取集合 "friends:alice" 和 "friends:bob" 的并集。
```bash
SUNION friends:alice friends:bob
```
7. 判断 "carol" 是否是 "friends:alice" 的成员。
```bash
SISMEMBER friends:alice carol
```
8. 从 "friends:alice" 移除 "carol"。
```bash
SREM friends:alice carol
```

## 6. SortedSet 类型命令(注意：SortedSet默认排序为升序，如果需要降序在命令Z后面加入REV即可 eg.ZADD->ZREVADD)

1. 向 SortedSet "leaderboard" 添加元素：
   - "Jack" score 85
   - "Lucy" score 89
   - "Rose" score 82
   - "Tom" score 95
   - "Jerry" score 78
   - "Amy" score 92
   - "Miles" score 76
```bash
ZADD leaderboard 85 Jack 89 Lucy 82 Rose 95 Tom 78 Jerry 92 Amy 76 Miles
```
2. 删除 "Tom"。
```bash
ZREM leaderboard Tom
# 返回1，代表删除成功
```
3. 获取 "Amy" 的分数。
```bash
ZSCORE leaderboard Amy
# 返回92
```
4. 获取 "Rose" 的排名。

```bash
ZREVRANK leaderboard Rose
# 从大到小排序，Redis默认索引从0开始，返回3，实际排第4
```

​			5.查询80分以下有几个学生 

```bash
ZCOUNT leaderboard 0 80
# 返回2
```

​			6.给Amy同学加2分 

```bash
ZINCRBY leaderboard 2 Amy
# 94
```

​			7.查出成绩前3名的同学 

```bash
ZREVRANGE leaderboard 0 2
# 1) "Amy" 2) "Lucy" 3) "Jack"

```

​			8.查出成绩80分以下的所有同学

```bash
ZRANGEBYSCORE leaderboard 0 80
# 1) "Miles" 2) "Jerry"

```

