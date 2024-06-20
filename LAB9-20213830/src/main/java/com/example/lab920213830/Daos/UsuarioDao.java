package com.example.lab920213830.Daos;

import com.example.lab920213830.Beans.Licencia;
import com.example.lab920213830.Beans.Pais;
import com.example.lab920213830.Beans.Rol;
import com.example.lab920213830.Beans.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDao extends DaoBase{
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        String sql =  "SELECT * FROM usuario u \n"
                    + "left join rol r on (r.idrol = u.rol_idrol)\n"
                    + "left join licencia l on (l.idlicencia = u.licencia_idlicencia)\n"
                    + "left join pais p on (p.idpais = l.pais_idpais)";
        try (Connection conn = this.getConection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                fetchEmployeeData(usuario, rs);
                listaUsuarios.add(usuario);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaUsuarios;
    }

    private void fetchEmployeeData(Usuario usuario, ResultSet rs) throws SQLException {

        usuario.setIdUsuario(rs.getInt(1));
        usuario.setNombres(rs.getString(2));
        usuario.setApellidos(rs.getString(3));
        usuario.setTelefono(rs.getInt(4));
        Rol rol = new Rol();
        rol.setIdRol(rs.getInt(5));
        rol.setNombreRol(rs.getString("nombre_rol"));

        usuario.setRol(rol);
        Licencia licencia = new Licencia();
        licencia.setIdLicencia(rs.getInt("u.idlicencia"));
        licencia.setCategoria(rs.getString("l.categoria"));
        licencia.setFechaEmision(rs.getString("l.fecha_emision"));
        licencia.setFechaCaducidad(rs.getString("l.fecha_caducidad"));
        usuario.setLicencia(licencia);
        Pais pais = new Pais();
        pais.setIdPais(rs.getInt("pais_idpais"));
        pais.setNombrePais(rs.getString("nombre_pais"));
        licencia.setPais(pais);

    }
    public boolean validarUsuarioPasswordHashed(String username, String password){

        String sql = "SELECT * FROM credenciales_usuario where correo = ? and contraseña_encriptada = sha2(?,256)";

        boolean exito = false;

        try(Connection connection = getConection();
            PreparedStatement pstmt = connection.prepareStatement(sql)){

            pstmt.setString(1,username);
            pstmt.setString(2,password);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    exito = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exito;
    }

    public Usuario obtenerUsuario(int idUsuario) {

        Usuario usuario = null;

        String sql =  "SELECT * FROM usuario u \n"
                + "left join rol r on (r.idrol = u.rol_idrol)\n"
                + "left join licencia l on (l.idlicencia = u.licencia_idlicencia)\n"
                + "left join pais p on (p.idpais = l.pais_idpais)";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    usuario = new Usuario();
                    fetchEmployeeData(usuario, rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return usuario;
    }


}
