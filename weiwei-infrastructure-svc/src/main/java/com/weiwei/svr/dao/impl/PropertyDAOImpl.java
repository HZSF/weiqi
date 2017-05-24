package com.weiwei.svr.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.IPropertyDAO;
import com.weiwei.svr.dbmodel.PropertyLendJoinTable;
import com.weiwei.svr.dbmodel.PropertySellJoinTable;

@Service
public class PropertyDAOImpl extends JdbcDaoSupport implements IPropertyDAO{
	@Autowired
	public PropertyDAOImpl(DataSource dataSource){
		setDataSource(dataSource);
	}

	public void insertSellProperty(String username, String region, String category, int area,
			int levels, int ask_price, String description, Timestamp time) {
		String sql_insert = "INSERT INTO dedecmsv57utf8sp1.property_sell (region_id, category_id, area, levels, ask_price, description, submit_time, customer_id) "
				+ "VALUES((SELECT id FROM dedecmsv57utf8sp1.regions WHERE name = ? ), (SELECT id FROM dedecmsv57utf8sp1.property_category WHERE name = ? ), ?, ?, ?, ?, ?,"
				+ "(SELECT id FROM customers WHERE userName= ? ))";
		getJdbcTemplate().update(sql_insert, new Object[]{region, category, area, levels, ask_price, description, time, username});
	}

