package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        // データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "@qsYd6%cMpUG6_m");

            // DBとやりとりする窓口（PreparedStatementオブジェクト）を用意する
            String Sql = "SELECT * FROM person WHERE id = ?";
            pstmt = con.prepareStatement(Sql);

            // SQLを実行する
            System.out.print("検索キーワードを入力してください > ");
            int input = keyInNum();

            // PreparedStatementオブジェクトの?に値をセット
            pstmt.setInt(1, input);
            rs = pstmt.executeQuery();

            // 結果を表示する
            while (rs.next()) {
                // Name列の値を取得
                String name = rs.getString("Name");
                // age列の値を取得
                int age = rs.getInt("age");

                // 取得した値を表示
                System.out.println(name);
                System.out.println(age);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバーのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            // 接続を閉じる
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときににエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStatmentを閉じるときににエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if(con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * キーボードから入力された値をintで返す　引数：なし　戻り値」int
     */
    private static int keyInNum() {
        String line = null;
        int result = 0;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
            result = Integer.parseInt(line);
        } catch (IOException e) {
        } catch(NumberFormatException e) {
        }
        return result;
    }
}