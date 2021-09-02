package com.lti.triplnr20.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="requests")
public class FriendRequest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable = false)
	private int requestId;
	@JsonIgnoreProperties({"trips", "friends"})
	@ManyToOne @JoinColumn(nullable = false)
	private User from;
	@JsonIgnoreProperties({"trips", "friends"})
	@ManyToOne @JoinColumn(nullable = false)
	private User to;

}
