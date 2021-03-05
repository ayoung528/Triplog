package com.ssafy.trip.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.ssafy.trip.model.audit.DateAudit;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "nickname"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class MemberUser extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String nickname;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @Size(max = 1000)
    private String intro;
    
    @Size(max = 500)
    private String imagesrc;

    private int mainpost;
    
    private boolean valid;
    
    @Size(max = 100)
    private String chatbotid;
    
//    @ManyToMany(mappedBy = "likeArticle")
//	private
//    List<Article> likes;
//    
//    
//    public List<Article> getLikes() {
//		return likes;
//	}
//
//	public void setLikes(List<Article> likes) {
//		this.likes = likes;
//	}

	

	public MemberUser() {

    }

	public MemberUser(Long num, @NotBlank @Size(max = 40) String name, @NotBlank @Size(max = 15) String nickname,
			@NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String password, Set<Role> roles,
			@Size(max = 1000) String intro, @Size(max = 500) String imageSrc, int mainPost, boolean valid, @Size(max=100) String chatbotId) {
		super();
		this.num = num;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.intro = intro;
		this.imagesrc = imageSrc;
		this.mainpost = mainPost;
		this.valid = valid;
		this.chatbotid = chatbotId;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getImageSrc() {
		return imagesrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imagesrc = imageSrc;
	}

	public int getMainPost() {
		return mainpost;
	}

	public void setMainPost(int mainPost) {
		this.mainpost = mainPost;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getChatbotId() {
		return chatbotid;
	}

	public void setChatbotId(String chatbotId) {
		this.chatbotid = chatbotId;
	}

	@Override
	public String toString() {
		return "MemberUser [num=" + num + ", name=" + name + ", nickname=" + nickname + ", email=" + email
				+ ", password=" + password + ", roles=" + roles + ", intro=" + intro + ", imagesrc=" + imagesrc
				+ ", mainpost=" + mainpost + ", valid=" + valid + ", chatbotid=" + chatbotid + "]";
	}
	
}