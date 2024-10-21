using System.Text.Json.Serialization;

namespace station_boutique.Models;

public class VenteDetails
{
    [JsonPropertyName("id")]
    public string Id
    {
        get => id;
        set
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentNullException(nameof(value), "Id cannot be null or empty.");
            id = value;
        }
    }
    
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

    [JsonPropertyName("puVente")]
    public decimal PuVente
    {
        get => puVente;
        set
        {
            if (value <= 0)
                throw new ArgumentOutOfRangeException(nameof(value), "PuVente must be greater than 0.");
            puVente = value;
        }
    }

    [JsonPropertyName("qte")]
    public decimal Qte
    {
        get => qte;
        set
        {
            if (value < 1)
                throw new ArgumentOutOfRangeException(nameof(value), "Qte must be at least 1.");
            qte = value;
        }
    }

    private String id;
    private String idProduit;
    private decimal puVente;
    private decimal qte;
    public VenteDetails()
    {
    }
    
    public VenteDetails(string idProduit, decimal puVente, decimal qte)
    {
        this.IdProduit = idProduit;
        this.PuVente = puVente;
        this.Qte = qte;
    }
}