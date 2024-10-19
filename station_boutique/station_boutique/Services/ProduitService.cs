// File: Services/ProduitService.cs
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;
using station_boutique.Models;

namespace station_boutique.Services
{
    public class ProduitService
    {
        private readonly HttpClient _httpClient;
        private readonly String api_url = "http://localhost:8080/fini_station-war/station_rest" ;
        public ProduitService(HttpClient httpClient)
        {
            _httpClient = httpClient;
        }

        public async Task<List<Produit>> GetProductsAsync()
        {
            var response = await _httpClient.GetAsync(api_url + "/products");
            response.EnsureSuccessStatusCode();
            var jsonResponse = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<List<Produit>>(jsonResponse);
        }
    }
}