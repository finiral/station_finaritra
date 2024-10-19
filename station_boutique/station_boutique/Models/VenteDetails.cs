using System.Text.Json.Serialization;

namespace station_boutique.Models;

public class VenteDetails
{
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
    public double PuVente
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
    public int Qte
    {
        get => qte;
        set
        {
            if (value < 1)
                throw new ArgumentOutOfRangeException(nameof(value), "Qte must be at least 1.");
            qte = value;
        }
    }

    private String idProduit;
    private double puVente;
    private int qte;
    public VenteDetails()
    {
    }
    
    public VenteDetails(string idProduit, double puVente, int qte)
    {
        this.IdProduit = idProduit;
        this.PuVente = puVente;
        this.Qte = qte;
    }
}