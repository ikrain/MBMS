package cn.krain.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author CC
 * @data 2020/12/15 - 15:00
 */
@Entity
public class Sort {
    private String id;
    private String sort;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sort")
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sort sort1 = (Sort) o;
        return Objects.equals(id, sort1.id) &&
                Objects.equals(sort, sort1.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sort);
    }
}
