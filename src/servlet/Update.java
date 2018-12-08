package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
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
    @WebServlet("/Update")
    public class Update extends HttpServlet {
    	private static final long serialVersionUID = 1L;
     
    	protected void doGet(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
           //�`���w��
    		response.setContentType("text/html charset=UTF-8");
    		PrintWriter out = response.getWriter();


    		// (1) �ڑ��p��URI��p�ӂ���(�K�v�ɉ����ĔF�؎w��user/password��t����)
            String url = "jdbc:sqlserver://DESKTOP-SKOHAJK:1433;databaseName=旅行アプリDB;user=user;password=user";

    		Connection conn = null;  //�N���X�p�ϐ�
            ResultSet rs = null;  //serect���ʕ\���p

            try {
            	//�N���X�w��@SQLServer�̒�^��
            	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            	
                // (2) DriverManager�N���X�̃��\�b�h�Őڑ�����
                conn = DriverManager.getConnection(url);                
                Statement stmt = conn.createStatement();
                
                            
                String sql ="SELECT * FROM Table_sample WHERE id=" + request.getParameter("id") +"AND 外出先="+ "'"+ request.getParameter("ikisaki") + "'";          //�����ꏊ���w��
                // (3) SQL���M�p�C���X�^���X�̍쐬            
                PreparedStatement st = conn.prepareStatement(sql);

                //SERECT���s
            	rs = st.executeQuery();
            	
				out.println("<h4>登録内容変更</h4>");
                
            	//���ʂ̏o��
        		out.println("<table border=\"1\" width=\"500\" cellspacing=\"0\" cellpadding=\"5\" bordercolor=\"#333333\">");
        		out.println("<tr>"
				+"<th>id"
				+"<th>日付"
				+"<th>外出先"
				+"<th>コメント"
				+"</tr>");

        		
        		while(rs.next()) {
            		//�ϐ��錾�A�Z�b�g
            		int id = rs.getInt("id");
            		String d =rs.getString("日付");
            		String ikisaki =rs.getString("外出先");
            		String c =rs.getString("コメント");
            		
            		//��ʕ\��
            		out.println("<tr>"
    				+"<th>" + id
    				+"<th>" + d
    				+"<th>" + ikisaki
    				+"<th>" + c
//    				+"<th>" + item
    				+id
    				+"</tr>");
    				out.println("</table>");

    				
    				out.println("<p>上記内容を変更する場合は入力してください。</p>");
    				
    				//��������
    				out.println("<form method=\"get\" action=\"Update2\"><!-- action�ɃT�[�u���b�g�����w��-->"
    				+"id:<INPUT type=\"text\" value=\""
    				+id
    				+ "\" name=\"new_id\"><br><br>"
    				+"日付:<INPUT type=\"text\" value=\""
    				+d
    				+ "\" name=\"new_d\"><br><br>"
    				+"外出先:<INPUT type=\"text\" value=\""
    				+ikisaki
    				+ "\" name=\"new_ikisaki\"><br><br>"
    				+"コメント:<INPUT type=\"text\" value=\""
    				+c
    				+ "\" name=\"new_c\"><br><br>"

    				
    				+"<INPUT type=\"hidden\" name=\"id\""
    				+ "value = \""
    				+id
    				+"\">"
    				+"<INPUT type=\"hidden\" name=\"ikisaki\""
    				+ "value = \""
    				+ikisaki
    				+"\">"

    				
    				+"<INPUT type=\"submit\" value=\"更新\"></form>");
    				//�����܂�
    				
        		}
        		
                
                //sql�����`����    �f�[�^�}���p
//              String sql ="UPDATE Table_sample SET �R�����g= '"+ st + "' WHERE id= 8";   //�X�V�@SET�ōX�V����ӏ���I���@WHERE�ōX�V����f�[�^���w��

                //�f�[�^�폜
//            	 stmt.executeUpdate(sql);
                
            //�J�������̂����
           stmt.close();
           
           //��O����
        }catch (ClassNotFoundException e){
            out.println("ClassNotFoundException:" + e.getMessage());
          }catch (SQLException e){
            out.println("SQLException:" + e.getMessage());
          }catch(Exception e){
        	  out.println("Exception:" + e.getMessage());
        	  
        	  //�ŏI����
        	  }finally {
        		  try{
        		        if (conn != null){
        		          conn.close();
        		        }
        		      }catch (SQLException e){
        		        out.println("SQLException:" + e.getMessage());
        		      }
        		    }
            //��ʕ\��
            out.println("<br><a href=\"http://localhost:8080/sample_act/Select\">登録一覧へ戻る</a>");
            out.println("</body>");
            out.println("</html>"); 
            }
    	
    	}