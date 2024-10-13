INSERT INTO Liquide (nom_liquide, prixUnitaireAchat, prixUnitaireVente) 
VALUES ('Essence', 1.20, 1.50),
       ('Diesel', 1.00, 1.30),
       ('GPL', 0.80, 1.10),
       ('Kérosène', 1.50, 1.80);

       /* ATAO MITOVY AMLE AO AMBONy */
INSERT INTO STATION.PRODUIT 
    (ID, VAL, DESCE, IDTYPEPRODUIT, PUACHAT, PUVENTE, IDUNITE, IDCATEGORIE, PRESENTATION, SEUILMIN, SEUILMAX, PUACHATUSD, PUACHATEURO, PUVENTEUSD, PUVENTEEURO, ISACHAT, ISVENTE) 
VALUES 
    ('PRD0001', 'Essence', 'Essence', 'TP00002', 1.20, 1.50, 'UNI000007', 'GRP000354', 'Carburant Essence', 0, 1000, 0, 0, 0, 0, 1, 1);

INSERT INTO STATION.PRODUIT 
    (ID, VAL, DESCE, IDTYPEPRODUIT, PUACHAT, PUVENTE, IDUNITE, IDCATEGORIE, PRESENTATION, SEUILMIN, SEUILMAX, PUACHATUSD, PUACHATEURO, PUVENTEUSD, PUVENTEEURO, ISACHAT, ISVENTE) 
VALUES 
    ('PRD0002', 'Diesel', 'Diesel', 'TP00002', 1.00, 1.30, 'UNI000007', 'GRP000354', 'Carburant Diesel', 0, 1000, 0, 0, 0, 0, 1, 1);

INSERT INTO STATION.PRODUIT 
    (ID, VAL, DESCE, IDTYPEPRODUIT, PUACHAT, PUVENTE, IDUNITE, IDCATEGORIE, PRESENTATION, SEUILMIN, SEUILMAX, PUACHATUSD, PUACHATEURO, PUVENTEUSD, PUVENTEEURO, ISACHAT, ISVENTE) 
VALUES 
    ('PRD0003', 'GPL', 'Gaz de Pétrole Liquéfié', 'TP00002', 0.80, 1.10, 'UNI000007', 'GRP000354', 'Carburant GPL', 0, 1000, 0, 0, 0, 0, 1, 1);

INSERT INTO STATION.PRODUIT 
    (ID, VAL, DESCE, IDTYPEPRODUIT, PUACHAT, PUVENTE, IDUNITE, IDCATEGORIE, PRESENTATION, SEUILMIN, SEUILMAX, PUACHATUSD, PUACHATEURO, PUVENTEUSD, PUVENTEEURO, ISACHAT, ISVENTE) 
VALUES 
    ('PRD0004', 'Kérosène', 'Kérosène', 'TP00002', 1.50, 1.80, 'UNI000007', 'GRP000354', 'Carburant Kérosène', 0, 1000, 0, 0, 0, 0, 1, 1);

INSERT INTO Reservoir (qte_max, id_liquide)
VALUES (10000.00, 1),  -- Essence
       (15000.00, 2),  -- Diesel
       (5000.00, 3),   -- GPL
       (8000.00, 4);   -- Kérosène
INSERT INTO Pompe (numero_pompe, qteMax, id_reservoir)
VALUES (101, 5000.00, 1),   -- Associated with Reservoir of Essence
       (102, 7000.00, 2),   -- Associated with Reservoir of Diesel
       (103, 2000.00, 3),   -- Associated with Reservoir of GPL
       (104, 3000.00, 4),   -- Associated with Reservoir of Kérosène
       (105, 4500.00, 1);   -- Another associated with Reservoir of Essence
INSERT INTO Pompiste (nom_pompiste)
VALUES ('Jean Dupont'),
       ('Marie Martin'),
       ('Alain Bernard'),
       ('Sophie Durant'),
       ('Luc Robert');

       -- Insertion de données de test dans la table MesureReservoir
INSERT INTO MesureReservoir (mesureLongueur, mesureVolume, id_reservoir) VALUES
(5.25, 100.50, 1), 
(6.75, 120.30, 1), 
(7.80, 140.75, 1), 
(8.90, 160.20, 1),
(4.50, 90.50, 1);

INSERT INTO MesureReservoir (mesureLongueur, mesureVolume, id_reservoir) VALUES
(1, 100, 1), 
(10, 1000, 1)



