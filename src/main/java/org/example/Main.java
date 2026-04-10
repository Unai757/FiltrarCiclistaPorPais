package org.example;

import java.sql.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        //Creamos la sentencia sql donde mostramos los datos del ciclista y el nombre de su equipo, también pedimos el pais para filtrar.
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingresa la nacionalidad que quieras usar para filtrar: ");
        String pais = sc.nextLine();
        String sql = "SELECT CICLISTA.ID_Ciclista AS ciclista_id,CICLISTA.NOMBRE as ciclista_nombre, CICLISTA.NACIONALIDAD AS nacionalidad, " +
                "CICLISTA.EDAD AS EDAD,EQUIPO.NOMBRE as equipo_nombre " +
                "FROM CICLISTA JOIN EQUIPO USING (ID_EQUIPO) " +
                "WHERE EQUIPO.PAIS=?";

        try (Connection conn = DriverManager.getConnection(
                DBConfig.getUrl(),
                DBConfig.getUser(),
                DBConfig.getPassword());
             //Ponemos aqui el preparedstatement y resultset en el try with resources para que se cierren automaticamente



             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pais);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("--- CICLISTAS DEL EQUIPO DE " + pais.toUpperCase() + " ---");
                while (rs.next()) {
                    int id = rs.getInt("ciclista_id");
                    String ciclista = rs.getString("ciclista_nombre");
                    String nacionalidad = rs.getString("nacionalidad");
                    int edad = rs.getInt("edad");
                    String equipo = rs.getString("equipo_nombre");

                    System.out.println(id + " - " + ciclista + " - " + nacionalidad + " - " + edad + " - " + equipo);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error de base de datos: " + e.getMessage());

        }
    }
}

