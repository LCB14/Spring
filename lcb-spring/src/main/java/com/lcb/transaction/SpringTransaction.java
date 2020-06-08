package com.lcb.transaction;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author changbao.l Date: 2020-06-08 Time: 10:28 上午
 * @version $
 */
public class SpringTransaction {
    public static void main(String[] args) {
        /**
         *  TransactionDefinition  -- 事务定义
         *  PlatformTransactionManager -- 事务管理器
         *  TransactionStatus -- 事务运行状态
         */
        TransactionTemplate transactionTemplate = new TransactionTemplate();
    }
}
