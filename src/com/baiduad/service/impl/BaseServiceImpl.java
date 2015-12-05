package com.baiduad.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;




import com.baiduad.model.Page;
import com.baiduad.util.Logger;

@Component
public abstract class BaseServiceImpl<T> {
	
	private static final Logger log = new Logger(BaseServiceImpl.class);
	
	@Autowired
	protected JdbcTemplate jt;
	
	protected boolean add(String sql,Object...obj) {
		int temp = this.jt.update(sql, obj);
		if (temp > 0) {
			log.error(sql+"插入记录成功",null);
		} else {
			log.error(sql+"插入记录失败",null);
		}
		return temp > 0;
	}
	
	protected boolean del(String sql,Object...obj) {
		int temp = this.jt.update(sql, obj);
		if (temp > 0) {
			log.error(sql+"删除记录成功",null);
		} else {
			log.error(sql+"删除记录失败",null);
		}
		return temp > 0;
	}
	
	protected boolean update(String sql,Object...obj){
		int temp = 0;
		if(obj==null){
			temp =  this.jt.update(sql);
		}else{
			temp =  this.jt.update(sql,obj);
		}
		 
		if (temp > 0) {
			log.error(sql+"更新记录成功",null);
		} else {
			log.error(sql+"更新记录失败",null);
		}
		return temp > 0;
	}
	
	protected  List  queryAll(String sql,Object...obj) {
		try {
			if(obj==null || obj.length ==0){
				return (List) this.jt.query(sql, getRowMapper());
			}
			return  (List) this.jt.query(sql, obj, getRowMapper());
		} catch (Exception e) {
			log.error(sql+"获取结果集出错",e);
			return null;
		}
	}
	
	
	protected  T  getObject(String sql,Object...obj) {
		try {
			return (T) this.jt.queryForObject(sql, obj, getRowMapper());
		} catch (Exception e) {
			return null;
		}
	}
	
	  //查询所总条数  
	protected int count(String sql,Object[] param){  
       sql = "select count(1) from ("+sql+") cc";
       System.out.println(sql);
       if(param!=null && param.length > 0)
         return this.jt.queryForObject(sql,Integer.class, param);
       return this.jt.queryForObject(sql,Integer.class);
    }  
      
	/**分页获取数据
	 * @param sql
	 * @param param
	 * @param pages
	 * @return
	 */
	protected Page<T> pageBysql(String sql,Object[] param,Integer pages){
		Page<T> page = new Page<T>(pages);
		int count = count(sql,param);
		if(count > 0){
			page.setTotalCount(count);
			sql= sql+" limit "+page.getStart()+","+page.getPageSize(); 
			System.out.println(sql);
			List<T> list = queryAll(sql,param);
			page.setList(list);
		}
		return page;
	}
	
	public abstract RowMapper getRowMapper();
}
