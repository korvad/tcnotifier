package org.edu.reksoft.json;

import java.io.Serializable;

public abstract class AbstractModel implements Serializable {

    private static final long serialVersionUID = 6911943264610551552L;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractModel that = (AbstractModel) o;
        return id.equals(that.id);
    }

    @Override
   public int hashCode() {
        return id.hashCode();
    }
}
