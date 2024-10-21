using System.Text.Json.Serialization;

namespace station_boutique.Models;

public class Client
{
    [JsonPropertyName(("id"))]
    public string Id
    {
        get => id;
        set => id = value ?? throw new ArgumentNullException(nameof(value));
    }

    [JsonPropertyName("nom")]
    public string Nom
    {
        get => nom;
        set => nom = value ?? throw new ArgumentNullException(nameof(value));
    }

    private String id;
    private String nom;

    public Client()
    {
    }

    public Client(string id, string nom)
    {
        this.Id= id;
        this.Nom = nom;
    }
}