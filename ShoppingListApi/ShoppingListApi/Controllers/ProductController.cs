using Microsoft.AspNetCore.Mvc;
using ShoppingListApi.Models;

namespace ShoppingListApi.Controllers;

[ApiController]
[Route("[controller]")]
public class ProductController : ControllerBase
{

    private readonly ILogger<ProductController> _logger;

    public ProductController(ILogger<ProductController> logger)
    {
        _logger = logger;
    }

    [HttpGet(Name = "GetProducts")]
    public IEnumerable<Product> Get()
    {
        return Product.GetAll();
    }

    // GET api/product/2
    [HttpGet("{id}")]
    public Product Get(string id)
    {
        return Product.GetById(id);
    }

    // POST api/product
    [HttpPost]
    public string Post([FromBody] Product value)
    {
        return Product.Add(value);
    }
    
    // PUT api/product/2
    [HttpPut("{id}")]
    public string Put(string id, [FromBody] Product value)
    {
        return Product.Update(id,value);
    }
    
    // Detele api/product/2
    [HttpDelete("{id}")]
    public string Put(string id)
    {
        return Product.Remove(id);
    }
}