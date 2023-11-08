namespace ShoppingListApi.Models;
using ShoppingListApi.Models;


public class Product
{
    
    public String Id { get; set; }
    public String Name { get; set; }
    public int Qtt { get; set; }
    public Boolean IsChecked { get; set; }

    public Product(string id, string name, int qtt, bool isChecked)
    {
        Id = id;
        Name = name;
        Qtt = qtt;
        IsChecked = isChecked;
    }

    public static string Add(Product product)
    {
        Singleton.GetInstance().listProducts.Add(product);
        return "{ \"status\" :\"ok\" }";
    }

    public static string Update(String id, Product product)
    {
        var products = Singleton.GetInstance().listProducts.Where(product => product.Id == id).ToList();
        
        if (products.Count > 0)
        {
            Singleton.GetInstance().listProducts.Remove(products.First());
            Singleton.GetInstance().listProducts.Add(product);
            return "{ \"status\" :\"ok\" }";
        }
        else
        {
            return "{ \"status\" :\"not ok\" }";
        }

    }
    
    public static string Remove(String id)
    {
        var products = Singleton.GetInstance().listProducts.Where(product => product.Id == id).ToList();
        
        if (products.Count > 0)
        {
            Product p1 = products.First();
            Singleton.GetInstance().listProducts.Remove(p1);
            return "{ \"status\" :\"ok\" }";
        }
        else
        {
            return "{ \"status\" :\"not ok\" }";
        }
    }

    public static List<Product> GetAll()
    {
        return Singleton.GetInstance().listProducts;
    }
    
    public static Product GetById(String id)
    {
        var products = Singleton.GetInstance().listProducts.Where(product => product.Id == id).ToList();

        if (products.Count > 0)
        {
            return products.First();
        }
        else
        {
            return null;
        }
        
    }

}