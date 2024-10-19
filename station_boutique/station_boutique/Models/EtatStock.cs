using System.Text.Json.Serialization;

namespace station_boutique.Models
{
    public class EtatStock
    {
        [JsonPropertyName("id")]
        public string Id { get; set; }

        [JsonPropertyName("idProduitLib")]
        public string IdProduitLib { get; set; }

        [JsonPropertyName("idTypeProduitLib")]
        public string IdTypeProduitLib { get; set; }

        [JsonPropertyName("idMagasinLib")]
        public string IdMagasinLib { get; set; }

        [JsonPropertyName("quantite")]
        public double Quantite { get; set; }

        [JsonPropertyName("entree")]
        public double Entree { get; set; }

        [JsonPropertyName("sortie")]
        public double Sortie { get; set; }

        [JsonPropertyName("reste")]
        public double Reste { get; set; }

        [JsonPropertyName("puVente")]
        public double PuVente { get; set; }

        // Default constructor
        public EtatStock() { }

        // Constructor using setters
        public EtatStock(string id, string idProduitLib, string idTypeProduitLib, string idMagasinLib, double quantite, double entree, double sortie, double reste, double puVente)
        {
            Id = id;
            IdProduitLib = idProduitLib;
            IdTypeProduitLib = idTypeProduitLib;
            IdMagasinLib = idMagasinLib;
            Quantite = quantite;
            Entree = entree;
            Sortie = sortie;
            Reste = reste;
            PuVente = puVente;
        }
    }
}