CREATE TABLE players (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(50) NOT NULL,
    team VARCHAR(100) NOT NULL
);

INSERT INTO players (name, position, team) VALUES
('Neymar', 'Atacante', 'Santos'),
('Vini Jr', 'Atacante', 'Real Madrid'),
('Casemiro', 'Volante', 'Manchester United');