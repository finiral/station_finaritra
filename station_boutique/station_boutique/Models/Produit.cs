using System.Text.Json.Serialization;

namespace station_boutique.Models;

public class Produit
{
    public Produit(string id, string val, string desce, double puAchat, double puVente)
    {
        this.Id = id;
        this.Val = val;
        this.Desce = desce;
        this.PuAchat = puAchat;
        this.PuVente = puVente;
    }

    [JsonPropertyName("id")]
    public string Id { get; set; }

    [JsonPropertyName("val")]
    public string Val { get; set; }

    [JsonPropertyName("desce")]
    public string Desce { get; set; }

    [JsonPropertyName("puAchat")]
    public double PuAchat { get; set; }

    [JsonPropertyName("puVente")]
    public double PuVente { get; set; }

    public Produit()
    {
    }
    


}