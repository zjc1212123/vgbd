package vgbd.util;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * Create By hui on 9/8/19. Mysql 数据库连接工具类
 */
public class DbUtil {
    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object    类
     * @param fieldName 属性
     * @return 属性
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 设置参数
     *
     * @param object    对象
     * @param fieldName 属性
     * @param value     值
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = getDeclaredField(object, fieldName);
        assert field != null;
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取参数
     *
     * @param object    对象
     * @param fieldName 属性
     * @return 值
     */
    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        assert field != null;
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取数据库连接
     *
     * @return connection对象
     */
    public static Connection getConnection() {
        Connection connection = null;
        Properties properties = new Properties();
        InputStream in = DbUtil.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(in);
            String url = properties.get("mysql.url").toString();
            String username = properties.get("mysql.username").toString();
            String password = properties.get("mysql.password").toString();
            String driver = properties.get("mysql.driver").toString();
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     *
     * @param con connection对象
     * @param ps  preparedStatement对象
     * @param rs  resultSet对象
     */
    public static void release(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通用查询
     *
     * @param clazz 类
     * @param sql   sql语句
     * @param args  查询参数
     * @param <T>   泛型
     * @return 泛型对象
     */
    public static <T> T query(Class<T> clazz, String sql, Object... args) {
        T object = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsm;
        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            rsm = rs.getMetaData();
            Map<String, Object> map = new HashMap<>(16);
            if (rs.next()) {
                for (int i = 0; i < rsm.getColumnCount(); i++) {
                    String column = rsm.getColumnLabel(i + 1);
                    Object o = rs.getObject(i + 1);
                    map.put(column, o);
                }
            }
            if (map.size() > 0) {
                object = clazz.newInstance();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String field = entry.getKey();
                    Object value = entry.getValue();
                    setFieldValue(object, field, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.release(con, ps, rs);
        }
        return object;
    }

    /**
     * 通用集合查询
     *
     * @param clazz 类
     * @param sql   sql语句
     * @param args  查询参数
     * @param <T>   泛型
     * @return 泛型集合
     */
    public static <T> List<T> list(Class<T> clazz, String sql, Object... args) {
        List<T> objects = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsm;
        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            rsm = rs.getMetaData();
            Map<String, Object> map = new HashMap<>(16);
            while (rs.next()) {
                T object = null;
                for (int i = 0; i < rsm.getColumnCount(); i++) {
                    String column = rsm.getColumnLabel(i + 1);
                    Object o = rs.getObject(i + 1);
                    map.put(column, o);
                }
                if (map.size() > 0) {
                    object = clazz.newInstance();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String field = entry.getKey();
                        Object value = entry.getValue();
                        setFieldValue(object, field, value);
                    }
                }
                objects.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.release(con, ps, rs);

        }
        return objects;
    }

    /**
     * 通用插入
     *
     * @param sql  sql语句
     * @param args 查询参数
     * @return ture/fasle
     */
    public static boolean save(String sql, Object... args) {
        boolean state = true;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.execute();
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        } finally {
            DbUtil.release(con, ps, null);
        }
        return state;
    }

    /**
     * 通用删除
     *
     * @param sql  sql语句
     * @param args 查询参数
     * @return ture/fasle
     */
    public static boolean remove(String sql, Object... args) {
        boolean state = true;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.execute();
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        } finally {
            DbUtil.release(con, ps, null);
        }
        return state;
    }

    /**
     * 通用更新
     *
     * @param sql  sql语句
     * @param args 查询参数
     * @return ture/fasle
     */
    public static boolean update(String sql, Object... args) {
        boolean state = true;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.execute();
        } catch (Exception e) {
            state = false;
            e.printStackTrace();
        } finally {
            DbUtil.release(con, ps, null);
        }
        return state;
    }

    /**
     * 通用统计
     *
     * @param sql  sql语句
     * @param args 查询参数
     * @return int
     */
    public static int count(String sql, Object... args) {
        int count = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.release(con, ps, rs);
        }
        return count;
    }

    /* 测试方法 */
    public static void main(String[] args) throws Exception {
        Connection connection = DbUtil.getConnection();
        if (connection != null) {
            System.out.println("连接成功");
        } else {
            System.out.println("连接失败");
        }
        connection.close();
    }
}
