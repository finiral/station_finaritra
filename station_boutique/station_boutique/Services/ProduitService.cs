// File: Services/ProduitService.cs
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using station_boutique.Models;

namespace station_boutique.Services
{
    public class ProduitService
    {
        private readonly HttpClient _httpClient;
        private readonly String api_url = "http://localhost:8080/fini_station-war/station_rest" ;
        private readonly String idMagasin="POMP001";
        public ProduitService(HttpClient httpClient)
        {
            _httpClient = httpClient;
        }

        public async Task<List<Produit>> GetProductsAsync()
        {
            var response = await _httpClient.GetAsync(api_url + "/products/boutique");
            response.EnsureSuccessStatusCode();
            var jsonResponse = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<List<Produit>>(jsonResponse);
        }
        
        public async Task<List<EtatStock>> GetStocksAsync()
        {
            var response = await _httpClient.GetAsync(api_url + "/stocks");
            response.EnsureSuccessStatusCode();
            var jsonResponse = await response.Content.ReadAsStringAsync();
            return JsonSerializer.Deserialize<List<EtatStock>>(jsonResponse);
        }
        
        public async Task<Boolean> SendVentes(Vente vente)
        {
            var jsonContent = new StringContent(JsonSerializer.Serialize(vente), Encoding.UTF8, "application/json");
            var response = await _httpClient.PostAsync(api_url + "/ventes", jsonContent);

            if (response.IsSuccessStatusCode)
            {
                return true;
            }

            // Handle error response
            var errorResponse = await response.Content.ReadAsStringAsync();
            throw new HttpRequestException($"Server error: {response.StatusCode}, {errorResponse}");
        }
        
        public async Task<bool> SendStock(List<MvtStockFille> stocks, DateTime dt)
        {
            var timestamp = new DateTimeOffset(dt).ToUnixTimeMilliseconds();
            var jsonContent = new StringContent(JsonSerializer.Serialize(stocks), Encoding.UTF8, "application/json");
            var response = await _httpClient.PostAsync($"{api_url}/stocks?timestamp={timestamp}&idMagasin={idMagasin}", jsonContent);

            if (response.IsSuccessStatusCode)
            {
                return true;
            }
            else
            {
                var errorResponse = await response.Content.ReadAsStringAsync();
                throw new HttpRequestException($"Server error: {response.StatusCode}, {errorResponse}");
            }
        }
        
    }
}