package br.com.ims.tool.entity;


import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cesar
 */
@Entity
@Table(name = "decisionchance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Decisionchance.findAll", query = "SELECT d FROM Decisionchance d"),
    @NamedQuery(name = "Decisionchance.findById", query = "SELECT d FROM Decisionchance d WHERE d.id = :id"),
    @NamedQuery(name = "Decisionchance.findByOrdernum", query = "SELECT d FROM Decisionchance d WHERE d.ordernum = :ordernum"),
    @NamedQuery(name = "Decisionchance.findByOperation", query = "SELECT d FROM Decisionchance d WHERE d.operation = :operation"),
    @NamedQuery(name = "Decisionchance.findByValue1", query = "SELECT d FROM Decisionchance d WHERE d.value1 = :value1"),
    @NamedQuery(name = "Decisionchance.findByValue2", query = "SELECT d FROM Decisionchance d WHERE d.value2 = :value2"),
    @NamedQuery(name = "Decisionchance.findByValue3", query = "SELECT d FROM Decisionchance d WHERE d.value3 = :value3"),
    @NamedQuery(name = "Decisionchance.findByValue4", query = "SELECT d FROM Decisionchance d WHERE d.value4 = :value4"),
    @NamedQuery(name = "Decisionchance.findByValue5", query = "SELECT d FROM Decisionchance d WHERE d.value5 = :value5"),
    @NamedQuery(name = "Decisionchance.findByValue6", query = "SELECT d FROM Decisionchance d WHERE d.value6 = :value6"),
    @NamedQuery(name = "Decisionchance.findByValue7", query = "SELECT d FROM Decisionchance d WHERE d.value7 = :value7"),
    @NamedQuery(name = "Decisionchance.findByValue8", query = "SELECT d FROM Decisionchance d WHERE d.value8 = :value8"),
    @NamedQuery(name = "Decisionchance.findByValue9", query = "SELECT d FROM Decisionchance d WHERE d.value9 = :value9"),
    @NamedQuery(name = "Decisionchance.findByValue10", query = "SELECT d FROM Decisionchance d WHERE d.value10 = :value10"),
    @NamedQuery(name = "Decisionchance.findByTag", query = "SELECT d FROM Decisionchance d WHERE d.tag = :tag"),
    @NamedQuery(name = "Decisionchance.findByVersionid", query = "SELECT d FROM Decisionchance d WHERE d.versionid = :versionid")})
public class Decisionchance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "ordernum")
    private Integer ordernum;
    @Size(max = 10)
    @Column(name = "operation")
    private String operation;
    @Size(max = 50)
    @Column(name = "value1")
    private String value1;
    @Size(max = 50)
    @Column(name = "value2")
    private String value2;
    @Size(max = 50)
    @Column(name = "value3")
    private String value3;
    @Size(max = 50)
    @Column(name = "value4")
    private String value4;
    @Size(max = 50)
    @Column(name = "value5")
    private String value5;
    @Size(max = 50)
    @Column(name = "value6")
    private String value6;
    @Size(max = 50)
    @Column(name = "value7")
    private String value7;
    @Size(max = 50)
    @Column(name = "value8")
    private String value8;
    @Size(max = 50)
    @Column(name = "value9")
    private String value9;
    @Size(max = 50)
    @Column(name = "value10")
    private String value10;
    @Column(name = "tag")
    private Integer tag;
    @Column(name = "versionid")
    private Integer versionid;
    @JoinColumn(name = "decisiongroupid", referencedColumnName = "id")
    @ManyToOne
    private Decisiongroup decisiongroupid;
    @JoinColumn(name = "decisiongroupchild", referencedColumnName = "id")
    @ManyToOne
    private Decisiongroup decisiongroupchild;
    @JoinColumn(name = "nextformid", referencedColumnName = "id")
    @ManyToOne
    private Form nextformid;

    public Decisionchance() {
    }

    public Decisionchance(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

    public String getValue5() {
        return value5;
    }

    public void setValue5(String value5) {
        this.value5 = value5;
    }

    public String getValue6() {
        return value6;
    }

    public void setValue6(String value6) {
        this.value6 = value6;
    }

    public String getValue7() {
        return value7;
    }

    public void setValue7(String value7) {
        this.value7 = value7;
    }

    public String getValue8() {
        return value8;
    }

    public void setValue8(String value8) {
        this.value8 = value8;
    }

    public String getValue9() {
        return value9;
    }

    public void setValue9(String value9) {
        this.value9 = value9;
    }

    public String getValue10() {
        return value10;
    }

    public void setValue10(String value10) {
        this.value10 = value10;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    public Decisiongroup getDecisiongroupid() {
        return decisiongroupid;
    }

    public void setDecisiongroupid(Decisiongroup decisiongroupid) {
        this.decisiongroupid = decisiongroupid;
    }

    public Decisiongroup getDecisiongroupchild() {
        return decisiongroupchild;
    }

    public void setDecisiongroupchild(Decisiongroup decisiongroupchild) {
        this.decisiongroupchild = decisiongroupchild;
    }

    public Form getNextformid() {
        return nextformid;
    }

    public void setNextformid(Form nextformid) {
        this.nextformid = nextformid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Decisionchance)) {
            return false;
        }
        Decisionchance other = (Decisionchance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Decisionchance[ id=" + id + " ]";
    }
    
}
