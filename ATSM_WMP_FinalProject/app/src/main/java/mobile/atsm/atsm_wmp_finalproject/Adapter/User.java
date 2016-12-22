package mobile.atsm.atsm_wmp_finalproject.Adapter;

/**
 * Created by JinGuang on 12/22/2016.
 */
public class User {
    public User(String name, String id,String email) {
        super();
        this.name = name;
        this.id = id;
        this.email= email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    String id;
    String name;
    String email;
    boolean selected;
}
