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
@Table(name = "conditionvalue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conditionvalue.findAll", query = "SELECT c FROM Conditionvalue c"),
    @NamedQuery(name = "Conditionvalue.findById", query = "SELECT c FROM Conditionvalue c WHERE c.id = :id"),
    @NamedQuery(name = "Conditionvalue.findByOrdernum", query = "SELECT c FROM Conditionvalue c WHERE c.ordernum = :ordernum"),
    @NamedQuery(name = "Conditionvalue.findByOperation", query = "SELECT c FROM Conditionvalue c WHERE c.operation = :operation"),
    @NamedQuery(name = "Conditionvalue.findByValue1", query = "SELECT c FROM Conditionvalue c WHERE c.value1 = :value1"),
    @NamedQuery(name = "Conditionvalue.findByValue2", query = "SELECT c FROM Conditionvalue c WHERE c.value2 = :value2"),
    @NamedQuery(name = "Conditionvalue.findByValue3", query = "SELECT c FROM Conditionvalue c WHERE c.value3 = :value3"),
    @NamedQuery(name = "Conditionvalue.findByValue4", query = "SELECT c FROM Conditionvalue c WHERE c.value4 = :value4"),
    @NamedQuery(name = "Conditionvalue.findByValue5", query = "SELECT c FROM Conditionvalue c WHERE c.value5 = :value5"),
    @NamedQuery(name = "Conditionvalue.findByValue6", query = "SELECT c FROM Conditionvalue c WHERE c.value6 = :value6"),
    @NamedQuery(name = "Conditionvalue.findByValue7", query = "SELECT c FROM Conditionvalue c WHERE c.value7 = :value7"),
    @NamedQuery(name = "Conditionvalue.findByValue8", query = "SELECT c FROM Conditionvalue c WHERE c.value8 = :value8"),
    @NamedQuery(name = "Conditionvalue.findByValue9", query = "SELECT c FROM Conditionvalue c WHERE c.value9 = :value9"),
    @NamedQuery(name = "Conditionvalue.findByValue10", query = "SELECT c FROM Conditionvalue c WHERE c.value10 = :value10"),
    @NamedQuery(name = "Conditionvalue.findByVersionid", query = "SELECT c FROM Conditionvalue c WHERE c.versionid = :versionid"),
    @NamedQuery(name = "Conditionvalue.findByTagtrue", query = "SELECT c FROM Conditionvalue c WHERE c.tagtrue = :tagtrue"),
    @NamedQuery(name = "Conditionvalue.findByTagfalse", query = "SELECT c FROM Conditionvalue c WHERE c.tagfalse = :tagfalse")})
public class Conditionvalue implements Serializable {
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
    @Column(name = "versionid")
    private Integer versionid;
    @Column(name = "tagtrue")
    private Integer tagtrue;
    @Column(name = "tagfalse")
    private Integer tagfalse;
    @JoinColumn(name = "conditiongroupid", referencedColumnName = "id")
    @ManyToOne
    private Conditiongroup conditiongroupid;

    public Conditionvalue() {
    }

    public Conditionvalue(Integer id) {
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

    public Integer getVersionid() {
        return versionid;
    }

    public void setVersionid(Integer versionid) {
        this.versionid = versionid;
    }

    public Integer getTagtrue() {
        return tagtrue;
    }

    public void setTagtrue(Integer tagtrue) {
        this.tagtrue = tagtrue;
    }

    public Integer getTagfalse() {
        return tagfalse;
    }

    public void setTagfalse(Integer tagfalse) {
        this.tagfalse = tagfalse;
    }

    public Conditiongroup getConditiongroupid() {
        return conditiongroupid;
    }

    public void setConditiongroupid(Conditiongroup conditiongroupid) {
        this.conditiongroupid = conditiongroupid;
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
        if (!(object instanceof Conditionvalue)) {
            return false;
        }
        Conditionvalue other = (Conditionvalue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ims.service.Conditionvalue[ id=" + id + " ]";
    }
    
}
