package com.feifan.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Mybatis 拦截器 允许使用的插件来拦截的方法包括 Executor (update, query, flushStatements, commit, rollback, getTransaction, close,
 * isClosed) ParameterHandler (getParameterObject, setParameters)
 * ResultSetHandler(handleResultSets,handleOutputParameters) StatementHandler (prepare, parameterize, batch, update,
 * query)
 * 
 * @author Donald
 * @date 2019/01/21
 * @see
 */
@Configuration
@Component
@Intercepts({@Signature(type = Executor.class, method = "query",
    args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MysqlInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // TODO Auto-generated method stub
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement)args[0];
        String mapperMethod = ms.getId();
        RawSqlSource sqlSource = (RawSqlSource)ms.getSqlSource();
        BoundSql sql = sqlSource.getBoundSql(null);
        LOGGER.info("mybatis intercept sql:{},Mapper方法是：{}", sql.getSql(), mapperMethod);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // TODO Auto-generated method stub
        LOGGER.info("MysqlInterCeptor plugin>>>>>>>{}", target);
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
        String dialect = properties.getProperty("dialect");
        LOGGER.info("mybatis intercept dialect:>>>>>>>{}", dialect);
    }

}
