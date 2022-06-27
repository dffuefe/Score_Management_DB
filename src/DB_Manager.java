import java.sql.*;
import java.util.Vector;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DB_Manager {
    //마리아 DB 접속을위한 위치와 정보들
    private static String className = "org.mariadb.jdbc.Driver";
    private static String url = "jdbc:mariadb://127.0.0.1:3306/mydb";
    private static String ID = "root";
    private static String PW = "system";

    static {
        try {
            Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn(){
            Connection con = null;

            try{
                con = DriverManager.getConnection(url, ID, PW);
            }catch (Exception e){
                e.printStackTrace();
            }
            return con;
        }

    public int insertScore(Score score){
        Connection con = null;
        Statement stmt = null;

        int result = 0;
        try{
            con = getConn();
            stmt = con.createStatement();
            String sql = "INSERT INTO SCORE VALUES('" + score.getName() + "' , " + score.getKor() + " , "
                    + score.getEng() + " , "+ score.getMath() + " , " + score.getTotal() + " , " + score.getAverage()
                    +  ")";
            result = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally { //반드시 실행
            try{
                if(stmt != null){
                    stmt.close();
                }
                if(con != null){
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public int deleteScore(Score score){
        Connection con = null;
        Statement stmt = null;

        int result = 0;
        try{
            con = getConn();
            stmt = con.createStatement();
            String sql = "delete from score where name = '" + score.getName() + "'";;
            result = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally { //반드시 실행
            try{
                if(stmt != null){
                    stmt.close();
                }
                if(con != null){
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public int updateScore(Score score){
        Connection con = null;
        Statement stmt = null;

        int result = 0;
        try{
            con = getConn();
            stmt = con.createStatement();
            String sql = "Update score set kor = " + score.getKor() + ", eng =" + score.getEng() +
                    ", math =" + score.getMath() + ", total =" + score.getTotal() + ", average = " + score.getAverage()
                    + " where name ='" + score.getName() + "'";
            result = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally { //반드시 실행
            try{
                if(stmt != null){
                    stmt.close();
                }
                if(con != null){
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Vector getScore(){
        Vector data = new Vector();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = getConn();
            String sql = "select name, kor, eng, math, total, average, \r\n" +
                    "rank() over(order by total desc) as ranking\r\n" +
                    "from score";
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){//rs에 저장된 데이터를 한 행씩 불러온다
                String name = rs.getString("name");
                int ranking = rs.getInt("ranking");
                int kor = rs.getInt("kor");
                int eng = rs.getInt("eng");
                int math = rs.getInt("math");
                int total = rs.getInt("total");
                int average = rs.getInt("average");

                Vector row = new Vector();
                row.add(ranking);
                row.add(name);
                row.add(kor);
                row.add(eng);
                row.add(math);
                row.add(total);
                row.add(average);
                data.add(row);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
