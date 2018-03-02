package com.bograntex.bograntexAdmin.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bograntex.bograntexAdmin.data.AppDataConnect;
import com.bograntex.bograntexAdmin.utils.CriptUtils;
import com.vaadin.server.VaadinSession;

public final class User {
	
	private String empresa;
    private String role;
    private int idUser;
    private String usuario;
    private String firstName;
    private String lastName;
    private String title;
    private boolean male;
    private String email;
    private String location;
    private String phone;
    private Integer newsletterSubscription;
    private String website;
    private String bio;
    private String password;

	private User createUser(ResultSet result) throws SQLException {
		User usuario = new User();
		usuario.setIdUser(result.getInt("ID_USER"));
		usuario.setUsuario(result.getString("USUARIO"));
		usuario.setPassword(result.getString("SENHA"));
		return usuario;
	}
	
	public User getUser(String usuarioNome) throws SQLException {
		Connection conn = AppDataConnect.getInstanceERP();
		String SQL = "SELECT * FROM BOCA_USER USUARIO WHERE USUARIO.USUARIO = ?";
		PreparedStatement stmt = conn.prepareStatement(SQL);
		stmt.setMaxRows(1);
		stmt.setString(1, usuarioNome.toUpperCase());
		ResultSet result = stmt.executeQuery();
		if(result.next()) {
			return this.createUser(result);
		} else {
			return null;
		}
	}
	
	public boolean isExistUser(String usuario) throws SQLException {
		Connection conn = AppDataConnect.getInstanceERP();
		String SQL = "SELECT 1 FROM BOCA_USER USUARIO WHERE USUARIO.USUARIO = ?";
		PreparedStatement stmt = conn.prepareStatement(SQL);
		stmt.setMaxRows(1);
		stmt.setString(1, usuario.toUpperCase());
		ResultSet res = stmt.executeQuery();
		if(res.next()) {
			stmt.close();
			return true;
		} else {
			stmt.close();
			return false;
		}
	}

	public void gerarUsuario(String usuario) throws Exception {
		CriptUtils cript = new CriptUtils();
		Connection conn = AppDataConnect.getInstanceERP();
		this.setPassword("bg123");
		if (this.isExistUser(usuario)) {
			String SQL = "UPDATE BOCA_USER USUARIO SET USUARIO.SENHA = ? WHERE USUARIO.USUARIO = ? ";
			PreparedStatement stmt = conn.prepareStatement(SQL);
			stmt.setString(1, cript.encripta(this.getPassword()));
			stmt.setString(2, usuario.toUpperCase());
			stmt.executeQuery();
			stmt.close();
		} else {
			String SQL = "INSERT INTO BOCA_USER (USUARIO, SENHA) VALUES (?, ?)";
			PreparedStatement stmt = conn.prepareStatement(SQL);
			stmt.setString(1, usuario.toUpperCase());
			stmt.setString(2, cript.encripta(this.getPassword()));
			stmt.executeQuery();
			stmt.close();
		}
	}

	public User getUsuarioLogin(String userName, String password) throws Exception {
		CriptUtils cript = new CriptUtils();
		User usuario = this.getUser(userName);
		if(usuario != null && usuario.getPassword().equals(cript.encripta(password))) {
			VaadinSession.getCurrent().setAttribute("USER_LOGIN", usuario);
		}
		return usuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public Integer getNewsletterSubscription() {
        return newsletterSubscription;
    }

    public void setNewsletterSubscription(final Integer newsletterSubscription) {
        this.newsletterSubscription = newsletterSubscription;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(final String bio) {
        this.bio = bio;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(final boolean male) {
        this.male = male;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
