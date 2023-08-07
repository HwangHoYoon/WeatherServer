package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;


@Data
@Entity(name = "QnaAnswer")
@Table(name = "QnaAnswer")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QnaAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qnaAnswerId")
    private Integer qnaId;


    /* 유저 식별자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qnaId")
    private Qna qnaTb;


    @Column(name = "title")
    private String title;


    @Column(name = "content")
    private String content;

    @Column(name = "reviewers")
    private String reviewers;


    @Column(name = "regDate")
    private Date regDate;

    @Column(name = "modifyDate")
    private Date modifyDate;
    



}
