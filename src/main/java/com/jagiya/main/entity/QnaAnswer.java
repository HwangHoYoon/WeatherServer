package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "QNA 답변 VO")
public class QnaAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qnaAnswerId")
    @Schema(description = "qnaAnswer Id")
    private Integer qnaId;


    /* qna 식별자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qnaId")
    @Schema(description = "QNA 식별자")
    private Qna qnaTb;


    @Column(name = "title")
    @Schema(description = "답변제목")
    private String title;


    @Column(name = "content")
    @Schema(description = "답변내용")
    private String content;

    @Column(name = "reviewers")
    @Schema(description = "답변자")
    private String reviewers;


    @Column(name = "regDate")
    @Schema(description = "답변일")
    private Date regDate;

    @Column(name = "modifyDate")
    @Schema(description = "수정일")
    private Date modifyDate;
    



}
