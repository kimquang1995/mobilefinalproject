package mobile.atsm.atsm_wmp_finalproject.Adapter;

/**
 * Created by JinGuang on 12/23/2016.
 */

public class Tag {
    public String id_tag;

    public Tag(String id_tag, String name_tag, String create_date) {
        this.id_tag = id_tag;
        this.name_tag = name_tag;
        this.create_date = create_date;
    }

    public String name_tag;

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getId_tag() {
        return id_tag;
    }

    public void setId_tag(String id_tag) {
        this.id_tag = id_tag;
    }

    public String getName_tag() {
        return name_tag;
    }

    public void setName_tag(String name_tag) {
        this.name_tag = name_tag;
    }

    public String create_date;
}
