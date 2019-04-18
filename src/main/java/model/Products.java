package model;

public class Products {
    private int id;
    private int compid;
    private String name;
    private int stock;
    private double value;

    public Products(int id, int compid, String name, int stock, double value)
    {
        this.id = id;
        this.compid = compid;
        this.name = name;
        this.stock = stock;
        this.value = value;
    }

    public Products(int compid, String name, int stock, double value)
    {
        this.compid = compid;
        this.name = name;
        this.stock = stock;
        this.value = value;
    }

    public Products()
    {
        this.compid = 1;
        this.name = "ProductTest";
        this.stock = 1;
        this.value = 1;
    }

    public int getId ()
    {
        return this.id;
    }
    public void setId (int id)
    {
        this.id = id;
    }
    public int getCompid ()
    {
        return this.compid;
    }
    public void setCompid (int w)
    {
        this.compid = w;
    }

    public String getName ()
    {
        return this.name;
    }
    public void setName (String name)
    {
        this.name = name;
    }
    public int getStock ()
    {
        return this.stock;
    }
    public void setStock (int stock)
    {
        this.stock= stock;
    }
    public double getValue ()
    {
        return this.value;
    }
    public void setValue (double value)
    {
        this.value= value;
    }

}
