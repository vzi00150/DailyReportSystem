package com.techacademy.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Where;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="employee")
@Where(clause = "delete_flag = 0")
public class Employee {

    /** 主キー。自動生成 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 名前。20桁。*/
    @NotEmpty
    @Column(length = 20)
    private String name;

    /** 削除フラグ */
    private Integer deleteFlag;

    /** 登録日時 */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy="employee", cascade = CascadeType.ALL)
    @Valid
    private Authentication authentication;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
    @Valid
    private List<Report> report;

    /** レコードが削除される前に行なう処理 */
    @PreRemove
    @Transactional
    private void preRemove() {
        // 認証エンティティからuserを切り離す
        if (authentication!=null) {
            authentication.setEmployee(null);
        }
        if (report!=null) {
        //    report.setReport(null);
        }
    }




}
