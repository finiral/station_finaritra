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
    
    public async Task<IActionResult> Stock()
    {
        var products = await _produitService.GetProductsAsync();
        var stocks = await _produitService.GetStocksAsync();

        ViewBag.Products = products;
        ViewBag.Stocks = stocks;

        return View();
    }
    
    [HttpPost]
    public async Task<IActionResult> SendVente(List<string> produitSelect, List<double> unitPrice, List<int> qteInput, DateTime dateInput)
    {
        try
        {
            Vente vente = new Vente();
            vente.Dt = dateInput;
            List<VenteDetails> ventesd = new List<VenteDetails>();
            for (int i = 0; i < produitSelect.Count; i++)
            {

                VenteDetails detail = new VenteDetails
                {
                    IdProduit = produitSelect[i],
                    PuVente = unitPrice[i],
                    Qte = qteInput[i]
                };
                ventesd.Add(detail);
            }
            vente.VenteDetails = ventesd;
            await _produitService.SendVentes(vente);
            return RedirectToAction("Vente");
        }
        catch (Exception e)
        {
            Console.WriteLine(e.ToString());
            return RedirectToAction("Error", new { message = e.Message });

        }
       
    }
    
    [HttpPost]
    public async Task<IActionResult> SendStock(List<string> produitSelect, List<int> qteInput, DateTime dateInput)
    {
        try
        {
            List<MvtStockFille> stocks = new List<MvtStockFille>();
            for (int i = 0; i < produitSelect.Count; i++)
            {
                MvtStockFille stock = new MvtStockFille
                {
                    IdProduit = produitSelect[i],
                    Entree = qteInput[i]
                };
                stocks.Add(stock);
            }
            await _produitService.SendStock(stocks,dateInput);
            return RedirectToAction("Stock");
        }
        catch (Exception e)
        {
            Console.WriteLine(e.ToString());
            return RedirectToAction("Error", new { message = e.Message });
        }
    }

    public IActionResult Privacy()
    {
        return View();
    }

    [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
    public IActionResult Error(string message)
    {
        var errorViewModel = new ErrorViewModel
        {
            RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier,
            Message = message
        };
        return View(errorViewModel);
    }
}