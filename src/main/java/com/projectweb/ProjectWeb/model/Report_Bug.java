package com.projectweb.ProjectWeb.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "report_bug")
public class Report_Bug {
    @Id
    @Column(name = "ID_REPORT")
    private Integer ID_REPORT;

    @Enumerated(jakarta.persistence.EnumType.ORDINAL)
    @Column(name = "TYPE_BUG")
    private EnumType.Bug_Type TYPE_BUG;

    @Column(name = "SCRIPT_BUG")
    private String SCRIPT_BUG;

    @Column(name = "DATE_REPORT")
    private LocalDateTime DATE_REPORT;

    @Column(name = "ID_USER")
    private String ID_USER;

    @Transient
    private String EMAIL_ACC;

    @ManyToOne
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER", insertable = false, updatable = false)
    private User_Entity userEntity;
}
