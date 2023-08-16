package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;


@Data
@Entity(name = "Qna")
@Table(name = "Qna")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "QNA VO")
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qnaId")
    @Schema(description = "QNA Id")
    private Integer qnaId;


    /* 유저 식별자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersId")
    @Schema(description = "유저 식별자")
    private Users usersTb;



    @Column(name = "title")
    @Schema(description = "제목")
    private String title;


    @Column(name = "content")
    @Schema(description = "내용")
    private String content;


    @Column(name = "regDate")
    @Schema(description = "작성일")
    private Date regDate;

    @Column(name = "modifyDate")
    @Schema(description = "수정일")
    private Date modifyDate;




}
