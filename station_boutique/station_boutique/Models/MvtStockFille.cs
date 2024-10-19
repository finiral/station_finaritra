using System.Text.Json.Serialization;

namespace station_boutique.Models;

public class MvtStockFille
{
    private double entree;
    private String idProduit;
    private double sortie;

    [JsonPropertyName("idProduit")]
    public string IdProduit
    {
        get => idProduit;
        set
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentNullException(nameof(value), "IdProduit cannot be null or empty.");
            idProduit = value;
        }
    }
    
    [JsonPropertyName("entree")]
    public double Entree
    {
        get => entree;
        set
        {
            if (value<0)
                throw new ArgumentNullException(nameof(value), "Entree negatif impossible");
            entree = value;
        }
    }
    
    [JsonPropertyName("sortie")]
    public double Sortie
    {
        get => sortie;
        set
        {
            if (value<0)
                throw new ArgumentNullException(nameof(value), "Sortie negatif impossible");
            sortie = value;
        }
    }
    
    public MvtStockFille()
    {
    }
    
    public MvtStockFille(string idProduit, double entree, double sortie)
    {
        this.IdProduit = idProduit;
        this.Entree = entree;
        this.Sortie = sortie;
    }
}