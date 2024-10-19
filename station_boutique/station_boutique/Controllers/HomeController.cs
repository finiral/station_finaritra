using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using station_boutique.Models;
using station_boutique.Services;

namespace station_boutique.Controllers;

public class HomeController : Controller
{
    private readonly ILogger<HomeController> _logger;

    private readonly ProduitService _produitService=new ProduitService(new HttpClient());

    public HomeController(ILogger<HomeController> logger)
    {
        _logger = logger;
    }

    public async Task<IActionResult> Index()
    {
        return RedirectToAction("Vente");
    }

    public async Task<IActionResult> Vente()
    {
        var products = await _produitService.GetProductsAsync();
        return View(products);
    }

    public IActionResult Privacy()
    {
        return View();
    }

    [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
    public IActionResult Error()
    {
        return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
    }
}