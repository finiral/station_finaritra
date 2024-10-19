using System.Text.Json.Serialization;

namespace station_boutique.Models;

public class Vente
{
   
    [JsonPropertyName("daty")]
    public DateTime Dt
    {
        get => dt;
        set
        {
            if (value == default)
                throw new ArgumentException("Dt cannot be the default value.", nameof(value));
            dt = value;
        }
    }

    [JsonPropertyName("idMagasin")]
    public string IdMagasin
    {
        get => idMagasin;
        set
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentNullException(nameof(value), "IdMagasin cannot be null or empty.");
            idMagasin = value;
        }
    }
    
    [JsonPropertyName("venteDetails")]
    public List<VenteDetails> VenteDetails
    {
        get => venteDetails;
        set
        {
            if (value == null || value.Count == 0)
                throw new ArgumentNullException(nameof(value), "VenteDetails cannot be null or empty.");
        
            foreach (var detail in value)
            {
                if (detail == null)
                    throw new ArgumentException("VenteDetails contains a null element.", nameof(value));
            }

            venteDetails = value;
        }
    }

    private DateTime dt;
    private String idMagasin;
    private List<VenteDetails> venteDetails;
    
    public Vente()
    {
    }
    
    public Vente(DateTime dt, string idMagasin,List<VenteDetails> vd)
    {
        this.Dt = dt;
        this.IdMagasin = idMagasin;
        this.VenteDetails = vd;
    }
}