namespace ShoppingListApi.Models;

public sealed class Singleton
{

    public List<Product> listProducts = new List<Product>();

    private Singleton()
    {
        listProducts.Add(new Product("1","Aaa", 3, false));
        listProducts.Add(new Product("2","BBB", 2, false));
        
    }
    private static Singleton _instance;
    public static Singleton GetInstance()
    {
        if (_instance == null)
        {
            _instance = new Singleton();
        }
        return _instance;
    }


}