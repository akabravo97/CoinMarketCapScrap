
package testconn;
import org.jsoup.*;
import java.sql.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
public class TestConn {
    public static Elements price;
    public static Elements volume;
    public static Elements change;
    public static Elements names;
    public static Elements cap;
    public static Elements supply;
    public static String insertData=null;
    public static void Connection(){
        java.sql.Connection conn=null;
        java.sql.Statement stmt=null;
        try{
            Class.forName("org.sqlite.JDBC");
            String url="jdbc:sqlite:test.db";
            conn=DriverManager.getConnection(url);
            stmt=conn.createStatement();
            String createTable="create table if not exists coin (id int auto_increment,name varchar(255),marketCap varchar(255),price varchar(255),volume varchar(255),supply varchar(255),change varchar(255));";
            stmt.execute(createTable);
            for(int i=0;i<names.size();i++){
                insertData="insert into coin (name,marketCap,price,volume,supply,change) values('"+names.get(i).text()+"','"+cap.get(i).text()+"','"+price.get(i).text()+"','"+volume.get(i).text()+"','"+supply.get(i).text()+"','"+change.get(i).text()+"')";
                stmt.execute(insertData);
            }
            System.out.println("DB Success!!");
            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        
        try{
            Document doc=Jsoup.connect("https://coinmarketcap.com/").get();
            names=doc.getElementsByClass("currency-name-container");
            cap=doc.getElementsByClass("no-wrap market-cap text-right");
            price=doc.getElementsByClass("price");
            volume=doc.getElementsByClass("volume");
            supply=doc.getElementsByClass("no-wrap text-right circulating-supply");
            change=doc.getElementsByAttributeValue("data-timespan","24h");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        Connection();
    }
    
}