	public List<?> getSellingPropertyListLimitedNumber(int n) {
		String sql = "SELECT p.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_sell ps WHERE r.id=ps.region_id AND p.id=ps.category_id LIMIT "+ n;
		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(PropertySellJoinTable.class));
	}

	public List<?> getSellingPropertyListLimitedNumberStartFromId(int startId, int n) {
		String sql = "SELECT p.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_sell ps WHERE r.id=ps.region_id AND p.id=ps.category_id AND p.id > ? LIMIT "+ n;
		return getJdbcTemplate().query(sql, new Object[]{startId}, new BeanPropertyRowMapper(PropertySellJoinTable.class));
	}
	
	public void insertLendProperty(String username, String region, String category, int area,
			int levels, int ask_price, String description, Timestamp time) {
		String sql_insert = "INSERT INTO dedecmsv57utf8sp1.property_lend (region_id, category_id, area, levels, ask_price, description, submit_time, customer_id) "
				+ "VALUES((SELECT id FROM dedecmsv57utf8sp1.regions WHERE name = ? ), (SELECT id FROM dedecmsv57utf8sp1.property_category WHERE name = ? ), ?, ?, ?, ?, ?,"
				+ "(SELECT id FROM customers WHERE userName= ? ))";
		getJdbcTemplate().update(sql_insert, new Object[]{region, category, area, levels, ask_price, description, time, username});
	}
	
	public List<?> getLendingPropertyListLimitedNumber(int n) {
		String sql = "SELECT p.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_lend ps WHERE r.id=ps.region_id AND p.id=ps.category_id LIMIT "+ n;
		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(PropertyLendJoinTable.class));
	}

	public List<?> getLendingPropertyListLimitedNumberStartFromId(int startId, int n) {
		String sql = "SELECT p.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_lend ps WHERE r.id=ps.region_id AND p.id=ps.category_id AND p.id > ? LIMIT "+ n;
		return getJdbcTemplate().query(sql, new Object[]{startId}, new BeanPropertyRowMapper(PropertyLendJoinTable.class));
	}

	public int addToFavoritesSell(String username, int property_sell_id) {
		String sql_insert = "INSERT INTO dedecmsv57utf8sp1.property_favorite_sell (customer_id, property_sell_id) "
				+ "SELECT * FROM (SELECT (SELECT id FROM dedecmsv57utf8sp1.customers WHERE userName = ?), ?) AS tmp "
				+ "WHERE NOT EXISTS (SELECT pf.id FROM dedecmsv57utf8sp1.property_favorite_sell pf, dedecmsv57utf8sp1.customers cust "
				+ "WHERE pf.customer_id = cust.id AND cust.`userName` = ? AND pf.is_cancelled IS NULL AND pf.property_sell_id=?) LIMIT 1";
		return getJdbcTemplate().update(sql_insert, new Object[]{username, property_sell_id, username, property_sell_id});
	}
	
	public int cancelFavoritesSell(String username, int property_sell_id) {
		String sql = "UPDATE property_favorite_sell SET is_cancelled='1' WHERE property_sell_id=? AND is_cancelled IS NULL "
				+ "AND customer_id=(SELECT id FROM dedecmsv57utf8sp1.customers WHERE userName=?)";
		return getJdbcTemplate().update(sql, property_sell_id, username);
	}
	
	public List<?> getFavoriteSellListLimitedNumber(String username, int n) {
		String sql = "SELECT p.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_sell ps, customers cust, property_favorite_sell pfs "
				+ "WHERE cust.userName = ? AND cust.id=pfs.customer_id AND pfs.is_cancelled IS NULL AND pfs.property_sell_id=ps.id "
				+ "AND r.id=ps.region_id AND p.id=ps.category_id LIMIT "+ n;
		return getJdbcTemplate().query(sql, new Object[]{username}, new BeanPropertyRowMapper(PropertySellJoinTable.class));
	}
	
	public List<?> getFavoriteSellListLimitedNumberStartFromId(String username, int startId, int n) {
		String sql = "SELECT p.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_sell ps, customers cust, property_favorite_sell pfs "
				+ "WHERE cust.userName = ? AND cust.id=pfs.customer_id AND pfs.is_cancelled IS NULL AND pfs.property_sell_id=ps.id "
				+ "AND r.id=ps.region_id AND p.id=ps.category_id AND p.id > ? LIMIT "+ n;
		return getJdbcTemplate().query(sql, new Object[]{username, startId}, new BeanPropertyRowMapper(PropertySellJoinTable.class));
	}
	
	public int addToFavoritesLend(String username, int property_lend_id) {
		String sql_insert = "INSERT INTO dedecmsv57utf8sp1.property_favorite_lend (customer_id, property_lend_id) "
				+ "SELECT * FROM (SELECT (SELECT id FROM dedecmsv57utf8sp1.customers WHERE userName = ?), ?) AS tmp "
				+ "WHERE NOT EXISTS (SELECT pf.id FROM dedecmsv57utf8sp1.property_favorite_lend pf, dedecmsv57utf8sp1.customers cust "
				+ "WHERE pf.customer_id = cust.id AND cust.`userName` = ? AND pf.is_cancelled IS NULL AND pf.property_lend_id=?) LIMIT 1";
		return getJdbcTemplate().update(sql_insert, new Object[]{username, property_lend_id, username, property_lend_id});
	}
	
	public int cancelFavoritesLend(String username, int property_lend_id) {
		String sql = "UPDATE property_favorite_lend SET is_cancelled='1' WHERE property_lend_id=? AND is_cancelled IS NULL "
				+ "AND customer_id=(SELECT id FROM dedecmsv57utf8sp1.customers WHERE userName=?)";
		return getJdbcTemplate().update(sql, property_lend_id, username);
	}
	
	public List<?> getFavoriteLendListLimitedNumber(String username, int n) {
		String sql = "SELECT p.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_lend ps, customers cust, property_favorite_lend pfs "
				+ "WHERE cust.userName = ? AND cust.id=pfs.customer_id AND pfs.is_cancelled IS NULL AND pfs.property_lend_id=ps.id "
				+ "AND r.id=ps.region_id AND p.id=ps.category_id LIMIT "+ n;
		return getJdbcTemplate().query(sql, new Object[]{username}, new BeanPropertyRowMapper(PropertyLendJoinTable.class));
	}
	
	public List<?> getFavoriteLendListLimitedNumberStartFromId(String username, int startId, int n) {
		String sql = "SELECT p.id, r.name AS address_area, p.name AS category_name, ps.area, ps.levels, ps.ask_price, ps.description, ps.submit_time "
				+ "FROM regions r, property_category p, property_lend ps, customers cust, property_favorite_lend pfs "
				+ "WHERE cust.userName = ? AND cust.id=pfs.customer_id AND pfs.is_cancelled IS NULL AND pfs.property_lend_id=ps.id "
				+ "AND r.id=ps.region_id AND p.id=ps.category_id AND p.id > ? LIMIT "+ n;
		return getJdbcTemplate().query(sql, new Object[]{username, startId}, new BeanPropertyRowMapper(PropertyLendJoinTable.class));
	}
}
