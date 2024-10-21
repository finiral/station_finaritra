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
    
    [JsonPropertyName("idClient")]
    public string IdClient
    {
        get => idClient;
        set
        {
            if (string.IsNullOrWhiteSpace(value))
                throw new ArgumentNullException(nameof(value), "IdClient cannot be null or empty.");
            idClient = value;
        }
    }
    
    [JsonPropertyName("venteDetails")]
    public List<VenteDetails> VenteDetails
    {
        get => venteDetails;
        set
        {
            if (venteDetails != null)
            {
                foreach (var detail in value)
                {
                    if (detail == null)
                        throw new ArgumentException("VenteDetails contains a null element.", nameof(value));
                }    
            }
            

            venteDetails = value;
        }
    }

    private String id;
    private DateTime dt;
    private String idMagasin;
    private String idClient;
    private List<VenteDetails> venteDetails;
    
    public Vente()
    {
        this.IdMagasin = "POMP001";
    }
    
    public Vente(DateTime dt, string idMagasin,List<VenteDetails> vd)
    {
        this.Dt = dt;
        this.IdMagasin = idMagasin;
        this.VenteDetails = vd;
    }
}