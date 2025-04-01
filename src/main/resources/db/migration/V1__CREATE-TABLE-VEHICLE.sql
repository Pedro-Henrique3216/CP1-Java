CREATE TABLE Vehicles (
     id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
     marca VARCHAR(255) NOT NULL,
     modelo VARCHAR(255) NOT NULL,
     ano INT NOT NULL,
     potencia DECIMAL(8, 2) NOT NULL,
     economia DECIMAL(8, 2) NOT NULL,
     tipo VARCHAR(50) NOT NULL,
     preco DECIMAL(15,2) NOT NULL
);