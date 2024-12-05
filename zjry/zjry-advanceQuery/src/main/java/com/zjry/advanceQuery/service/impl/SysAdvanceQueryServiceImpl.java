/**
 * 
 */
package com.zjry.advanceQuery.service.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.MappedStatement;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjry.advanceQuery.constant.ConstantsAdvanceQuery;
import com.zjry.advanceQuery.domain.SysAdvanceQueryConfig;
import com.zjry.advanceQuery.mapper.SysAdvanceQueryConfigMapper;
import com.zjry.advanceQuery.service.ISysAdvanceQueryService;
import com.zjry.advanceQuery.vo.AdvanceQueryVo;
import com.zjry.common.config.ZjRyConfig;
import com.zjry.common.core.redis.RedisCache;

/**
 * @author yangtao
 * 高级查询service实现
 */
@Service
public class SysAdvanceQueryServiceImpl implements ISysAdvanceQueryService {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	@Autowired
	private RedisCache redisCache;
	@Autowired
	private SysAdvanceQueryConfigMapper sysAdvanceQueryConfigMapper;
	@Autowired
	private ZjRyConfig zjRyConfig;
	
	@Override
	public List<Map> list(AdvanceQueryVo advanceQueryVo) {
		SysAdvanceQueryConfig config = sysAdvanceQueryConfigMapper.selectConfigByRouteId(advanceQueryVo.getRouteId().toLowerCase());
		if(config == null) {
			logger.error("此功能未配置...");
			return null;
		}
		String sqlId = config.getSqlId();
		logger.info("AdvanceQuery sqlId: " + sqlId);
		MappedStatement statement = sqlSessionTemplate.getConfiguration().getMappedStatement(sqlId);
		if(statement == null) {
			logger.error("sqlId["+sqlId+"] does not exits...");
			return null;
		}
		try {
			String mapperClassName = sqlId.substring(0, sqlId.lastIndexOf("."));
			String methodName = sqlId.substring(sqlId.lastIndexOf(".") + 1);
			logger.info("mapperClassName: " + mapperClassName + ", methodName: " + methodName);
			Class<?> cls = Class.forName(mapperClassName);
			Object mapperClass = sqlSessionTemplate.getMapper(cls);
			Class<?> entityClass = this.getMethodParamType(cls, methodName);
			if(entityClass == null) {
				logger.error("获取方法["+methodName+"]参数类型错误！！");
				return null;
			}
			Method method = cls.getMethod(methodName, entityClass);
			// 高级查询sqlid以及查询条件，做标记
			redisCache.setCacheObject(zjRyConfig.getName() + ":" + ConstantsAdvanceQuery.REDIS_KEY_PREFIX + sqlId, advanceQueryVo);
			return (List<Map>) method.invoke(mapperClass, entityClass.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取某个类中，某个方法的参数类型
	 * @param cls
	 * @param methodName
	 * @return
	 */
	private Class<?> getMethodParamType(Class<?> cls, String methodName) {
		Method[] methods = cls.getMethods();
		for(Method method : methods) {
			if(methodName.equals(method.getName())) {
				Class<?> paramType = (Class<?>) method.getGenericParameterTypes()[0];
				return paramType;
			}
		}
		return null;
	}
	
}
