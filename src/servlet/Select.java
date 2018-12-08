package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Test1
 */
    /**
     * @see HttpServlet#HttpServlet()
     */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @WebServlet("/Select")
    public class Select extends HttpServlet {
    	private static final long serialVersionUID = 1L;
     
    	protected void doGet(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
           //形式指定
    		response.setContentType("text/html charset=UTF-8");
    		PrintWriter out = response.getWriter();

    		// (1) 接続用のURIを用意する(必要に応じて認証指示user/passwordを付ける)
            String url = "jdbc:sqlserver://DESKTOP-SKOHAJK:1433;databaseName=旅行アプリDB;user=user;password=user";
           
            ResultSet rs = null;  //serect結果表示用
    		Connection conn = null;  //クラス用変数
            
            try {
            	//クラス指定　SQLServerの定型文
            	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            	
                // (2) DriverManagerクラスのメソッドで接続する
                conn = DriverManager.getConnection(url);                
                Statement stmt = conn.createStatement();
                
                //sql文を定義する    データ抽出用
                String sql ="SELECT * FROM Table_sample WHERE id >= ?";          //検索場所を指定
                
                // (3) SQL送信用インスタンスの作成            
                PreparedStatement st = conn.prepareStatement(sql);
                
                //idが2以上を選択
            	st.setInt(1,1);
            	//SERECT実行
            	rs = st.executeQuery();
                
            	//結果の全出力
        		out.println("<table border=\"1\" width=\"500\" cellspacing=\"0\" cellpadding=\"5\" bordercolor=\"#333333\">");
        		out.println("<tr>"
				+"<th>id"
				+"<th>日付"
				+"<th>外出先"
				+"<th>コメント"
//				+"<th>持ち物"
				+"</tr>");

        		
        		while(rs.next()) {
            		//変数宣言、セット
            		int id = rs.getInt("id");
            		String d =rs.getString("日付");
            		String ikisaki =rs.getString("外出先");
            		String c =rs.getString("コメント");
//            		String item =rs.getString("持ち物");
            		
            		//画面表示
            		out.println("<tr>"
    				+"<th>" + id
    				+"<th>" + d
    				+"<th>" + ikisaki
    				+"<th>" + c
//    				+"<th>" + item
    				+"<th><a href=\"http://localhost:8080/sample_act/Update?id="
    				+id
    				+"&ikisaki="
    				+ikisaki
    				+"\">編集</a>"
    				+"<form method=\"get\" action=\"Select\"  type=\"text\" name=\"id\">"
//    				+"<th>編集"
    				+"<th><a href=\"http://localhost:8080/sample_act/Delete?id="
    				+id
    				+"&ikisaki="
    				+ikisaki
    				+"\">削除</a>"
    				+"<form method=\"get\" action=\"Select\"  type=\"text\" name=\"id\">"
    				+"</tr>");
    		}
				out.println("</table>");
				
/*            		out.println("<p>"
            				+ "id:"+ id + ",日付:" + d + ",外出先:"+ ikisaki + ",コメント:" + c + ",持ち物:" + item);
            		}
            		*/

                
            //開いたものを閉じる
           rs.close();
           stmt.close();
           
           //例外処理
        }catch (ClassNotFoundException e){
            out.println("ClassNotFoundException:" + e.getMessage());
          }catch (SQLException e){
            out.println("SQLException:" + e.getMessage());
          }catch(Exception e){
        	  out.println("Exception:" + e.getMessage());
        	  
        	  //最終処理
        	  }finally {
        		  try{
        		        if (conn != null){
        		          conn.close();
        		        }
        		      }catch (SQLException e){
        		        out.println("SQLException:" + e.getMessage());
        		      }
        		    }
            //画面表示
            out.println("<br><a href=\"file:///C:/Users/repens/eclipse-workspace/sample_act/WebContent/TOP.html\">TOPへ戻る</a>");
            out.println("</body>");
            out.println("</html>"); 
            }
    	}