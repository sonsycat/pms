/**
 * 
 */
package com.zjry.advanceQuery.interceptor;

import java.lang.reflect.Field;
import java.sql.Connection;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjry.advanceQuery.constant.ConstantsAdvanceQuery;
import com.zjry.advanceQuery.util.AdvanceQueryUtil;
import com.zjry.advanceQuery.vo.AdvanceQueryVo;
import com.zjry.common.config.ZjRyConfig;
import com.zjry.common.core.redis.RedisCache;

/**
 * @author yangtao
 * 高级查询拦截器
 */
@Component
@Intercepts(
	{
		@Signature(type=StatementHandler.class, method="prepare", 
				args = {Connection.class, Integer.class})
	}
)
public class AdvanceQueryInterceptor implements Interceptor {

	@Autowired
	private RedisCache redisCache;
	@Autowired
	private ZjRyConfig zjRyConfig;
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		String sqlId = mappedStatement.getId();
		// 非高级查询操作，直接跳过
		if(redisCache.getCacheObject(zjRyConfig.getName() + ":" + ConstantsAdvanceQuery.REDIS_KEY_PREFIX + sqlId) == null) {
			return invocation.proceed();
		}
		
		AdvanceQueryVo advanceQueryVo = (AdvanceQueryVo) redisCache.getCacheObject(zjRyConfig.getName() + ":" + ConstantsAdvanceQuery.REDIS_KEY_PREFIX + sqlId);
        // 获取原始sql
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();

        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        // 修改为高级查询sql
        field.set(boundSql, AdvanceQueryUtil.getAdvanceQuerySql(sql, advanceQueryVo));
        
        redisCache.deleteObject(zjRyConfig.getName() + ":" + ConstantsAdvanceQuery.REDIS_KEY_PREFIX + sqlId);
		return invocation.proceed();
	}
	
}
