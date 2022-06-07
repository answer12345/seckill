秒杀方案：
1.悲观锁：
    防止超卖（对库存数量进行判断）：update t_seckill_goods set stock_count=stock_count-1 where id=#{id} and stock_count > 0
    防止用户重复下单：将用户id和商品id添加成唯一索引
    利用mysql自带的事务，读写锁完成阻塞。只适合小秒杀量场景

2.redis预减库存+rabbitMQ
    启动时候就将所有的商品和数量放入redis
    将购买成功的用户放入redis，防止重复购买
    redis进行欲减库存
    将请求交给rabbitMQ进行异步处理

3.接口限流防刷
    计数器算法：