package com.weiwei.svr.dao.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;

import com.weiwei.svr.dao.ICustomerDAO;
import com.weiwei.svr.dbmodel.ProvinceTable;
import com.weiwei.svr.dbmodel.TableCities;
import com.weiwei.svr.dbmodel.TableCustomers;

import java.security.SecureRandom;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class CustomerDAO extends JdbcDaoSupport implements ICustomerDAO{

	@Autowired
	public CustomerDAO(DataSource dataSource){
		setDataSource(dataSource);
	}
	
	public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    // The following constants may be changed without breaking existing hashes.
    public static final int SALT_BYTE_SIZE = 24;
    public static final int HASH_BYTE_SIZE = 24;
    public static final int PBKDF2_ITERATIONS = 1000;
    
    
/*
	public boolean authenticatePassword(String username, String password) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM customer WHERE userName=? AND password=?";
		List<?> result = getJdbcTemplate().query(sql, new String[]{username, password}, new BeanPropertyRowMapper(TableCustomer.class));
		if(result == null || result.size() == 0)
			return false;
		else
			return true;
	}
	public boolean existingCustomer(String username) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM customer WHERE userName=?";
		List<?> result = getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableCustomer.class));
		if(result == null || result.size() == 0)
			return false;
		else
			return true;
	}

	public void registerNewCustomer(String username, String password, String phoneNumber, Timestamp date) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO customer (userName, password, mobilePhonenumber, registerDate) VALUES (?, ?, ?, ?)";
		getJdbcTemplate().update(sql, new Object[]{username, password, phoneNumber, date});
	}

	public List<?> findCustomerIdByUsername(String username) {
		// TODO Auto-generated method stub
		String sql = "SELECT id FROM customer WHERE userName=?";
		return getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableCustomer.class));
	}

	public List<?> findCustomerByUsername(String username) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM customer WHERE userName=?";
		return getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableCustomer.class));
	}
	
	public void updateInfoByUsername(String username, String columnName, String value){
		String sql = "UPDATE customer SET " + columnName + "=? WHERE userName=?";
		getJdbcTemplate().update(sql, value, username);
	}
	
	public List<?> findCustomerByUserId(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM customer WHERE id=?";
		return getJdbcTemplate().query(sql, new Object[]{id}, new BeanPropertyRowMapper(TableCustomer.class));
	}
	*/
	
	
	
	
	
	public boolean authenticatePassword(String username, String password) {
		String sql = "SELECT password, salt FROM customers WHERE userName=?";
		List<TableCustomers> result = getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableCustomers.class));
		if(result == null || result.size() == 0)
			return false;
		else{
			try{
				return validatePassword(password, result.get(0).getPassword(), result.get(0).getSalt());
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public boolean existingCustomer(String username) {
		// TODO Auto-generated method stub
		String sql = "SELECT id FROM customers WHERE userName=?";
		List<?> result = getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableCustomers.class));
		if(result == null || result.size() == 0)
			return false;
		else
			return true;
	}
	
	public boolean existingMobilePhone(String phoneNumber){
		String sql = "SELECT id FROM customers WHERE mobilePhonenumber=?";
		List<?> result = getJdbcTemplate().query(sql, new String[]{phoneNumber}, new BeanPropertyRowMapper(TableCustomers.class));
		if(result == null || result.size() == 0)
			return false;
		else
			return true;
	}
	
	public void registerNewCustomer(String username, String password, String phoneNumber, String companyName, Timestamp date) {
		// TODO Auto-generated method stub
		try{
			String[] hashsalt = createHash(password);
			String hash = hashsalt[0];
			String salt = hashsalt[1];
			String sql = "INSERT INTO customers (userName, password, mobilePhonenumber, companyName, registerDate, salt) VALUES (?, ?, ?, ?, ?, ?)";
			getJdbcTemplate().update(sql, new Object[]{username, hash, phoneNumber, companyName, date, salt});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public List<?> findCustomerIdByUsername(String username) {
		// TODO Auto-generated method stub
		String sql = "SELECT id FROM customers WHERE userName=?";
		return getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableCustomers.class));
	}
	
	public List<?> findCustomerByUsername(String username) {
		// TODO Auto-generated method stub
		String sql = "SELECT cust.*, p.province_name, c.city_name FROM customers cust, province p, cities c WHERE cust.userName=? AND p.id=cust.companyProvinceAddressid AND c.id = cust.companyCityAddressid";
		//String sql = "SELECT * FROM customers WHERE userName=?";
		return getJdbcTemplate().query(sql, new String[]{username}, new BeanPropertyRowMapper(TableCustomers.class));
	}
	
	public void updateInfoByUsername(String username, String columnName, String value){
		String sql = "UPDATE customers SET " + columnName + "=? WHERE userName=?";
		getJdbcTemplate().update(sql, value, username);
	}
	
	public List<?> findCustomerByUserId(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM customers WHERE id=?";
		return getJdbcTemplate().query(sql, new Object[]{id}, new BeanPropertyRowMapper(TableCustomers.class));
	}
	
    public static String[] createHash(String password)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return createHash(password.toCharArray());
    }
    /**
     * Returns a salted PBKDF2 hash of the password.
     *
     * @param   password    the password to hash
     * @return              a salted PBKDF2 hash of the password
     */
    public static String[] createHash(char[] password)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return new String[]{toHex(hash), toHex(salt)};
    }
	/**
     * Validates a password using a hash.
     *
     * @param   password        the password to check
     * @param   correctHash     the hash of the valid password
     * @return                  true if the password is correct, false if not
     */
    public static boolean validatePassword(String password, String correctHash, String salt)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        return validatePassword(password.toCharArray(), correctHash, salt);
    }

    /**
     * Validates a password using a hash.
     *
     * @param   password        the password to check
     * @param   correctHash     the hash of the valid password
     * @return                  true if the password is correct, false if not
     */
    public static boolean validatePassword(char[] password, String correctHash, String salt)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        byte[] salt_byte = fromHex(salt);
        byte[] hash_byte = fromHex(correctHash);
        // Compute the hash of the provided password, using the same salt, 
        // iteration count, and hash length
        byte[] testHash = pbkdf2(password, salt_byte, PBKDF2_ITERATIONS, hash_byte.length);
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash_byte, testHash);
    }
    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line 
     * system using a timing attack and then attacked off-line.
     * 
     * @param   a       the first byte array
     * @param   b       the second byte array 
     * @return          true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }
    /**
     *  Computes the PBKDF2 hash of a password.
     *
     * @param   password    the password to hash.
     * @param   salt        the salt
     * @param   iterations  the iteration count (slowness factor)
     * @param   bytes       the length of the hash to compute in bytes
     * @return              the PBDKF2 hash of the password
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
        throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }
    /**
     * Converts a string of hexadecimal characters into a byte array.
     *
     * @param   hex         the hex string
     * @return              the hex string decoded into a byte array
     */
    private static byte[] fromHex(String hex)
    {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }
    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param   array       the byte array to convert
     * @return              a length*2 character string encoding the byte array
     */
    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
	
	/*
	public void reOrganise(){
		String sql = "SELECT id, password FROM customers WHERE id='41'";
		List<PasswordBean> passwords = getJdbcTemplate().query(sql, new BeanPropertyRowMapper(PasswordBean.class));
		try{
			for(PasswordBean bean : passwords){
				String password = bean.getPassword();
				String[] hashsalt = createHash(password);
				String hash = hashsalt[0];
				String salt = hashsalt[1];
				String sql_update = "UPDATE customers SET password=?, salt=? WHERE id='"+bean.getId()+"'";
				getJdbcTemplate().update(sql_update, hash, salt);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String[] createHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		return createHash(password.toCharArray());
	}

    public String[] createHash(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[24];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(password, salt, 1000, 24);
        // format iterations:salt:hash
        return new String[]{toHex(hash), toHex(salt)};
    }
    private byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return skf.generateSecret(spec).getEncoded();
    }
    private String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }*/
    
    public List<?> findProvinceList() {
    	String sql = "SELECT * FROM province";
    	return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(ProvinceTable.class));
	}
    public List<?> findCityListByProvinceId(int province_id){
    	String sql = "SELECT * FROM cities WHERE province_id=?";
    	return getJdbcTemplate().query(sql, new Object[]{province_id}, new BeanPropertyRowMapper(TableCities.class));
    }

	public void updateInfoAreaByUsername(String username, int province_id, int city_id) {
		String sql = "UPDATE customers SET companyProvinceAddressid=?, companyCityAddressid=? WHERE userName=?";
		getJdbcTemplate().update(sql, province_id, city_id, username);
	}
	
	public void updatePortraitImg(String username, InputStream imageIs, int size){
		String imgname;
		if(username.length()>45){
			imgname = username.substring(0, 45);
		}else{
			imgname = username;
		}
		InputStream is1 = null;
		InputStream is2 = null;
		try{
			LobHandler lobHandler = new DefaultLobHandler();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = imageIs.read(buffer)) > -1 ) {
			    baos.write(buffer, 0, len);
			}
			baos.flush();
			is1 = new ByteArrayInputStream(baos.toByteArray()); 
			is2 = new ByteArrayInputStream(baos.toByteArray()); 
			SqlLobValue lobValue = new SqlLobValue(is1, size, lobHandler);
			SqlLobValue lobValue1 = new SqlLobValue(is2, size, lobHandler);
			getJdbcTemplate().update(
					"INSERT INTO portrait (img_title, img_data, customer_id) VALUES (?, ?, (SELECT id FROM customers WHERE userName='"+username+"')) "
							+ "ON DUPLICATE KEY UPDATE img_data=?",
			         new Object[] {imgname, lobValue, lobValue1},
			         new int[] {Types.VARCHAR, Types.BLOB, Types.BLOB});
		  } catch (DataAccessException e) {
		   System.out.println("DataAccessException " + e.getMessage());
		  } catch (Exception e){
			  e.printStackTrace();
		  } finally{
			  if (imageIs != null) {
					try {
						imageIs.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			  if (is1 != null) {
					try {
						is1.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			  if (is2 != null) {
					try {
						is2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		  }
	}
	
	public byte[] getPortraitImg(String username){
		String sql = "select img_data from portrait where customer_id = (SELECT id FROM customers WHERE userName='"+username+"')";
		return getJdbcTemplate().queryForObject(sql, byte[].class);
	}
}
