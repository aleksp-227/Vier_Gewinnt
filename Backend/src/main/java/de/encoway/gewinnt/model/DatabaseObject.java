package de.encoway.gewinnt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class DatabaseObject {

    @Id
    private String id;

    @Lob
    private byte[] data;

    public DatabaseObject(String id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public DatabaseObject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
