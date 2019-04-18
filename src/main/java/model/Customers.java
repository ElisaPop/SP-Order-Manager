package model;

public class Customers {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;

    public Customers(int id, String name, String surname, String email, String phone)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public Customers(String name, String surname, String email, String phone)
    {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public Customers()
    {
        this.name = "Alvin";
        this.surname = "Filler";
        this.email = "fillerphil@gmail.com";
        this.phone = "0345";
    }

    public int getId ()
    {
        return this.id;
    }
    public void setId (int id)
    {
        this.id = id;
    }
    public String getName ()
    {
        return this.name;
    }
    public void setName (String name)
    {
        this.name = name;
    }
    public String getSurname ()
    {
        return this.surname;
    }
    public void setSurname (String surname)
    {
        this.surname = surname;
    }
    public String getPhone ()
    {
        return this.phone;
    }
    public void setPhone (String phone)
    {
        this.phone= phone;
    }
    public String getEmail ()
    {
        return this.email;
    }
    public void setEmail (String email)
    {
        this.email= email;
    }

}
